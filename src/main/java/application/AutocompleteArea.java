package application;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import org.fxmisc.richtext.PopupAlignment;
import org.fxmisc.richtext.StyleSpans;
import org.fxmisc.richtext.StyleSpansBuilder;
import org.fxmisc.richtext.StyledTextArea;
import spellcheck.Autocompleter;
import spellcheck.Spellcheker;
import vocabulary.VocabularyBuilder;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.OptionalInt;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** Implementation of a StyledTextArea with autocompletion functionality.
 * @author Elena Khasanova
 *
 * **/

public class AutocompleteArea extends StyledTextArea<Boolean> {

    // desired number of completions and suggestions returned by autocomplete and spellchecker
    private static final int NUM_COMPLETIONS = 6;
    private static final int NUM_SUGGESTIONS = 6;
    // set up reflection for spelling suggest
    private static final Method hitWord;
    private boolean spellingOn = false;
    private static final Method getCharacterIndex;
    // the StyledAreaView object
    private static Object styledView;

    // indices which contain word, set by getWordAtIndex
    private int startIndex;
    private int endIndex;

    // current autocomplete autocompleteOptions
    private List<String> autocompleteOptions;

    static {
        try {
            hitWord = Class.forName("org.fxmisc.richtext.skin.StyledTextAreaView").getDeclaredMethod("hit", double.class,
                    double.class);
            getCharacterIndex = Class.forName("org.fxmisc.richtext.skin.CharacterHit")
                    .getDeclaredMethod("getCharacterIndex");

        } catch (ClassNotFoundException | NoSuchMethodException ex) {
            throw new RuntimeException(ex);
        }

        hitWord.setAccessible(true);
        getCharacterIndex.setAccessible(true);
    }

    private Autocompleter ac;
    private VocabularyBuilder dictionary;
    private Spellcheker ss;

    // matches case of user typing for autocomplete and spellchecker
    private final boolean matchCase = true;
    // track autocomplete and spelling suggestion states
    private boolean autocompleteOn = false;
    // track if style needs update on plain text change
    private boolean styleNeedUpdate = true;
    // PopUp context menu to display and select entry
    private ContextMenu entriesPopup;

    public AutocompleteArea(Autocompleter ac, Spellcheker ss, VocabularyBuilder dictionary) {
        super(true, (textNode, correct) -> {
            // define boolean Text node style; apply style when the word is incorrect
            if (!correct) {
                textNode.setUnderline(true);
                textNode.setBackgroundFill(Color.LIGHTGREEN);
            }
        });

        // save objects passed in the autocompleter and spellchecker
        this.ac = ac;
        this.ss = ss;
        // dictionary used in spelling suggestions
        this.dictionary = dictionary;

        // SPELLING SUGGESTIONS

        // register mouse click for correcting misspelled words
        this.setOnMouseClicked(e -> {
            if ((e.getButton() == MouseButton.SECONDARY) && spellingOn) {
                // get StyledTextAreaView object
                styledView = getChildrenUnmodifiable().get(0);

                // get character hit on click and its index
                Object chHit = invoke(hitWord, styledView, e.getX(), e.getY());
                OptionalInt opInt = (OptionalInt) invoke(getCharacterIndex, chHit);

                // valid index clicked
                if (opInt.isPresent()) {
                    int index = opInt.getAsInt();

                    // check if index corresponds to misspelled word
                    if (!getStyleOfChar(index)) {
                        String possibleWord = getWordAtIndex(index);
                        showSuggestions(possibleWord, e);
                    }
                }
            }
        });

        // keep track of text changes, update spellcheck if needed
        this.plainTextChanges().subscribe(change -> {
            // apply style to the text
            if (spellingOn && styleNeedUpdate) {
                this.setStyleSpans(0, checkSpelling());
            }
        });

        // AUTOCOMPLETE

        // initialize list and autocompleteOptions menu for autoComplete
        autocompleteOptions = new ArrayList<>();
        entriesPopup = new ContextMenu();
        setPopupWindow(entriesPopup);
        setPopupAlignment(PopupAlignment.CARET_BOTTOM);

        // observe caretPosition property for autocomplete functionality
        this.caretPositionProperty().addListener((obs, oldPosition, newPosition) -> {
            if (autocompleteOn) {
                String prefix = getWordAtIndex(newPosition.intValue());
                showCompletions(prefix);
            }
        });
    }

