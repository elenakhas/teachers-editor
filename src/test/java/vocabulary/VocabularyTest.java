package vocabulary;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class VocabularyTest {

    private Vocabulary emptyVocab;
    private Vocabulary smallVocab;
    private Vocabulary largeVocab;

    @Before
    public void setUp() throws Exception {
        emptyVocab = new Vocabulary();
        String emptyVocabFile = "data/emptyVocab.txt";
        VocabularyLoader.loadVocabulary(emptyVocab, emptyVocabFile);
        smallVocab = new Vocabulary();
        largeVocab = new Vocabulary();
        String largeVocabFile = "data/dict.txt";
        VocabularyLoader.loadVocabulary(largeVocab, largeVocabFile);

        smallVocab.addWord("Hello");
        smallVocab.addWord("HElLo");
        smallVocab.addWord("help");
        smallVocab.addWord("a");
        smallVocab.addWord("ubiquitous");

    }

    /**
     * Test if the size method is working correctly.
     */
    @Test
    public void testSize() {
        assertEquals("Testing size for empty dict", 0, emptyVocab.size());
        assertEquals("Testing size for small dict", 4, smallVocab.size());
        assertEquals("Testing size for large dict", 97723, largeVocab.size());
    }

    /**
     * Test the isWord method
     */
    @Test
    public void testIsWord() {
        assertFalse("Testing isWord on empty: Hello", emptyVocab.isWord("Hello"));
        assertTrue("Testing isWord on small: Hello", smallVocab.isWord("Hello"));
        assertTrue("Testing isWord on large: Hello", largeVocab.isWord("Hello"));

        assertTrue("Testing isWord on small: hello", smallVocab.isWord("hello"));
        assertTrue("Testing isWord on large: hello", largeVocab.isWord("hello"));

        assertFalse("Testing isWord on small: hellow", smallVocab.isWord("hellow"));
        assertFalse("Testing isWord on large: hellow", largeVocab.isWord("hellow"));

        assertFalse("Testing isWord on empty: empty string", emptyVocab.isWord(""));
        assertFalse("Testing isWord on small: empty string", smallVocab.isWord(""));
        assertFalse("Testing isWord on large: empty string", largeVocab.isWord(""));

        assertFalse("Testing isWord on small: no", smallVocab.isWord("no"));
        assertTrue("Testing isWord on large: no", largeVocab.isWord("no"));

        assertTrue("Testing isWord on small: subsequent", smallVocab.isWord("ubiquitous"));
        assertTrue("Testing isWord on large: subsequent", largeVocab.isWord("ubiquitous"));


    }

    /**
     * Test the addWord method
     */
    @Test
    public void addWord() {
        assertFalse("Asserting hellow is not in empty dict", emptyVocab.isWord("hellow"));
        assertFalse("Asserting hellow is not in small dict", smallVocab.isWord("hellow"));
        assertFalse("Asserting hellow is not in large dict", largeVocab.isWord("hellow"));

        emptyVocab.addWord("hellow");
        smallVocab.addWord("hellow");
        largeVocab.addWord("hellow");

        assertTrue("Asserting hellow is in empty dict", emptyVocab.isWord("hellow"));
        assertTrue("Asserting hellow is in small dict", smallVocab.isWord("hellow"));
        assertTrue("Asserting hellow is in large dict", largeVocab.isWord("hellow"));

        assertFalse("Asserting xyzabc is not in empty dict", emptyVocab.isWord("xyzabc"));
        assertFalse("Asserting xyzabc is not in small dict", smallVocab.isWord("xyzabc"));
        assertFalse("Asserting xyzabc is in large dict", largeVocab.isWord("xyzabc"));


        emptyVocab.addWord("XYZAbC");
        smallVocab.addWord("XYZAbC");
        largeVocab.addWord("XYZAbC");

        assertTrue("Asserting xyzabc is in empty dict", emptyVocab.isWord("xyzabc"));
        assertTrue("Asserting xyzabc is in small dict", smallVocab.isWord("xyzabc"));
        assertTrue("Asserting xyzabc is large dict", largeVocab.isWord("xyzabc"));


        assertFalse("Testing isWord on empty: empty string", emptyVocab.isWord(""));
        assertFalse("Testing isWord on small: empty string", smallVocab.isWord(""));
        assertFalse("Testing isWord on large: empty string", largeVocab.isWord(""));

        assertFalse("Testing isWord on small: no", smallVocab.isWord("no"));
        assertTrue("Testing isWord on large: no", largeVocab.isWord("no"));

        assertTrue("Testing isWord on small: subsequent", smallVocab.isWord("ubiquitous"));
        assertTrue("Testing isWord on large: subsequent", largeVocab.isWord("ubiquitous"));

    }
}