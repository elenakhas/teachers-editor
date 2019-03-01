package vocabulary;

import java.util.TreeSet;

/**
 * Represents a reference dictionary implementing a TreeSet data structure
 * @author Elena Khasanova
 * @version 1.0;
 */

public class DictionaryTree implements VocabularyBuilder {

    // TreeSet to store the dictionary entries
    private final TreeSet<String> dict;

    public DictionaryTree(String filename) {
        this.dict = new TreeSet<>();
    }

    /**
     * Add this word to the dictionary. Does not handle capitals.*
     * @param word - the word to add
     * @return true if the word was added to the dictionary
     * */
    public boolean addWord(String word) {
        dict.add(word.toLowerCase());
        return dict.contains(word);
    }


    /**
     * Return the number of words in the dictionary
     */
    public int size() {
        return dict.size();
    }

    /**
     * Checks if the string is a word according to this dictionary
     * @param s - string to check
     * @return True if it is contained in the dictionary
     */
    public boolean isWord(String s) {
        return dict.contains(s.toLowerCase());
    }

}
