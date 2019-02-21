package document;

import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.ling.TaggedWord;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.List;

@SuppressWarnings("ALL")
class StanfordPOS {

    public static void main(String[] args) throws IOException {
        FileContent fcon = new FileContent("data/ket_example.txt");
        String s = fcon.getContent();
        StanfordPOS sfp = new StanfordPOS();
        System.out.println(sfp.getsTaggedSentences(s));
        System.out.println(sfp.simplifiedTags(s));
    }

    public List<List<TaggedWord>> getsTaggedSentences(String input) {
        MaxentTagger mxntg = new MaxentTagger("models/english-left3words-distsim.tagger");
        List<List<HasWord>> sentences = MaxentTagger.tokenizeText(new StringReader(input));

        return mxntg.process(sentences);
    }

    public HashMap<String, String> simplifiedTags(String input) {
        HashMap<String, String> postagged = new HashMap<>();
        List<List<TaggedWord>> taggedList = getsTaggedSentences(input);
        for (List<TaggedWord> tags : taggedList) { // iterate over list
            for (TaggedWord taggedWord : tags) {//for each word in the sentence
                String word = taggedWord.toString().replaceFirst("/\\w+", "");
                if (taggedWord.tag().equals("NN") | taggedWord.tag().equals("NNS") |
                        taggedWord.tag().equals("NNP") | taggedWord.tag().equals("NNPS")) {
                    postagged.put(word, "noun");
                } else if (taggedWord.tag().equals("MD") | taggedWord.tag().equals("VB") |
                        taggedWord.tag().equals("VBD") | taggedWord.tag().equals("VBG") |
                        taggedWord.tag().equals("VBN") | taggedWord.tag().equals("VBP")
                        | taggedWord.tag().equals("VBZ")) {
                    postagged.put(word, "verb");
                } else if (taggedWord.tag().equals("JJ") | taggedWord.tag().equals("JJR") |
                        taggedWord.tag().equals("JJS")) {
                    postagged.put(word, "adj");
                } else if (taggedWord.tag().equals("PRP") | taggedWord.tag().equals("PRP$")) {
                    postagged.put(word, "pron");
                } else if (taggedWord.tag().equals("RB") | taggedWord.tag().equals("RBR") |
                        taggedWord.tag().equals("RBS")) {
                    postagged.put(word, "adv");
                } else if (taggedWord.tag().equals("CD") | taggedWord.tag().equals("LS")) {
                    postagged.put(word, "num");
                } else if (taggedWord.tag().equals("WDT") | taggedWord.tag().equals("WP") |
                        taggedWord.tag().equals("WP$") | taggedWord.tag().equals("WRB")) {
                    postagged.put(word, "wh");
                } else if (taggedWord.tag().equals("DT") | taggedWord.tag().equals("PDT")) {
                    postagged.put(word, "det");
                } else if (taggedWord.tag().equals("CC") | taggedWord.tag().equals("IN")) {
                    postagged.put(word, "prepconj");
                } else {
                    postagged.put(word, "other");
                }
            }
        }
        return postagged;
    }

}


