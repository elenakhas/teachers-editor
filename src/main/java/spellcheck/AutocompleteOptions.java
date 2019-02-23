package spellcheck;

import vocabulary.VocabularyBuilder;

import java.util.LinkedList;
import java.util.List;

/**
 *  An implementation of autocomplete using a trie structure
 * @author Elena Khasanova
 * @version 1.0;
 * */

@SuppressWarnings({"unchecked", "ForLoopReplaceableByForEach"})
public class AutocompleteOptions implements Autocompleter, VocabularyBuilder {

    /**
     * A trie data structure to store suggestions for autocomplete
     */
    private final TrieNode root;

    public AutocompleteOptions() {
        root = new TrieNode();
    }

    /**
     * Insert a word into the trie to update the dictionary
     * @param word A word to be added
     * @return true if the word was successfully added or false if it already exists
     * in the dictionary.
     */
    public boolean addWord(String word) {
        word = word.toLowerCase();
        // create a char array for the word
        char[] word_array = word.toCharArray();
        // find the stem - iterate over the characters in the word and check if they are in the trie
        // if ch is not in the trie, add it, link to the previous one
        TrieNode curr = root;
        for (char c : word_array) {
            TrieNode child = curr.getChild(c);
            if (child == null) {
                curr.insert(c);
            }
            curr = curr.getChild(c);
        }
        //if the current node ends word - return False, it already existed
        if (curr.endsWord()) {
            return false;
        }
        //set the last added node to be the end of the word
        curr.setEndsWord(true);
        return true;
    }


    /**
     * Return the number of words in the dictionary.
     */
    public int size() {
        int numWords = 0;
        //traverse the tree, check if the node ends word, increment the size;
        // Pre-order traversal from one node down, check if there are words
        LinkedList<TrieNode> q = new LinkedList();
        q.add(root);
        while (!q.isEmpty()) {
            TrieNode curr = q.remove();
            if (curr != null) {
                if (curr.endsWord()) {
                    numWords++;
                }
                for (Character c : curr.getValidNextCharacters()) {
                    q.add(curr.getChild(c));
                }
            }
        }
        return numWords;
    }


    /**
     * Returns whether the string is a word in the trie
     * @param s An input string
     * @return true if the string is a word
     */
    public boolean isWord(String s) {
        if (s.equals("")) {
            return false;
        }
        s = s.toLowerCase();
        // create a char array for the string
        char[] s_array = s.toCharArray();
        // find the prefix - iterate over the characters in the word and check if they are in the trie
        // if ch is in the trie, get the next one
        // if ch is not in the trie, return false
        TrieNode curr = root;
        for (char c : s_array) {
            if (curr.getChild(c) == null) {
                return false; // if child equals null
            }
            curr = curr.getChild(c);
        }
        return curr.endsWord();
    }

    /**
     * Return a list, in order of increasing word length, containing the numCompletions - shortest legal completions
     * of the prefix string. All legal completions are  valid words in the dictionary.
     * If the prefix is itself a valid word , it is included in the list of returned words.
     * If this prefix is not in the trie, it returns an empty list.
     *
     * @param prefix  - the text to use as the prefix
     * @param numCompletions - the maximum number of predictions desired
     * @return A list containing the up to numCompletions best predictions
     */
    public List<String> predictCompletions(String prefix, int numCompletions) {
        // Find the prefix in the trie.  If the prefix does not appear in the trie, return an empty list
        LinkedList<TrieNode> queue = new LinkedList();
        // Create a list of completions to return (initially empty)
        List<String> completions = new LinkedList();
        List<String> emptyList = new LinkedList();
        TrieNode curr = root;
        char[] stemChar = prefix.toCharArray();
        for (char ch : stemChar) {
            if (curr.getChild(ch) == null) {
                return emptyList;
            }
            curr = curr.getChild(ch);
        }

        // Once the stem is found, perform a breadth first search to generate completions:
        // Create a queue (LinkedList) and add the node that completes the stem to the back of the list.
        queue.add(curr);
        //    While the queue is not empty and you don't have enough completions:
        while (!queue.isEmpty() && completions.size() < numCompletions) {
            // remove the first Node from the queue
            TrieNode toRemove = queue.remove();
            // If it is a word, add it to the completions list
            if (toRemove.endsWord()) {
                completions.add(toRemove.getText());
            }
            // Add all of its child nodes to the back of the queue
            for (Character c : toRemove.getValidNextCharacters()) {
                queue.add(toRemove.getChild(c));
            }
            // Return the list of completions
        }
        return completions;
    }
}

