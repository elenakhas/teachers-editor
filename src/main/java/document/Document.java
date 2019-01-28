package document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 *  Represents a text document - string content of the file, and its methods.
 *  Extended by TextForReading class
 * @author Elena Khasanova
 * @version 1.1;
 *
 */
public abstract class Document {

    public String content;
    public List<String> words;
    public HashMap<String, Integer> levelWords;
    public HashMap<String, Integer> frequency;
    public int numWords;
    public int numSyllables;
    public int numSentences;

    /**
     * Create a new document with the given content
     */
    //initialize the constructor
    public Document(String content) {
        this.content = content;
        getProperties(content);
    }

    // method to get the text content of the file
    public String getContent() throws IOException {
        return this.content;
    }

    // THE FOLLOWING METHODS WILL BE USED FOR CALCULATING READABILITY SCORE

    /**
     * Calculates the number of syllables in the text according to a set of rules.
     * Will be used for calculating readability scores.
     * @param word
     * @return
     */
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
        this.words = new ArrayList<>();
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
        return this.words;
    }

    public List<String> getWords(){
        return words;
    }

    /**
     * Return the number of words in this document
     */
    public int getNumWords(){
        return numWords;
    }

    /**
     * Return the number of sentences in this document
     */
    public int getNumSentences(){
        return numSentences;
    }

    /**
     * Return the number of syllables in this document
     */
    public int getNumSyllables(){
        return numSyllables;
    }

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
    public HashMap<String, Integer> wordsOfLevel(List<String> text, HashSet<String> vocab) {
        this.levelWords = new HashMap();
        for (String word : text) {
            if (vocab.contains(word)) {
                Integer occurences = this.levelWords.get(word);
                levelWords.put(word, (occurences == null) ? 1 : occurences + 1);
            }
        }
        return this.levelWords;
    }

    /**
     * Gives the percentage of words of a certain level
     * */  // Later - exclude articles

    public float wordsOfALevel() {
        return (float)this.levelWords.size() / getNumWords() * 100;
    }
    // returns words and their frequencies
    public HashMap<String, Integer> frequency(List<String> text) {
        this.frequency = new HashMap<>();
        for (String word:text) {
            Integer occurences = frequency.get(word);
            frequency.put(word, (occurences == null) ? 1 : occurences + 1);
        }
        return this.frequency;
    }

    public abstract String interpretFleshKincaid(double fleschKincaid);

}