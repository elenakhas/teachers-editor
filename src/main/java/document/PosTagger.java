package document;

import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.ling.TaggedWord;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@SuppressWarnings("ALL")
class PosTagger {



/** **/

    public static void main(String[] args) throws IOException {
        FileContent fcon = new FileContent("src/test/test_data/textKET");
        String s = fcon.getContent();
        PosTagger sfp = new PosTagger();
        List<List<TaggedWord>> taggedSentences = sfp.getsTaggedSentences(s);
        System.out.println(taggedSentences);
        System.out.println(sfp.simplifiedTags(taggedSentences));
    }

    public static List<List<TaggedWord>> getsTaggedSentences(String input) {
        MaxentTagger mxntg = new MaxentTagger("models/english-left3words-distsim.tagger");
        List<List<HasWord>> sentences = MaxentTagger.tokenizeText(new StringReader(input));

        return mxntg.process(sentences);
    }

    public static HashMap<String, String> simplifiedTags(List<List<TaggedWord>> taggedList) {
        HashMap<String, String> simplePOSTagged = new HashMap<>();
        for (List<TaggedWord> tags : taggedList) { // iterate over list
            for (TaggedWord taggedWord : tags) {//for each word in the sentence
                String word = taggedWord.toString().replaceFirst("/\\w+", "");
                if (taggedWord.tag().equals("NN") | taggedWord.tag().equals("NNS") |
                        taggedWord.tag().equals("NNP") | taggedWord.tag().equals("NNPS")) {
                    simplePOSTagged.put(word, "noun");
                } else if (taggedWord.tag().equals("MD") | taggedWord.tag().equals("VB") |
                        taggedWord.tag().equals("VBD") | taggedWord.tag().equals("VBG") |
                        taggedWord.tag().equals("VBN") | taggedWord.tag().equals("VBP")
                        | taggedWord.tag().equals("VBZ")) {
                    simplePOSTagged.put(word, "verb");
                } else if (taggedWord.tag().equals("JJ") | taggedWord.tag().equals("JJR") |
                        taggedWord.tag().equals("JJS")) {
                    simplePOSTagged.put(word, "adj");
                } else if (taggedWord.tag().equals("PRP") | taggedWord.tag().equals("PRP$")) {
                    simplePOSTagged.put(word, "pron");
                } else if (taggedWord.tag().equals("RB") | taggedWord.tag().equals("RBR") |
                        taggedWord.tag().equals("RBS")) {
                    simplePOSTagged.put(word, "adv");
                } else if (taggedWord.tag().equals("CD") | taggedWord.tag().equals("LS")) {
                    simplePOSTagged.put(word, "num");
                } else if (taggedWord.tag().equals("WDT") | taggedWord.tag().equals("WP") |
                        taggedWord.tag().equals("WP$") | taggedWord.tag().equals("WRB")) {
                    simplePOSTagged.put(word, "wh");
                } else if (taggedWord.tag().equals("DT") | taggedWord.tag().equals("PDT")) {
                    simplePOSTagged.put(word, "det");
                } else if (taggedWord.tag().equals("CC") | taggedWord.tag().equals("IN")) {
                    simplePOSTagged.put(word, "prepconj");
                } else {
                    simplePOSTagged.put(word, "other");
                }
            }
        }
        return simplePOSTagged;
    }

    // THE FOLLOWING METHODS EXTRACT WORDS WITH A SPECIFIC POStag AND STORE THEM IN AN ArrayList.
    // In the next release, the ArrayList structure will be used to highlight the words of a certain POS in the GUI

    /** Extracts strings labeled as nouns from the HashMap simplePOSTagged.
     * @return an ArrayList of nouns in the text
     *
     * **/

    public ArrayList<String> getNouns(HashMap<String, String> simplePOSTagged) {
        ArrayList nouns = new ArrayList();
        for(String word: simplePOSTagged.keySet()){
            if (simplePOSTagged.get(word).equals("noun")){
                nouns.add(word);
            }
        }
        return nouns;
    }
    /** Extracts strings labeled as verbs from the HashMap simplePOSTagged.
     * @return an ArrayList of verbs in the text
     * **/

