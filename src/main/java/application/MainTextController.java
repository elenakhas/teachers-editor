package application;

import document.AbstractDocument;
import document.Essay;
import document.FileContent;
import document.ReadingMaterial;
import javafx.scene.control.CheckBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Functionality applied to MainView
 * @author Elena Khasanova
 * @version 1.0;
 * */

class MainTextController {

    // the area containing the file content
    private AutocompleteArea text;
    // instance of the text document
    private AbstractDocument txt;

    // text objects to set output of method calls to be shown in MainTextView
    private Text score;
    private Text fKInterpretation;
    private Text levelPercentage;
    private Text unknownWords;
    private Text wordcount;
    private Text sentenceNumber;
    private Text frequentWords;
    private Text uniqueWords;

    // name of the file opened
    private String fileName;


    public MainTextController(AutocompleteArea text, Text score, Text fKInterpretation, Text levelPercentage, Text unknownWords,
                              Text wordcount, Text sentenceNumber, Text frequentWords, Text uniqueWords) {
        this.text = text;
        text.setEditable(true);
        this.score = score;
        this.fKInterpretation = fKInterpretation;
        this.levelPercentage = levelPercentage;
        this.unknownWords = unknownWords;
        this.wordcount = wordcount;
        this.sentenceNumber = sentenceNumber;
        this.frequentWords = frequentWords;
        this.uniqueWords = uniqueWords;
    }

/** Clears the text area and the MainStatisticsArea**/
    private void clearing(){
        text.clear();
        score.setText("");
        fKInterpretation.setText("");
        levelPercentage.setText("");
        unknownWords.setText("");
        wordcount.setText("");
        sentenceNumber.setText("");
        frequentWords.setText("");
        uniqueWords.setText("");
    }

    /** Loads the ReadingMaterial text and assigns it to text area if the text is selected**/

