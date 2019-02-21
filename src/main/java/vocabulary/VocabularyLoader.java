package vocabulary;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
/**
 *  Class to load vocabulary from files into VocabularyBuilder object representing reference dictionary and
 *  wordlists of different levels of English
 * @author Elena Khasanova
 * @version 1.2;
 * */

public class VocabularyLoader {

        public static void loadVocabulary(VocabularyBuilder vocab, String filename) {
        // Dictionary files have 1 word per line
        try {
            String nextWord;
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            while ((nextWord = reader.readLine()) != null) {
                vocab.addWord(nextWord.toLowerCase());
            }
        } catch (IOException e) {
            System.err.println("Problem loading the file: " + filename);
            e.printStackTrace();
        }
    }
}
