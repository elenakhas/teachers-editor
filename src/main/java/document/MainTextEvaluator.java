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

public interface MainTextEvaluator {

    /** Returns the number of words in the text**/
    int getNumWords();

    /** Returns the number of sentences in the text**/
    int getNumSentences();

    /** Returns the total number of syllables in the text**/
    int getTotalNumSyllables();

    /** Calculates the Flesch Reading Ease score**/
    float calcFleschScore();

    /** Calculates the Flesch-Kincaid Reading Ease score**/
    float calcFleschKincaid();

    /** Returns the words of a certain level and their frequencies**/
    HashMap<String, Integer> wordsOfLevel(List<String> text, HashSet<String> vocab);

    /** Caclulates the percentage words of a certain level in a text**/
    float percentWordsOfLevel();

    /** Calculates the number of different words in a text**/
    int wordsVariety();

}
