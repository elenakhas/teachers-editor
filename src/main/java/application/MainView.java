package application;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Separator;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import vocabulary.VocabularyBuilder;

import java.io.IOException;

class MainView {

    private final TextController ctrl;

    // V-H boxes
    private BorderPane root;
    // create textfield area
    private GridPane gridPane;
    //VBox

    private ToolBar toolBar;

    //Button
    private Button chooserRead;
    private Button chooserEssay;
    private Button fleschButton;
    private Button fleschkincaid;
    private Button checkLevel;
    private Button showStatistics;
    private Button showProperties;


    private AutocompleteArea text;

    // text
    private Text fscore;
    private Text fKInterpretation;
    private Text levelPercentage;
    private Text unknownWords;
    private Text wordcount;
    private Text filename;
    private Text sentenceNumber;
    private Text frequentWords;
    private Text uniqueWords;


    MainView() {
        toolBar = new ToolBar();
        chooserRead = new Button();
        fileReadingChooser(chooserRead);
        chooserEssay = new Button();
        fileEssayChooser(chooserEssay);
        fleschButton = new Button();
        fleschkincaid = new Button();
        checkLevel = new Button();
        showStatistics = new Button();
        showProperties = new Button();

        Loader load = new Loader();
        VocabularyBuilder dictionary = load.getDictionary();
        this.text = new AutocompleteArea(load.getAutoComplete(), load.getSpellingSuggest(dictionary), dictionary);
        this.fscore = new Text();
        this.fKInterpretation = new Text();
        this.levelPercentage = new Text();
        this.unknownWords = new Text();
        this.wordcount = new Text();
        this.sentenceNumber = new Text();
        this.frequentWords = new Text();
        this.uniqueWords = new Text();

        root = new BorderPane();
        VBox box = new VBox();
        box.setPrefWidth(20);
        root.setRight(box);

        ctrl = new TextController(text, fscore, fKInterpretation, levelPercentage, unknownWords, wordcount,
                sentenceNumber,frequentWords, uniqueWords);
        filename = new Text();
        filename.setFont((Font.font("Verdana", 14)));
        filename.setFill(Color.DARKBLUE);
        root.setTop(filename);

    }

    public void start(Stage primaryStage) {
        Scene scene = new Scene(root, 400, 500);
        primaryStage.setTitle("Teacher's Editor");
        primaryStage.setScene(scene);
        toolbar();
        textArea();
        analysisArea();
        primaryStage.sizeToScene();
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
        toolBar.getItems().add(showStatistics);

        showStatistics.setText("Show statistics");
        showStatistics.setMinWidth(100);
        toolBar.getItems().add(new Separator());

        toolBar.getItems().add(showProperties);
        showProperties.setText("Show properties");
        showStatistics.setMinWidth(100);
        toolBar.getItems().add(new Separator());

        levelCheckBoxes();

    }

    private void levelCheckBoxes() {
        root.setLeft(toolBar);
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
        final CheckBox spellingBox = new CheckBox("Spelling");
        toolBar.getItems().add(spellingBox);

        // checkbox handlers
        autocompleteBox.setOnAction(event -> ctrl.handleAutoComplete(autocompleteBox));

        // set actions for checkLevel button:
        checkLevel.setOnAction(event -> ctrl.handleCheckboxes(cbKET, cbPET, cbStarters, cbMovers, cbFlyers, cbFCE, cbTOEFL));

        spellingBox.setOnAction(event -> ctrl.handleSpelling(spellingBox));

    }

