package document;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

public class TokenizerTest {

    private Tokenizer tkn;

@Before
    public void setUp(){
    tkn = new Tokenizer();
    }
    @Test
    public void testTokenize() {
    // tokenize string into words
        ArrayList<String> simpleString = tkn.tokenize("[a-zA-Z]+", "This is the test string");
        assertEquals("Checking that words in the list are correct", Arrays.asList("This", "is", "the", "test", "string"), simpleString);
        // tokenize punctuation
        ArrayList<String > punctuation = tkn.tokenize("[!?.]+", "This is a sentence. " +
                "It contains lots .... Punctuation???? Yes!End punctuation!?.?!");
        assertEquals("Check that all punctuation marks are in the list and grouped", Arrays.asList(".", "....", "????",
                "!", "!?.?!"), punctuation);
        // check on empty string
        ArrayList<String> emptystring = tkn.tokenize("/d", "");
        assertEquals("Check it returns an empty list", Arrays.asList(), emptystring);
    }
}