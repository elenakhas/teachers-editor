package vocabulary;

import java.util.HashSet;
/**
 * This class represents vocabulary (wordlist or a reference dictionary)
 * @author Elena Khasanova
 * @version 1.1;
 */

public class Vocabulary implements VocabularyBuilder {

    // stores words from a given wordlist / dictionary
    private final HashSet<String> vocab;

    /**
     * load vocabulary once the object is constructed
     */
    public Vocabulary() {
        this.vocab = new HashSet<>();
    }

    /** Getter method for Vocabulary
     * @return vocabulary as a HashSet**/
    public HashSet<String> getVocab() {
        return this.vocab;
    }

    /**
     * Adds word to the dictionary
     * Convert wprd to lowercase for search purposes
     * @param word - the word to add
     * @return true if the word was added to the dictionary (it wasn't already there)
     */
    public boolean addWord(String word) {
        return vocab.add(word.toLowerCase());
    }

    /**
     * Checks if the string is a word according to this dictionary
     * @param s - string to check
     * @return True if it is contained in the dictionary
     */
    public boolean isWord(String s) {
        return vocab.contains(s.toLowerCase());
    }

    /**
     * Return the number of words in this vocabulary
     */
    public int size() {
        return vocab.size();
    }


}
