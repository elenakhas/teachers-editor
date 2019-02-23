package spellcheck;

import java.util.List;

/**
 *  Interface representing autocompleter
 * @author Elena Khasanova
 * @version 1.0;
 *
 **/

public interface Autocompleter {

    /** Returns the list of completions for a word being typed**/
    List<String> predictCompletions(String prefix, int numCompletions);
}
