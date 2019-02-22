//package document;
//
//import org.junit.Before;
//import org.junit.Test;
//
//import java.io.IOException;
//import java.util.HashMap;
//
//import static org.junit.Assert.assertEquals;
//
//public class POStaggingTest {
//
//    private PosTagger pstg;
//    private String words;
//
//    @Before
//    public void setUp() {
//        pstg = new PosTagger();
//        words = "When Tamara was twenty years old, she started a cheaper university in Long Island. She should come. Jeux. She really enjoyed all her excellent time at university. There was a big university! The best, actually. Tamara's life was promptly great.";
//
//    }
//
//    @Test
//    public void testTagging() throws IOException {
//        HashMap empty = pstg.tagging("");
//        HashMap all = pstg.tagging(words);
//        System.out.println(all.toString());
//        assertEquals("Checking on empty string: it returns SYM", 1, empty.size());
//        assertEquals("Checking on a string", 29, all.size());
//        assertEquals("Checking that the key and value match", "JJ", all.get("excellent"));
//
//
//    }
//
//    @Test
//    public void simplifiedTags() throws IOException {
//        HashMap<String, String> all = pstg.simplifiedTags(words);
//
//        assertEquals("Check the custom tag is correct", "adv", all.get("really"));
//        assertEquals("Check the custom tag is correct", "num", all.get("twenty"));
//        assertEquals("Check the custom tag is correct", "det", all.get("all"));
//        assertEquals("Check the custom tag is correct", "there", all.get("There"));
//        assertEquals("Check the custom tag is correct", "other", all.get("Jeux"));
//        assertEquals("Check the custom tag is correct", "prepconj", all.get("at"));
//        assertEquals("Check the custom tag is correct", "adj", all.get("excellent"));
//        assertEquals("Check the custom tag is correct", "adj", all.get("cheaper"));
//        assertEquals("Check the custom tag is correct", "adj", all.get("best"));
//        assertEquals("Check the custom tag is correct", "verb", all.get("should"));
//        assertEquals("Check the custom tag is correct", "noun", all.get("apple"));
//        assertEquals("Check the custom tag is correct", "noun", all.get("Island"));
//        assertEquals("Check the custom tag is correct", "adj", all.get("many"));
//        assertEquals("Check the custom tag is correct", "other", all.get("Tamara's"));
//        assertEquals("Check the custom tag is correct", "pron", all.get("ourselves"));
//        assertEquals("Check the custom tag is correct", "pron", all.get("his"));
//        assertEquals("Check the custom tag is correct", "adv", all.get("promptly"));
//        assertEquals("Check the custom tag is correct", "adv", all.get("further"));
//        assertEquals("Check the custom tag is correct", "adv", all.get("highest"));
//        assertEquals("Check the custom tag is correct", "prepconj", all.get("by"));
//        assertEquals("Check the custom tag is correct", "other", all.get("&"));
//        assertEquals("Check the custom tag is correct", "prepconj", all.get("to"));
//        assertEquals("Check the custom tag is correct", "other", all.get("oh"));
//        assertEquals("Check the custom tag is correct", "verb", all.get("crash"));
//        assertEquals("Check the custom tag is correct", "verb", all.get("came"));
//        assertEquals("Check the custom tag is correct", "verb", all.get("coming"));
//        assertEquals("Check the custom tag is correct", "wh", all.get("what"));
//        assertEquals("Check the custom tag is correct", "wh", all.get("whoever"));
//        assertEquals("Check the custom tag is correct", "wh", all.get("however"));
//
//    }
//}