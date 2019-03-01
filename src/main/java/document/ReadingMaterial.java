package document;

/**
 *  Class represents a text used for reading exercises as opposed to students' written work
 *  Extends AbstractDocument.
 * @author Elena Khasanova
 * @version 1.2;
 * */

public class ReadingMaterial extends AbstractDocument {

    public ReadingMaterial(String content) {
        super(content);
    }

    /**
     * Interprets the Flesch-Kincaid grade readability score for a text
     * @param score - readability score returned by calcFleschKincaid method
     * @return interpretation message
     */
    public String interpretFleshKincaid(float score) {
        String complexity;
        String explanation;

        if (Float.isNaN(score)){
            complexity = "NO CATEGORY";
            explanation = "Can not evaluate using this score, check it's not empty";
        }
        else if (score <= 0) {
            complexity = "NO CATEGORY";
            explanation = "This is not really a reading material";
        }
        else if (score <= 3) {
            complexity = "BASIC";
            explanation = "Learning to read books";
        }
        else if (score <= 6) {
            complexity = "BASIC";
            explanation = "New to reading, can read something simple like 'The Gruffalo'";
        }
        else if (score <= 9) {
            complexity = "AVERAGE";
            explanation = "Moderate reader, the majority. Can read something like 'Harry Potter', short blogs, social media, email";
        }
        else if (score <= 12) {
            complexity = "AVERAGE";
            explanation = "Confident reader. Can read something like 'Jurassic Park', in-depth blogs, ebooks";
        }
        else if (score <= 15) {
            complexity = "SKILLED";
            explanation = "Advanced reader. Can read something like 'A brief history of time', whitepaper books";
        }
        else{
            complexity = "SKILLED";
            explanation = "Proficient reader. Can read everything, including academic papers";
        }

        String fleschKincaidEvaluation = score + ";" + complexity + ": " + explanation;
        return fleschKincaidEvaluation;
    }

    /**
     * Interprets the Flesch Readability Ease score for a text
     * @param score - readability score returned by calcFlesch method
     * @return interpretation message
     */
    public  String interpretFlesch(float score){
        String explanation;


        if (Float.isNaN(score)){
            explanation = "Can not evaluate using this score, check it's not empty";
        }
        else if (score <= 0) {
            explanation = "Can not evaluate using this score; not suitable for reading";
        } else if (score < 30) {
            explanation = "Very difficult, C2 Mastery level.";
        } else if (score < 50) {
            explanation = "Fairly difficult, C1 Advanced level";
        } else if (score < 60) {
            explanation = "Plain English, B2 Upper-Intermediate level";
        } else if (score < 70) {
            explanation = "Fairly easy, B1 Intermediate level";
        } else if (score < 80) {
            explanation = "Easy, A2 Elementary level";
        } else {
            explanation = "Very easy, A1 Beginners level";
        }

        return score + ";" + explanation;
    }


}