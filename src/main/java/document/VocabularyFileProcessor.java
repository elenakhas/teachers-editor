package document;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// The class preprocesses a vocabulary file and writes it into a new file for further processing;
// will probably be a different program
public class VocabularyFileProcessor {
    /**
     * 1. The method reads a file from a given filename
     * and returns a string representation of the file
     *
     * @param filename
     * @return content String representation of a file
     * @throws IOException if file not found
     */
    public String getContent(String filename) throws IOException {
        String content = new String(Files.readAllBytes(Paths.get(filename)), StandardCharsets.UTF_8);
        return content;
    }

    /**
     * 2. The method tokenizes the string content of the
     *
     * @param pattern
     * @param content
     * @return tokens, an ArrayList containing the matched regex tokens
     */
    public ArrayList<String> tokenizer(String pattern, String content) {
        ArrayList tokens = new ArrayList();
        Pattern tokenSplitter = Pattern.compile(pattern);
        Matcher m = tokenSplitter.matcher(content);
        while (m.find()) {
            tokens.add(m.group());
        }
        return tokens;
    }

    /**
     * 3. Returns all words from a file as a set
     *
     * @param content
     * @return
     */
    public List<String> getVocab(String content) {
        ArrayList words = tokenizer("[a-zA-Z]+", content);
        HashSet<String> set = new HashSet<String>(words);
        List<String> sortedList = new ArrayList(set);
        Collections.sort(sortedList);
        return sortedList;
    }

    /**
     * Writes vocabulary in a file
     *
     * @param sortedVocab
     * @param newfilename
     * @throws IOException
     */
    public void writeVocab(List<String> sortedVocab, String newfilename) throws IOException {
        FileWriter fw = new FileWriter(newfilename);
        BufferedWriter out = new BufferedWriter(fw);
        for (String str : sortedVocab) {
            out.write(str);
            out.write(System.getProperty("line.separator"));
            out.flush();
        }
        out.close();
    }

    /**
     * Creates a vocabulary file - 1 word per line
     *
     * @param filename
     * @param newfilename
     * @throws IOException
     */
    public void createVocabularyFile(String filename, String newfilename) throws IOException {
        String content = getContent(filename);
        List<String> vocabulary = getVocab(content);
        writeVocab(vocabulary, newfilename);
    }

    public static void main(String[] args) throws IOException {
        VocabularyFileProcessor pr = new VocabularyFileProcessor();
        pr.createVocabularyFile("data/YLE-starters.txt", "data/STARTERSwordlist.txt");
        System.out.println("The vocabulary file has been created");
    }
}