    private void fileReadingChooser(Button chooserRead) {
        chooserRead.setOnAction(event -> {

            try {
                ctrl.loadReadingText();
                filename.setText(ctrl.getFilename());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void fileEssayChooser(Button chooserEssay) {
        chooserEssay.setOnAction(event -> {

            try {
                ctrl.loadEssay();
                filename.setText(ctrl.getFilename());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    // Text area that deals with the file
    private void textArea() {
        // create a text area for results
        // create the general layout

        //textField = new VBox(10);
        //root.setMargin(textField, new Insets(20, 20, 20, 20));
        root.setCenter(text);
        text.setPrefSize(570, 492);
        text.setStyle("-fx-font-size: 13px");
        VBox.setVgrow(text, Priority.ALWAYS);
        gridPane = new GridPane();
        root.setBottom(gridPane);
        //root.getChildren().add(textField);
        //textField.getChildren().add(text);
        //textField.setMargin(text, new Insets(10,10,10,10));
        //text.setPrefSize(root.getLayoutBounds().getWidth(), root.getLayoutBounds().getHeight());
        //textField.getChildren().add(gridPane);
        text.setWrapText(true);
    }


    private void analysisArea() {

        //Setting size for the pane
        //gridPane.setPrefSize(150, 200);
        //Setting the padding
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        //Setting the vertical and horizontal gaps between the columns
        gridPane.setVgap(10);
        gridPane.setHgap(10);
        //Setting the Grid alignment
        gridPane.setAlignment(Pos.TOP_LEFT);
        Text title = new Text();
        title.setFont(Font.font("Verdana", 14));
        title.setUnderline(true);
        title.setText("BASIC TEXT ANALYSIS");
        Text flesch = new Text();
        flesch.setText("Flesch readability score: ");
        Text fkinkaid = new Text();
        fkinkaid.setText("Flesch-Kincaid readability level: ");
        Text level = new Text();
        level.setText("Percentage of words of this level: ");
        Text spelling = new Text();
        spelling.setText("Unknown or mistaken words: ");
        Text words = new Text();
        words.setText("Number of words: ");
        Text unique = new Text();
        unique.setText("Number of unique words: ");
        Text sentences = new Text();
        sentences.setText("Number of sentences: ");
        Text frequentwords = new Text();
        frequentwords.setText("Most frequent words: ");

        gridPane.addRow(0,title);
        //gridPane.add(flesch, 1,1);
        gridPane.addRow(1, words, wordcount);
        gridPane.addRow(2, flesch, fscore);
        gridPane.addRow(3, fkinkaid, fKInterpretation);
        gridPane.addRow(4, level, levelPercentage);
        gridPane.addRow(5, spelling, unknownWords);
        gridPane.addRow(6, unique, uniqueWords);
        gridPane.addRow(7, sentences, sentenceNumber);
        gridPane.addRow(8, frequentwords, frequentWords);


//        gridPane.add(fscore, 1, 2);
//        gridPane.add(fKInterpretation, 1,3);
//        gridPane.add(level, 0, 5);
        //gridPane.add(levelPercentage, 1,5);
        //gridPane.add(countUnknownWords, 1,5);



        // set actions for buttons

        fleschButton.setOnAction(event -> ctrl.getFlesch());
        fleschkincaid.setOnAction(event -> ctrl.fleschKincaid());

        showProperties.setOnAction(event -> {
            ctrl.getWordcount();
            ctrl.getUniqueWords();
            ctrl.getTopTenWords();
            ctrl.getSentenceCount();
        });
        showStatistics.setOnAction(event -> {
            TextEvalView tv = new TextEvalView(ctrl);
            try {
                tv.showNewWindow();
            }
            catch (IOException e){
            }
        });
    }
//
//
////
//    public  void stylePOS() throws IOException {
////
//        String content = text.getText();
//        String word;
//        Text wordInText = null;
////
////        // keep track of end of matcher
////        int lastEnd = 0;
////        StyleSpansBuilder<Boolean> spansBuilder = new StyleSpansBuilder<>();
////
////        // Pattern and Matcher to get whitespace delimited words
//        Pattern pattern = Pattern.compile("[\\w'-]+", Pattern.CASE_INSENSITIVE);
//        Matcher matcher = pattern.matcher(content);
//        StanfordPOS sfp = new StanfordPOS();
//        HashMap<String, String> tagged;
//        tagged = sfp.simplifiedTags(text.getText());
////        String styleClass = "-fx-fill: red;";
////
////
//        while (matcher.find()) {
//           word = matcher.group();
//           int start = matcher.start();
//           int end = matcher.end();
////            // may need to change if handling caps
//           if(tagged.containsKey(word)){
//               text.selectRange(start, end);
//           }
//            wordInText = text.getText(start, end);
//           }
//        }
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
/*#TODO: save; unit tests;*/
