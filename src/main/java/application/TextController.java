package application;

import document.Document;
import document.FileContent;
import document.ReadingText;
import document.Vocabulary;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class TextController {

    TextArea text;
    Document txt;
    Text score;
    Text fKInterpretation;
    Text levelPercentage;
    private Desktop desktop = Desktop.getDesktop();
//    Text petPercentage;
//    Text startersPercentage;
//    Text moversPercentage;
//    Text flyersPercentage;
//    Text toeflPercentage;
//    Text fcePercentage;
//    Text ieltsPercentage;


    public TextController(TextArea text, Text score, Text fKInterpretation, Text levelPercentage){ //, Text petPercentage, Text startersPercentage,
                          //Text moversPercentage, Text flyersPercentage, Text fcePercentage,
                          //Text ieltsPercentage, Text toeflPercentage) {
        this.text = text;
        this.score = score;
        this.fKInterpretation = fKInterpretation;
        this.levelPercentage = levelPercentage;
//        this.petPercentage = petPercentage;
//        this.startersPercentage = startersPercentage;
//        this.moversPercentage = moversPercentage;
//        this.flyersPercentage = flyersPercentage;
//        this.toeflPercentage = toeflPercentage;
//        this.fcePercentage = fcePercentage;
//        this.ieltsPercentage = ieltsPercentage;
    }

    public void loadText() throws IOException {
        FileChooser fc = new FileChooser();
        File selected = fc.showOpenDialog(null);
        if (selected != null) {
            String filename = selected.getAbsolutePath();
            //desktop.open(selected);
            //String filename = "src/test/test_data/textKET";
            FileContent fcon = new FileContent(filename);
            //String content = null;
            txt = new ReadingText(fcon.getContent());
            String content = txt.getContent();
            text.setText(content);
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

    private String percentageKet(){
        Vocabulary voc = new Vocabulary("data/KETwordlist.txt");
        txt.wordsOfLevel(txt.getWords(), voc.getVocab());
        String percentage = (String.format("%.2f", txt.percentWordsOfLevel()));
        String levelPercentage = percentage + "% of KET words";
        return levelPercentage;
    }

    private String percentagePet(){
        Vocabulary voc = new Vocabulary("data/PETwordlist.txt");
        txt.wordsOfLevel(txt.getWords(), voc.getVocab());
        String percentage = (String.format("%.2f", txt.percentWordsOfLevel()));
        String levelPercentage = percentage + "% of PET words";
        return levelPercentage;
    }
    private String percentageStarters(){
        Vocabulary voc = new Vocabulary("data/STARTERSwordlist.txt");
        txt.wordsOfLevel(txt.getWords(), voc.getVocab());
        String percentage = (String.format("%.2f", txt.percentWordsOfLevel()));
        String levelPercentage = percentage + "% of Starters words";
        return levelPercentage;
    }

    public String percentageMovers(){
        Vocabulary voc = new Vocabulary("data/MOVERSwordlist.txt");
        txt.wordsOfLevel(txt.getWords(), voc.getVocab());
        String percentage = (String.format("%.2f", txt.percentWordsOfLevel()));
        String levelPercentage = percentage + "% of Movers words";
        return levelPercentage;
    }

    public String percentageFlyers(){
        Vocabulary voc = new Vocabulary("data/FLYERSwordlist.txt");
        txt.wordsOfLevel(txt.getWords(), voc.getVocab());
        String percentage = (String.format("%.2f", txt.percentWordsOfLevel()));
        String levelPercentage = percentage + "% of Flyers words";
        return levelPercentage;
    }

    public  void handleCheckboxes(CheckBox cbKET, CheckBox cbPET, CheckBox cbStarters, CheckBox cbMovers, CheckBox cbFlyers) {
        ArrayList<String> messageLevel = new ArrayList<>();
        if (cbKET.isSelected()) {
            messageLevel.add(percentageKet());
        }
        if (cbPET.isSelected()) {
            messageLevel.add(percentagePet());
        }
        if (cbStarters.isSelected()){
            messageLevel.add(percentageStarters());
        }
        if (cbMovers.isSelected()){
            messageLevel.add(percentageMovers());
        }
        if (cbFlyers.isSelected()){
            messageLevel.add(percentageFlyers());
        }
        int i = 0;
        for (String message : messageLevel){
            if (i==0){
                levelPercentage.setText(message);
            }
            else{
                levelPercentage.setText(levelPercentage.getText()+"\n"+message);
            }
            i++;
        }
    }


}

