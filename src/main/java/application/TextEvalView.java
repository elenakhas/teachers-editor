package application;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Dialog;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.io.IOException;

class TextEvalView {

    private TextController ctrl;
    private Text numNouns;
    private Text numAdj;
    private Text numVerbs;
    private Text numAdv;
    private Text numNumbers;
    private Text numPron;
    private Text numDet;
    private Text numWh;
    private Text numPrepConj;
    private Text numOther;


    private Text comparativeAdj;
    private Text superlativeAdj;
    private Text modalVerbs;
    private Text existentialThere;
    private Text comparativeADv;
    private Text superlativeADv;
    private Text imperativeForms;
    private Text presentSimple;
    private Text presentContinuous;
    private Text pastSimple;
    private Text pastContinuous;
    private Text presentPerfectContinuousForms;
    private Text presentPerfect;
    private Text pastPerfect;
    private Text futureSimple;
    private Text futureContinuousForms;
    private Text futurePerfectForms;

    public TextEvalView(TextController ctrl){
        this.ctrl = ctrl;
        this.numNouns = new Text();
        this.numAdj = new Text();
        this.numVerbs = new Text();
        this.numAdv = new Text();
        this.numNumbers = new Text();
        this.numPron = new Text();
        this.numDet = new Text();
        this.numWh = new Text();
        this.numPrepConj = new Text();
        this.numOther = new Text();
        this.comparativeAdj = new Text();
        this.superlativeAdj = new Text();
        this.modalVerbs = new Text();
        this.existentialThere = new Text();
        this.comparativeADv = new Text();
        this.superlativeADv = new Text();
        this.imperativeForms = new Text();
        this.presentSimple = new Text();
        this.presentContinuous = new Text();
        this.pastSimple = new Text();
        this.pastContinuous = new Text();
        this.presentPerfectContinuousForms = new Text();
        this.presentPerfect = new Text();
        this.pastPerfect = new Text();
        this.futureSimple = new Text();
        this.futureContinuousForms = new Text();
        this.futurePerfectForms = new Text();
    }




