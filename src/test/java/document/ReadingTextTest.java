package document;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

@SuppressWarnings("FieldCanBeLocal")
public class ReadingTextTest {
    private ReadingMaterial textKET;
    private ReadingMaterial textTOEFL;

    @Test
    public void testInterpretFleshKincaid() throws IOException {
        // assure that it returns the right score for texts of different categories
        FileContent fc = new FileContent("src/test/test_data/textKET");
        textKET = new ReadingMaterial(fc.getContent());
        String interpretation = textKET.interpretFleshKincaid(textKET.calcFleschKincaid());
        assertEquals("Check the interpretation matches the complexity",
                "AVERAGE: Moderate reader, the majority. Can read something like 'Harry Potter', short blogs, social media, email", interpretation);

        FileContent fico = new FileContent("src/test/test_data/textTOEFL");
        textTOEFL = new ReadingMaterial(fico.getContent());
        String interpret = textTOEFL.interpretFleshKincaid(textTOEFL.calcFleschKincaid());
        assertEquals("Check the interpretation matches the complexity",
                "AVERAGE: Confident reader. Can read something like 'Jurassic Park', in-depth blogs, ebooks", interpret);
    }
}