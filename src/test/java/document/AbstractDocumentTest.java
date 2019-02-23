package document;

import org.junit.Before;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import vocabulary.Vocabulary;
import vocabulary.VocabularyLoader;

import java.io.IOException;
import java.util.*;

import static java.lang.Float.isNaN;
import static org.junit.Assert.*;

/**
 * Test class for AbstractDocument
 * @author Elena Khasanova
 * @version 1.1;
 */

public class AbstractDocumentTest {


    private AbstractDocument docOne;
    private AbstractDocument docTwo;
    private AbstractDocument emptyDoc;
    private HashSet<String> vocab;

    @Before
    public void setUp() throws Exception {
        FileContent fc = new FileContent("src/test/test_data/testsentences.txt");
        docOne = new ReadingMaterial(fc.getContent());
        docTwo = new ReadingMaterial("A cat sat on a mat. Did you see that cat?");
        emptyDoc = new ReadingMaterial("");
        Vocabulary voc = new Vocabulary();
        VocabularyLoader.loadVocabulary(voc, "src/test/test_data/test_vocabulary");
        this.vocab = voc.getVocab();
    }

    @Test
    public void testGetContent() throws IOException {
        ExpectedException exception = ExpectedException.none();
        exception.expect(IOException.class);

        assertEquals("Check it returns the right content ", "A cat sat on a mat. Did you see that cat?", docTwo.getContent());
        assertEquals("Check it returns the right content ", "", emptyDoc.getContent());
        assertEquals("Check it returns the right content ",
                    "This is the test. This is a very good test!!!! " +
                            "Why??? The number of sentences? It should be 5! Or maybe six... Or even seven",
                    docOne.getContent());

    }
    @Test
    public void testGetSyllables() {
        // The content here is not important because we call the method on a word,
        // so an empty document is used just for accessing the methods
        // check empty string
        assertEquals("Check the number of syllables", 0, emptyDoc.wordGetSyllables(""));
        // check one vowel string
        assertEquals("Check the number of syllables", 1, emptyDoc.wordGetSyllables("aaaaaaaaaaaaa"));
        // if the vowel is the last silent e and is not the only syllable
        assertEquals("Check the number of syllables", 2, emptyDoc.wordGetSyllables("sentence"));
        // if the vowel is the last silent e and it is the only syllable
        assertEquals("Check the number of syllables", 1, emptyDoc.wordGetSyllables("e"));
        assertEquals("Check the number of syllables", 1, emptyDoc.wordGetSyllables("the"));
        // if the char is a vowel and the previous letter is not (it is not a diphtong or a triphtong), increment the count
        assertEquals("Check the number of syllables", 4, emptyDoc.wordGetSyllables("bolorbolo"));
        // if the word ends with le, and the letter before is a consonant
        assertEquals("Check the number of syllables", 2, emptyDoc.wordGetSyllables("people"));
        // if the word ends with le, and the letter before is a vowel
        assertEquals("Check the number of syllables", 3, emptyDoc.wordGetSyllables("nightingale"));
        // check diphthongs and triphtongs
        assertEquals("Check the number of syllables", 2, emptyDoc.wordGetSyllables("goodbye"));
        assertEquals("Check the number of syllables", 3, emptyDoc.wordGetSyllables("beautiful"));
    }

