package spellcheck;

import java.util.List;

public interface Autocompleter {
    List<String> predictCompletions(String stem, int numCompletions);
}
