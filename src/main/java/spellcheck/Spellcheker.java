package spellcheck;

import java.util.List;

public interface Spellcheker {

    List<String> getSuggestions(String word, int numSuggestions);

}