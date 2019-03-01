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
 *  Represents a text document: string content of the file and its properties: words, frequencies.
 *  Extended by ReadingMaterial and Essay classes
 * @author Elena Khasanova
 * **/
@SuppressWarnings({"unchecked", "CanBeFinal"})
public abstract class AbstractDocument implements MainTextStatistics, ExtendedTextStatistics {

    // Store collections
    private final String content;
    private List<String> words;
    private HashMap <String, String> simplePOSTagged;
    private List<List<TaggedWord>> taggedForParser;
    // Store the document properties used for calculating the readability scores*/
    private int numWords;
    private int totalNumSyllables;
    private int numSentences;
    //Multiple class variables are conveniently used to display the results of text analysis in the GUI//
    // vocabulary properties
    private int numNouns;
    private int numAdj;
    private int numVerbs;
    private int numAdv;
    private int numNumbers;
    private int numPron;
    private int numDet;
    private int numWh;
    private int numPrepConj;
    private int numOther;
    // grammar properties
    private int numComparativeAJ;
    private int numSuperlativeAJ;
    private int numModals;
    private int numExistential;
    private int numComparativeAD;
    private int numSuperlativeAD;
    private int numImperative;
    private int numPresentSimpleActive;
    private int numPresentContinuousActive;
    private int numPresentPerfectActive;
    private int numPresentPerfectContinuous;
    private int numPastSimpleActive;
    private int numPastContinuousActive;
    private int numPastPerfectActive;
    private int numFutureSimpleActive;
    private int numFutureContinuous;
    private int numFuturePerfect;
    private int spellingMistakes;


    /**
     * The constructor creates a new instance of AbstractDocument from the given string content, loads the document properties:
     * number of sentences;
     * number of words;
     * number of syllables
     * initialize a PosTagger object
     */
    AbstractDocument(String content) {
        this.content = content;
        calcTextProperties(content);
    }

    public static void main(String[] args) throws IOException {
        AbstractDocument doc = new ReadingMaterial("a cat sat on a mat. Sit, cat, sit! He walks often. She calls mom. " +
                "Will you please? Cat should sit.");
        doc.getPosTagging();
        doc.getPosStatistics();
        System.out.println(doc.numNouns);
        System.out.println(doc.numVerbs);
        System.out.println(doc.numAdj);
        System.out.println(doc.simplePOSTagged);
        doc.getGrammarStatistics();
        System.out.println(doc.numModals);
        System.out.println(doc.numImperative);
        System.out.println(doc.numPresentSimpleActive);
    }

    // GETTER METHODS FOR PRIVATE VARIABLES
    /**
     *  Getter for the string content of the file
     * @return the content of the file stored as an attribute
     */
    public String getContent() {
        return this.content;
    }
    public List<String> getWords() {
        return this.words;
    }
    public int getNumWords() {
        return this.numWords;
    }
    public int getNumSentences() {
        return this.numSentences;
    }
    public int getTotalNumSyllables() {
        return this.totalNumSyllables;
    }

    public int getNumNouns(){return  this.numNouns;}
    public int getNumVerbs(){return  this.numVerbs;}
    public int getNumAdj(){return  this.numAdj;}
    public int getNumAdv(){return  this.numAdv;}
    public int getNumNumbers(){return  this.numNumbers;}
    public int getNumPron(){return  this.numPron;}
    public int getNumDet(){return  this.numDet;}
    public int getNumWh(){return  this.numWh;}
    public int getNumPrepConj(){return  this.numPrepConj;}
    public int getNumOther(){return  this.numOther;}
    public int getNumComparativeAJ(){return  this.numComparativeAJ;}
    public int getNumSuperlativeAJ(){return  this.numSuperlativeAJ;}
    public int getNumComparativeAD(){return  this.numComparativeAD;}
    public int getNumSuperlativeAD(){return  this.numSuperlativeAD;}
    public int getNumModals(){return  this.numModals;}
    public int getNumImperative(){return  this.numImperative;}
    public int getNumExistential(){return  this.numExistential;}
    public int getNumPresentSimpleActive(){return  this.numPresentSimpleActive;}
    public int getNumPresentContinuousActive(){return  this.numPresentContinuousActive;}
    public int getNumPresentPerfectActive(){return  this.numPresentPerfectActive;}
    public int getNumPresentPerfectContinuous(){return  this.numPresentPerfectContinuous;}
    public int getNumPastSimpleActive(){return  this.numPastSimpleActive;}
    public int getNumPastPerfectActive(){return  this.numPastPerfectActive;}
    public int getNumPastContinuousActive(){return this.numPastContinuousActive;}
    public int getNumFutureSimpleActive(){return  this.numFutureSimpleActive;}
    public int getNumFutureContinuous(){return  this.numFutureContinuous;}
    public int getNumFuturePerfect(){return  this.numFuturePerfect;}

