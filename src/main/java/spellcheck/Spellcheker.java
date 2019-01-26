package spellcheck;

import java.util.List;

public interface Spellcheker {

        public List<String> getSuggestions(String word, int numSuggestions);

    }