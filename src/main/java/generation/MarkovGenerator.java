package generation;

import document.FileContent;
import document.Tokenizer;

import java.util.*;



/** An implementation of MarkovGenerator using a HashMap containing a HashMap**/

public class MarkovGenerator {

    // Tokenized content of the file
    ArrayList<String> data;

    // A word-level language model; String array represents the history, HashMap maps the word
    // to the number of occurrences after this history
    HashMap<String[], HashMap<String, Float>> lm;

    // All words in the document
    Set<String> vocabulary;

    MarkovGenerator(String fname) {
        FileContent fc = new FileContent(fname);
        Tokenizer tkn = new Tokenizer();
        String content = fc.getContent();
        this.data = tkn.tokenize("[!?.]+|(\\w+(-\\w+)*)", content);
        this.vocabulary = new HashSet<String>(this.data);
    }

    public static void main(String[] args){

        MarkovGenerator mg = new MarkovGenerator("data/shakespeare_input.txt");
        mg.trainLm(4);
        mg.addK(1);
        mg.normalization();
        System.out.println("trained");
        String result = mg.generateText(4, 20);
        System.out.println(result);

    }

    /** Trains a word language model:
     *  @param order the desired length of n-grams
     *
     **/

    public void trainLm(int order) {

        //initialize a new HashMap for this language model
        this.lm = new HashMap();
        String word;
        // iterate over words in data
        for (int i = order; i < this.data.size(); i++) {
            //Build history for each word
            String[] history = new String[order];
            // populate the string with the words that precede each word
            for (int k = order; k > 0; k--) {
                history[k - 1] = this.data.get(i - k);
            }

            // get the word count for the current hist, create it if it does not exist
            HashMap<String, Float> wordCount = this.lm.getOrDefault(history, new HashMap<>());
            this.lm.put(history, wordCount);
            word = this.data.get(i);

            // put history + number of occurrences
            float count = wordCount.getOrDefault(history, (float) 0);
            wordCount.put(word, count + 1);
        }
    }

    /** Simple add-k smoothing: increments each word count with a selected number
     * @param k - the number to be added to the count
     * **/

    public void addK(float k) {
        for (HashMap<String, Float> wc : this.lm.values()) {
            for (String word : vocabulary) {
                float count = wc.getOrDefault(word, (float) 0);
                    wc.put(word, count + k);
            }
        }
        HashMap<String, Float> wc = new HashMap<>();
        for (String word : vocabulary) {
            wc.put(word, k);
        }
        this.lm.put(null, wc);
    }

    /** Get the distribution of wordcounts with values for as many preceding words
     *  for each word as occur in the text according to the desired order;
     *  Recursively adds the counts corresponding to preceding words of a lower order if there are not enough
     *  words of selected order
     * @param prevWords - an array of the words that precede the current word
     * @return distribution of counts for the occurrence of current word after the preceding words;

     * **/
    private HashMap<String, Float> getDistribution(String [] prevWords){

        HashMap<String,Float> distribution;
        // if there are no preceding words, put default value for null
        if(prevWords.length <= 0){
            distribution = this.lm.getOrDefault(prevWords, this.lm.get(null));
        }
        // if there are preceding words, get the count for preceding words recursively, from 1 to the number of preceding words
        else{
            distribution = this.lm.getOrDefault(prevWords, this.getDistribution(Arrays.copyOfRange(prevWords, 1, prevWords.length)));

        }
        return distribution;
    }

    /** Get the history of as many preceding words
     *  for each word as occur in the text according to the desired order
     * @param data -  the content of a text represented as an ArrayList
     * @param order - the length of n-gram
     * @return history - an array of preceding words for each word
     * **/

    public String[] getHistory(ArrayList<String> data, int order) {
        String[] history = new String[order];
        while (data.size() < order) {
            order--;
        }
        for (int k = order; k > 0; k--) {
            history[k - 1] = data.get(data.size() - k);
        }
        return history;
    }

    /** Normalizes the counter: divides the count by the total number of occurrences of each word
     * **/

    public void normalization() {
        // wordcount HashMap
        for (HashMap<String, Float> wc : this.lm.values()) {
            float totalCount = 0;
            for (float count : wc.values()) {
                totalCount += count;
            }
            for (String word : wc.keySet()) {
                float normalizedCount = wc.get(word) / totalCount;
                wc.put(word, normalizedCount);
            }
        }
    }
    /**
     * Randomly chooses the next word using the language model
     * @param prevWords the words that occur before each word
     * @return A random word occurring after the prevWords
     **/

    public String generateWord(String[] prevWords) {
        Random rnd = new Random();
        float index = rnd.nextFloat();
        String word = null;
        HashMap<String, Float> distribution = this.getDistribution(prevWords);

        for (Map.Entry<String, Float> entry : distribution.entrySet()) {

            float prob = entry.getValue();
            word = entry.getKey();
            index = index - prob;
            if (index <= 0) {
                return word;
            }
        }
        return word;
    }

    /**
     * Generates a random text based on the language model
     * @param order - the desired length of n-gram
     * @param nWords - the number of words to generate
     * @return Generated text
     **/
    public String generateText(int order, int nWords) {
        ArrayList<String> generatedWords = new ArrayList<>();
        for (int i = 1; i <= nWords; i++) {
            String word = generateWord(getHistory(generatedWords, order));
            generatedWords.add(word);
        }
        return String.join(" ", generatedWords);
    }



    // #TODO: write a function to loop over the orders - from max to min, add_k, finish with normalization


}
