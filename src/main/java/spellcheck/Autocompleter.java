package spellcheck;

import java.util.List;

public interface Autocompleter {
        public List<String> predictedCompletions(String stem, int numCompletions);
    }
