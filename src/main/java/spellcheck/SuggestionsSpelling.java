package spellcheck;

import vocabulary.Vocabulary;
import vocabulary.VocabularyBuilder;
import vocabulary.VocabularyLoader;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

/**
 * An implementation of spellchecker using character distance algorithm
 * @author Elena Khasanova
 * @version 1.0;
 * */

public class SuggestionsSpelling implements Spellcheker {

    // reference dictionary
    private final VocabularyBuilder dic;

    public SuggestionsSpelling(VocabularyBuilder dic) {
        this.dic = dic;
    }

    public static void main(String[] args) {
        //
        String word = "ai";
        VocabularyBuilder dict = new Vocabulary();
        String filename = "data/dict.txt";
        VocabularyLoader.loadVocabulary(dict, filename);
        //vocabulary vb = new vocabulary(filename);
        //HashSet<String> dictionary = dict.getVocab();
        SuggestionsSpelling w = new SuggestionsSpelling(dict);
        List<String> l = w.distanceOne(word, true);
        System.out.println("One away word Strings for for \"" + word + "\" are:");
        System.out.println(l + "\n");
//
        word = "tailo";
        List<String> suggest = w.getSuggestions(word, 10);
        System.out.println("Spelling Suggestions for \"" + word + "\" are:");
        System.out.println(suggest);

    }

    /**
     * Add to the currentList Strings that are one character mutation away from the input string.
     *
     * @param input       - the original String
     * @param currentList - the list of words to append modified words
     * @param wordsOnly   - controls whether to return only words or any String
     * @return
     */
    private void oneCharSubstitution(String input, List<String> currentList, boolean wordsOnly) {
        // for each letter in the s and for all possible replacement characters
        for (int index = 0; index < input.length(); index++) {
            for (int charCode = (int) 'a'; charCode <= (int) 'z'; charCode++) {
                // permute characters in the string
                StringBuffer sb = new StringBuffer(input);
                sb.setCharAt(index, (char) charCode);

                // if the item isn't in the list, isn't the original string,
                // if wordsOnly is true, and it is a valid word, add it to the list
                if (!currentList.contains(sb.toString()) && (!wordsOnly || dic.isWord(sb.toString()))
                   && !input.equals(sb.toString())) {

                    currentList.add(sb.toString());
                }
            }
        }
    }

    /**
     * Add to the currentList Strings that are one character insertion away
     * from the input string.
     *
     * @param input       - the original String
     * @param currentList - the list of words to append modified words
     * @param wordsOnly   - controls whether to return only words or any String
     * @return
     */
    private void oneCharInsertions(String input, List<String> currentList, boolean wordsOnly) {
        for (int index = 0; index < input.length() + 1; index++) {
            for (int charCode = (int) 'a'; charCode <= (int) 'z'; charCode++) {
                // permute characters in the string
                StringBuffer sb = new StringBuffer(input);
                sb.insert(index, (char) charCode);

                // if the item isn't in the list, isn't the original string,
                // if wordsOnly is true, and it is a valid word, add it to the list
                if (!currentList.contains(sb.toString()) && (!wordsOnly || dic.isWord(sb.toString()))
                    && !input.equals(sb.toString())) {

                    currentList.add(sb.toString());
                }
            }
        }
    }

    /**
     * Add to the currentList Strings that are one character deletion away
     * from the input string.
     *
     * @param input       - the original String
     * @param currentList - the list of words to append modified words
     * @param wordsOnly   - controls whether to return only words or any String
     * @return
     */
    private void oneCharDeletions(String input, List<String> currentList, boolean wordsOnly) {
        for (int index = 0; index < input.length(); index++) {
            // permute characters in the string
            StringBuffer sb = new StringBuffer(input);
            sb.deleteCharAt(index);
            // if the item isn't in the list, isn't the original string,
            // if wordsOnly is true, and it is a valid word, add it to the list
            if (!currentList.contains(sb.toString()) && (!wordsOnly || dic.isWord(sb.toString()))
               && !input.equals(sb.toString())) {

                currentList.add(sb.toString());
            }
        }
    }

    /**
     * Return the list of Strings that are one modification away from the input string.
     *
     * @param input     - the original String
     * @param wordsOnly - controls whether to return only words or any String
     * @return list of Strings which are one char away from the original string
     */
    private List<String> distanceOne(String input, boolean wordsOnly) {
        List<String> retList = new ArrayList<>();
        oneCharInsertions(input, retList, wordsOnly);
        oneCharSubstitution(input, retList, wordsOnly);
        oneCharDeletions(input, retList, wordsOnly);
        return retList;
    }

    /**
     * Add to the suggestions Strings that are one modification away from the input string
     *
     * @param word   - the misspelled word
     * @param numSuggestions - the maximum number of suggestions to return
     * @return the list of spelling suggestions
     */
    @Override
    public List<String> getSuggestions(String word, int numSuggestions) {
        // String to explore;  a queue to store words to explore
        List<String> queue = new LinkedList<>();
        // visited strings, to avoid exploring the same string multiple times
        HashSet<String> visited = new HashSet<>();
        // list of suggestions
        List<String> suggestions = new LinkedList<>();
        boolean wordsOnly = true;
        // insert first node;
        queue.add(word);
        visited.add(word);
        // while the queue has elements and we need more suggestions
        while (!queue.isEmpty() && suggestions.size() < numSuggestions) {
            //remove the word from the start of the queue and assign to curr
            String curr = queue.remove(0);
            // get a list of strings one mutation away from current
            List<String> neighbors = distanceOne(curr, wordsOnly);
            // for each string in the list of neighbors
            for (String n : neighbors) {
                //if n is not visited
                if (!visited.contains(n)) {
				    // add n to the visited set
                    visited.add(n);
				    // add n to the back of the queue
                    queue.add(n);
                }
                // if n is a word in the dictionary	, add n to the list of words to return
                if (dic.isWord(n) && !suggestions.contains(n) && suggestions.size() < numSuggestions) {

                    suggestions.add(n);
                }
            }
        }
        return suggestions;
    }

}

