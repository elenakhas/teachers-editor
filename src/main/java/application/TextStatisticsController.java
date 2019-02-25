package application;

import document.AbstractDocument;
import javafx.scene.text.Text;

import java.io.IOException;
/**
 * The class provides a link between the ExtendedTextStatistics interface methods implemented in AbstractDocument.
 * Contains the AbstractDocument object
 * @see document.AbstractDocument
 * @author Elena Khasanova
 */

class TextStatisticsController {


    // Create instance of the text document
    private AbstractDocument txt;
    private int numWords;

    // Create a view object
    TextStatisticsView view;


    // instantiate everything in the constructor
    public TextStatisticsController(MainTextController ctrl, TextStatisticsView view){
        this.txt = ctrl.getDoc();
        this.numWords = this.txt.getNumWords();
        this.view = view;
    }

    /**Helper method to set results to a respective text object in a specified format: "count / percentage"**/
    private static void setResults(Text text, int formCount, int numWords){
        text.setText(formCount + " / " + String.format("%.2f", (double) formCount/numWords*100) + " %");
    }

    /** Sets the number of words of selected POS to the respective text object **/

    public void getVocabularyStatistics() throws IOException {

        txt.getPosTagging();
        txt.getPosStatistics();

        setResults(this.view.numNouns, txt.getNumNouns(), this.numWords);
        setResults(this.view.numAdj, txt.getNumAdj(), this.numWords);
        setResults(this.view.numAdv, txt.getNumAdv(), this.numWords);
        setResults(this.view.numVerbs, txt.getNumVerbs(), this.numWords);
        setResults(this.view.numNumbers, txt.getNumNumbers(), this.numWords);
        setResults(this.view.numPron, txt.getNumPron(), this.numWords);
        setResults(this.view.numDet, txt.getNumDet(), this.numWords);
        setResults(this.view.numWh, txt.getNumWh(), this.numWords);
        setResults(this.view.numPrepConj, txt.getNumPrepConj(), this.numWords);
        setResults(this.view.numOther, txt.getNumOther(), this.numWords);
    }

    /** Sets the number of occurrence of grammar forms to the respective text object **/

    public void getGrammarStatistics() {

        txt.getGrammarStatistics();

        // lexicalized grammar features
        setResults(this.view.comparativeAdj, txt.getNumComparativeAJ(), this.numWords);
        setResults(this.view.superlativeAdj, txt.getNumSuperlativeAJ(), this.numWords);
        setResults(this.view.comparativeAdv, txt.getNumComparativeAD(), this.numWords);
        setResults(this.view.superlativeAdv, txt.getNumSuperlativeAD(), this.numWords);
        setResults(this.view.modalVerbs, txt.getNumModals(), this.numWords);
        setResults(this.view.existentialThere, txt.getNumExistential(), this.numWords);

        setResults(this.view.imperativeForms, txt.getNumImperative(), this.numWords);

        // The calculation is not completely accurate as the form can consist of one or two words,
        // will be improved in the next release
        setResults(this.view.presentSimple, txt.getNumPresentSimpleActive(), this.numWords);

        // substract the number of Present Continuous forms from the total number of words in the text
        // as the form always consists of two words
        setResults(this.view.presentContinuous, txt.getNumPresentContinuousActive(),
                this.numWords-txt.getNumPresentContinuousActive());

        // substract twice the number of Present Perfect Continuous forms from the total number of words
        // in the text as the form always consists of three words
        setResults(this.view.presentPerfectContinuousForms, txt.getNumPresentPerfectContinuous(),
                this.numWords- 2* txt.getNumPresentPerfectContinuous());

        // The calculation is not completely accurate as the form can consist of one or two words,
        // will be improved in the next release
        setResults(this.view.pastSimple, txt.getNumPastSimpleActive(), this.numWords);

        // substract the number of Past Continuous forms from the total number of words in the text
        // as the form always consists of two words
        setResults(this.view.pastContinuous, txt.getNumPastContinuousActive(),
                this.numWords-txt.getNumPastContinuousActive());

        // substract the number of Present Perfect forms from the total number of words in the text
        // as the form always consists of two words
        setResults(this.view.presentPerfect, txt.getNumPresentPerfectActive(),
                this.numWords-txt.getNumPresentPerfectActive());

        // substract the number of Past Perfect forms from the total number of words in the text
        // as the form always consists of two words
        setResults(this.view.pastPerfect, txt.getNumPastPerfectActive(),
                this.numWords-txt.getNumPastPerfectActive());

        // substract the number of Future Simple forms from the total number of words in the text
        // as the form always consists of two words
        setResults(this.view.futureSimple, txt.getNumFutureSimpleActive(),
                this.numWords-txt.getNumFutureSimpleActive());

        // substract twice the number of Future Continuous forms from the total number of words in the text
        // as the form always consists of three words
        setResults(this.view.futureContinuousForms, txt.getNumFutureContinuous(),
                this.numWords-2*txt.getNumFutureContinuous());

        // substract twice the number of Future Perfect forms from the total number of words in the text
        // as the form always consists of three words
        setResults(this.view.futurePerfectForms, txt.getNumFuturePerfect(),
                this.numWords-2*txt.getNumFuturePerfect());

    }
}
