package application;

import document.AbstractDocument;
import document.Essay;
import document.FileContent;
import document.ReadingMaterial;
import javafx.scene.control.CheckBox;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Elena Khasanova
 * */

public class MainTextController {

    // The View Object
    MainTextView view;

    // instance of the text document
    private AbstractDocument txt;

    // loader object to load vocabulary files
    Loader loader;

    // name of the file opened
    private String fileName;


    public MainTextController(MainTextView view) {
        this.loader = new Loader();
        this.view = view;
    }

/** Clears the text area and the MainStatisticsArea**/
    private void clearing(){
        this.view.text.clear();
        this.view.fscore.setText("");
        this.view.fKInterpretation.setText("");
        this.view.levelPercentage.setText("");
        this.view.unknownWords.setText("");
        this.view.wordcount.setText("");
        this.view.sentenceNumber.setText("");
        this.view.frequentWords.setText("");
        this.view.uniqueWords.setText("");
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
            this.view.text.insertText(0, content);
            this.view.filename.setText(this.fileName);
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
            this.view.text.insertText(0, content);
            this.view.filename.setText(this.fileName);

        }
    }

    /** Getter for the AbstractDocument object**/
    public AbstractDocument getDoc(){
        return txt;
    }

    /** Sets Flesch score for the document and its interpretation to a respective text object**/
    public void getFlesch() {
        float fscore = txt.calcFleschScore();
        this.view.fscore.setText(String.format("%.2f", fscore) + " " + txt.interpretFlesch(fscore));
    }

    /** Sets FleschKincaid score for the document and its interpretation to a respective text object**/
    public void fleschKincaid() {
        String fsk = txt.interpretFleshKincaid(txt.calcFleschKincaid());
        this.view.fKInterpretation.setText(fsk);
    }

    /** Sets the number of spelling mistakes to a respective text object**/
    public void setUnknownWords() {
        Loader loader = new Loader();
        String spm = String.valueOf(txt.countUnknownWords(txt.getWords(), loader.getDictionary()));
        this.view.unknownWords.setText(spm);
    }

    /** Sets the number of words to the respective text object **/
    public void setWordcount() {
        this.view.wordcount.setText(String.valueOf(txt.getNumWords()));

    }

    /** Sets the number of sentences to the respective text object **/
    public void setSentenceCount(){
        this.view.sentenceNumber.setText(String.valueOf(txt.getNumSentences()));
    }

    /** Sets the number of unique words to the respective text object **/
    public void setUniqueWords(){
        this.view.uniqueWords.setText(String.valueOf(txt.wordsVariety()));
    }

    /** Sets the TopTenWords to the respective text object **/
    public void setTopTenWords(){
        String output = "";
        for (String key : txt.mostFrequentWords().keySet()){
            String message = key + " : " + txt.mostFrequentWords().get(key) + "\n";
            output = output + message;
        }
        this.view.frequentWords.setText(output);
    }

    /** Event hadler for checkboxes **/

    public void handleLevelCheckboxes(CheckBox cbKET, CheckBox cbPET, CheckBox cbStarters, CheckBox cbMovers,
                                      CheckBox cbFlyers, CheckBox cbFCE, CheckBox cbTOEFL, CheckBox cbIELTS) {
        ArrayList<String> messageLevel = new ArrayList<>();
        if (cbKET.isSelected()) {
            messageLevel.add(this.txt.levelMessage("KET", this.loader.getKetVocab()));
        }
        if (cbPET.isSelected()) {
            messageLevel.add(this.txt.levelMessage("PET", this.loader.getPetVocab()));
        }
        if (cbStarters.isSelected()) {
            messageLevel.add(this.txt.levelMessage("Starters", this.loader.getStartersVocab()));
        }
        if (cbMovers.isSelected()) {
            messageLevel.add(this.txt.levelMessage("Movers", this.loader.getMoversVocab()));
        }
        if (cbFlyers.isSelected()) {
            messageLevel.add(this.txt.levelMessage("Flyers", this.loader.getFlyersVocab()));
        }
        if (cbFCE.isSelected()) {
            messageLevel.add(this.txt.levelMessage("FCE", this.loader.getFCEVocab()));
        }
        if (cbTOEFL.isSelected()) {
            messageLevel.add(this.txt.levelMessage("TOEFL", this.loader.getTOEFLvocab()));
        }
        if (cbIELTS.isSelected()) {
            messageLevel.add(this.txt.levelMessage("IELTS", this.loader.getIELTSvocab()));
        }
        int i = 0;
        for (String message : messageLevel) {
            if (i == 0) {
                this.view.levelPercentage.setText(message);
            } else {
                this.view.levelPercentage.setText(this.view.levelPercentage.getText() + "\n" + message);
            }
            i++;
        }
    }

    /** Event handler for autocomplete **/

    public void handleAutoComplete(CheckBox autocompleteBox) {

        if(autocompleteBox.isSelected()) {
            this.view.text.setAutoComplete(true);
        }
        else {
            this.view.text.setAutoComplete(false);
        }
    }

    /** Event handler for spellchecker **/
    public void handleSpelling(CheckBox spellingBox) {

        if (spellingBox.isSelected()) {
            this.view.text.setSpelling(true);
        } else {
            this.view.text.setSpelling(false);
        }
    }


}

