package document;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;

/**
 * This class represents vocabulary (wordlist or a reference dictionary) and its methods
 */
public class Vocabulary {

    private HashSet<String> vocab;  // all words from a given wordlist / dictionary
    public String filename; // wordlist / dictionary file
    //private String s;

    /**
     * load vocabulary once the object is constructed
     */
    public Vocabulary(String filename) {
        this.filename = filename;
        this.vocab = new HashSet<String>();
        loadVocabulary();
    }

    private void loadVocabulary() {
        //this.vocab = new HashSet<String>();
        // Dictionary files have 1 word per line
        try {
            String nextWord;
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            while ((nextWord = reader.readLine()) != null) {
                vocab.add(nextWord.toLowerCase());
            }
        } catch (IOException e) {
            System.err.println("Problem loading the file: " + filename);
            e.printStackTrace();
        }
    }

    public HashSet<String> getVocab() {
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
//        Vocabulary v = new Vocabulary("data/pet.txt");
//        //LoadFile.loadDictionary(d, filename);
//        System.out.println(v.size());
//    }
}
