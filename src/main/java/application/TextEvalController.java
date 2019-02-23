package application;
//

import document.AbstractDocument;
import javafx.scene.text.Text;

import java.io.IOException;

class TextEvalController {
    private AbstractDocument txt;
    private Text nNouns;
    private Text nAdj;
    private Text nVerbs;
    private Text nAdv;
    private Text nNumbers;
    private Text nPron;
    private Text nDet;
    private Text nWh;
    private Text nPrepConj;
    private Text nOther;

    private Text comparativeAdj;
    private Text superlativeAdj;
    private Text modalVerbs;
    private Text existentialThere;
    private Text comparativeAdv;
    private Text superlativeAdv;
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
    private int numWords;

    public TextEvalController(TextController ctrl, Text nNouns, Text nAdj, Text nVerbs, Text nAdv, Text nNumbers,
                              Text nPron, Text nDet, Text nWh, Text nPrepConj, Text nOther, Text comparativeAdj, Text superlativeAdj,
                              Text modalVerbs, Text existentialThere, Text comparativeAdv, Text superlativeAdv, Text imperativeForms,
                              Text presentSimple, Text presentContinuous, Text pastSimple, Text pastContinuous, Text presentPerfectContinuousForms,
                              Text presentPerfect, Text pastPerfect, Text futureSimple, Text futureContinuousForms, Text futurePerfectForms){
        this.nNouns = nNouns;
        this.nAdj = nAdj;
        this.nVerbs = nVerbs;
        this.nAdv = nAdv;
        this.nNumbers = nNumbers;
        this.nPron = nPron;
        this.nDet = nDet;
        this.nWh = nWh;
        this.nPrepConj = nPrepConj;
        this.nOther = nOther;
        this.comparativeAdj = comparativeAdj;
        this.superlativeAdj = superlativeAdj;
        this.modalVerbs = modalVerbs;
        this.existentialThere = existentialThere;
        this.comparativeAdv = comparativeAdv;
        this.superlativeAdv = superlativeAdv;
        this.imperativeForms = imperativeForms;
        this.presentSimple = presentSimple;
        this.presentContinuous = presentContinuous;
        this.pastSimple = pastSimple;
        this.pastContinuous = pastContinuous;
        this.presentPerfectContinuousForms = presentPerfectContinuousForms;
        this.presentPerfect = presentPerfect;
        this.pastPerfect = pastPerfect;
        this.futureSimple = futureSimple;
        this.futureContinuousForms = futureContinuousForms;
        this.futurePerfectForms = futurePerfectForms;
        this.txt = ctrl.getDoc();
        this.numWords = this.txt.getNumWords();

    }

    private static void setResults(Text text, int formCount, int numWords){
        text.setText(formCount + " / " + String.format("%.2f", (double) formCount/numWords*100) + " %");
    }

    public void getVocabularyStatistics() throws IOException {
        txt.getPOStagging();
        txt.getPosStatistics();

        setResults(this.nNouns, txt.getNumNouns(), this.numWords);
        setResults(this.nAdj, txt.getNumAdj(), this.numWords);
        setResults(this.nAdv, txt.getNumAdv(), this.numWords);
        setResults(this.nVerbs, txt.getNumVerbs(), this.numWords);
        setResults(this.nNumbers, txt.getNumNumbers(), this.numWords);
        setResults(this.nPron, txt.getNumPron(), this.numWords);
        setResults(this.nDet, txt.getNumDet(), this.numWords);
        setResults(this.nWh, txt.getNumWh(), this.numWords);
        setResults(this.nPrepConj, txt.getNumPrepConj(), this.numWords);
        setResults(this.nOther, txt.getNumOther(), this.numWords);

    }


    public void getGrammarStatistics() {
        txt.getGrammarStatistics();
        setResults(this.comparativeAdj, txt.getNumComparativeAJ(), this.numWords);
        setResults(this.superlativeAdj, txt.getNumSuperlativeAJ(), this.numWords);
        setResults(this.comparativeAdv, txt.getNumComparativeAD(), this.numWords);
        setResults(this.superlativeAdv, txt.getNumSuperlativeAD(), this.numWords);
        setResults(this.modalVerbs, txt.getNumModals(), this.numWords);
        setResults(this.existentialThere, txt.getNumExistential(), this.numWords);
        setResults(this.imperativeForms, txt.getNumImperative(), this.numWords);

        // The calculation is not completely accurate as the form can consist of one or two words, will be improved in the next release
        setResults(this.presentSimple, txt.getNumPresentSimpleActive(), this.numWords);
        // substract the number of Present Continuous forms from the total number of words in the text as the form always consists of two words
        setResults(this.presentContinuous, txt.getNumPresentContinuousActive(), this.numWords-txt.getNumPresentContinuousActive());
        // substract twice the number of Present Perfect Continuous forms from the total number of words in the text as the form always consists of three words
        setResults(this.presentPerfectContinuousForms, txt.getNumPresentPerfectContinuous(), this.numWords- 2* txt.getNumPresentPerfectContinuous());
        // The calculation is not completely accurate as the form can consist of one or two words, will be improved in the next release
        setResults(this.pastSimple, txt.getNumPastSimpleActive(), this.numWords);
        // substract the number of Past Continuous forms from the total number of words in the text as the form always consists of two words
        setResults(this.pastContinuous, txt.getNumPastContinuousActive(), this.numWords-txt.getNumPastContinuousActive());
        // substract the number of Present Perfect forms from the total number of words in the text as the form always consists of two words
        setResults(this.presentPerfect, txt.getNumPresentPerfectActive(), this.numWords-txt.getNumPresentPerfectActive());
        // substract the number of Past Perfect forms from the total number of words in the text as the form always consists of two words
        setResults(this.pastPerfect, txt.getNumPastPerfectActive(), this.numWords-txt.getNumPastPerfectActive());
        // substract the number of Future Simple forms from the total number of words in the text as the form always consists of two words
        setResults(this.futureSimple, txt.getNumFutureSimpleActive(), this.numWords-txt.getNumFutureSimpleActive());
        // substract twice the number of Future Continuous forms from the total number of words in the text as the form always consists of three words
        setResults(this.futureContinuousForms, txt.getNumFutureContinuous(), this.numWords-2*txt.getNumFutureContinuous());
        // substract twice the number of Future Perfect forms from the total number of words in the text as the form always consists of three words
        setResults(this.futurePerfectForms, txt.getNumFuturePerfect(), this.numWords-2*txt.getNumFuturePerfect());


    }
}
