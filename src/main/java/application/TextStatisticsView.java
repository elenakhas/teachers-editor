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

/**
 *  The popup window containing the TextStatisticsController output:
 *  statistics on the parts-of-speech and grammar features
 * @see application.TextStatisticsController
 * @author Elena Khasanova
 */


class TextStatisticsView {

    private MainTextController ctrl;

    // Text object fields to display output of the TextStatisticsController calls

    // for VOCABULARY
    protected Text numNouns;
    protected Text numAdj;
    protected Text numVerbs;
    protected Text numAdv;
    protected Text numNumbers;
    protected Text numPron;
    protected Text numDet;
    protected Text numWh;
    protected Text numPrepConj;
    protected Text numOther;

    // for GRAMMAR
    protected Text comparativeAdj;
    protected Text superlativeAdj;
    protected Text modalVerbs;
    protected Text existentialThere;
    protected Text comparativeAdv;
    protected Text superlativeAdv;
    protected Text imperativeForms;
    protected Text presentSimple;
    protected Text presentContinuous;
    protected Text pastSimple;
    protected Text pastContinuous;
    protected Text presentPerfectContinuousForms;
    protected Text presentPerfect;
    protected Text pastPerfect;
    protected Text futureSimple;
    protected Text futureContinuousForms;
    protected Text futurePerfectForms;

    public TextStatisticsView(MainTextController ctrl){

        // instantiate MainTextController
        this.ctrl = ctrl;

        // Instantiate text objects
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
        this.comparativeAdv = new Text();
        this.superlativeAdv = new Text();
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

    /**Get the results of **/

    public void getGrammarAndVocabCalculations() throws IOException {
        TextStatisticsController textStatCtrl = new TextStatisticsController(ctrl, this);

        textStatCtrl.getVocabularyStatistics();
        textStatCtrl.getGrammarStatistics();
    }

    /**Set properties of the Vocabulary GridPane and display the results **/

    public void vocabGridPane(GridPane vocab) {

        // set properties
        vocab.setMinSize(100, 200);
        vocab.setPadding(new Insets(10, 10, 10, 10));
        vocab.setVgap(10);
        vocab.setHgap(10);
        vocab.setAlignment(Pos.TOP_LEFT);

        // the title for the area
        Text subtitleColumn1 = new Text();
        subtitleColumn1.setFill(Color.DARKBLUE);
        subtitleColumn1.setText("Vocabulary: ");


        // Set titles for vocabulary properties:
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

       // vocab.addRow(0, );

        // set the results of calculations on vocabulary properties to respective text area to display
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

    }


    /**Set properties of the Grammar GridPane and display the results **/

    public void grammarGridPane(GridPane grammar) {
        // set properties
        grammar.setMinSize(100, 200);
        grammar.setPadding(new Insets(10, 10, 10, 10));
        grammar.setVgap(10);
        grammar.setHgap(10);
        grammar.setAlignment(Pos.TOP_LEFT);

        // set title for the area
        Text subtitleColumn2 = new Text();
        subtitleColumn2.setFill(Color.DARKBLUE);
        subtitleColumn2.setText("Grammar: ");

        // set titles for vocabulary properties
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

        // set the results of calculations on vocabulary properties to respective text area to display
        grammar.addRow(2, subtitleColumn2);
        grammar.addRow(3, compAdj, comparativeAdj);
        grammar.addRow(4, supAdj, superlativeAdj);
        grammar.addRow(5, compAdv, comparativeAdv);
        grammar.addRow(6, supAdv, superlativeAdv);
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

    }

    /**Add the elements to the window, display the window **/
    public void showNewWindow() throws IOException {

        // vbox layout for the window
        VBox vbox = new VBox();
        Text title = new Text();
        title.setFont(Font.font("Verdana", 14));
        title.setUnderline(true);
        title.setText("EXTENDED TEXT ANALYSIS");
        title.setTextAlignment(TextAlignment.CENTER);
        vbox.getChildren().add(title);

        // hbox with two GridPanes - for vocabulary and for grammar-related output
        HBox hbox = new HBox();
        vbox.getChildren().add(hbox);
        GridPane vocab = new GridPane();
        GridPane grammar = new GridPane();
        hbox.getChildren().addAll(vocab, grammar);

        // get results to further display in GridPanes
        getGrammarAndVocabCalculations();

        // add the vocabulary and grammar gridpane
        vocabGridPane(vocab);
        grammarGridPane(grammar);

        //Create a scene object
        Scene scene = new Scene(vbox);
        Stage statsStage = new Stage();
        final Dialog dlg = new Dialog();
        dlg.getDialogPane().setContent(vbox);

        //Set title to the Stage
        statsStage.setTitle("Text statistics");
        //Add  scene to the stage
        statsStage.setScene(scene);
        //Display the contents of the stage
        statsStage.setAlwaysOnTop(true);
        statsStage.sizeToScene();
        statsStage.showAndWait();

    }

}
