package document;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;


/**
 *  A class for getting the string content of the text file; will be updated with the methods treating
 *  various formats of the file
 * @author Elena Khasanova
 * @version 1.0;
 * **/
public class FileContent {

    private final String filename;

    public FileContent(String filename){
        this.filename = filename;
    }

    /**
     * Reads a text character by character from a file and returns its string content
     * @return s - string content of the file
     * @throws IOException
     */

    String message;
    public String getContent() {
        String s = null;
        try {
            s = new String(Files.readAllBytes(Paths.get(filename)), StandardCharsets.UTF_8);
        }
        catch (IOException e) {
            message = "Problem reading a file: " + filename;
        }
        return s;
    }

}