    //THE FOLLOWING METHODS WILL BE USED FOR CALCULATING READABILITY SCORES
    /**
     * A helper method to compute the number of syllables in the word according to a set of rules.
     * Used for calculating the total number of syllables in the text and further the readability scores.
     *
     * @param word - one word in a text
     * @return num - number of syllables in a word
     */
    protected int wordGetSyllables(String word) {
        int num = 0;   //
        char[] characters = word.toCharArray();
        // a variable to control the value of a previous vowel to handle diphthongs and triphthongs
        boolean ifSyllable = true;
        String vowels = "aeiouyAEIOUY";
        for (int i = 0; i < characters.length; i++) {

            // if the vowel is the last silent e and is not the only syllable, substract 1
            if (i == characters.length - 1 && characters[i] == 'e' && ifSyllable && num > 0) {
                num--;
            }

            // if the word ends with le, and the letter before is a consonant, increment the count
            if (characters.length > 2 && i == characters.length - 1 && characters[i] == 'e' && characters[i - 1] == 'l' &&
                    vowels.indexOf(characters[i - 2]) < 0 && ifSyllable) {
                num++;
            }

            // if the character is a vowel and the previous letter is not, increment the count, set ifSyllable to false
            if (ifSyllable && vowels.indexOf(characters[i]) >= 0) {
                ifSyllable = false;
                num++;
            }

            // if the current letter is not a vowel, set ifSyllable to true as the previous vowel makes a syllable
            else if (vowels.indexOf(characters[i]) < 0) {
                ifSyllable = true;
            }
        }

        return num;
    }

    /**
     * A helper method to check if a string is a word and not a punctuation mark
     * @param text - string representation of the sentence or sequence of sentences
     * @return boolean - true if a string is a word
     */
    private boolean isWord(String text) {
        return !(text.indexOf("!") >= 0 || text.indexOf(".") >= 0 || text.indexOf("?") >= 0);
    }

    /**
     * A helper method to calculate and save properties of the text:
     * words as a list, number of words, number of sentences, number of syllables;
     * @param content - string content of the file
     */
    private void calcTextProperties(String content) {
        Tokenizer tkn = new Tokenizer();
        List<String> tokens = tkn.tokenize("[!?.]+|(\\w+(-\\w+)*)", content);
        this.words = new ArrayList<>();
        for (int i = 0; i < tokens.size(); i++) {
            if (isWord(tokens.get(i))) {
                this.numWords++;
                this.words.add(tokens.get(i));
                this.totalNumSyllables += wordGetSyllables((tokens.get(i)));
            }
            if (isWord(tokens.get(i)) && i == tokens.size() - 1) {
                this.numSentences++;
            }
            if (!isWord(tokens.get(i))) {
                this.numSentences++;
            }
        }
    }

    // MAIN TEXT EVALUATOR METHODS, OUTPUT DISPLAYED IN THE MAIN VIEW