    /**
     * Gets white space delimited word which contains character at pos in text
     * Sets startIndex and endIndex instance variables.
     *
     * @param pos - index in text area
     * @return word at index
     */

    private String getWordAtIndex(int pos) {
        String text = this.getText().substring(0, pos);

        // keeping track of index
        int index;

        // get first whitespace "behind caret"
        for (index = text.length() - 1; index >= 0 && !Character.isWhitespace(text.charAt(index)); index--) ;

        // get prefix and startIndex of word
        String prefix = text.substring(index + 1);
        startIndex = index + 1;

        // get first whitespace forward from caret
        for (index = pos; index < this.getLength() && !Character.isWhitespace(this.getText().charAt(index)); index++) ;

        String suffix = this.getText().substring(pos, index);
        endIndex = index;

        // replace regex wildcards: literal "." with "\.".
        prefix = prefix.replaceAll("\\.", "\\.");
        suffix = suffix.replaceAll("\\.", "\\.");

        // combine both parts of words
        prefix = prefix + suffix;

        // return current word being typed
        return prefix;
    }

    /**
     * Populate the set of options with the autocompleteOptions passed in
     * @param options - list of autocompleteOptions
     */
    private List<CustomMenuItem> createOptions(List<String> options, boolean[] flags) {
        List<CustomMenuItem> menuItems = new LinkedList<>();
        // If you'd like more entries, modify this line.
        int count = Math.min(options.size(), NUM_SUGGESTIONS);

        // add autocompleteOptions to ContextMenu
        for (int i = 0; i < count; i++) {
            String str = options.get(i);

            // check if need to match case (flags will always be null if matchCase is true)
            // see showSuggestions/Completions
            if (flags != null) {
                str = convertCaseUsingFlags(str, flags);
            }

            final String result = str;

            Label entryLabel = new Label(result);
            CustomMenuItem item = new CustomMenuItem(entryLabel, true);

            // register users choice of word by click
            item.setOnAction(actionEvent -> {
                styleNeedUpdate = false;
                replaceText(startIndex, endIndex, result);
                getWordAtIndex(startIndex);
                setStyle(startIndex, endIndex, true);
                styleNeedUpdate = true;
            });

            menuItems.add(item);
        }

        return menuItems;
    }

