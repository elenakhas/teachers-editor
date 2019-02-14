//package generation;
//
//import document.FileContent;
//import document.Tokenizer;
//import generation.MarkovGenerator.Counter;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.Map;
//
//public class MarkovGenerator {
//
////    private class Counter<T> {
////        final Map<T, Integer> counts = new HashMap<>();
////
////        public void add(T t) {
////            counts.merge(t, 1, Integer::sum);
////        }
////
////        public int count(T t) {
////            return counts.getOrDefault(t, 0);
////        }
////    }
//        public void train_lm(String fname, int order, int add_k) {
//            /** Trains a language model
//             * @param String filename - path to a text
//             * @param order the length of n-grams
//             * @param add_k value for smoothing
//             * @return hashmap: {n-gram : word and its probability}
//             */
//
//            FileContent fc = new FileContent(fname);
//            String content = fc.getContent();
//            Tokenizer tkn = new Tokenizer();
//            ArrayList <String> data = tkn.tokenize("[!?.]+|[a-zA-Z]+", content);
//
//            HashMap<ArrayList <String>, Float> lm = new HashMap();
//            //pad = "~" * order
//            //data = pad + data
//            for (String word : data) {
//                ArrayList<String> history = new ArrayList<>();
//
//                for (int i = 0; i<= data.size(); i++){
//                    word = data.get(i+order);
//                    history.add(data.get(i));
//
//
//                }
//
//            }
//            history,char =data[i:i + order],data[i + order]
//            lm[history][ char] +=1
//
//            vocabulary = set(data) #set of all characters = Vocabulary
//    #add_k - populate the dictionary with add_k values for
//            for history in lm.keys():
//            for char in vocabulary:
//            lm[history][ char] +=add_k
//
//    #if the history is unseen, set probability to
//            for char in vocabulary:
//            lm[None][ char] =add_k
//
//            def normalize (counter):
//            s = float(sum(counter.values()))
//            return [(c, cnt / s)for c, cnt in counter.items()]
//            outlm = {hist:normalize(chars) for hist, chars in lm.items()}
//            return outlm
//
//        }
//        def generate_word (lm, history, order):
//                '' ' Randomly chooses the next letter using the language model.
//        Inputs:
//        lm:
//        The output from calling train_char_lm.
//                history:A sequence of text at least 'order' long.
//        order:The length of the n - grams in the language model.
//
//                Returns:
//        A letter
//        '' '
//        history = history[-order:]
//        dist = lm[history]
//        x = random()
//        for c, v in dist:
//        x = x - v
//        if x <= 0:
//                return c
//
//
//        def generate_text (lm, order, nletters = 500):
//                '' '
//        Generates a bunch of random text based on the language model.
//
//                Inputs:
//        lm:
//        The output from calling train_char_lm.
//                history:A sequence of previous text.
//                order:The length of the n - grams in the language model.
//
//                Returns:
//        A letter
//        '' '
//        history = "~" * order
//        out = []
//                for i in range(nletters):
//        c = generate_letter(lm, history, order)
//        history = history[-order:]+c
//        out.append(c)
//                return "".join(out)
//
//
//        def probability (history, poss_character, lm, order):
//                #access the distributions corresponding to history:
//        history = history[-order:]
//                try:
//        distribution = lm[history]
//        except KeyError:
//        distribution = lm[None] #if the history is unseen
//        for char,val in distribution:
//                if char ==poss_character:
//                return val
//        raise ValueError
//
//
//        def perplexity (test_filename, lm, order):
//                '' '
//        Computes the perplexity of a text file given the language model.
//                Inputs:
//        test_filename:
//        path to text file
//        lm:
//        The output from calling train_char_lm.
//                order:The length of the n - grams in the language model.
//                Output:
//        Float value for perplexity
//        '' '
//        test = open(test_filename, encoding = "latin-1").read()
//        pad = "~" * order
//                test = pad + test
//        try:
//        total_prob = (sum(log(probability(test[:index],test[index], lm, order))for index in
//        range(order, len(test) - order)))
//        perplexity = total_prob **(-1 / (len(test) - order))
//                return perplexity
//
//        except ValueError:
//                return float("inf")
//
//
//        def build_lm (fname, order, add_k):
//        lms = []
//                for i in range(order):
//        lm = train_char_lm(fname, order - i, add_k)
//        lms.append(lm)
//                return lms
//
//
//        def calculate_prob_with_backoff ( char,history, lms, lambdas):
//                '' 'Uses interpolation to compute the probability of char given a series of
//        language models trained with different length n - grams.
//                Inputs:
//        char:Character to compute the probability of.
//        history:
//        A sequence of previous text.
//                lms:A list of language models, outputted by calling train_char_lm.
//        lambdas:A list of weights for each lambda model.These should sum to 1.
//        Returns:
//        Probability of char appearing next in the sequence.
//                '' '
//
//
//        probabilities = [probability(history, char,lms[i], order - i)for i in range(len(lms))]
//                #print(probabilities)#
//        prob = sum(probabilities[i] * lambdas[i] for i in range(len(probabilities)))
//                return prob
//
//
//        def set_lambdas (lms, dev_filename = None):
//                '' 'Returns a list of lambda values that weight the contribution of each n-gram model
//        This can either be done heuristically or by using a development set.
//
//        Inputs:
//        lms:
//        A list of language models, outputted by calling train_char_lm.
//        dev_filename:Path to a development text file to optionally use for tuning the lmabdas.
//                Returns:
//        a list of lambda values.
//        '' '
//        lambdas = [random() for i in range(len(lms))]
//        lambda_sum = sum(lambdas)
//        lambdas = [lamb / lambda_sum for lamb in lambdas]
//                return lambdas
//    }
//
//if __name__ == '__main__':
//    print('Training language model')
//    # lm = train_char_lm("shakespeare_input.txt", order=4)
//    order = 4
//    add_k = 1
//            # lm = train_char_lm("shakespeare_input.txt", order, add_k)
//    # print(perplexity("shakespeare_sonnets.txt", lm, order))
//            # print(perplexity("nytimes_article.txt", lm, order))
//    lms = build_lm("shakespeare_input.txt", order, add_k)
//    # lambdas = set_lambdas(lms)
//    lambdas = [0.4, 0.3, 0.2, 0.1]
//
//    print(calculate_prob_with_backoff("e", "henc", lms, lambdas))
//    lambdas = [0.1, 0.2, 0.3, 0.4]
//    print(calculate_prob_with_backoff("e", "henc", lms, lambdas))
//}
//}
