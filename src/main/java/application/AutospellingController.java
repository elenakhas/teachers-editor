package application;

import document.Vocabulary;
import javafx.scene.control.ContextMenu;
import spellcheck.AutocompleteOptions;
import spellcheck.SuggestionsSpelling;

import java.util.List;

public class AutospellingController {


    private boolean autoCompleteOn = false;
    private boolean spellingOn = false;
    private boolean matchCase = true;

    // indices which contain word, set by getWordAtIndex
    private int startIndex;
    private int endIndex;

    // current autocomplete options
    private List<String> options;

    // popup to display/select entry
    private ContextMenu entriesPopup;

    private AutocompleteOptions ac;
    private Vocabulary voc;
    private SuggestionsSpelling ss;


}