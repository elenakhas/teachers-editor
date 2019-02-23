package spellcheck;

import java.util.List;


/**
 *  Interface representing spelling checking functionality
 * @author Elena Khasanova
 * @version 1.0;
 * **/
public interface Spellcheker {

    /** Returns the list of possible replacements of the word containing mistaked **/

    List<String> getSuggestions(String word, int numSuggestions);

}