    /**
     * Calculates the Flesch Reading Ease score of this text document;
     * it rates text on a 100-point scale; the higher the score, the easier it is to understand the document
     * most standard text is scored between 60 and 70.
     * The exact formula is: RE = 206.835 – (1.015 x ASL) – (84.6 x ASW),
     * where ASL is an average sentence length (words per sentence);
     * ASW is an average number of syllables per word.
     * @return float - Flesch score;
     */
    public float calcFleschScore() {
        return (float) (206.835 - 1.015 * ((float) getNumWords() / (float) getNumSentences())
                - 84.6 * ((float) getTotalNumSyllables() / (float) getNumWords()));
    }

    public  String interpretFlesch(float score){
        String explanation = null;

        if (score < 30) {
            explanation = "Very difficult, college graduate level.";
        }
        else if (score >= 30 && score < 50) {
            explanation = "Difficult, college student level";
        }
        else if (score >= 50 && score < 60) {
            explanation = "Fairly difficult, 10th to 12th grade";
        }
        else if (score >= 60 && score < 70) {
            explanation = "Standard, 8th - 9th grade";
        }
        else if (score >= 70 && score < 80) {
            explanation = "Fairly easy, 7th grade";
        }
        else if (score >= 80 && score < 90) {
            explanation = "Easy, 6th grade";
        }
        else if (score >= 90) {
            explanation = "Very easy, 5th grade";
        }

        return explanation;
    }


    /**
     * Calculate Flesch-Kincaid Grade Level readability formula.
     * It is similar to Flesch, but establishes correspondence to an American school grade levels
     * and popular writing genres.
     * @return float - score
     */

    public float calcFleschKincaid() {

        return (float) ((0.39 * ((float) getNumWords() / (float) getNumSentences()))
                + (11.8 * ((float) getTotalNumSyllables() / (float) getNumWords())) - 15.59);
    }

    /** Gives explanation of Flesch-Kincaid score, differs for ReadingMaterial and Essay
     * @param fleschKincaid - Flesch-Kincaid readability score
     * @return a String interpreting the score
     * **/
   public abstract String interpretFleshKincaid(float fleschKincaid);

    @SuppressWarnings("unchecked")

    /** Selects the words found in a wordlist of a certain level and their frequency of occurrence, exclude articles
     * @param text - list of words of a document;
     * @param vocab - hashset with words from a level wordlist
     * @return HashMap - word contained in both text and vocab:frequency
     * **/
    public HashMap<String, Integer> wordsOfLevel(List<String> text, HashSet<String> vocab) {
        HashMap<String, Integer> wordsOfALevel = new HashMap();
        // lemmatize the words of a document
        Morphology morph = new Morphology();
        for (String word : text) {
            word = morph.stem(word);
            word = word.toLowerCase();
            if (vocab.contains(word) && !word.equals("a") && !word.equals("the")) {
                Integer occurences = wordsOfALevel.get(word);
                wordsOfALevel.put(word, (occurences == null) ? 1 : occurences + 1);
            }
        }
        return wordsOfALevel;
    }

    /**
     * Gives the percentage of words of a certain level in a document
     * @return float
     */

    public float percentWordsOfLevel(HashMap<String, Integer> wordsOfALevel) {
        int sum = 0;
        for (int value : wordsOfALevel.values()) {
            sum += value;
        }
        return (float) sum / getNumWords() * 100;
    }

    /**
     * Computes the percentage of unique words of a certain level in a text
     * @param wordsOfALevel - a HashMap containing the words of a certain level
     * and their counts
     * @return float - percentage of words
     */
    public float uniqueWordsOfALevel(HashMap<String, Integer> wordsOfALevel) {
        return (float) wordsOfALevel.size() / getNumWords() * 100;
    }

    /** A helper method that computes the frequency of all words in the text
     * @param words - a List containing all words in a text
     * @return words mapped to their frequencies
     * **/

    protected HashMap<String, Integer> frequencyOfWords(List<String> words) {
        HashMap <String, Integer> frequency = new HashMap<>();
        for (String word : words) {
            // words are converted to lowercase, does not deal with capitals
            word = word.toLowerCase();
            Integer occurrences = frequency.get(word);
            frequency.put(word, (occurrences == null) ? 1 : occurrences + 1);
        }
        return frequency;
    }

