package document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 *  Represents a text document - string content of the file, and its methods
 * @author Elena Khasanova
 * @version 1.1;
 *
 */
public abstract class Document {


    private String filename;
    private String content;
    public HashMap<String, Integer> levelWords;
    public HashMap<String, Integer> frequency;

    /**
     * Create a new document from the given file
     */
    //initialize the constructor
    public Document() {
    }

    public Document(String content) {
        this.content = content;
    }

    // method to get the text content of the file
    public String getContent() throws IOException {
        return this.content;
    }

    // the following methods will be used for calculating readability score

    // Y is considered a vowel
    public int getSyllables(String word) {
        int num = 0;   //
        char[] characters = word.toCharArray();
        // if the previous character is a vowel that forms a syllable
        boolean ifSyllable = true;
        String vowels = "aeiouyAEIOUY";
        for (int i = 0; i < characters.length; i++) {
            // if the vowel is the last silent e and is not the only syllable, substract 1
            if (i == characters.length - 1 && characters[i] == 'e' && ifSyllable && num > 0) {
                num--;
            }
            // if the char is a vowel and the previous letter is not (it is not a diphtong or a triphtong), increment the count
            if (ifSyllable && vowels.indexOf(characters[i]) >= 0) {
                ifSyllable = false;
                num++;
            }
            // if the word ends with le, and the letter before is a consonant, add 1
            if (i == characters.length - 1 && characters[i] == 'e' && !ifSyllable) {
                num++;
            }
            // if the letter is not a vowel, set the previous letter as syllable
            if (vowels.indexOf(characters[i]) < 0) {
                ifSyllable = true;
            }
        }

        return num;
    }
/// don't need this method
    public ArrayList<String> getWords(String content) {
        Tokenizer tkn = new Tokenizer();
        ArrayList words = tkn.tokenize("[a-zA-Z]+", content);
        return words;
    }

    /**
     * Return the number of words in this document
     */
    public abstract int getNumWords();

    /**
     * Return the number of sentences in this document
     */
    public abstract int getNumSentences();

    /**
     * Return the number of syllables in this document
     */
    public abstract int getNumSyllables();

    /**
     * return the Flesch readability score of this document
     */
    public double getFleschScore() {
        double score = 206.835 - 1.015 * ((double) getNumWords() / (double) getNumSentences())
                - 84.6 * ((double) getNumSyllables() / (double) getNumWords());
        return score;
    }

    public double fleschKincaid() {
        double score = (0.39 * ((double) getNumWords() / (double) getNumSentences()))
                - (11.8 * ((double) getNumSyllables() / (double) getNumWords()) - 15.59);
        return score;
    }

    /**
     * @param text
     * @param vocab
     * @return HashMap from a word in a level vocabulary to its frequency in the text file
     */
    public HashMap<String, Integer> frequencyLevel(List<String> text, HashSet<String> vocab) {
        this.levelWords = new HashMap();
        for (String word:text) {
            if (vocab.contains(word)) {
                levelWords = frequency(text);
            }
        }
        return this.levelWords;
    }

    // calculates the number of words in a set that contains the words from vocabulary in a text
    public int wordsOfALevel() {
        return levelWords.size();
    }

    // returns words and their frequencies
    public HashMap<String, Integer> frequency(List<String> text) {
        HashMap <String, Integer> frequency = new HashMap<>();
        for (String word:text) {
            Integer occurences = frequency.get(word);
            frequency.put(word, (occurences == null) ? 1 : occurences + 1);
        }
        return frequency;
    }
}