    public void loadReadingText() throws IOException {
        FileChooser fc = new FileChooser();
        File selected = fc.showOpenDialog(null);
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("TXT", "*.txt"));
        if (selected != null) {
            clearing();
            String filename = selected.getAbsolutePath();
            fileName = selected.getPath();
            FileContent fcon = new FileContent(filename);
            txt = new ReadingMaterial(fcon.getContent());
            String content = txt.getContent();
            text.insertText(0, content);
        }
    }

    /** Loads the Essay text and assigns it to text area if the text is selected**/
    public void loadEssay() throws IOException {
        FileChooser fc = new FileChooser();
        File selected = fc.showOpenDialog(null);
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("TXT", "*.txt"));
        if (selected != null) {
            clearing();
            fileName = selected.getPath();
            String filename = selected.getAbsolutePath();
            FileContent fcon = new FileContent(filename);
            txt = new Essay(fcon.getContent());
            String content = txt.getContent();
            text.insertText(0, content);
        }
    }

    /** Getter for the AbstractDocument object**/
    public AbstractDocument getDoc(){
        return txt;
    }

    /** Sets Flesch score for the document and its interpretation to a respective text object**/
    public void getFlesch() {
        float fscore = txt.calcFleschScore();
        score.setText(String.format("%.2f", fscore) + " " + txt.interpretFlesch(fscore));
    }

    /** Sets FleschKincaid score for the document and its interpretation to a respective text object**/
    public void fleschKincaid() {
        String fsk = txt.interpretFleshKincaid(txt.calcFleschKincaid());
        fKInterpretation.setText(fsk);
    }

    /** Sets the number of spelling mistakes to a respective text object**/
    private void setUnknownWords() {
        Loader loader = new Loader();
        String spm = String.valueOf(txt.countUnknownWords(txt.getWords(), loader.getDictionary()));
        unknownWords.setText(spm);
    }

    /** Gets the percentage of words of KET level for the document and its interpretation **/
    private String percentageKet() {
        Loader loader = new Loader();
        txt.wordsOfLevel(txt.getWords(), loader.getKetVocab());
        String percentage = (String.format("%.2f", txt.percentWordsOfLevel()));
        String levelPercentage = percentage + "% of KET words";
        String unique = String.format("%.2f", txt.uniqueWordsOfALevel());
        return levelPercentage + ";  unique words: " + unique;
    }

    /** Gets the percentage of words of PET level for the document and its interpretation **/
    private String percentagePet() {
        Loader loader = new Loader();
        txt.wordsOfLevel(txt.getWords(), loader.getPetVocab());
        String percentage = (String.format("%.2f", txt.percentWordsOfLevel()));
        String levelPercentage = percentage + "% of PET words";
        String unique = String.format("%.2f", txt.uniqueWordsOfALevel());
        return levelPercentage + ", unique words: " + unique;
    }

    /** Gets the percentage of words of Starters level for the document and its interpretation **/
    private String percentageStarters() {
        Loader loader = new Loader();
        txt.wordsOfLevel(txt.getWords(), loader.getStartersVocab());
        String percentage = (String.format("%.2f", txt.percentWordsOfLevel()));
        String levelPercentage = percentage + "% of Starters words";
        String unique = String.format("%.2f", txt.uniqueWordsOfALevel());
        return levelPercentage + ", unique words: " + unique;
    }

    /** Gets the percentage of words of Movers level for the document and its interpretation **/
    private String percentageMovers() {
        Loader loader = new Loader();
        txt.wordsOfLevel(txt.getWords(), loader.getMoversVocab());
        String percentage = (String.format("%.2f", txt.percentWordsOfLevel()));
        String levelPercentage = percentage + "% of Movers words";
        String unique = String.format("%.2f", txt.uniqueWordsOfALevel());
        return levelPercentage + ", unique words: " + unique;
    }

    /** Gets the percentage of words of Flyers level for the document and its interpretation **/
    private String percentageFlyers() {
        Loader loader = new Loader();
        txt.wordsOfLevel(txt.getWords(), loader.getFlyersVocab());
        String percentage = (String.format("%.2f", txt.percentWordsOfLevel()));
        String levelPercentage = percentage + "% of Flyers words";
        String unique = String.format("%.2f", txt.uniqueWordsOfALevel());
        return levelPercentage + ", unique words: " + unique;
    }

    /** Gets the percentage of words of FCE level for the document and its interpretation **/
    private String percentageFCE() {
        Loader loader = new Loader();
        txt.wordsOfLevel(txt.getWords(), loader.getFCEVocab());
        String percentage = (String.format("%.2f", txt.percentWordsOfLevel()));
        String levelPercentage = percentage + "% of FCE words";
        String unique = String.format("%.2f", txt.uniqueWordsOfALevel());
        return levelPercentage + ", unique words: " + unique;
    }

    /** Gets the percentage of words of TOEFL level for the document and its interpretation **/
    private String percentageTOEFL() {
        Loader loader = new Loader();
        txt.wordsOfLevel(txt.getWords(), loader.getTOEFLvocab());
        String percentage = (String.format("%.2f", txt.percentWordsOfLevel()));
        String levelPercentage = percentage + "% of TOEFL words";
        String unique = String.format("%.2f", txt.uniqueWordsOfALevel());
        return levelPercentage + ", unique words: " + unique;
    }

    /** Sets the number of words to the respective text object **/
    public void setWordcount() {
        wordcount.setText(String.valueOf(txt.getNumWords()));
    }

    /** Sets the number of sentences to the respective text object **/
    public void setSentenceCount(){
        sentenceNumber.setText(String.valueOf(txt.getNumSentences()));
    }

    /** Sets the number of unique words to the respective text object **/
    public void setUniqueWords(){
        uniqueWords.setText(String.valueOf(txt.wordsVariety()));
    }

    /** Sets the TopTenWords to the respective text object **/
    public void setTopTenWords(){

        for (String key : txt.mostFrequentWords().keySet()){
            String message = key + " : " + txt.mostFrequentWords().get(key);
            frequentWords.setText(frequentWords.getText() + "\n" + message);
        }
    }

    /** Sets the name of the file to the respective text object **/
    public String getFilename(){
        return fileName;
    }

    /** Event hadler for checkboxes **/

    public void handleCheckboxes(CheckBox cbKET, CheckBox cbPET, CheckBox cbStarters, CheckBox cbMovers, CheckBox cbFlyers, CheckBox cbFCE, CheckBox cbTOEFL) {
        ArrayList<String> messageLevel = new ArrayList<>();
        if (cbKET.isSelected()) {
            messageLevel.add(percentageKet());
        }
        if (cbPET.isSelected()) {
            messageLevel.add(percentagePet());
        }
        if (cbStarters.isSelected()) {
            messageLevel.add(percentageStarters());
        }
        if (cbMovers.isSelected()) {
            messageLevel.add(percentageMovers());
        }
        if (cbFlyers.isSelected()) {
            messageLevel.add(percentageFlyers());
        }
        if (cbFCE.isSelected()) {
            messageLevel.add(percentageFCE());
        }
        if (cbTOEFL.isSelected()) {
            messageLevel.add(percentageTOEFL());
        }
        int i = 0;
        for (String message : messageLevel) {
            if (i == 0) {
                levelPercentage.setText(message);
            } else {
                levelPercentage.setText(levelPercentage.getText() + "\n" + message);
            }
            i++;
        }
    }

    /** Event handler for autocomplete **/

    public void handleAutoComplete(CheckBox autocompleteBox) {

        if(autocompleteBox.isSelected()) {
            text.setAutoComplete(true);
        }
        else {
            text.setAutoComplete(false);
        }
    }

    /** Event handler for spellchecker **/
    public void handleSpelling(CheckBox spellingBox) {

        if (spellingBox.isSelected()) {
            text.setSpelling(true);
            setUnknownWords();
        } else {
            text.setSpelling(false);
        }
    }


}

