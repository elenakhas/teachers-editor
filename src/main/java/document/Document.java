package document;

import edu.stanford.nlp.ling.TaggedWord;
import edu.stanford.nlp.process.Morphology;
import edu.stanford.nlp.trees.TypedDependency;
import edu.stanford.nlp.util.ArrayUtils;
import vocabulary.VocabularyBuilder;

import java.io.IOException;
import java.util.*;

import static java.util.stream.Collectors.toMap;

/**
 *  Represents a text document - string content of the file and its properties: words, frequencies.
 *  Extended by ReadingText and Essay classes
 * @author Elena Khasanova
 * @version 1.2;
 *
 */
@SuppressWarnings({"unchecked", "CanBeFinal"})
public abstract class Document implements TextEvaluator, ExtendedEvaluator {

    private final String content;
    public int numNouns;
    public int numAdj;
    public int numVerbs;
    public int numAdv;
    public int numNumbers;
    public int numPron;
    public int numDet;
    public int numWh;
    public int numPrepConj;
    public int numOther;
    public int comparativeAJ = 0;
    public int superlativeAJ = 0;
    public int modals = 0;
    public int existential = 0;
    public int comparativeAD = 0;
    public int superlativeAD = 0;
    public int imperative=0;
    public int presentSimpleActive = 0;
    public int presentContinuousActive = 0;
    public int pastSimpleActive = 0;
    public int pastContinuousActive = 0;
    public int presentPerfectContinuous = 0;
    public int pastPerfectActive = 0;
    public int futureSimpleActive = 0;
    public int futureContinuous = 0;
    public int futurePerfect = 0;
    public int presentPerfect = 0;
    private List<String> words;
    private HashMap<String, Integer> levelWords;
    private HashMap <String, String> simplePOSTagged;
    private List<List<TaggedWord>> taggedForParser;


    private int numWords;
    private int numSyllables;
    private int numSentences;

    /**
     * Create a new document with the given content, load the document properties used for
     */
    //initialize the constructor
    Document(String content) {
        this.content = content;
        getProperties(content);
    }

    public static void main(String[] args) throws IOException {
        Document doc = new ReadingText("a cat sat on a mat. Sit, cat, sit! He walks often. She calls mom. " +
                "Will you please? Cat should sit.");
        doc.getPOStagging();
        doc.getPOSNumbers();
        System.out.println(doc.numNouns);
        System.out.println(doc.numVerbs);
        System.out.println(doc.numAdj);
        System.out.println(doc.simplePOSTagged);
        doc.grammarAnalyser();
        System.out.println(doc.modals);
        System.out.println(doc.imperative);
        System.out.println(doc.presentSimpleActive);
    }

    // THE FOLLOWING METHODS WILL BE USED FOR CALCULATING READABILITY SCORE

    /**
     *  Getter for the string content of the file
      */
    public String getContent() {
        return this.content;
    }

    /**
     * Calculates the number of syllables in the text according to a set of rules.
     * Will be used for calculating readability scores.
     *
     * @param word
     * @return
     */
    public int getSyllables(String word) {
        int num = 0;   //
        char[] characters = word.toCharArray();
        // if the previous character is a vowel that forms a syllable
        boolean ifSyllable = true;
        String vowels = "aeiouyAEIOUY";
        for (int i = 0; i < characters.length; i++) { // nightingale
            // if the vowel is the last silent e and is not the only syllable, substract 1
            if (i == characters.length - 1 && characters[i] == 'e' && ifSyllable && num > 0) {
                num--;
            }
//          if the word ends with le, and the letter before is a consonant, add 1
            if (characters.length > 2 && i == characters.length - 1 && characters[i] == 'e' && characters[i - 1] == 'l' &&
                    vowels.indexOf(characters[i - 2]) < 0 && ifSyllable) {
                num++;
            }
            // if the char is a vowel and the previous letter is not (it is not a diphtong or a triphtong), increment the count
            if (ifSyllable && vowels.indexOf(characters[i]) >= 0) {
                ifSyllable = false;
                num++;
            }
            // if the letter is not a vowel, set the previous letter as syllable
            else if (vowels.indexOf(characters[i]) < 0) {
                ifSyllable = true;
            }
        }

        return num;
    }

    /**
     * Check if a string is a word and not a punctuation mark
     *
     * @param sentence
     * @return boolean
     */
    private boolean isWord(String sentence) {
        return !(sentence.indexOf("!") >= 0 || sentence.indexOf(".") >= 0 || sentence.indexOf("?") >= 0);
    }

    /**
     * Calculate and save properties of the text in class variables, get all the words from the text as a list
     *
     * @param content
     * @return list of words from the text
     */
    private void getProperties(String content) {
        Tokenizer tkn = new Tokenizer();
        List<String> tokens = tkn.tokenize("[!?.]+|[a-zA-Z]+", content);
        this.words = new ArrayList<>();
        for (int i = 0; i < tokens.size(); i++) {
            if (isWord(tokens.get(i))) {
                numWords++;
                words.add(tokens.get(i));
                numSyllables += getSyllables((tokens.get(i)));
            }
            if (isWord(tokens.get(i)) && i == tokens.size() - 1) {
                numSentences++;
            }
            if (!isWord(tokens.get(i))) {
                numSentences++;
            }
        }
    }

