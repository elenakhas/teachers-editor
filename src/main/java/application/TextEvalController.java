package application;
//

import document.Document;
import javafx.scene.text.Text;

import java.io.IOException;

class TextEvalController {
    private Document txt;
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

    public TextEvalController(TextController ctrl, Text nNouns, Text nAdj, Text nVerbs, Text nAdv, Text nNumbers,
            Text nPron, Text nDet, Text nWh, Text nPrepConj, Text nOther,  Text comparativeAdj, Text superlativeAdj,
            Text modalVerbs, Text existentialThere, Text comparativeADv, Text superlativeADv, Text imperativeForms,
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
        this.comparativeADv = comparativeADv;
        this.superlativeADv = superlativeADv;
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
        TextController ctrl1 = ctrl;
        this.txt = ctrl.getDoc();
    }

    public void getVocabularyStatistics() throws IOException {
        txt.getPOStagging();
        txt.getPOSNumbers();
        nNouns.setText(txt.numNouns + " // " + String.format("%.2f", (double)txt.numNouns/txt.getNumWords()*100) + " %");
        nAdj.setText(txt.numAdj + " // " + String.format("%.2f", (double)txt.numAdj/txt.getNumWords()*100) + " %");
        nAdv.setText(txt.numAdv + " // " + String.format("%.2f", (double)txt.numAdv/txt.getNumWords()*100) + " %");
        nVerbs.setText(txt.numVerbs + " // " + String.format("%.2f", (double)txt.numVerbs/txt.getNumWords()*100) + " %");
        nNumbers.setText(txt.numNumbers + " // " + String.format("%.2f",(double) txt.numNumbers/txt.getNumWords()*100) + " %");
        nDet.setText(txt.numDet + " // " + String.format("%.2f", (double)txt.numDet/txt.getNumWords()*100) + " %");
        nPrepConj.setText(txt.numPrepConj + " // " + String.format("%.2f", (double)txt.numPrepConj/txt.getNumWords()*100) + " %");
        nPron.setText(txt.numPron + " // " + String.format("%.2f", (double)txt.numPron/txt.getNumWords()*100) + " %");
        nWh.setText(txt.numWh + " // " + String.format("%.2f", (double)txt.numWh/txt.getNumWords()*100) + " %");
        nOther.setText(txt.numOther + " // " + String.format("%.2f", (double)txt.numOther/txt.getNumWords()*100) + " %");
    }

    public void getGrammarStatistics() {
        txt.grammarAnalyser();
        comparativeAdj.setText(txt.comparativeAJ + " // " + String.format("%.2f", (double)txt.comparativeAJ/txt.getNumWords()*100) + " %");
        superlativeAdj.setText(txt.superlativeAJ + " // " + String.format("%.2f", (double)txt.superlativeAJ/txt.getNumWords()*100) + " %");
        comparativeADv.setText(txt.comparativeAD + " // " + String.format("%.2f", (double)txt.comparativeAD/txt.getNumWords()*100) + " %");
        superlativeADv.setText(txt.superlativeAD + " // " + String.format("%.2f", (double)txt.superlativeAD/txt.getNumWords()*100) + " %");
        modalVerbs.setText(txt.modals + " // " + String.format("%.2f", (double)txt.modals/txt.getNumWords()*100) + " %");
        existentialThere.setText(txt.existential + " // " + String.format("%.2f", (double)txt.existential/txt.getNumWords()*100) + " %");
        imperativeForms.setText(txt.imperative + " // " + String.format("%.2f",(double) txt.imperative/txt.getNumWords()*100) + " %");
        presentSimple.setText(txt.presentSimpleActive + " // " + String.format("%.2f", (double)txt.presentSimpleActive/txt.getNumWords()*100) + " %");
        presentContinuous.setText(txt.presentContinuousActive + " // " + String.format("%.2f", (double)txt.presentContinuousActive/(txt.getNumWords() - txt.presentContinuousActive)*100) + " %");
        presentPerfectContinuousForms.setText(txt.presentContinuousActive + " // " + String.format("%.2f", (double)txt.presentPerfectContinuous/(txt.getNumWords() - 2*txt.presentPerfectContinuous)*100) + " %");
        pastSimple.setText(txt.pastSimpleActive + " // " + String.format("%.2f", (double)txt.pastSimpleActive/txt.getNumWords()*100) + " %");
        pastContinuous.setText(txt.pastContinuousActive + " // " + String.format("%.2f", (double)txt.pastContinuousActive/(txt.getNumWords() - txt.pastContinuousActive)*100) + " %");
        presentPerfect.setText(txt.presentPerfect + " // " + String.format("%.2f",(double) txt.presentPerfect/(txt.getNumWords() - txt.presentPerfect)*100) + " %");
        pastPerfect.setText(txt.pastPerfectActive + " // " + String.format("%.2f", (double)txt.pastPerfectActive/(txt.getNumWords() - txt.pastPerfectActive)*100) + " %");
        futureSimple.setText(txt.futureSimpleActive + " // " + String.format("%.2f", (double)txt.futureSimpleActive/(txt.getNumWords() - txt.futureSimpleActive)*100) + " %");
        futureContinuousForms.setText(txt.futureContinuous + " // " + String.format("%.2f", (double)txt.futureContinuous/(txt.getNumWords() - 2* txt.futureContinuous)*100) + " %");
        futurePerfectForms.setText(txt.futurePerfect + " // " + String.format("%.2f", (double)txt.futurePerfect/(txt.getNumWords() - 2* txt.futurePerfect)*100) + " %");
    }
}
