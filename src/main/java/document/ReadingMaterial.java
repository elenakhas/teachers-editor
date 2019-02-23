package document;

/**
 *  Class represents a text used for reading exercises
 *  Extends AbstractDocument with an interpretation, does not evaluate spelling mistakes
 *  of FleschKincaid score for a reading text.
 * @author Elena Khasanova
 * @version 1.2;
 *
 */

public class ReadingMaterial extends AbstractDocument {

    public ReadingMaterial(String content) {
        super(content);
    }

    /**
     * Interprets the Flesch-Kincaid grade readability score for a text
     * @param score - readability score returned by getFleschKincaid method
     * @return interpretation message
     */
    public String interpretFleshKincaid(float score) {
        String complexity = null;
        String explanation = null;

        if (score <= 0) {
            complexity = "NO CATEGORY";
            explanation = "This is not really a reading material";
        }
        if (score >= 0 && score <= 3) {
            complexity = "BASIC";
            explanation = "Learning to read books";
        }
        if (score >= 3 && score <= 6) {
            complexity = "BASIC";
            explanation = "New to reading, can read something simple like 'The Gruffalo'";
        }
        if (score >= 6 && score <= 9) {
            complexity = "AVERAGE";
            explanation = "Moderate reader, the majority. Can read something like 'Harry Potter', short blogs, social media, email";
        }
        if (score >= 9 && score <= 12) {
            complexity = "AVERAGE";
            explanation = "Confident reader. Can read something like 'Jurassic Park', in-depth blogs, ebooks";
        }
        if (score >= 12 && score <= 15) {
            complexity = "SKILLED";
            explanation = "Advanced reader. Can read something like 'A brief history of time', whitepaper books";
        }
        if (score >= 15) {
            complexity = "SKILLED";
            explanation = "Proficient reader. Can read everything, including academic papers";
        }

        String fleschKincaidEvaluation = complexity + ": " + explanation;
        return fleschKincaidEvaluation;
    }


}