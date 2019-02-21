package document;

import org.junit.Before;
import org.junit.Test;
import vocabulary.Vocabulary;
import vocabulary.VocabularyLoader;

import static org.junit.Assert.assertEquals;

public class VocabularyTest {

    private String largeVocabFile = "data/dict.txt";

    Vocabulary emptyVocab;
    Vocabulary smallVocab;
    Vocabulary largeVocab;

    @Before
    public void setUp() throws Exception {
        VocabularyLoader vl = new VocabularyLoader();
        emptyVocab = new Vocabulary();
        smallVocab = new Vocabulary();
        largeVocab = new Vocabulary();

        smallVocab.addWord("Hello");
        smallVocab.addWord("HElLo");
        smallVocab.addWord("help");
        smallVocab.addWord("a");
        smallVocab.addWord("ubiquitous");

        VocabularyLoader.loadVocabulary(largeVocab, largeVocabFile);
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
        assertEquals("Testing isWord on empty: Hello", false, emptyVocab.isWord("Hello"));
        assertEquals("Testing isWord on small: Hello", true, smallVocab.isWord("Hello"));
        assertEquals("Testing isWord on large: Hello", true, largeVocab.isWord("Hello"));

        assertEquals("Testing isWord on small: hello", true, smallVocab.isWord("hello"));
        assertEquals("Testing isWord on large: hello", true, largeVocab.isWord("hello"));

        assertEquals("Testing isWord on small: hellow", false, smallVocab.isWord("hellow"));
        assertEquals("Testing isWord on large: hellow", false, largeVocab.isWord("hellow"));

        assertEquals("Testing isWord on empty: empty string", false, emptyVocab.isWord(""));
        assertEquals("Testing isWord on small: empty string", false, smallVocab.isWord(""));
        assertEquals("Testing isWord on large: empty string", false, largeVocab.isWord(""));

        assertEquals("Testing isWord on small: no", false, smallVocab.isWord("no"));
        assertEquals("Testing isWord on large: no", true, largeVocab.isWord("no"));

        assertEquals("Testing isWord on small: subsequent", true, smallVocab.isWord("ubiquitous"));
        assertEquals("Testing isWord on large: subsequent", true, largeVocab.isWord("ubiquitous"));


    }

    /**
     * Test the addWord method
     */
    @Test
    public void addWord() {
        assertEquals("Asserting hellow is not in empty dict", false, emptyVocab.isWord("hellow"));
        assertEquals("Asserting hellow is not in small dict", false, smallVocab.isWord("hellow"));
        assertEquals("Asserting hellow is not in large dict", false, largeVocab.isWord("hellow"));

        emptyVocab.addWord("hellow");
        smallVocab.addWord("hellow");
        largeVocab.addWord("hellow");

        assertEquals("Asserting hellow is in empty dict", true, emptyVocab.isWord("hellow"));
        assertEquals("Asserting hellow is in small dict", true, smallVocab.isWord("hellow"));
        assertEquals("Asserting hellow is in large dict", true, largeVocab.isWord("hellow"));

        assertEquals("Asserting xyzabc is not in empty dict", false, emptyVocab.isWord("xyzabc"));
        assertEquals("Asserting xyzabc is not in small dict", false, smallVocab.isWord("xyzabc"));
        assertEquals("Asserting xyzabc is in large dict", false, largeVocab.isWord("xyzabc"));


        emptyVocab.addWord("XYZAbC");
        smallVocab.addWord("XYZAbC");
        largeVocab.addWord("XYZAbC");

        assertEquals("Asserting xyzabc is in empty dict", true, emptyVocab.isWord("xyzabc"));
        assertEquals("Asserting xyzabc is in small dict", true, smallVocab.isWord("xyzabc"));
        assertEquals("Asserting xyzabc is large dict", true, largeVocab.isWord("xyzabc"));


        assertEquals("Testing isWord on empty: empty string", false, emptyVocab.isWord(""));
        assertEquals("Testing isWord on small: empty string", false, smallVocab.isWord(""));
        assertEquals("Testing isWord on large: empty string", false, largeVocab.isWord(""));

        assertEquals("Testing isWord on small: no", false, smallVocab.isWord("no"));
        assertEquals("Testing isWord on large: no", true, largeVocab.isWord("no"));

        assertEquals("Testing isWord on small: subsequent", true, smallVocab.isWord("ubiquitous"));
        assertEquals("Testing isWord on large: subsequent", true, largeVocab.isWord("ubiquitous"));

    }
}