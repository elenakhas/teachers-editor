package application;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Separator;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import vocabulary.VocabularyBuilder;

import java.io.IOException;

/**
 *  Main view of the graphic user interface containing the Autospelling Text Area
 *
 * @author Elena Khasanova
 * @version 1.2;
 */


class MainTextView {

    // text controller containing basic functionality for a text
    private final MainTextController ctrl;

    // BorderPane layout for main view
    private BorderPane root;

    // GridPanea for output of the functions called from the main view
    private GridPane gridPaneLeft;
    private GridPane gridPaneRight;

    // setToolbar with buttons and ckeckboxes
    private ToolBar toolBar;

    //Buttons
    private Button chooserRead;
    private Button chooserEssay;
    private Button fleschButton;
    private Button fleschkincaid;
    private Button checkLevel;
    private Button showStatistics;
    private Button showProperties;

    // Text Area
    private AutocompleteArea text;

    // Name of the opened file
    private Text filename;

    // Text displayed on the GridPane upon the method called by clicking on the buttons
    // Left
    private Text fscore;
    private Text fKInterpretation;
    private Text levelPercentage;
    private Text unknownWords;
    //Right
    private Text wordcount;
    private Text sentenceNumber;
    private Text frequentWords;
    private Text uniqueWords;


    MainTextView() {
        // create the setToolbar and the buttons it contains
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

        // load the reference dictionaries, autocomplete and spelling suggest objects
        Loader load = new Loader();
        VocabularyBuilder dictionary = load.getDictionary();
        this.text = new AutocompleteArea(load.getAutoComplete(), load.getSuggestionsSpelling(dictionary), dictionary);

        // create Text objects to display the results of method called by clicking on the buttons
        this.fscore = new Text();
        this.fKInterpretation = new Text();
        this.levelPercentage = new Text();
        this.unknownWords = new Text();
        this.wordcount = new Text();
        this.sentenceNumber = new Text();
        this.frequentWords = new Text();
        this.uniqueWords = new Text();

        // set the properties of the main view layouts
        root = new BorderPane();
        VBox rightBorder = new VBox();
        rightBorder.setPrefWidth(20);
        root.setRight(rightBorder);

        // instantiate the MainTextController and its fields containing properties of the text to be displayed
        ctrl = new MainTextController(text, fscore, fKInterpretation, levelPercentage, unknownWords, wordcount,
                sentenceNumber,frequentWords, uniqueWords);

        // create a text object to display the filename
        filename = new Text();
        filename.setFont((Font.font("Verdana", 14)));
        filename.setFill(Color.DARKBLUE);
        root.setTop(filename);

    }

    /** Starts the view **/
    public void start(Stage primaryStage) {
        Scene scene = new Scene(root, 400, 500);
        primaryStage.setTitle("Teacher's Editor");
        primaryStage.setScene(scene);
        setToolbar();
        textArea();
        mainStatisticsArea();
        primaryStage.sizeToScene();
        primaryStage.show();

    }

    /** Sets the properties of the toolbar**/
    private void setToolbar() {

        // create a filechooser button for a text
        toolBar.setOrientation(Orientation.VERTICAL);
        chooserRead.setText("Open Text...");
        chooserRead.setMinWidth(100);
        toolBar.getItems().add(chooserRead);
        toolBar.getItems().add(new Separator());

        // Create a filechooser button for an essay
        chooserEssay.setText("Open Essay...");
        chooserEssay.setMinWidth(100);
        toolBar.getItems().add(chooserEssay);
        toolBar.getItems().add(new Separator());

        // create a Flesch score button
        fleschButton.setText("Flesch");
        fleschButton.setMinWidth(100);
        toolBar.getItems().add(fleschButton);
        toolBar.getItems().add(new Separator());


        // create a FleschKincaid Score button
        fleschkincaid.setText("FleschKincaid");
        fleschkincaid.setMinWidth(100);
        toolBar.getItems().add(fleschkincaid);
        toolBar.getItems().add(new Separator());


        // Create a button to check the level of vocabulary in the text
        checkLevel.setText("Check the level");
        checkLevel.setMinWidth(100);

        // Create a button to call TextStatistics controller methods
        toolBar.getItems().add(showStatistics);
        showStatistics.setText("Show statistics");
        showStatistics.setMinWidth(100);
        toolBar.getItems().add(new Separator());

        // Create a button to show the basic statistics in this view
        toolBar.getItems().add(showProperties);
        showProperties.setText("Show properties");
        showStatistics.setMinWidth(100);
        toolBar.getItems().add(new Separator());

        // add the checkboxes (for English exams)
        levelCheckBoxes();

    }

