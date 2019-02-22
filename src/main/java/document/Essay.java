package document;

import vocabulary.VocabularyBuilder;

import java.util.List;


/**
 *  Represents a document of a type Essay, a student's written work
 *  Extends AbstractDocument
 * @author Elena Khasanova
 * @version 1.0;
 * **/

public class Essay extends AbstractDocument {

    private int spellingMistakes;

    public Essay(String content) {
        super(content);
    }

    /** Gives explanation of Flesch-Kincaid score for an essay
     * @param score - double value computed by getFleschKincaid method
     * @return fleschKincaidEvaluation - string containing interpretation of a score
     * **/

    @Override
    public String interpretFleshKincaid(double score) {
        String complexity = null;
        String explanation = null;

        if (score <= 3) {
            complexity = "NO CATEGORY";
            explanation = "This is not an essay. Please, resubmit your work.";
        }
        if (score >= 3 && score <= 6) {
            complexity = "BASIC";
            explanation = "Elementary writing";
        }
        if (score >= 6 && score <= 9) {
            complexity = "AVERAGE";
            explanation = "Pre-Intermediate writing";
        }
        if (score >= 9 && score <= 12) {
            complexity = "AVERAGE";
            explanation = "Intermediate writing";
        }
        if (score >= 12 && score <= 15) {
            complexity = "SKILLED";
            explanation = "Advanced writing";
        }
        if (score >= 15) {
            complexity = "SKILLED";
            explanation = "High-level writing";
        }

        String fleschKincaidEvaluation = complexity + ": " + explanation;
        return fleschKincaidEvaluation;
    }

    /** Calculates the number of spelling mistakes and words not contained in the reference dictionary of a spellcheker
     * @param words - List of words in a document
     * @return spellingMistakes - integer, count of mistaken words
     * **/
    public int unknownWords(List<String> words, VocabularyBuilder vocab){
        for (String word : words) {
            if (vocab.isWord(word)) {
                spellingMistakes++;
            }
        }
            return spellingMistakes;
    }

}
