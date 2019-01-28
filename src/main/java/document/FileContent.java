package document;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileContent {

    private String filename;

    public FileContent(String filename){
        this.filename = filename;
    }

    /**
     * Reads a text file and returns string content
     * @return
     * @throws IOException
     */
    public String getContent() throws IOException {
        String s = new String(Files.readAllBytes(Paths.get(filename)), StandardCharsets.UTF_8);
        return s;
    }

}
