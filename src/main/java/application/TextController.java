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

class TextController {

    private AutocompleteArea text;
    private AbstractDocument txt;
    private Text score;
    private Text fKInterpretation;
    private Text levelPercentage;
    private Text spellingMistakes;
    private Text wordcount;
    private Text sentenceNumber;
    private Text frequentWords;
    private Text uniqueWords;
    private String name;


    public TextController(AutocompleteArea text, Text score, Text fKInterpretation, Text levelPercentage, Text spellingMistakes,
                          Text wordcount, Text sentenceNumber, Text frequentWords, Text uniqueWords) {
        this.text = text;
        text.setEditable(true);
        this.score = score;
        this.fKInterpretation = fKInterpretation;
        this.levelPercentage = levelPercentage;
        this.spellingMistakes = spellingMistakes;
        this.wordcount = wordcount;
        this.sentenceNumber = sentenceNumber;
        this.frequentWords = frequentWords;
        this.uniqueWords = uniqueWords;
    }
//
//    public TextController(AutocompleteArea text){
//        this.text = text;
//    }

    private void clearing(){
        text.clear();
        score.setText("");
        fKInterpretation.setText("");
        levelPercentage.setText("");
        spellingMistakes.setText("");
        wordcount.setText("");
        sentenceNumber.setText("");
        frequentWords.setText("");
        uniqueWords.setText("");
    }

    public void loadReadingText() throws IOException {
        FileChooser fc = new FileChooser();
        File selected = fc.showOpenDialog(null);
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("TXT", "*.txt"));
        if (selected != null) {
            clearing();
            String filename = selected.getAbsolutePath();
            name = selected.getPath();
            FileContent fcon = new FileContent(filename);
            txt = new ReadingMaterial(fcon.getContent());
            String content = txt.getContent();
            text.insertText(0, content);
        }
    }

    public void loadEssay() throws IOException {
        FileChooser fc = new FileChooser();
        File selected = fc.showOpenDialog(null);
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("TXT", "*.txt"));
        if (selected != null) {
            clearing();
            name = selected.getPath();
            String filename = selected.getAbsolutePath();
            FileContent fcon = new FileContent(filename);
            //String content = null;
            txt = new Essay(fcon.getContent());
            String content = txt.getContent();
            text.insertText(0, content);
        }
    }
    public AbstractDocument getDoc(){
        return txt;
    }

    public void getFlesch() {
        double fscore = txt.getFleschScore();
        score.setText(String.format("%.2f", fscore));
    }

    public void fleschKincaid() {
        String fsk = txt.interpretFleshKincaid(txt.getFleschKincaid());
        fKInterpretation.setText(fsk);
    }

    private void SpellingMistakes() {
        Loader loader = new Loader();
        String spm = String.valueOf(txt.unknownWords(txt.getWords(), loader.getDictionary()));
        spellingMistakes.setText(spm);
    }

    private String percentageKet() {
        Loader loader = new Loader();
        txt.wordsOfLevel(txt.getWords(), loader.getKetVocab());
        String percentage = (String.format("%.2f", txt.percentWordsOfLevel()));
        String levelPercentage = percentage + "% of KET words";
        String unique = String.format("%.2f", txt.uniqueWordsOfALevel());
        return levelPercentage + ", unique words: " + unique;
    }

    private String percentagePet() {
        Loader loader = new Loader();
        txt.wordsOfLevel(txt.getWords(), loader.getPetVocab());
        String percentage = (String.format("%.2f", txt.percentWordsOfLevel()));
        String levelPercentage = percentage + "% of PET words";
        String unique = String.format("%.2f", txt.uniqueWordsOfALevel());
        return levelPercentage + ", unique words: " + unique;
    }

    private String percentageStarters() {
        Loader loader = new Loader();
        txt.wordsOfLevel(txt.getWords(), loader.getStartersVocab());
        String percentage = (String.format("%.2f", txt.percentWordsOfLevel()));
        String levelPercentage = percentage + "% of Starters words";
        String unique = String.format("%.2f", txt.uniqueWordsOfALevel());
        return levelPercentage + ", unique words: " + unique;
    }

    private String percentageMovers() {
        Loader loader = new Loader();
        txt.wordsOfLevel(txt.getWords(), loader.getMoversVocab());
        String percentage = (String.format("%.2f", txt.percentWordsOfLevel()));
        String levelPercentage = percentage + "% of Movers words";
        String unique = String.format("%.2f", txt.uniqueWordsOfALevel());
        return levelPercentage + ", unique words: " + unique;
    }

    private String percentageFlyers() {
        Loader loader = new Loader();
        txt.wordsOfLevel(txt.getWords(), loader.getFlyersVocab());
        String percentage = (String.format("%.2f", txt.percentWordsOfLevel()));
        String levelPercentage = percentage + "% of Flyers words";
        String unique = String.format("%.2f", txt.uniqueWordsOfALevel());
        return levelPercentage + ", unique words: " + unique;
    }
    private String percentageFCE() {
        Loader loader = new Loader();
        txt.wordsOfLevel(txt.getWords(), loader.getFCEVocab());
        String percentage = (String.format("%.2f", txt.percentWordsOfLevel()));
        String levelPercentage = percentage + "% of FCE words";
        String unique = String.format("%.2f", txt.uniqueWordsOfALevel());
        return levelPercentage + ", unique words: " + unique;
    }
    private String percentageTOEFL() {
        Loader loader = new Loader();
        txt.wordsOfLevel(txt.getWords(), loader.getTOEFLvocab());
        String percentage = (String.format("%.2f", txt.percentWordsOfLevel()));
        String levelPercentage = percentage + "% of TOEFL words";
        String unique = String.format("%.2f", txt.uniqueWordsOfALevel());
        return levelPercentage + ", unique words: " + unique;
    }

    public void getWordcount() {
        wordcount.setText(String.valueOf(txt.getNumWords()));
    }

    public void getSentenceCount(){
        sentenceNumber.setText(String.valueOf(txt.getNumSentences()));
    }
    public void getUniqueWords(){
        uniqueWords.setText(String.valueOf(txt.wordsVariety()));
    }
    public void getTopTenWords(){

        for (String key : txt.mostFrequentWords().keySet()){
            String message = key + " : " + txt.mostFrequentWords().get(key);
            frequentWords.setText(frequentWords.getText() + "\n" + message);
        }
    }

    public String getFilename(){
        return name;
    }

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

    public void handleAutoComplete(CheckBox autocompleteBox) {

        if(autocompleteBox.isSelected()) {
            text.setAutoComplete(true);
        }
        else {
            text.setAutoComplete(false);
        }
    }
    public void handleSpelling(CheckBox spellingBox) {

        if (spellingBox.isSelected()) {
            text.setSpelling(true);
            SpellingMistakes();
        } else {
            text.setSpelling(false);
        }
    }


}

