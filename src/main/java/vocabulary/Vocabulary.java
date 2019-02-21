package vocabulary;

import java.util.HashSet;
/**
 * This class represents vocabulary (wordlist or a reference dictionary)
 * @author Elena Khasanova
 * @version 1.1;
 */

public class Vocabulary implements VocabularyBuilder {

    private final HashSet<String> vocab;  // all words from a given wordlist / dictionary
    //public String filename; // wordlist / dictionary file
    //private String s;

    /**
     * load vocabulary once the object is constructed
     */
    public Vocabulary() {
        //this.filename = filename;
        this.vocab = new HashSet<>();
    }




    public HashSet<String> getVocab() {
 //       loadVocabulary();
        return this.vocab;
    }

    /**
     * convert to lowercase for search purposes
     *
     * @param word The word to add
     * @return true if the word was added to the dictionary
     * (it wasn't already there).
     */
    public boolean addWord(String word) {
        return vocab.add(word.toLowerCase());
    }

    /**
     * Is this a word according to this dictionary?
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

//    public static void main(String[] args) {
//        vocabulary v = new vocabulary("data/pet.txt");
//        //LoadFile.loadDictionary(d, filename);
//        System.out.println(v.size());
//    }
}
