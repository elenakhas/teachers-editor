package spellcheck;

import java.util.List;


/**
 *  Interface representing spelling checking functionality
 * @author Elena Khasanova
 * **/
public interface Spellcheker {

    /** Returns the list of possible replacements of the word containing mistaked
     * @param word - a String to check
     * @param numSuggestions - the desired number of options to suggest
     * @return List of spelling suggestions
     * **/

    List<String> getSuggestions(String word, int numSuggestions);

}