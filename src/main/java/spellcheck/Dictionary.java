package spellcheck;

// word look-up dictionary for spellcheck suggestions
public interface Dictionary {

    //  String word - the word we are looking at
    // returns True if the word was added to the dictionary
     boolean addWord(String word);
     // returns True if the word is a word
     boolean isWord(String word);
     // returns the size of the dictionary
     int size();
}