    public void showNewWindow() throws IOException {

        //PopupWindow statistics = new PopupWindow(){};
        VBox vbox = new VBox();
        Text title = new Text();
        title.setFont(Font.font("Verdana", 14));
        title.setUnderline(true);
        title.setText("EXTENDED TEXT ANALYSIS");
        title.setTextAlignment(TextAlignment.CENTER);
        vbox.getChildren().add(title);


        HBox hbox = new HBox();
        vbox.getChildren().add(hbox);
        GridPane vocab = new GridPane();
        GridPane grammar = new GridPane();
        hbox.getChildren().addAll(vocab, grammar);

        // vocabulary pane
        //Setting size for the pane
        vocab.setMinSize(100, 200);
        //Setting the padding
        vocab.setPadding(new Insets(10, 10, 10, 10));
        vocab.setVgap(10);
        vocab.setHgap(10);
        //Setting the Grid alignment
        vocab.setAlignment(Pos.TOP_LEFT);

        Text subtitleColumn1 = new Text();
        subtitleColumn1.setFill(Color.DARKBLUE);
        subtitleColumn1.setText("vocabulary: ");


        Text nouns = new Text();
        nouns.setText("Nouns: ");
        Text verbs = new Text();
        verbs.setText("Verbs: ");
        Text adjectives = new Text();
        adjectives.setText("Adjectives: ");
        Text adverbs = new Text();
        adverbs.setText("Adverbs: ");
        Text pronouns = new Text();
        pronouns.setText("Pronouns: ");
        Text prepConj = new Text();
        prepConj.setText("Prepositions, conjunctions: ");
        Text numbers = new Text();
        numbers.setText("Numerals: ");
        Text det = new Text();
        det.setText("Determiners: ");
        Text wh = new Text();
        wh.setText("Wh- words: ");
        Text other = new Text();
        other.setText("Other: ");

        TextEvalController tec = new TextEvalController(ctrl, numNouns, numAdj, numVerbs, numAdv, numNumbers,
                numPron, numDet, numWh, numPrepConj, numOther,  comparativeAdj, superlativeAdj,
                modalVerbs, existentialThere, comparativeADv, superlativeADv, imperativeForms,
                presentSimple, presentContinuous, pastSimple, pastContinuous, presentPerfectContinuousForms,
                presentPerfect, pastPerfect, futureSimple, futureContinuousForms, futurePerfectForms);


        tec.getVocabularyStatistics();
        tec.getGrammarStatistics();

        vocab.addRow(0,title);
        //gridPane.add(flesch, 1,1);
        vocab.addRow(1, subtitleColumn1);
        vocab.addRow(2, nouns, numNouns);
        vocab.addRow(3, verbs, numVerbs);
        vocab.addRow(4, adjectives, numAdj);
        vocab.addRow(5, adverbs, numAdv);
        vocab.addRow(6, pronouns, numPron);
        vocab.addRow(7, prepConj, numPrepConj);
        vocab.addRow(8, numbers, numNumbers);
        vocab.addRow(9, det, numDet);
        vocab.addRow(10, wh, numWh);
        vocab.addRow(11, other, numOther);


       // GRAMMAR GRID PANE
        grammar.setMinSize(100, 200);
        //Setting the padding
        grammar.setPadding(new Insets(10, 10, 10, 10));
        grammar.setVgap(10);
        grammar.setHgap(10);
        //Setting the Grid alignment
        grammar.setAlignment(Pos.TOP_LEFT);

        Text subtitleColumn2 = new Text();
        subtitleColumn2.setFill(Color.DARKBLUE);
        subtitleColumn2.setText("Grammar: ");


        Text compAdj = new Text();
        compAdj.setText("Comparative adjectives: ");
        Text supAdj = new Text();
        supAdj.setText("Superlative adjectives: ");
        Text compAdv = new Text();
        compAdv.setText("Comparative adverbs: ");
        Text supAdv = new Text();
        supAdv.setText("Superlative adverbs: ");
        Text modals = new Text();
        modals.setText("Modal verbs: ");
        Text exist = new Text();
        exist.setText("Existential there: ");
        Text imperative = new Text();
        imperative.setText("Imperative forms: ");
        Text prSimple = new Text();
        prSimple.setText("Present Simple: ");
        Text prContinuous = new Text();
        prContinuous.setText("Present Continuous: ");
        Text pastSimp = new Text();
        pastSimp.setText("Past Simple: ");
        Text pastCont = new Text();
        pastCont.setText("Past Continuous: ");
        Text presPerf = new Text();
        presPerf.setText("Present Perfect: ");
        Text presPerfCont = new Text();
        presPerfCont.setText("Present Perfect Continuous: ");
        Text pastPerf = new Text();
        pastPerf.setText("Past Perfect: ");
        Text futSimple = new Text();
        futSimple.setText("Future Simple: ");
        Text futCont = new Text();
        futCont.setText("Future Continuous: ");
        Text futPerf = new Text();
        futPerf.setText("Future Perfect: ");


        grammar.addRow(2, subtitleColumn2);
        grammar.addRow(3, compAdj, comparativeAdj);
        grammar.addRow(4, supAdj, superlativeAdj);
        grammar.addRow(5, compAdv, comparativeADv);
        grammar.addRow(6, supAdv, superlativeADv);
        grammar.addRow(7, modals, modalVerbs);
        grammar.addRow(8, exist, existentialThere);
        grammar.addRow(9, imperative, imperativeForms);
        grammar.addRow(10, prSimple, presentSimple);
        grammar.addRow(11, prContinuous, presentContinuous);
        grammar.addRow(12, presPerf, presentPerfect);
        grammar.addRow(13, presPerfCont, presentPerfectContinuousForms);
        grammar.addRow(14, pastSimp, pastSimple);
        grammar.addRow(15, pastCont, pastContinuous);
        grammar.addRow(16, pastPerf, pastPerfect);
        grammar.addRow(17, futSimple, futureSimple);
        grammar.addRow(18, futCont, futureContinuousForms);
        grammar.addRow(19, futPerf, futurePerfectForms);


        //Setting the vertical and horizontal gaps between the columns
        Scene scene = new Scene(vbox);
        Stage statsStage = new Stage();

        //Creating a scene object

        final Dialog dlg = new Dialog();
        dlg.getDialogPane().setContent(vbox);
        //Setting title to the Stage
        statsStage.setTitle("Text statistics");
        //Adding scene to the stage
        statsStage.setScene(scene);
        //Displaying the contents of the stage
        statsStage.setAlwaysOnTop(true);
        statsStage.sizeToScene();
        statsStage.showAndWait();

    }

}
