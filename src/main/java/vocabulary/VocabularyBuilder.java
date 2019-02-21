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
     *
     * @param word The word to add
     * @return true if the word was added to the dictionary(it wasn't already there).
     */

    boolean addWord(String word);

    /**
     * Is this a word according to this vocabulary?
     */
    boolean isWord(String s);

    /**
     * Return the number of words in the vocabulary
     */
    int size();

}