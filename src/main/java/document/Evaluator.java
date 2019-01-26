package document;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public interface Evaluator {

    public int getNumWords();
    public int getNumSentences();
    public int getNumSyllables();
    public double getFleschScore();
    public double fleschKincaid();
    public HashMap<String, Integer> frequencyLevel(List<String> text, HashSet<String> vocab);
    public int wordsOfALevel();
    public HashMap<String, Integer> frequency(List<String> text);


}
