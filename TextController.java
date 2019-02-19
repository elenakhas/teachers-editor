package application;

import document.Document;
import document.Essay;
import document.FileContent;
import document.ReadingText;
import javafx.scene.control.CheckBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class TextController {

    AutocompleteArea text;
    Document txt;
    Text score;
    Text fKInterpretation;
    Text levelPercentage;
    Text spellingMistakes;


    public TextController(AutocompleteArea text, Text score, Text fKInterpretation, Text levelPercentage, Text spellingMistakes) { //, Text petPercentage, Text startersPercentage,
        //Text moversPercentage, Text flyersPercentage, Text fcePercentage,
        //Text ieltsPercentage, Text toeflPercentage) {
        this.text = text;
        this.score = score;
        this.fKInterpretation = fKInterpretation;
        this.levelPercentage = levelPercentage;
        this.spellingMistakes = spellingMistakes;


    }

    public void loadReadingText() throws IOException {
        FileChooser fc = new FileChooser();
        File selected = fc.showOpenDialog(null);
        if (selected != null) {
            String filename = selected.getAbsolutePath();
            FileContent fcon = new FileContent(filename);
            txt = new ReadingText(fcon.getContent());
            String content = txt.getContent();
            text.insertText(0, content);
        }
    }

    public void loadEssay() throws IOException {
        FileChooser fc = new FileChooser();
        File selected = fc.showOpenDialog(null);
        if (selected != null) {
            String filename = selected.getAbsolutePath();
            //desktop.open(selected);
            //String filename = "src/test/test_data/textKET";
            FileContent fcon = new FileContent(filename);
            //String content = null;
            txt = new Essay(fcon.getContent());
            String content = txt.getContent();
            text.insertText(0, content);
        }
    }

    public void getFlesch() {
        double fscore = txt.getFleschScore();
        score.setText(String.format("%.2f", fscore));
    }

    public void fleschKincaid() {
        String fsk = txt.interpretFleshKincaid(txt.fleschKincaid());
        fKInterpretation.setText(fsk);
    }

    public void SpellingMistakes() {
        Loader loader = new Loader();
        String spm = String.valueOf(txt.spellingMistakes(txt.getWords(), loader.getDictionary()));
        spellingMistakes.setText(spm);
    }

    private String percentageKet() {
        Loader loader = new Loader();
        txt.wordsOfLevel(txt.getWords(), loader.getKetVocab());
        String percentage = (String.format("%.2f", txt.percentWordsOfLevel()));
        String levelPercentage = percentage + "% of KET words";
        return levelPercentage;
    }

    private String percentagePet() {
        Loader loader = new Loader();
        txt.wordsOfLevel(txt.getWords(), loader.getPetVocab());
        String percentage = (String.format("%.2f", txt.percentWordsOfLevel()));
        String levelPercentage = percentage + "% of PET words";
        return levelPercentage;
    }

    private String percentageStarters() {
        Loader loader = new Loader();
        txt.wordsOfLevel(txt.getWords(), loader.getStartersVocab());
        String percentage = (String.format("%.2f", txt.percentWordsOfLevel()));
        String levelPercentage = percentage + "% of Starters words";
        return levelPercentage;
    }

    public String percentageMovers() {
        Loader loader = new Loader();
        txt.wordsOfLevel(txt.getWords(), loader.getMoversVocab());
        String percentage = (String.format("%.2f", txt.percentWordsOfLevel()));
        String levelPercentage = percentage + "% of Movers words";
        return levelPercentage;
    }

    public String percentageFlyers() {
        Loader loader = new Loader();
        txt.wordsOfLevel(txt.getWords(), loader.getFlyersVocab());
        String percentage = (String.format("%.2f", txt.percentWordsOfLevel()));
        String levelPercentage = percentage + "% of Flyers words";
        return levelPercentage;
    }

    public void handleCheckboxes(CheckBox cbKET, CheckBox cbPET, CheckBox cbStarters, CheckBox cbMovers, CheckBox cbFlyers) {
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

