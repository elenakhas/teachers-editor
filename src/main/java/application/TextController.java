package application;

import document.ReadingText;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;

import java.io.IOException;

public class TextController {

    TextArea text;
    ReadingText txt;
    Text score;
    Text fKInterpretation;


    public TextController(TextArea text, Text score, Text fKInterpretation){
        txt = new ReadingText();
        this.text = text;
        this.score = score;
        this.fKInterpretation = fKInterpretation;
    }

    public void loadText(){

        txt.filename = "data/testsentences.txt";
        String content = null;
        try {
            content = txt.getContent();
        } catch (IOException e) {
            e.printStackTrace();
        }
        txt.getProperties(content);
        text.setText(content);
    }

    public void getFlesch(){
        double fscore = txt.getFleschScore();
        score.setText(String.valueOf(fscore));
        //System.out.println(fscore);
    }

    public void fleschKincaid(){
        String fsk = txt.interpretFleshKincaid(txt.fleschKincaid());
        fKInterpretation.setText(fsk);
    }
}
