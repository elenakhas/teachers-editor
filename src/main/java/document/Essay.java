package document;

import vocabulary.VocabularyBuilder;

import java.util.List;

public class Essay extends Document {

    private int spellingMistakes;

    public Essay(String content) {
        super(content);
    }

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

    public int unknownWords(List<String> words, VocabularyBuilder vocab){
        for (String word : words) {
            if (vocab.isWord(word)) {
                spellingMistakes++;
            }
        }
            return spellingMistakes;
    }

    // other methods:
    // show POS
    // show words repeated in one paragraph
    // number of unique words
    // number of spelling mistakes
}