    /**
     * Checks spelling of text in text area, builds style spans for a piece of text
     * @return StyleSpans with misspelled words set to style false (!correct)
     */
    private StyleSpans<Boolean> checkSpelling() {
        String text = getText();
        String word;

        // keep track of end of matcher
        int lastEnd = 0;
        StyleSpansBuilder<Boolean> spansBuilder = new StyleSpansBuilder<>();

        // Pattern and Matcher to get whitespace delimited words
        Pattern pattern = Pattern.compile("[\\w'-]+", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(text);

        while (matcher.find()) {
            word = matcher.group();

            // may need to change if handling caps
            boolean styleClass = dictionary.isWord(word);
            spansBuilder.add(true, matcher.start() - lastEnd);
            spansBuilder.add(styleClass, matcher.end() - matcher.start());
            lastEnd = matcher.end();
        }

        // set trailing characters to true
        spansBuilder.add(true, text.length() - lastEnd);

        // maybe finish out end of text with style true
        return spansBuilder.create();

    }

    /**
     * Show suggestions for word in menu at a click
     * @param word  - word to get suggestions for
     * @param click - mouse click for displaying menu
     */
    private void showSuggestions(String word, MouseEvent click) {
        List<String> suggestions = ss.getSuggestions(word, NUM_SUGGESTIONS);

        // boolean array for matching case of use input
        boolean[] caseFlags = null;
        if (matchCase) {
            caseFlags = getCaseFlags(word);
        }

        // references for creating menu items
        Label sLabel;
        CustomMenuItem item;

        entriesPopup.getItems().clear();

        List<CustomMenuItem> menuItems = createOptions(suggestions, caseFlags);

        // check if no suggestions found
        if (suggestions.size() == 0) {
            sLabel = new Label("No suggestions.");
            item = new CustomMenuItem(sLabel, true);
            item.setDisable(true);
            ((LinkedList<CustomMenuItem>) menuItems).addFirst(item);
        }

        // add "misspelled" word to dictionary
        sLabel = new Label("Add to dictionary.");
        item = new CustomMenuItem(sLabel, true);

        // register event to add word
        item.setOnAction(actionEvent -> {
            // add word to both dictionaries
            dictionary.addWord(word);
            ((VocabularyBuilder)ac).addWord(word);
            setStyle(startIndex, endIndex, true);
        });

        menuItems.add(item);

        entriesPopup.getItems().addAll(menuItems);
        entriesPopup.show(getScene().getWindow(), click.getScreenX(), click.getScreenY());

    }

    private void showCompletions(String prefix) {
        // keep track of prefix
        // check if in middle of typing word
        if (!prefix.equals("")) {

            // get completion autocompleteOptions
            autocompleteOptions = (ac).predictCompletions(prefix, NUM_COMPLETIONS);

            // check if autocompleteOptions found
            if (autocompleteOptions.size() > 0) {

                // boolean array for matching case of use input
                boolean[] caseFlags = null;
                if (matchCase) {
                    caseFlags = getCaseFlags(prefix);
                }

                List<CustomMenuItem> menuOptions = createOptions(autocompleteOptions, caseFlags);

                entriesPopup.getItems().clear();
                entriesPopup.getItems().addAll(menuOptions);

                if (!entriesPopup.isShowing()) {
                    entriesPopup.show(getScene().getWindow());
                }
            }
            // no autocompleteOptions for complete
            else {
                entriesPopup.hide();
            }

        }
        // no current prefix
        else {
            entriesPopup.hide();
        }
    }

    public void setSpelling(boolean state) {
        spellingOn = state;

        if (state && getText().length() > 0) {
            this.setStyleSpans(0, checkSpelling());
        }
        // change all text to true/correct style
        else {
            setStyle(0, getText().length(), true);
        }
    }



    public void setAutoComplete(boolean state) {
        autocompleteOn = state;
    }

    /**
     * Returns a boolean array with true in position where word has an uppercase letter
     *
     * @param word
     * @return boolean array for uppercase or null if none
     */
    private boolean[] getCaseFlags(String word) {

        boolean[] flags = new boolean[word.length()];

        // for if should return array or null
        boolean anyUpperCase = false;

        for (int i = 0; i < flags.length; i++) {
            // if isUpperCase
            if (Character.isUpperCase(word.charAt(i))) {
                flags[i] = true;
                anyUpperCase = true;
            } else {
                flags[i] = false;
            }
        }

        if (anyUpperCase) {
            return flags;
        }

        return null;
    }

    /**
     * Converts characters in word passed in which have "true" in the parallel
     * index to flags array
     *
     * @param word
     * @param flags
     * @return string with uppercase in true positions
     */
    private String convertCaseUsingFlags(String word, boolean[] flags) {
        StringBuilder sb = new StringBuilder(word);

        for (int i = 0; i < flags.length && i < word.length(); i++) {
            if (flags[i]) {
                char c = sb.charAt(i);
                sb.setCharAt(i, Character.toUpperCase(c));
            }
        }

        return sb.toString();
    }


    // encapsulate Method.invoke method
    private static Object invoke(Method m, Object obj, Object... args) {
        try {
            return m.invoke(obj, args);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
}


