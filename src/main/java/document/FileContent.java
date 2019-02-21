package document;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileContent {

    private final String filename;

    public FileContent(String filename){
        this.filename = filename;
    }

    /**
     * Reads a text file and returns string content
     * @return
     * @throws IOException
     */
    public String getContent() {
        String s = null;
        try {
            s = new String(Files.readAllBytes(Paths.get(filename)), StandardCharsets.UTF_8);
        }
        catch (IOException e) {
            System.err.println("Problem reading a file: " + filename);
            e.printStackTrace();
        }
        return s;
    }

}
