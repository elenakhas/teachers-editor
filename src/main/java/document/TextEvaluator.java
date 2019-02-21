package document;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

interface TextEvaluator {

// basic evaluator

    int getNumWords();

    int getNumSentences();

    int getNumSyllables();

    float getFleschScore();

    float fleschKincaid();

    HashMap<String, Integer> wordsOfLevel(List<String> text, HashSet<String> vocab);

    float percentWordsOfLevel(); // number of words of a certain level

    int wordsVariety(); // proportion of unique words in a text

}
