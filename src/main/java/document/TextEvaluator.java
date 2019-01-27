package document;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public interface TextEvaluator {

    int getNumWords();

    int getNumSentences();

    int getNumSyllables();

    double getFleschScore();

    double fleschKincaid();

    HashMap<String, Integer> frequencyLevel(List<String> text, HashSet<String> vocab);

    int wordsOfALevel(); // number of words of a certain level

    HashMap<String, Integer> frequencyOfWords(List<String> text); // frequency of words in a text

    int variety(HashMap<String, Integer> words); // proportion of unique words in a text


}
