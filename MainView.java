package application;

import document.VocabularyBuilder;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Separator;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class MainView {

    final TextController ctrl;

    // V-H boxes
    HBox root;
    // create textfield area
    VBox textField;

    ToolBar toolBar;

    //Button
    Button chooserRead;
    Button chooserEssay;
    Button fleschButton;
    Button fleschkincaid;
    Button checkLevel;
    Button showPOS;

    AutocompleteArea text;

    // text
    Text fscore;
    Text fKInterpretation;
    Text levelPercentage;
    Text spellingMistakes;


    MainView() {


        toolBar = new ToolBar();

        chooserRead = new Button();
        fileReadingChooser(chooserRead);
        chooserEssay = new Button();
        fileEssayChooser(chooserEssay);
        fleschButton = new Button();
        fleschkincaid = new Button();
        checkLevel = new Button();
        showPOS = new Button();

        Loader load = new Loader();
        VocabularyBuilder dictionary = load.getDictionary();
        this.text = new AutocompleteArea(load.getAutoComplete(), load.getSpellingSuggest(dictionary), dictionary);
        this.fscore = new Text();
        this.fKInterpretation = new Text();
        this.levelPercentage = new Text();
        this.spellingMistakes = new Text();
        //text.insertText(0,"");

        root = new HBox();
        textField = new VBox();
        ctrl = new TextController(text, fscore, fKInterpretation, levelPercentage, spellingMistakes);
        //handleAutoComplete();
        //handleSpelling();
    }

    public void start(Stage primaryStage) {
        Scene scene = new Scene(root, 300, 250);
        primaryStage.setTitle("Teacher's Editor");
        primaryStage.setScene(scene);
        toolbar();
        textArea();
        analysisArea();
        primaryStage.show();
    }

    private void toolbar() {
// create a file chooser for reading text

        toolBar.setOrientation(Orientation.VERTICAL);
        chooserRead.setText("Open Text...");
        chooserRead.setMinWidth(100);
        toolBar.getItems().add(chooserRead);
        toolBar.getItems().add(new Separator());

        // create a filechooser for an essay
        chooserEssay.setText("Open Essay...");
        chooserEssay.setMinWidth(100);
        toolBar.getItems().add(chooserEssay);
        toolBar.getItems().add(new Separator());

        // create a Flesch score button

        fleschButton.setText("Flesch");
        fleschButton.setMinWidth(100);
        toolBar.getItems().add(fleschButton);
        toolBar.getItems().add(new Separator());


        // create a FleschKincaid button
        fleschkincaid.setText("FleschKincaid");
        fleschkincaid.setMinWidth(100);
        toolBar.getItems().add(fleschkincaid);
        toolBar.getItems().add(new Separator());


        // create a button to check the level
        checkLevel.setText("Check the level");
        checkLevel.setMinWidth(100);

        // create a button to show POS
        toolBar.getItems().add(showPOS);

        showPOS.setText("Show POS");
        showPOS.setMinWidth(100);
        toolBar.getItems().add(new Separator());

        checkBoxes();
        VBox buttonField = new VBox(toolBar);
        root.getChildren().add(buttonField);


        // create area for displaying information under the text area

    }

    private void checkBoxes() {
        // create a checkbox for levels
        final CheckBox cbKET = new CheckBox("KET");
        //cbKET.setIndeterminate(true);
        // boolean isSelected = cbKET.isSelected();
        toolBar.getItems().add(cbKET);
        final CheckBox cbPET = new CheckBox("PET");
        toolBar.getItems().add(cbPET);
        final CheckBox cbStarters = new CheckBox("Starters");
        toolBar.getItems().add(cbStarters);
        final CheckBox cbMovers = new CheckBox("Movers");
        toolBar.getItems().add(cbMovers);
        final CheckBox cbFlyers = new CheckBox("Flyers");
        toolBar.getItems().add(cbFlyers);
        final CheckBox cbFCE = new CheckBox("FCE");
        toolBar.getItems().add(cbFCE);
        final CheckBox cbIELTS = new CheckBox("IELTS");
        toolBar.getItems().add(cbIELTS);
        final CheckBox cbTOEFL = new CheckBox("TOEFL");
        toolBar.getItems().add(cbTOEFL);
        toolBar.getItems().add(new Separator());
        toolBar.getItems().add(checkLevel);
        toolBar.getItems().add(new Separator());
        final CheckBox autocompleteBox = new CheckBox("Autocomplete");
        toolBar.getItems().add(autocompleteBox);
        autocompleteBox.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ctrl.handleAutoComplete(autocompleteBox);
            }
        });
        // set actions for checkLevel button:
        checkLevel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ctrl.handleCheckboxes(cbKET, cbPET, cbStarters, cbMovers, cbFlyers);
            }
        });

        final CheckBox spellingBox = new CheckBox("Spelling");
        toolBar.getItems().add(spellingBox);
        spellingBox.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ctrl.handleSpelling(spellingBox);
            }
        });

    }

    private void fileReadingChooser(Button chooserRead) {
        chooserRead.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {

                try {
                    ctrl.loadReadingText();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void fileEssayChooser(Button chooserEssay) {
        chooserEssay.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {

                try {
                    ctrl.loadEssay();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    // Text area that deals with the file
    private void textArea() {
        // create a text area for results
        // create the general layout

        root.getChildren().add(textField);
        textField.getChildren().add(text);
        text.setWrapText(true);
    }


    private void analysisArea() {
        HBox fleschField = new HBox();
        textField.getChildren().add(fleschField);
        textField.getChildren().add(fscore);
        textField.getChildren().add(fKInterpretation);
        textField.getChildren().add(levelPercentage);
        textField.getChildren().add(spellingMistakes);

        // set actions for buttons

        fleschButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ctrl.getFlesch();
            }
        });
        fleschkincaid.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ctrl.fleschKincaid();
            }
        });
//        showPOS.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event) {
//                StanfordPOS sfp = new StanfordPOS();
//                HashMap<String, String> tagged;
//                try {
//                    tagged = sfp.simplifiedTags(text.getText());
//                    text.setStyleSpans(0, stylePOS());
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
    }
//
//
//
//    public StyleSpans<Boolean> stylePOS() throws IOException {
//
//        String content = text.getText();
//        String word;
//
//        // keep track of end of matcher
//        int lastEnd = 0;
//        StyleSpansBuilder<Boolean> spansBuilder = new StyleSpansBuilder<>();
//
//        // Pattern and Matcher to get whitespace delimited words
//        Pattern pattern = Pattern.compile("[\\w'-]+", Pattern.CASE_INSENSITIVE);
//        Matcher matcher = pattern.matcher(content);
//        StanfordPOS sfp = new StanfordPOS();
//        HashMap<String, String> tagged;
//        tagged = sfp.simplifiedTags(text.getText());
//        String styleClass = "-fx-fill: red;";
//
//
//        while (matcher.find()) {
//            word = matcher.group();
//            // may need to change if handling caps
//            //boolean style = tagged.containsKey(word);
//            boolean style = tagged.containsKey(word);
//            spansBuilder.add(style, matcher.start() - lastEnd);
//            spansBuilder.add(Boolean.valueOf(styleClass), matcher.end() - matcher.start());
//            lastEnd = matcher.end();
//        }
//
//        // set trailing characters to true
//        spansBuilder.add(Boolean.valueOf(styleClass), text.getText().length() - lastEnd);
//
//        // maybe finish out end of text with style true
//        return spansBuilder.create();
//    }
}

// save button
// disappearing information when click Hide
// autocomplete
// spelling
/*#TODO: save; unit tests; hide text if unchecked;*/
