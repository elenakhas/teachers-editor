package vocabulary;
/**
 *  Interface representing the functionality of a vocabulary object
 * @author Elena Khasanova
 * @version 1.2;
 *
 */
public interface VocabularyBuilder {

    /**
     * Add this word to the vocabulary.
     * @param word The word to add
     * @return true if the word was added to the dictionary(it wasn't already there).
     */

    boolean addWord(String word);

    /**
     * Checks if the string is a word according to this dictionary
     * @param s - string to check
     * @return True if it is contained in the dictionary
     */
    boolean isWord(String s);

    /**
     * @return the number of words in the vocabulary
     */
    int size();

}