    /**
     * Returns the number of different words in the document
     *  @return float - number of words stored in the frequency HashMap
     */
    public int wordsVariety() {
        return frequencyOfWords(this.words).size();
    }

    /**
     * Returns the top 10 most frequent words in the document
     *  @return sorted HashMap of top 10 words : frequencies
     */
    public HashMap <String, Integer> mostFrequentWords(){

        // an array of typical stopwords for English (from NLTK), modified: excluded some prepositions and modifiers
        // as they are relevant especially for low levels of English proficiency;
        String[] stopWords = {"i", "me", "my", "we", "our", "ours", "you", "your", "yours", "he", "him",
                "his", "she", "her", "hers","it", "its","they", "them", "their", "theirs", "what", "this",
                "that", "am", "is", "are", "was", "were", "be", "been", "being", "have", "has", "had",
                "having", "do", "does", "did", "doing", "a", "an", "the", "and", "but", "if", "or", "of",
                "at", "by", "for", "with", "in", "to", "from", "up", "in", "out", "on", "off", "so", "s", "t", "don"};
        HashMap <String, Integer> frequency = frequencyOfWords(this.words);
        // sort the HashMap of words and their frequencies in a descending order
        Map<String, Integer> sorted = frequency
                .entrySet()
                .stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .collect(
                        toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2,
                                LinkedHashMap::new));

        // select top ten words
        HashMap<String, Integer> topWords = new HashMap<>();
        int i=0;
                for (String word : sorted.keySet()) {
                    if (!ArrayUtils.contains(stopWords, word)) {
                        if (i < 10) {
                            topWords.put(word, sorted.get(word));
                        }
                        i++;
                    }
                }

