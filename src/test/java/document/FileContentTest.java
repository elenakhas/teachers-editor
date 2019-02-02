package document;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class FileContentTest {

    @Test
    public void getContent() throws IOException {
        FileContent fc = new FileContent("src/test/test_data/testsentences.txt");
        String content = fc.getContent();
        assertEquals("Checks that content is returned:", "This is the test. " +
                "This is a very good test!!!! Why??? The number of sentences? " +
                "It should be 5! Or maybe six... Or even seven", content);
//        // check that there is an exception thrown when accessing a non-existing file
//        String filename = "src/test/sun.txt";
//        FileContent file = new FileContent(filename);
//        String contentOfFile = file.getContent();
//        System.out.println(contentOfFile);

    }
}