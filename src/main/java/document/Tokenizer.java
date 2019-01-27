package document;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The class used for tokenization of text files
 */
public class Tokenizer {

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