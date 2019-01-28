package application;

import document.Document;
import document.FileContent;
import document.ReadingText;
import document.Vocabulary;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.HashMap;

public class TextController {

    TextArea text;
    Document txt;
    Text score;
    Text fKInterpretation;
    Text levelPercentage;


    public TextController(TextArea text, Text score, Text fKInterpretation, Text levelPercentage) {
        this.text = text;
        this.score = score;
        this.fKInterpretation = fKInterpretation;
        this.levelPercentage = levelPercentage;
    }

    public void loadText() throws IOException {
        String filename = "data/testsentences.txt";
        FileContent fc = new FileContent(filename);
        //String content = null;
        txt = new ReadingText(fc.getContent());
        String content = txt.getContent();
        text.setText(content);
    }

    public void getFlesch() {
        double fscore = txt.getFleschScore();
        score.setText(String.valueOf(fscore));
    }

    public void fleschKincaid() {
        String fsk = txt.interpretFleshKincaid(txt.fleschKincaid());
        fKInterpretation.setText(fsk);
    }

    public void percentageKet(){
        Vocabulary voc = new Vocabulary("data/KETnew.txt");
        txt.wordsOfLevel(txt.getWords(), voc.getVocab());
        String percentage = String.valueOf(txt.wordsOfALevel());
        levelPercentage.setText(percentage + "% of KET words");
    }
}
