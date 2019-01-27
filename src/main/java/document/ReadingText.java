package document;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class ReadingText extends Document {

    public String filename;
    private String content;
    private int numSyllables;
    private int numWords;
    private int numSentences;
    public String fleschKincaidEvaluation;
    public HashMap<String, Integer> levelWords;
    public HashMap<String, Integer> frequency;

    public ReadingText() {
    }

    public ReadingText(String text) {
        this.content = content;
    }

    // returns string content of the file
    public String getContent() throws IOException {
        String s = new String(Files.readAllBytes(Paths.get(filename)), StandardCharsets.UTF_8);
        return s;
    }

    public int getNumSyllables(List<String> words) {
        for (String word : words) {
            numSyllables = numSyllables + getSyllables(word);
        }
        return numSyllables;
    }

    /**
     * Check if a string is a word
     * @param sentence
     * @return boolean
     */
    private boolean isWord(String sentence) {
        return !(sentence.indexOf("!") >= 0 || sentence.indexOf(".") >= 0 || sentence.indexOf("?") >= 0);
    }

    /**
     * Calculate and save properties of the text in class variables, get all the words from the text as a list
     * @param content
     * @return list of words from the text
     */
    public List<String> getProperties(String content) {
        Tokenizer tkn = new Tokenizer();
        List<String> tokens = tkn.tokenize("[!?.]+|[a-zA-Z]+", content);
        List<String> words = new ArrayList<>();
        for (int i = 0; i < tokens.size(); i++) {
            if (isWord(tokens.get(i))) {
                numWords++;
                words.add(tokens.get(i));
                numSyllables += getSyllables((tokens.get(i)));
            }
            if (isWord(tokens.get(i)) == true && i == tokens.size() - 1) {
                numSentences++;
            }
            if (isWord(tokens.get(i)) == false) {
                numSentences++;
            }
        }
        return words;
    }

    /**
     * Return number of words in a text
     * @return
     */
    public int getNumWords() {
        return numWords;
    }

    /**
     * Return number of sentences
     * @return
     */
    public int getNumSentences() {
        return numSentences;
    }

    /**
     * Return number of syllables
     * @return
     */
    public int getNumSyllables() {
        return numSyllables;
    }

    /**
     * Interprets the Flesch-Kincaid grade readability score for a text
     * @param score
     * @return
     */
    public String interpretFleshKincaid(double score) {
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

        fleschKincaidEvaluation = complexity + ": " + explanation;
        return fleschKincaidEvaluation;
    }

    /**
     * Calculates the percentage of the words of a given level in a given text
     * @param
     * @return
     */
    public double percentOfLevelWords() {
        return levelWords.size() / numWords;
    }
    public static void main(String[] args) throws IOException {
        ReadingText text = new ReadingText();
        text.filename = "data/testsentences.txt";
        String content = text.getContent();
        System.out.println(content);
        List<String> words = text.getWords(content);
        System.out.println("Printing the processing thing");
        System.out.println(text.getProperties(content));
        System.out.println(text.numSyllables);
        System.out.println(text.numWords);
        System.out.println(text.numSentences);
        System.out.println(text.getFleschScore());
        System.out.println(text.fleschKincaid());
        System.out.println(text.interpretFleshKincaid(text.fleschKincaid()));
        //System.out.printl

    }

}