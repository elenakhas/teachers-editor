package document;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 *  Interface to record the methods with output displayed in the main view of the GUI.
 *  The interface itself is never called and used to conveniently separate the methods for basic and extended
 *  analysis of the text.
 * @author Elena Khasanova
 * @version 1.0;
 * **/

public interface MainTextStatistics {

    /**
     * @return  the number of words in the text**/
    int getNumWords();

    /**
     * @return  the number of sentences in the text**/
    int getNumSentences();

    /** @return  the total number of syllables in the text**/
    int getTotalNumSyllables();

    /** Calculates the Flesch Reading Ease score
     * @return score as a float**/
    float calcFleschScore();

    /** Calculates the Flesch-Kincaid Reading Ease score
     * @return score as a float**/
    float calcFleschKincaid();

    /** Returns the words of a certain level and their frequencies
     * @param text - text to evaluate as a list of words
     * @param vocab - reference wordlist of a desired level
     * @return HashMap of words and their frequency of occurrence**/
    HashMap<String, Integer> wordsOfLevel(List<String> text, HashSet<String> vocab);

    /** Caclulates the percentage words of a certain level in a text
     * @param words - HashMap storing words of a certain level and their frequencies
     * @return percentage
     * **/
    float percentWordsOfLevel(HashMap<String, Integer> words);

    /** Calculates the number of different words in a text
     * @return - number of unique words**/
    int wordsVariety();

}