    /**
     *  Get all words in this document as a list; used in calculation of spelling mistakes
     */
    public List<String> getWords() {
        return words;
    }

    /**
     * Return the number of words in this document
     */
    public int getNumWords() {
        return numWords;
    }

    /**
     * Return the number of sentences in this document
     */
    public int getNumSentences() {
        return numSentences;
    }

    /**
     * Return the number of syllables in this document
     */
    public int getNumSyllables() {
        return numSyllables;
    }

    /**
     * return the Flesch readability score of this document
     */
    public float getFleschScore() { //
        return (float) (206.835 - 1.015 * ((float) getNumWords() / (float) getNumSentences())
                - 84.6 * ((float) getNumSyllables() / (float) getNumWords()));
    }

    /**
     * Calculate Flesch-Kincaid level readability score
     *
     * @return float score
     */

    public float fleschKincaid() {

        return (float) ((0.39 * ((float) getNumWords() / (float) getNumSentences()))
                + (11.8 * ((float) getNumSyllables() / (float) getNumWords())) - 15.59);
    }

    /**
     * @param text
     * @param vocab
     * @return HashMap from a word in a level vocabulary to its frequency in the text file
     * these words are highlighted in the TextArea. We exclude articles from the count as they
     * are frequent but do not contribute to understanding if the text is of an appropriate level
     */
    @SuppressWarnings("unchecked")
    public HashMap<String, Integer> wordsOfLevel(List<String> text, HashSet<String> vocab) {
        //levelWords.clear();
        //noinspection unchecked
        this.levelWords = new HashMap();
        Morphology morph = new Morphology();

        for (String word : text) {
            word = morph.stem(word);
            word = word.toLowerCase();
            if (vocab.contains(word) && !word.equals("a") && !word.equals("the")) {
                Integer occurences = this.levelWords.get(word);
                levelWords.put(word, (occurences == null) ? 1 : occurences + 1);
            }
        }
        return this.levelWords;
    }

    /**
     * Gives the percentage of words of a certain level
     */

    public float percentWordsOfLevel() {
        int sum = 0;
        for (int value : levelWords.values()) {
            sum += value;
        }
        return (float) sum / getNumWords() * 100;
    }

    /**
     * Gives the percentage of unique words of a certain level in a text
     *
     * @return
     */
    public float uniqueWordsOfALevel() {
        return (float) this.levelWords.size() / getNumWords() * 100;
    }

    public abstract String interpretFleshKincaid(double fleschKincaid);

    // returns words and their frequencies; words are converted to lowercase
    HashMap<String, Integer> frequencyOfWords(List<String> text) {
        HashMap <String, Integer> frequency = new HashMap<>();
        for (String word : text) {
            word = word.toLowerCase();
            Integer occurrences = frequency.get(word);
            frequency.put(word, (occurrences == null) ? 1 : occurrences + 1);
        }
        return frequency;
    }

    public int wordsVariety() {

        return frequencyOfWords(words).size();
    }

    public HashMap <String, Integer> mostFrequentWords(){

        String[] stopwords = {"i", "me", "my", "we", "our", "ours", "you", "your", "yours", "he", "him",
                "his", "she", "her", "hers","it", "its","they", "them", "their", "theirs", "what", "this",
                "that", "am", "is", "are", "was", "were", "be", "been", "being", "have", "has", "had",
                "having", "do", "does", "did", "doing", "a", "an", "the", "and", "but", "if", "or", "of",
                "at", "by", "for", "with", "in", "to", "from", "up", "in", "out", "on", "off", "so", "s", "t", "don"};
        HashMap <String, Integer> frequency = frequencyOfWords(words);
        Map<String, Integer> sorted = frequency
                .entrySet()
                .stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .collect(
                        toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2,
                                LinkedHashMap::new));

