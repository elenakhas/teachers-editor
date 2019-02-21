package spellcheck;

import vocabulary.Vocabulary;
import vocabulary.VocabularyBuilder;
import vocabulary.VocabularyLoader;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

public class SuggestionsSpelling implements Spellcheker {
    // THRESHOLD to limit long searching
    private static final int THRESHOLD = 1000;
    private final VocabularyBuilder dic;
    private int spellingMistakes;

    public SuggestionsSpelling(VocabularyBuilder dic) {//vocabulary vb = new vocabulary(filename);
        this.dic = dic;
        // this.dic = dic;
    }

    public static void main(String[] args) {
//	   //basic testing code to get started
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
     * Add to the currentList Strings that are one character mutation away
     * from the input string.
     *
     * @param input       The original String
     * @param currentList is the list of words to append modified words
     * @param wordsOnly   controls whether to return only words or any String
     * @return
     */
    private void oneCharSubstitution(String input, List<String> currentList, boolean wordsOnly) {
        // for each letter in the s and for all possible replacement characters
        for (int index = 0; index < input.length(); index++) {
            for (int charCode = (int) 'a'; charCode <= (int) 'z'; charCode++) {
                // use StringBuffer for an easy interface to permuting the letters in the String
                StringBuffer sb = new StringBuffer(input);
                sb.setCharAt(index, (char) charCode);

                // if the item isn't in the list, isn't the original string, and
                // (if wordsOnly is true) is a real word, add to the list
                if (!currentList.contains(sb.toString()) &&
                        (!wordsOnly || dic.isWord(sb.toString())) &&
                        !input.equals(sb.toString())) {
                    currentList.add(sb.toString());
                }
            }
        }
    }

    /**
     * Add to the currentList Strings that are one character insertion away
     * from the input string.
     *
     * @param input       The original String
     * @param currentList is the list of words to append modified words
     * @param wordsOnly   controls whether to return only words or any String
     * @return
     */
    private void oneCharInsertions(String input, List<String> currentList, boolean wordsOnly) {
        for (int index = 0; index < input.length() + 1; index++) {
            for (int charCode = (int) 'a'; charCode <= (int) 'z'; charCode++) {
                // use StringBuffer for an easy interface to permuting the
                // letters in the String
                StringBuffer sb = new StringBuffer(input);
                sb.insert(index, (char) charCode);

                // if the item isn't in the list, isn't the original string, and
                // (if wordsOnly is true) is a real word, add to the list
                if (!currentList.contains(sb.toString()) &&
                        (!wordsOnly || dic.isWord(sb.toString())) &&
                        !input.equals(sb.toString())) {
                    currentList.add(sb.toString());
                }
            }
        }
    }


    /**
     * Add to the currentList Strings that are one character deletion away
     * from the input string.
     *
     * @param input       The original String
     * @param currentList is the list of words to append modified words
     * @param wordsOnly   controls whether to return only words or any String
     * @return
     */
    private void oneCharDeletions(String input, List<String> currentList, boolean wordsOnly) {
        for (int index = 0; index < input.length(); index++) {
            StringBuffer sb = new StringBuffer(input);
            sb.deleteCharAt(index);
            // if the item isn't in the list, isn't the original string, and
            // (if wordsOnly is true) is a real word, add to the list
            if (!currentList.contains(sb.toString()) &&
                    (!wordsOnly || dic.isWord(sb.toString())) &&
                    !input.equals(sb.toString())) {
                currentList.add(sb.toString());
            }
        }
    }

    /**
     * Return the list of Strings that are one modification away
     * from the input string.
     *
     * @param input     The original String
     * @param wordsOnly controls whether to return only words or any String
     * @return list of Strings which are nearby the original string
     */
    private List<String> distanceOne(String input, boolean wordsOnly) {
        List<String> retList = new ArrayList<>();
        oneCharInsertions(input, retList, wordsOnly);
        oneCharSubstitution(input, retList, wordsOnly);
        oneCharDeletions(input, retList, wordsOnly);
        return retList;
    }

    /**
     * Add to the currentList Strings that are one character deletion away
     * from the input string.
     *
     * @param word           The misspelled word
     * @param numSuggestions is the maximum number of suggestions to return
     * @return the list of spelling suggestions
     */
    @Override
    public List<String> getSuggestions(String word, int numSuggestions) {
//		Input:  word for which to provide number of spelling suggestions
//		Input:  number of maximum suggestions to provide
//		Output: list of spelling suggestions
        // initial variables
        List<String> queue = new LinkedList<>();     // String to explore;  create a queue to hold words to explore
        HashSet<String> visited = new HashSet<>();   // to avoid exploring the same
        // string multiple times
        List<String> retList = new LinkedList<>();   // words to return
        boolean wordsOnly = true;
        // insert first node;
        queue.add(word);
        visited.add(word);
//		while the queue has elements and we need more suggestions
        while (!queue.isEmpty() && retList.size() < numSuggestions) {
//			  remove the word from the start of the queue and assign to curr
            String curr = queue.remove(0);
            // get a list of neighbors (strings one mutation away from curr)
            List<String> neighbors = distanceOne(curr, wordsOnly);
//			  for each n in the list of neighbors
            for (String n : neighbors) {
                //if n is not visited
                if (!visited.contains(n)) {
//				       add n to the visited set
                    visited.add(n);
//				    add n to the back of the queue
                    queue.add(n);
                }
//			       if n is a word in the dictionary
//		          add n to the list of words to return
                if (dic.isWord(n) && !retList.contains(n) && retList.size() < numSuggestions) {
                    retList.add(n);
                }
            }


        }

//		return the list of real words

        return retList;

    }

}