    public ArrayList<String> getVerbs(HashMap<String, String> simplePOSTagged) {
        ArrayList verbs = new ArrayList();
        for(String word: simplePOSTagged.keySet()) {
            if (simplePOSTagged.get(word).equals("verb")) {
                verbs.add(word);
            }
        }
        return verbs;
    }
    /** Extracts strings labeled as adjectives from the HashMap simplePOSTagged.
     * @return an ArrayList of adjectives in the text
     * **/
    public ArrayList<String> getAdjectives(HashMap<String, String> simplePOSTagged) {
        ArrayList adj = new ArrayList();
        for(String word: simplePOSTagged.keySet()){
            if (simplePOSTagged.get(word).equals("adj")){
                adj.add(word);
            }
        }
        return adj;
    }
    /** Extracts strings labeled as pronouns from the HashMap simplePOSTagged.
     * @return an ArrayList of pronouns in the text
     * **/
    public ArrayList<String> getPronouns(HashMap<String, String> simplePOSTagged) {
        ArrayList pron = new ArrayList();
        for(String word: simplePOSTagged.keySet()){
            if (simplePOSTagged.get(word).equals("pron")){
                pron.add(word);
            }
        }
        return pron;
    }
    /** Extracts strings labeled as adverbs from the HashMap simplePOSTagged.
     * @return an ArrayList of nouns in the text
     * **/
    public ArrayList<String> getAdverbs(HashMap<String, String> simplePOSTagged) {
        ArrayList adv = new ArrayList();
        for(String word: simplePOSTagged.keySet()){
            if (simplePOSTagged.get(word).equals("adv")){
                adv.add(word);
            }
        }
        return adv;
    }
    /** Extracts strings labeled as wh-words from the HashMap simplePOSTagged.
     * @return an ArrayList of wh-words in the text
     * **/
    public ArrayList<String> getWh(HashMap<String, String> simplePOSTagged) {
        ArrayList whWords = new ArrayList();
        for(String word: simplePOSTagged.keySet()){
            if (simplePOSTagged.get(word).equals("wh")){
                whWords.add(word);
            }
        }
        return whWords;
    }
    /** Extracts strings labeled as numerals from the HashMap simplePOSTagged.
     * @return an ArrayList of numerals in the text
     * **/
    public ArrayList<String> getNumbers(HashMap<String, String> simplePOSTagged) {
        ArrayList nums = new ArrayList();
        for(String word: simplePOSTagged.keySet()){
            if (simplePOSTagged.get(word).equals("num")){
                nums.add(word);
            }
        }
        return nums;
    }
    /** Extracts strings labeled as determiners from the HashMap simplePOSTagged.
     * @return an ArrayList of determiners in the text
     * **/
    public ArrayList<String> getDeterminers(HashMap<String, String> simplePOSTagged) {
        ArrayList det = new ArrayList();
        for(String word: simplePOSTagged.keySet()){
            if (simplePOSTagged.get(word).equals("det")){
                det.add(word);
            }
        }
        return det;
    }
    /** Extracts strings labeled as prepositions or conjunctions from the HashMap simplePOSTagged.
     * @return an ArrayList of prepositions and conjunctions in the text
     * **/
    public ArrayList<String> getPrepConj(HashMap<String, String> simplePOSTagged) {
        ArrayList prepconj = new ArrayList();
        for(String word: simplePOSTagged.keySet()){
            if (simplePOSTagged.get(word).equals("prepconj")){
                prepconj.add(word);
            }
        }
        return prepconj;
    }
    /** Extracts strings labeled as "other" from the HashMap simplePOSTagged.
     * @return an ArrayList of words that don't fall into any of the above mentioned categories in the text
     * **/
    public ArrayList<String> getOther(HashMap<String, String> simplePOSTagged) {
        ArrayList other = new ArrayList();
        for(String word: simplePOSTagged.keySet()){
            if (simplePOSTagged.get(word).equals("other")){
                other.add(word);
            }
        }
        return other;
    }

}


