package document;

/**
 *  Interface to record the methods requested by a user when pressing "Show statistics" button in the GUI.
 *  The interface itself is never called and used to conveniently separate the methods for basic and extended
 *  analysis of the text. The first to be updated in the next release.
 * @author Elena Khasanova
 * @version 1.0;
 * **/

public interface ExtendedTextStatistics {

    /** Calculates the number of occurrences of the words of major parts of speech**/
    void getPosStatistics();

    /** Calculates the number of occurrences of certain grammar forms**/
    void getGrammarStatistics();

}
