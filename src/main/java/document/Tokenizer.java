package document;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A helper class used for tokenization of text files
 * @author Elena Khasanova
 */
@SuppressWarnings("unchecked")

public class Tokenizer {

    /**
     * Tokenizes the string content of the document
     * @param pattern - a regular expression to match the token
     * @param content - String content of the file
     * @return lis of matched tokens
     */
    public ArrayList<String> tokenize(String pattern, String content) {
        ArrayList tokens = new ArrayList();
        Pattern tokenSplitter = Pattern.compile(pattern);
        Matcher m = tokenSplitter.matcher(content);
        while (m.find()) {
            tokens.add(m.group());
        }
        return tokens;
    }
}
