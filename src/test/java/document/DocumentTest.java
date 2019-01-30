package document;

import org.junit.Before;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static java.lang.Float.isNaN;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class DocumentTest {


    private Document doc;
    private Document docOne;
    private Document emptyDoc;

    @Before
    public void setUp() throws Exception {
        FileContent fc = new FileContent("src/test/test_data/testsentences.txt");
        doc = new ReadingText(fc.getContent());
        docOne = new ReadingText("A cat sat on a mat. Did you see that cat?");
        emptyDoc = new ReadingText("");
    }

    @Test
    public void testGetContent() throws IOException {
        ExpectedException exception = ExpectedException.none();
        exception.expect(IOException.class);

        assertEquals("Check it returns the right content ", "A cat sat on a mat. Did you see that cat?", docOne.getContent());
        assertEquals("Check it returns the right content ", "", emptyDoc.getContent());
        assertEquals("Check it returns the right content ",
                    "This is the test. This is a very good test!!!! " +
                            "Why??? The number of sentences? It should be 5! Or maybe six... Or even seven",
                    doc.getContent());

    }
    @Test
    public void testGetSyllables() {
        // The content here is not important because we call the method on a word,
        // so an empty document is used just for accessing the methods
        // check empty string
        assertEquals("Check the number of syllables", 0, emptyDoc.getSyllables(""));
        // check one vowel string
        assertEquals("Check the number of syllables", 1, emptyDoc.getSyllables("aaaaaaaaaaaaa"));
        // if the vowel is the last silent e and is not the only syllable
        assertEquals("Check the number of syllables", 2, emptyDoc.getSyllables("sentence"));
        // if the vowel is the last silent e and it is the only syllable
        assertEquals("Check the number of syllables", 1, emptyDoc.getSyllables("e"));
        assertEquals("Check the number of syllables", 1, emptyDoc.getSyllables("the"));
        // if the char is a vowel and the previous letter is not (it is not a diphtong or a triphtong), increment the count
        assertEquals("Check the number of syllables", 4, emptyDoc.getSyllables("bolorbolo"));
        // if the word ends with le, and the letter before is a consonant
        assertEquals("Check the number of syllables", 2, emptyDoc.getSyllables("people"));
        // if the word ends with le, and the letter before is a vowel
        assertEquals("Check the number of syllables", 3, emptyDoc.getSyllables("nightingale"));
        // check diphthongs and triphtongs
        assertEquals("Check the number of syllables", 2, emptyDoc.getSyllables("goodbye"));
        assertEquals("Check the number of syllables", 3, emptyDoc.getSyllables("beautiful"));
    }

    @Test // tested in getter methods
    public void testGetProperties() throws IOException {
        // test on a document from a file
       // String content = doc.getContent();
       // doc.getProperties(content);
        List<String> expectedDoc = Arrays.asList("This", "is", "the", "test", "This", "is", "a", "very", "good", "test",
                "Why", "The", "number", "of", "sentences", "It", "should", "be", "Or", "maybe", "six", "Or", "even", "seven");
        assertEquals("Check two lists are the same", expectedDoc, doc.getWords());
//        System.out.println(doc.getNumWords());
        assertEquals("Check number of words", 24, doc.getNumWords());
        assertEquals("Check number of sentences", 7, doc.getNumSentences());
        assertEquals("Check number of syllables", 30, doc.getNumSyllables());
        // test on an empty document
        List<String> expectedEmptyDoc = Arrays.asList();
        assertEquals("Check two lists are the same", expectedEmptyDoc, emptyDoc.getWords());
        assertEquals("Check number of words", 0, emptyDoc.getNumWords());
        assertEquals("Check number of sentences", 0, emptyDoc.getNumSentences());
        assertEquals("Check number of syllables", 0, emptyDoc.getNumSyllables());
        // test on a document with many commas and dots
        Document commas = new ReadingText("sentence, with, lots, of, commas.!" + "\n" +
                "(And some-thing else)).  The output is: 11.5.");
        List<String> expectedCommas = Arrays.asList("sentence", "with", "lots", "of",
                "commas", "And", "some", "thing", "else", "The", "output","is");
        assertEquals("Check two lists are the same", expectedCommas, commas.getWords());
        assertEquals("Check number of words", 12, commas.getNumWords());
        assertEquals("Check number of sentences", 4, commas.getNumSentences());
        assertEquals("Check number of syllables", 15, commas.getNumSyllables());
    }

    @Test
    public void testGetFleschScore() {
        // check the document from file
        assertEquals("Check the Flesch Score", (float)97.605, doc.getFleschScore(), 0.02);
        // check the empty document
        assertTrue("Check the Flesch Score", isNaN(emptyDoc.getFleschScore()));
        // check the document with one word, negative value
        Document oneWord = new ReadingText("Oneworddocument"); //
        System.out.println(oneWord.getNumSyllables());
        assertEquals("Check the Flesch Score", -301.78, oneWord.getFleschScore(), 0.02);
    }

    @Test
    public void testFleschKincaid() {
        // check the document from file
        assertEquals("Check the Flesch-Kincaid Score", (float)2.177, doc.fleschKincaid(), 0.02);
        // check the empty document
        assertTrue("Check the Flesch Score", isNaN(emptyDoc.getFleschScore()));
        // check the document with one word, negative value
        Document oneWord = new ReadingText("Oneworddocument");
        assertEquals("Check the Flesch Score", -54.82, oneWord.fleschKincaid(), 0.02);
    }

    @Test
    public void wordsOfLevel() {
    }

    @Test
    public void wordsOfALevel() {
    }

    @Test
    public void frequencyOfWords() {
    }
}