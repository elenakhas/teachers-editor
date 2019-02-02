package document;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public interface TextEvaluator {

    int getNumWords();

    int getNumSentences();

    int getNumSyllables();

    float getFleschScore();

    float fleschKincaid();

    HashMap<String, Integer> wordsOfLevel(List<String> text, HashSet<String> vocab);

    float percentWordsOfLevel(); // number of words of a certain level



    HashMap<String, Integer> frequencyOfWords(List<String> text); // frequency of words in a text

    int wordsVariety(HashMap<String, Integer> words); // proportion of unique words in a text

/// add the function that returns the level based on several wordlists?

}