        // sort the selected top words in a descending order
        Map<String, Integer> sortTopWords = topWords
                .entrySet()
                .stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .collect(
                        toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2,
                                LinkedHashMap::new));
        return (HashMap<String, Integer>) sortTopWords;
    }

    /** Calculates the number of spelling mistakes and words not contained in the reference dictionary of a spellcheker
     * @param words - List of words in a document
     * @param vocab - reference dictionary
     * @return number of mistaken words
     * **/
    public int countUnknownWords(List<String> words, VocabularyBuilder vocab){
        for (String word : words) {
            if (!vocab.isWord(word)) {
                spellingMistakes++;
            }
        }
        return spellingMistakes;
    }

    // EXTENDED TEXT EVALUATOR METHODS TO BE DISPLAYED WHEN CALLED "SHOW STATISTICS" IN GUI

    // PART OF SPEECH TAGGING METHODS

    /** Provides POS tags of the words:
     *  updates the class variable taggedForParser (List of Lists of TaggedWord elements) further used by the syntax parser
     *  updates the class variable simplePOStagged (HashMap word:simplified POS tag) further used to compute vocabulary properties of a text
     *  This implementation allows for calling the tagger and using the model only once per a document
     * **/

    public void getPosTagging() {

        this.taggedForParser = PosTagger.getInstance().getsTaggedSentences(this.content);
        this.simplePOSTagged = PosTagger.getInstance().simplifiedTags(this.taggedForParser);
    }

    // THE FOLLOWING METHODS EXTRACT GRAMMAR PROPERTIES OF THE DOCUMENT

    /** Computes the number of occurrences of major parts of speech**/
    public void getPosStatistics() {
        this.numNouns = PosTagger.getInstance().getNouns(this.simplePOSTagged).size();
        this.numVerbs = PosTagger.getInstance().getVerbs(this.simplePOSTagged).size();
        this.numAdj = PosTagger.getInstance().getAdjectives(this.simplePOSTagged).size();
        this.numNumbers = PosTagger.getInstance().getNumbers(this.simplePOSTagged).size();
        this.numAdv = PosTagger.getInstance().getAdverbs(this.simplePOSTagged).size();
        this.numPron = PosTagger.getInstance().getPronouns(this.simplePOSTagged).size();
        this.numWh = PosTagger.getInstance().getWh(this.simplePOSTagged).size();
        this.numDet = PosTagger.getInstance().getDeterminers(this.simplePOSTagged).size();
        this.numPrepConj = PosTagger.getInstance().getPrepConj(this.simplePOSTagged).size();
        this.numOther = PosTagger.getInstance().getOther(this.simplePOSTagged).size();
    }

    /** Calculates the number of occurrences of various grammar features in a document by incrementing
     *  respective counters at each occurrence. The results are displayed for the user in GUI.
     *  Handles the following features:
     *  - comparative and superlative adjectives;
     *  - comparative and superlative adjectives;
     *  - modal verbs;
     *  - numImperative forms;
     *  - numExistential;
     *  - Present Simple Active;
     *  - Present Perfect Active;
     *  - Present Continuous Active;
     *  - Past Simple Active;
     *  - Past Perfect Active;
     *  - Past Continuous Active;
     *  - Present Perfect Continuous;
     *  - Future Simple Active;
     *  - Future Perfect Active;
     *  - Future Continuous;
     * */

    public void getGrammarStatistics(){
        GrammarEvaluation ge = new GrammarEvaluation();
        ArrayList <String> imperatives = new ArrayList<>();

        // iterate over the sentences in a list of parsed and tagged sentences
        for (List<TaggedWord> tags : this.taggedForParser) {

            // get the counts for lexicalized grammar features (modal verbs, numExistential,
            // superlative and comparative adverbs and adjectives)
            ge.posAnalysis(tags);

            // get the counts for numImperative forms
            for (String word : ge.imperatives(tags)) {
                imperatives.add(word);
            }

            // get a parsed sentence
            Collection<TypedDependency> td = ge.parsingSentence(tags);

            //increment the counts of grammar properties for each sentence
            this.numPresentSimpleActive += ge.getPresentSimple(td).size();
            this.numPresentContinuousActive += ge.getPresentContinuous(td).size();
            this.numPastSimpleActive += ge.getPastSimple(td).size();
            this.numPastContinuousActive += ge.getPastContinuous(td).size();
            this.numPastPerfectActive += ge.getPastPerfect(td).size();
            this.numPresentPerfectActive += ge.getPresentPerfect(td).size();
            this.numFutureSimpleActive += ge.getFutureSimple(td).size();
            // output contains dependencies for both auxiliaries, so we divide by 2
            this.numFutureContinuous += ge.getFutureContinuous(td).size() / 2;
            this.numFuturePerfect += ge.getFuturePerfect(td).size() / 2;
            this.numPresentPerfectContinuous += ge.getPresentPerfectContinuous(td).size() / 2;
        }

        // assign the size of the ArrayList storing imperatives to the respective AbstractDocument variable
        this.numImperative = imperatives.size();

        // assign the output of a respective method of GrammarEvaluation to the AbstractDocument variables
        this.numModals = ge.getModals();
        this.numComparativeAJ = ge.getComparativeAJ();
        this.numSuperlativeAJ = ge.getSuperlativeAJ();
        this.numExistential = ge.getExistential();
        this.numComparativeAD = ge.getComparativeAD();
        this.numSuperlativeAD = ge.getSuperlativeAD();
    }

    /** Provides the percentage of words of the requested level for the document and its interpretation
     * @param level - name of the level as a String
     * @param vocab - reference wordlist of a desired level as a HashSet of Strings
     * @return String describing the percentage of words of a certain level in a text
     *  and unique words among them
     * **/

    public String levelMessage(String level, HashSet<String> vocab){
        HashMap <String, Integer> levelWords = wordsOfLevel(this.words, vocab);
        String percentage = (String.format("%.2f", this.percentWordsOfLevel(levelWords)));
        String levelPercentage = percentage + "% of " + level + " words";
        String unique = String.format("%.2f", this.uniqueWordsOfALevel(levelWords));
        return levelPercentage + ";  unique words: " + unique + "%";
    }

}