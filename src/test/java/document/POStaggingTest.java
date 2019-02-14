package document;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;

public class POStaggingTest {

    private POStagging pstg;
    private String[] emptyList;
    private String[] allList;

    @Before
    public void setUp() {
        pstg = new POStagging();
        emptyList = new String[0];
        allList = new String[]{"therefore", "twenty-one", "all",
                "there", "jeux", "below", "excellent", "cheaper",
                "cheapest", "should", "apple", "Americans",
                "many", "'s", "ourselves", "his", "promptly", "further",
                "highest", "by", "&", "to", "oh", "crash", "came", "coming",
                "what", "whoever", "however"};

    }

    @Test
    public void testTagging() throws IOException {
        HashMap empty = pstg.tagging(emptyList);
        HashMap all = pstg.tagging(allList);
        System.out.println(all.toString());
        assertEquals("Checking on empty array", 0, empty.size());
        assertEquals("Checking on full array", 29, all.size());
        assertEquals("Checking that the key and value match", "JJ", all.get("excellent"));


    }

    @Test
    public void simplifiedTags() throws IOException {
        HashMap<String, String> all = pstg.simplifiedTags(allList);

        assertEquals("Check the custom tag is correct", "adv", all.get("therefore"));
        assertEquals("Check the custom tag is correct", "num", all.get("twenty-one"));
        assertEquals("Check the custom tag is correct", "det", all.get("all"));
        assertEquals("Check the custom tag is correct", "there", all.get("there"));
        assertEquals("Check the custom tag is correct", "other", all.get("jeux"));
        assertEquals("Check the custom tag is correct", "prepconj", all.get("below"));
        assertEquals("Check the custom tag is correct", "adj", all.get("excellent"));
        assertEquals("Check the custom tag is correct", "adj", all.get("cheaper"));
        assertEquals("Check the custom tag is correct", "adj", all.get("cheapest"));
        assertEquals("Check the custom tag is correct", "verb", all.get("should"));
        assertEquals("Check the custom tag is correct", "noun", all.get("apple"));
        assertEquals("Check the custom tag is correct", "noun", all.get("Americans"));
        assertEquals("Check the custom tag is correct", "det", all.get("many"));
        assertEquals("Check the custom tag is correct", "other", all.get("'s"));
        assertEquals("Check the custom tag is correct", "pron", all.get("ourselves"));
        assertEquals("Check the custom tag is correct", "pron", all.get("his"));
        assertEquals("Check the custom tag is correct", "adv", all.get("promptly"));
        assertEquals("Check the custom tag is correct", "adv", all.get("further"));
        assertEquals("Check the custom tag is correct", "adv", all.get("highest"));
        assertEquals("Check the custom tag is correct", "prepconj", all.get("by"));
        assertEquals("Check the custom tag is correct", "other", all.get("&"));
        assertEquals("Check the custom tag is correct", "prepconj", all.get("to"));
        assertEquals("Check the custom tag is correct", "other", all.get("oh"));
        assertEquals("Check the custom tag is correct", "verb", all.get("crash"));
        assertEquals("Check the custom tag is correct", "verb", all.get("came"));
        assertEquals("Check the custom tag is correct", "verb", all.get("coming"));
        assertEquals("Check the custom tag is correct", "wh", all.get("what"));
        assertEquals("Check the custom tag is correct", "wh", all.get("whoever"));
        assertEquals("Check the custom tag is correct", "wh", all.get("however"));

    }
}