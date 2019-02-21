package spellcheck;

import java.util.HashMap;
import java.util.Set;

public class TrieNode {

    private HashMap<Character, TrieNode> children;
    private String text;
    private boolean isWord;

    /**
     * Create a new TrieNode
     */
    public TrieNode() {
        children = new HashMap<Character, TrieNode>();
        text = "";
        isWord = false;
    }

    /**
     * Create a new TrieNode with a text String
     */
    private TrieNode(String text) {
        this();
        this.text = text;
    }

    /**
     * Return the child TrieNode from a given character
     *
     * @param ch The next character in the key
     * @return The TrieNode that is the next from this character; return null if there is no link to the next.
     */
    public TrieNode getChild(Character ch) {
        return children.get(ch);
    }

    /**
     * Inserts a character at this node.
     * Returns the new node if ch wasn't in the trie.  Otherwise, returns null.
     *
     * @param ch The character that will link to the new node
     * @return The new TrieNode, or null if the node is already in the trie.
     */
    public TrieNode insert(Character ch) {
        if (children.containsKey(ch)) {
            return null;
        }

        TrieNode next = new TrieNode(text + ch.toString());
        children.put(ch, next);
        return next;
    }

    /**
     * Return the text string at this node
     */
    public String getText() {
        return text;
    }

    /**
     * Set whether or not this node ends a word in the trie.
     */
    public void setEndsWord(boolean b) {
        isWord = b;
    }

    /**
     * Return whether or not this node ends a word in the trie.
     */
    public boolean endsWord() {
        return isWord;
    }

    /**
     * Return the set of characters that have links from this node
     */
    public Set<Character> getValidNextCharacters() {
        return children.keySet();
    }

}

