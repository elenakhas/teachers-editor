package document;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

@SuppressWarnings("FieldCanBeLocal")
public class ReadingTextTest {
    private ReadingText textKET;
    private ReadingText textTOEFL;

    @Test
    public void testInterpretFleshKincaid() throws IOException {
        // assure that it returns the right score for texts of different categories
        FileContent fc = new FileContent("src/test/test_data/textKET");
        textKET = new ReadingText(fc.getContent());
        String interpretation = textKET.interpretFleshKincaid(textKET.fleschKincaid());
        assertEquals("Check the interpretation matches the complexity",
                "AVERAGE: Moderate reader, the majority. Can read something like 'Harry Potter', short blogs, social media, email", interpretation);

        FileContent fico = new FileContent("src/test/test_data/textTOEFL");
        textTOEFL = new ReadingText(fico.getContent());
        String interpret = textTOEFL.interpretFleshKincaid(textTOEFL.fleschKincaid());
        assertEquals("Check the interpretation matches the complexity",
                "AVERAGE: Confident reader. Can read something like 'Jurassic Park', in-depth blogs, ebooks", interpret);
    }
}