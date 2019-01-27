package spellcheck;

import java.util.List;

public interface Autocompleter {
    List<String> predictedCompletions(String stem, int numCompletions);
}