    /** Create English exams level checkboxes and their functionality and place them in the toolbar**/

    private void levelCheckBoxes() {

        root.setLeft(toolBar);
        final CheckBox cbKET = new CheckBox("KET");
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

        // set action for the autocomplete checkbox
        autocompleteBox.setOnAction(event -> ctrl.handleAutoComplete(autocompleteBox));

        // set action for the spelling checkbox
        spellingBox.setOnAction(event -> ctrl.handleSpelling(spellingBox));

        // set actions for checkLevel button:
        checkLevel.setOnAction(event -> ctrl.handleCheckboxes(cbKET, cbPET, cbStarters, cbMovers, cbFlyers, cbFCE, cbTOEFL));
    }

    /** Sets events for the reading text file chooser**/

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

    /** Sets events for the essay file chooser**/
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

    /** Set properties of the text area to display the content of the file**/

    private void textArea() {
        root.setCenter(text);
        text.setPrefSize(570, 492);
        text.setStyle("-fx-font-size: 13px");
        VBox.setVgrow(text, Priority.ALWAYS);
        gridPaneLeft = new GridPane();
        gridPaneRight = new GridPane();

        VBox vbox = new VBox();
        Text title = new Text();
        title.setFont(Font.font("Verdana", 14));
        title.setUnderline(true);
        title.setText("BASIC TEXT ANALYSIS");
        vbox.getChildren().add(title);

        HBox gridarea = new HBox();
        vbox.getChildren().add(gridarea);
        gridarea.getChildren().addAll(gridPaneLeft, gridPaneRight);
        root.setBottom(vbox);
        text.setWrapText(true);
    }

    /** Sets the properties of the area to display results of the main text statistics ("Get Properties" call) **/
    private void mainStatisticsArea() {

        gridPaneLeft.setPadding(new Insets(10, 10, 10, 10));
        gridPaneLeft.setVgap(10);
        gridPaneLeft.setHgap(10);
        gridPaneLeft.setAlignment(Pos.TOP_LEFT);

        gridPaneRight.setPadding(new Insets(10, 10, 10, 10));
        gridPaneRight.setVgap(10);
        gridPaneRight.setHgap(10);
        gridPaneRight.setAlignment(Pos.TOP_LEFT);



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


        gridPaneLeft.addRow(1, words, wordcount);
        gridPaneLeft.addRow(2, flesch, fscore);
        gridPaneLeft.addRow(3, fkinkaid, fKInterpretation);
        gridPaneLeft.addRow(4, level, levelPercentage);

        gridPaneRight.addRow(1, spelling, unknownWords);
        gridPaneRight.addRow(2, unique, uniqueWords);
        gridPaneRight.addRow(3, sentences, sentenceNumber);
        gridPaneRight.addRow(4, frequentwords, frequentWords);

        // set actions for buttons related to the main area
        fleschButton.setOnAction(event -> ctrl.getFlesch());
        fleschkincaid.setOnAction(event -> ctrl.fleschKincaid());

        showProperties.setOnAction(event -> {
            ctrl.setWordcount();
            ctrl.setUniqueWords();
            ctrl.setTopTenWords();
            ctrl.setSentenceCount();
        });

        // set actions for "Show statistics" button: it opens a new window - TextStatisticsView and calls TextStatisticsController
        showStatistics.setOnAction(event -> {
            TextStatisticsView tv = new TextStatisticsView(ctrl);
            try {
                tv.showNewWindow();
            }
            catch (IOException e){
            }
        });
    }

}

// save button
/*#TODO: save; unit tests;*/
