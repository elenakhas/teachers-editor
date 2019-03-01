package spellcheck;

import java.util.List;

/**
 *  Interface representing autocompleter
 * @author Elena Khasanova
 *
 **/

public interface Autocompleter {

    /** Returns the list of completions for a word being typed
     * @param prefix - the String to complete
     * @param numCompletions - a desired number of options to complete the prefix
     * @return List of valid completions
     * **/
    List<String> predictCompletions(String prefix, int numCompletions);
}
