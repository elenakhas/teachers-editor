package vocabulary;

import java.util.TreeSet;

public class DictionaryBST implements VocabularyBuilder {

    private final TreeSet<String> dict;
    //private String filename;

    public DictionaryBST(String filename) {
        //this.filename = filename;
        this.dict = new TreeSet<>();
    }

    /**
     * Add this word to the dictionary.  Convert it to lowercase first
     * for the assignment requirements.
     *
     * @param word The word to add
     * @return true if the word was added to the dictionary
     * (it wasn't already there).
     */
    public boolean addWord(String word) {
        word = word.toLowerCase();
        dict.add(word);
        return dict.contains(word);
    }


    /**
     * Return the number of words in the dictionary
     */
    public int size() {
        return dict.size();
    }

    /**
     * Is this a word according to this dictionary?
     */
    public boolean isWord(String s) {
        s = s.toLowerCase();
        return dict.contains(s);
    }

}