    @Test // tested in getter methods
    public void testGetProperties() throws IOException {
        // test on a document from a file
        List<String> expectedDoc = Arrays.asList("This", "is", "the", "test", "This", "is", "a", "very", "good", "test",
                "Why", "The", "number", "of", "sentences", "It", "should", "be", "Or", "maybe", "six", "Or", "even", "seven");
        assertEquals("Check two lists are the same", expectedDoc, docOne.getWords());
        assertEquals("Check number of words", 24, docOne.getNumWords());
        assertEquals("Check number of sentences", 7, docOne.getNumSentences());
        assertEquals("Check number of syllables", 30, docOne.getTotalNumSyllables());
        // test on an empty document
        List<String> expectedEmptyDoc = Collections.emptyList();
        assertEquals("Check two lists are the same", expectedEmptyDoc, emptyDoc.getWords());
        assertEquals("Check number of words", 0, emptyDoc.getNumWords());
        assertEquals("Check number of sentences", 0, emptyDoc.getNumSentences());
        assertEquals("Check number of syllables", 0, emptyDoc.getTotalNumSyllables());
        // test on a document with many commas and dots
        AbstractDocument commas = new ReadingMaterial("sentence, with, lots, of, commas.!" + "\n" +
                "(And some-thing else)).  The output is: 11.5.");
        List<String> expectedCommas = Arrays.asList("sentence", "with", "lots", "of",
                "commas", "And", "some", "thing", "else", "The", "output","is");
        assertEquals("Check two lists are the same", expectedCommas, commas.getWords());
        assertEquals("Check number of words", 12, commas.getNumWords());
        assertEquals("Check number of sentences", 4, commas.getNumSentences());
        assertEquals("Check number of syllables", 15, commas.getTotalNumSyllables());
    }

    @Test
    public void testGetFleschScore() {
        // check the document from file
        assertEquals("Check the Flesch Score", (float)97.605, docOne.calcFleschScore(), 0.02);
        // check the empty document
        assertTrue("Check the Flesch Score", isNaN(emptyDoc.calcFleschScore()));
        // check the document with one word, negative value
        AbstractDocument oneWord = new ReadingMaterial("Oneworddocument"); //
        assertEquals("Check the Flesch Score", -301.78, oneWord.calcFleschScore(), 0.02);
    }

    @Test
    public void testFleschKincaid() {
        // check the document from file
        assertEquals("Check the Flesch-Kincaid Score", (float)0.49, docOne.calcFleschKincaid(), 0.02);
        // check the empty document
        assertTrue("Check the Flesch-Kincaid Score", isNaN(emptyDoc.calcFleschScore()));
    }

    @Test
    public void testWordsOfLevel() {
        // check that the returned words and frequencies are correct
        List<String> text = Arrays.asList("I", "love", "eating", "apple", "Apple", "potato", "soup", "soup", "Soup");
        // construct a vocabulary HashSet
        // we test it on emptyDoc as we pass the content and the wordlist separately
        HashMap<String, Integer> wordsFromVocab = emptyDoc.wordsOfLevel(text, vocab);
        HashMap<String, Integer> result = new HashMap<>();
        result.put("apple", 2);
        result.put("potato", 1);
        result.put("soup", 3);
        assertEquals("Check the content of the HashMap", result, wordsFromVocab);
    }

    @Test
    public void testPercentWordsOfLevel() {
        // should return correct percentage of words of a certain level
        // call the method wordsOfLevel to initialize the variable used in PercentWordsOfLevel method
        HashMap<String, Integer> levelWords = docOne.wordsOfLevel(docOne.getWords(), vocab);
        float result = docOne.percentWordsOfLevel();
        assertEquals("Check the percentage is the same", 25.0, result, 0.02);
    }

    @Test
    public void testFrequencyOfWords() {
        HashMap frequency = docOne.frequencyOfWords(docOne.getWords());
        // check that the correct value is returned for several keys; words include both
        // capitalized and non-capitalized ones
        assertEquals("Check the frequency is correct", 1, frequency.get("maybe"));
        assertEquals("Check the frequency is correct", 2, frequency.get("this"));
        // if the word is not in the document, assert its frequency is null
        assertNull("Check the frequency is correct", frequency.get("cat"));

        //check on an empty doc - all values should be null
        HashMap freqEmpty = emptyDoc.frequencyOfWords(emptyDoc.getWords());
        assertNull("Check the frequency is correct", freqEmpty.get("cat"));

    }

    @Test
    public void testWordsVariety(){
        int variety = docOne.frequencyOfWords(docOne.getWords()).size();
        assertEquals("Check the frequency is correct", 19, variety);




    }


}