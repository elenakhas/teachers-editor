//package generation;
//
//import document.FileContent;
//import document.Tokenizer;
//
//import java.util.ArrayList;
//import java.util.LinkedList;
//import java.util.List;
//import java.util.Random;
//
//class TextGenerator {
//
//        // The list of words with their next words
//        private List<ListNode> wordList;
//        // The starting "word"
//        private String starter;
//        // The random number generator
//        private Random numGenerator;
//
//        int order;
//
//        private TextGenerator(Random generator)
//        {
//            wordList = new LinkedList<>();
//            starter = "";
//            numGenerator = generator;
//        }
//
//        /**
//         * This is a minimal set of tests.  Note that it can be difficult
//         * to test methods/classes with randomized behavior.
//         * @param args
//         */
//        public static void main(String[] args)
//        {
//            // feed the generator a fixed random value for repeatable behavior
//            TextGenerator gen = new TextGenerator(new Random(42));
////            String textString = "Hello.  Hello there.  This is a test.  Hello there.  Hello Bob.  Test again.";
////            System.out.println(textString);
////            gen.train(textString);
////            System.out.println(gen);
////            System.out.println(gen.generateText(20));
////            String textString2 = "You say yes, I say no, "+
////                    "You say stop, and I say go, go, go, "+
////                    "Oh no. You say goodbye and I say hello, hello, hello, "+
////                    "I don't know why you say goodbye, I say hello, hello, hello, "+
////                    "I don't know why you say goodbye, I say hello. "+
////                    "I say high, you say low, "+
////                    "You say why, and I say I don't know. "+
////                    "Oh no. "+
////                    "You say goodbye and I say hello, hello, hello. "+
////                    "I don't know why you say goodbye, I say hello, hello, hello, "+
////                    "I don't know why you say goodbye, I say hello. "+
////                    "Why, why, why, why, why, why, "+
////                    "Do you say goodbye. "+
////                    "Oh no. "+
////                    "You say goodbye and I say hello, hello, hello. "+
////                    "I don't know why you say goodbye, I say hello, hello, hello, "+
////                    "I don't know why you say goodbye, I say hello. "+
////                    "You say yes, I say no, "+
////                    "You say stop and I say go, go, go. "+
////                    "Oh, oh no. "+
////                    "You say goodbye and I say hello, hello, hello. "+
////                    "I don't know why you say goodbye, I say hello, hello, hello, "+
////                    "I don't know why you say goodbye, I say hello, hello, hello, "+
////                    "I don't know why you say goodbye, I say hello, hello, hello,";
//
//            FileContent fc = new FileContent("data/KET_example.txt");
//            String text = fc.getContent();
//            gen.train(text);
//            gen.retrain(text);
//            //System.out.println(gen);
//            System.out.println(gen.generateText(30));
//
////            System.out.println(textString2);
////            gen.retrain(textString2);
////            System.out.println(gen);
////            System.out.println(gen.generateText(20));
//        }
//
//        /** Train the generator by adding the sourceText */
//        private void train(String fname)
//        {
//            FileContent fc = new FileContent(fname);
//            String content = fc.getContent();
//            Tokenizer tkn = new Tokenizer();
//            ArrayList<String> data = tkn.tokenize("[!?.]+|[a-zA-Z]+", content);
//
//
//            // TODO: Implement this method
//            if (data.size() < order) {
//                System.out.println("There is no input string!");
//            } else {
//
//                //starter = data.get(order);
//                String[] history = new String [order];
//                        //starter;
//
//                String currentWord;
//                ListNode node;
//                for (int i = order; i <= data.size(); i++) {
//                    if (i == sourceWords.length) {
//                        w = sourceWords[0];
//                    } else {
//                        w = sourceWords[i];
//                    }
//                    node = findNode(prevWord);
//                    if (node == null) {
//                        node = new ListNode(prevWord);
//                        wordList.add(node);
//                    }
//                    node.addNextWord(w);
//                    word_before = prevWord;
//                    prevWord = w;
//                }
//            }
//        }
//
//        /**
//         * Generate the number of words requested.
//         */
//        private String generateText(int numWords) {
//            // TODO: Implement this method
//            String output = "";
//            if (wordList.isEmpty()) {
//                System.out.println("Haven't trained yet!!");
//                return output;
//            }
//            if (numWords == 0) {
//                return output;
//            }
//            String currWord = starter;
//            output = output + currWord;
//            int count = 1;
//            while (count < numWords) {
//                ListNode node = findNode(currWord);
//                String w = node.getRandomNextWord(numGenerator);
//                output = output + " " + w;
//                currWord = w;
//                count++;
//            }
//            return output;
//        }
//
//        // Can be helpful for debugging
//        @Override
//        public String toString()
//        {
//            String toReturn = "";
//            for (ListNode n : wordList)
//            {
//                toReturn += n.toString();
//            }
//            return toReturn;
//        }
//
//        // TODO: Add any private helper methods you need here.
//
//        /** Retrain the generator from scratch on the source text */
//        private void retrain(String sourceText)
//        {
//            // TODO: Implement this method.
//            wordList = new LinkedList<>();
//            train(sourceText);
//        }
//
//        private ListNode findNode(String word) {
//            for (ListNode node : wordList) {
//                if (word.equals(node.getWord())) {
//                    return node;
//                }
//            }
//            return null;
//        }
//
//    }
//
//    /** Links a word to the next words in the list
//     * You should use this class in your implementation. */
//    class ListNode
//    { // The word that is linking to the next words
//        private final String word;
//        // The next words that could follow it
//        private final List<String> nextWords;
//
//        ListNode(String word)
//        {
//            this.word = word;
//            nextWords = new LinkedList<>();
//        }
//
//        public String getWord()
//        {
//            return word;
//        }
//
//        public void addNextWord(String nextWord)
//        {
//            nextWords.add(nextWord);
//        }
//
//        public String getRandomNextWord(Random generator)
//        {
//            // The random number generator should be passed from
//            // the TextGenerator class
//            int size = nextWords.size();
//            int index = generator.nextInt(size);
//            return nextWords.get(index);
//        }
//
//        public String toString()
//        {
//            String toReturn = word + ": ";
//            for (String s : nextWords) {
//                toReturn += s + " ";
//            }
//            toReturn += "\n";
//            return toReturn;
//        }
//
//    }
//