        // Create a Iterator to KeySet of HashMap
        //int i = 0;
        HashMap<String, Integer> topWords = new HashMap<>();
        int i=0;
                for (String word : sorted.keySet()) {
                    if (!ArrayUtils.contains(stopwords, word)) {
                        if (i < 10) {
                            topWords.put(word, sorted.get(word));
                        }
                        i++;
                    }
        }
        Map<String, Integer> sortTopWords = topWords
                .entrySet()
                .stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .collect(
                        toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2,
                                LinkedHashMap::new));
        return (HashMap<String, Integer>) sortTopWords;
    }

    // EXTENDED EVALUATOR METHODS

    // PART OF SPEECH

    public abstract int unknownWords(List<String> words, VocabularyBuilder vocab);

    public void getPOStagging() throws IOException {
        StanfordPOS sfp = new StanfordPOS();
        this.simplePOSTagged = sfp.simplifiedTags(content);
        this.taggedForParser = sfp.getsTaggedSentences(content);
    }

    public ArrayList<String> getNouns() {
        ArrayList nouns = new ArrayList();
        for(String word: simplePOSTagged.keySet()){
            if (simplePOSTagged.get(word).equals("noun")){
                nouns.add(word);
            }
        }
        return nouns;
    }

    public ArrayList<String> getVerbs() {
        ArrayList verbs = new ArrayList();
        for(String word: simplePOSTagged.keySet()) {
            if (simplePOSTagged.get(word).equals("verb")) {
                verbs.add(word);
            }
        }
        return verbs;
    }

    public ArrayList<String> getAdjectives() {
        ArrayList adj = new ArrayList();
        for(String word: simplePOSTagged.keySet()){
            if (simplePOSTagged.get(word).equals("adj")){
                adj.add(word);
            }
        }
        return adj;
    }

    public ArrayList<String> getPronouns() {
        ArrayList pron = new ArrayList();
        for(String word: simplePOSTagged.keySet()){
            if (simplePOSTagged.get(word).equals("pron")){
                pron.add(word);
            }
        }
        return pron;
    }

    public ArrayList<String> getAdverbs() {
        ArrayList adv = new ArrayList();
        for(String word: simplePOSTagged.keySet()){
            if (simplePOSTagged.get(word).equals("adv")){
                adv.add(word);
            }
        }
        return adv;
    }

    public ArrayList<String> getWh() {
        ArrayList whWords = new ArrayList();
        for(String word: simplePOSTagged.keySet()){
            if (simplePOSTagged.get(word).equals("wh")){
                whWords.add(word);
            }
        }
        return whWords;
    }

    public ArrayList<String> getNumbers() {
        ArrayList nums = new ArrayList();
        for(String word: simplePOSTagged.keySet()){
            if (simplePOSTagged.get(word).equals("num")){
                nums.add(word);
            }
        }
        return nums;
    }

    public ArrayList<String> getDeterminers() {
        ArrayList det = new ArrayList();
        for(String word: simplePOSTagged.keySet()){
            if (simplePOSTagged.get(word).equals("det")){
                det.add(word);
            }
        }
        return det;
    }

    public ArrayList<String> getPrepConj() {
        ArrayList prepconj = new ArrayList();
        for(String word: simplePOSTagged.keySet()){
            if (simplePOSTagged.get(word).equals("prepconj")){
                prepconj.add(word);
            }
        }
        return prepconj;
    }

    public ArrayList<String> getOther() {
        ArrayList other = new ArrayList();
        for(String word: simplePOSTagged.keySet()){
            if (simplePOSTagged.get(word).equals("other")){
                other.add(word);
            }
        }
        return other;
    }



    // GRAMMAR

    public void getPOSNumbers() {
        numNouns = getNouns().size();
        numVerbs = getVerbs().size();
        numAdj = getAdjectives().size();
        numNumbers = getNumbers().size();
        numAdv = getAdverbs().size();
        numPron = getPronouns().size();
        numWh = getWh().size();
        numDet = getDeterminers().size();
        numPrepConj = getPrepConj().size();
        numOther = getOther().size();

    }

/** calculates frequencies of various grammar features*/

    public void grammarAnalyser(){
        ParserUD ud = new ParserUD();
        ArrayList <String> imperatives = new ArrayList<>();
        for (List<TaggedWord> tags : taggedForParser) { // iterate over sentences in a text
            // update the number of lexical grammar markers for each sentence in a list, sum them up
            ud.posAnalysis(tags);
            for (String word : ud.imperatives(tags)) { // iterate over words in a sentence
                imperatives.add(word);
            }
            Collection<TypedDependency> td = ud.parsingSentence(tags);
            presentSimpleActive += ud.getPresentSimple(td).size(); //for one sentence
            presentContinuousActive += ud.getPresentContinuous(td).size();
            pastSimpleActive += ud.getPastSimple(td).size();
            pastContinuousActive += ud.getPastContinuous(td).size();
            presentPerfectContinuous += ud.getPresentPerfectContinuous(td).size() / 2; // output contains dependencies for both auxiliaries, so we divide by 2
            pastPerfectActive += ud.getPastPerfect(td).size();
            presentPerfect += ud.getPresentPerfect(td).size();
            futureSimpleActive += ud.getFutureSimple(td).size();
            futureContinuous += ud.getFutureContinuous(td).size() / 2; // output contains dependencies for both auxiliaries, so we divide by 2
            futurePerfect += ud.getFuturePerfect(td).size() / 2; // output contains dependencies for both auxiliaries, so we divide by 2
        }

        this.imperative = imperatives.size();
        this.modals = ud.modals;
        this.comparativeAJ = ud.comparativeAJ;
        this.superlativeAJ = ud.superlativeAJ;
        this.existential = ud.existential;
        this.comparativeAD = ud.comparativeAD;
        this.superlativeAD = ud.superlativeAD;
    }
}