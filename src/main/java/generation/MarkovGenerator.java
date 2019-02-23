package generation;

import document.FileContent;
import document.Tokenizer;

import java.util.*;



/** Class **/

public class MarkovGenerator {

    ArrayList<String> data;
    HashMap<String[], HashMap<String, Float>> lm;
    Set<String> vocabulary;

    MarkovGenerator(String fname) {
        FileContent fc = new FileContent(fname);
        Tokenizer tkn = new Tokenizer();
        String content = fc.getContent();
        this.data = tkn.tokenize("[!?.]+|[a-zA-Z]+", content);
        this.vocabulary = new HashSet<String>(this.data);

    }

    public static void main(String[] args){


        MarkovGenerator mg = new MarkovGenerator("src/test/test_data/textKET");
        mg.train_lm(4);
        mg.addK(1);
        mg.normalization();
        System.out.println("trained");
        String result = mg.generateText(4, 20);
        System.out.println(result);

    }

    public void train_lm(int order) {
        /** Trains a language model
         * @param String filename - path to a text
         * @param order the length of n-grams
         * @param add_k value for smoothing
         * @return hashmap: {n-gram : word and its probability}
         */

        this.lm = new HashMap();

        // iterate over words
        String word;
        for (int i = order; i < this.data.size(); i++) {
            //Build hist
            String[] history = new String[order];
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

    public void normalization() {
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
     **/

    public String generate_word(String[] prevWords) {
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

    private HashMap<String, Float> getDistribution(String [] prevWords){
        HashMap<String,Float> distribution;
        //System.out.println(prevWords.length);
        if(prevWords.length <= 0){
            distribution = this.lm.getOrDefault(prevWords, this.lm.get(null));
        }
        else{
            distribution = this.lm.getOrDefault(prevWords, this.getDistribution(Arrays.copyOfRange(prevWords, 1, prevWords.length)));

        }
        return distribution;
    }

    public String[] getHistory(ArrayList<String> data, int order) {
        String[] history = new String[order];
        while (data.size() < order) {
            //System.err.println("Not enough data");
            order--;
        }
        for (int k = order; k > 0; k--) {
            history[k - 1] = data.get(data.size() - k);
        }
        return history;
    }
    // #TODO: write a function to loop over the orders - from max to min, add_k, finish with normalization

    /**
     * Generates a bunch of random text based on the language model
     **/
    public String generateText(int order, int nWords) {
        ArrayList<String> generatedWords = new ArrayList<>();
        for (int i = 1; i <= nWords; i++) {
            String word = generate_word(getHistory(generatedWords, order));
            generatedWords.add(word);
        }
        return String.join(" ", generatedWords);
    }
}
