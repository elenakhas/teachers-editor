package spellcheck;

import java.util.LinkedList;
import java.util.List;

public class AutocompleteOptions implements Autocompleter {

    /**
     * A trie data structure that implements the Dictionary and the AutoComplete
     */

        private TrieNode root;
        //private int numNodes;

        public AutocompleteOptions()
        {
            root = new TrieNode();
        }

        /** Insert a word into the trie.*
         * @return true if the word was successfully added or false if it already exists
         * in the dictionary.
         */
        public boolean addWord(String word) {
            word = word.toLowerCase();
            // create a char array for the word
            char[] word_array = word.toCharArray();
            // find the stem - iterate over the characters in the word and check if they are in the trie
            // if ch is not in the trie, add it, link to the previous one, increment the size
            TrieNode curr = root;
            for (int i = 0; i < word_array.length; i++) {
                TrieNode child = curr.getChild(word_array[i]);
                if(child == null) {
                    curr.insert(word_array[i]);
                 //   numNodes++;
                }
                curr = curr.getChild(word_array[i]);
            }
            //if the current node ends word - return False, it already existed
            if (curr.endsWord()) {
                return false;
            }
            curr.setEndsWord(true); //set the last added node to be the end of the word
            return true;
        }


        /**
         * Return the number of words in the dictionary.
         */
        public int size()
        {
            int numWords = 0;
            //traverse the tree, check if the node ends word, increment the size;
            // Pre-order traversal from one node down, check if there are words
            LinkedList <TrieNode> q = new LinkedList();
            q.add(root);
            while (!q.isEmpty()) {
                TrieNode curr = q.remove();
                if(curr != null) {
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


        /** Returns whether the string is a word in the trie*/
        public boolean isWord(String s)
        {
            if(s.equals("")) {
                return false;
            }
            s = s.toLowerCase();
            // create a char array for the string
            char[] s_array = s.toCharArray();
            // find the stem - iterate over the characters in the word and check if they are in the trie
            // if ch is in the trie, get the next one
            //if ch is not in the trie, return false
            TrieNode curr = root;
            for (int i = 0; i < s_array.length; i++) {
                if(curr.getChild(s_array[i]) == null) {
                    return false; // if child equals null
                }
                curr = curr.getChild(s_array[i]);
            }
            if (curr.endsWord()) {
                return true;
            }
            return false;
        }

        /**
         * Return a list, in order of increasing word length,
         * containing the numCompletions - shortest legal completions
         * of the stem string. All legal completions are  valid words in the
         * dictionary. If the stem itself is a valid word, it is included
         * in the list of returned words.
         * If this stem is not in the trie, it returns an empty list.
         *
         * @param stem The text to use at the word stem
         * @param numCompletions The maximum number of predictions desired.
         * @return A list containing the up to numCompletions best predictions*/
        public List<String> predictCompletions(String stem, int numCompletions)
        {
            // I. Find the stem in the trie.  If the stem does not appear in the trie, return an empty list
            LinkedList<TrieNode> queue = new LinkedList();
            //    Create a list of completions to return (initially empty)
            List<String> completions = new LinkedList();
            List<String> emptyList = new LinkedList();
            TrieNode curr = root;
            char[] stemChar = stem.toCharArray();
            for(char ch : stemChar) {
                if (curr.getChild(ch)== null) {
                    return emptyList;
                }
                curr = curr.getChild(ch);
            }

            // II. Once the stem is found, perform a breadth first search to generate completions:
            //    Create a queue (LinkedList) and add the node that completes the stem to the back
            //       of the list.
            queue.add(curr);
            //    While the queue is not empty and you don't have enough completions:
            while(!queue.isEmpty() && completions.size() < numCompletions){
//           remove the first Node from the queue
                TrieNode toRemove = queue.remove();
                //       If it is a word, add it to the completions list
                if (toRemove.endsWord() == true) {
                    completions.add(toRemove.getText());
                }
                //       Add all of its child nodes to the back of the queue
                for (Character c : toRemove.getValidNextCharacters()) {
                    queue.add(toRemove.getChild(c));
                }
                // Return the list of completions
            }
            return completions;
        }
        // For debugging
        public void printTree()
        {
            printNode(root);
        }

        /** Do a pre-order traversal from this node down */
        public void printNode(TrieNode curr)
        {
            if (curr == null)
                return;

            System.out.println(curr.getText());

            TrieNode next = null;
            for (Character c : curr.getValidNextCharacters()) {
                next = curr.getChild(c);
                printNode(next);
            }
        }
    // Return the list of completions
    public List<String> predictedCompletions(String stem, int numCompletions) {
        return null;
    }
}

