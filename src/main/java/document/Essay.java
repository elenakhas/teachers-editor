package document;

/**
 *  Represents a document of a type Essay, a student's written work
 *  Extends AbstractDocument
 * @author Elena Khasanova
 * @version 1.0;
 * **/

public class Essay extends AbstractDocument {

    public Essay(String content) {
        super(content);
    }

    /**
     * Gives explanation of Flesch-Kincaid score for an essay
     *
     * @param score - double value computed by calcFleschKincaid method
     * @return fleschKincaidEvaluation - string containing interpretation of a score
     **/

    @Override
    public String interpretFleshKincaid(float score) {
        String complexity;
        String explanation;
        if (Float.isNaN(score)){
            complexity = "NO CATEGORY";
            explanation = "Can not evaluate using this score, check it's not empty";
        }
        else if (score <= 3) {
            complexity = "NO CATEGORY";
            explanation = "This is not an essay. Please, resubmit your work.";
        } else if (score <= 6) {
            complexity = "BASIC";
            explanation = "Elementary writing";
        } else if (score <= 9) {
            complexity = "AVERAGE";
            explanation = "Pre-Intermediate writing";
        } else if (score <= 12) {
            complexity = "AVERAGE";
            explanation = "Intermediate writing";
        } else if (score <= 15) {
            complexity = "SKILLED";
            explanation = "Advanced writing";
        } else {
            complexity = "SKILLED";
            explanation = "High-level writing";
        }

        String fleschKincaidEvaluation = String.format("%.2f", score) + " " +complexity + ": " + explanation;
        return fleschKincaidEvaluation;
    }

    public String interpretFlesch(float score) {
        String explanation;
        if (Float.isNaN(score)){
            explanation = "Can not evaluate using this score, check it's not empty";
        }
        else if (score <= 0) {
            explanation = "Can not evaluate using Flesch score";
        }else if (score < 30) {
            explanation = "Very complex advanced writing, C2 Mastery level.";
        } else if (score < 50) {
            explanation = "Fairly complex writing, C1 Advanced level";
        } else if (score < 60) {
            explanation = "Plain English, B2 Upper-Intermediate level";
        } else if (score < 70) {
            explanation = "Intermediate writing, B1 Intermediate level";
        } else if (score < 80) {
            explanation = "Simple writing, A2 Elementary level";
        } else {
            explanation = "Very simple, A1 Beginners level";
        }

            return explanation;
    }
}
