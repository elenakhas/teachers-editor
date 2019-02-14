package document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;


/**
 *  Represents a text document - string content of the file, and its methods.
 *  Extended by ReadingText class
 * @author Elena Khasanova
 * @version 1.2;
 *
 */
public abstract class Document implements TextEvaluator {

    public String content;
    public List<String> words;
    public HashMap<String, Integer> levelWords;
    public HashMap<String, Integer> frequency;
    private int numWords;
    private int numSyllables;
    private int numSentences;

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
        for (int i = 0; i < characters.length; i++) { // nightingale
            // if the vowel is the last silent e and is not the only syllable, substract 1
            if (i == characters.length-1 && characters[i] == 'e' && ifSyllable && num > 0) {
                num--;
            }
//          if the word ends with le, and the letter before is a consonant, add 1
           if (characters.length > 2 && i == characters.length - 1 && characters[i] == 'e' && characters[i-1] == 'l' &&
                   vowels.indexOf(characters[i-2])<0 && ifSyllable) {
                num++;
            }
            // if the char is a vowel and the previous letter is not (it is not a diphtong or a triphtong), increment the count
            if (ifSyllable && vowels.indexOf(characters[i]) >= 0) {
                ifSyllable = false;
                num++;
            }
            // if the letter is not a vowel, set the previous letter as syllable
            else if (vowels.indexOf(characters[i]) < 0) {
                ifSyllable = true;
            }
        }

        return num;
    }
    /**
     * Check if a string is a word (not punctuation)
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

    // get all words in this document
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
    public float getFleschScore() { //
        float score = (float) (206.835 - 1.015 * ((float) getNumWords() / (float) getNumSentences())
                        - 84.6 * ((float) getNumSyllables() / (float) getNumWords()));
        return score;
    }

    /**
     * Calculate Flesch-Kincaid level readability score
     * @return float score
     */

    public float fleschKincaid() {
        float score = (float) ((0.39 * ((float) getNumWords() / (float) getNumSentences()))
                        + (11.8 * ((float) getNumSyllables() / (float) getNumWords())) - 15.59);

        return score;
    }

    /**
     * @param text
     * @param vocab
     * @return HashMap from a word in a level vocabulary to its frequency in the text file
     * these words are highlighted in the TextArea. We exclude articles from the count as they
     * are frequent but do not contribute to understanding if the text is of an appropriate level
     */
    public HashMap<String, Integer> wordsOfLevel(List<String> text, HashSet<String> vocab) {
        //levelWords.clear();
        this.levelWords = new HashMap();
        for (String word : text) {
            word = word.toLowerCase();
            if (vocab.contains(word) && !word.equals("a") && !word.equals("the")) {
                Integer occurences = this.levelWords.get(word);
                levelWords.put(word, (occurences == null) ? 1 : occurences + 1);
            }
        }
        return this.levelWords;
    }

    /**
     * Gives the percentage of words of a certain level
     * */

    public float percentWordsOfLevel() {
        int sum = 0;
        for (int value : levelWords.values()) {
            sum += value;
        }
        float result = (float)sum / getNumWords() * 100;
        return result;
    }

    /**
     * Gives the percentage of unique words of a certain level in a text
     * @return
     */
    public float uniqueWordsOfALevel(){
        float result = (float)this.levelWords.size() / getNumWords() * 100;
        return result;
    }
    // returns words and their frequencies; words are converted to lowercase
    public HashMap<String, Integer> frequencyOfWords(List<String> text) {
        this.frequency = new HashMap<>();
        for (String word:text) {
            word = word.toLowerCase();
            Integer occurences = frequency.get(word);
            frequency.put(word, (occurences == null) ? 1 : occurences + 1);
        }
        return this.frequency;
    }

    public abstract String interpretFleshKincaid(double fleschKincaid);


    @Override
    public int wordsVariety(HashMap<String, Integer> frequency) {
        return frequency.size();
    }

    // GET LEMMATIZED VERSIONS FOR THE WORDS IN THE DOCUMENT//
    // THINK ABOUT A CLASSIFIER - TOPICS
}