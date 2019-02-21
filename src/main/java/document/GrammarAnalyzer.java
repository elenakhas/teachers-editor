package document;

import edu.stanford.nlp.ling.TaggedWord;
import edu.stanford.nlp.parser.shiftreduce.ShiftReduceParser;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.tregex.TregexMatcher;
import edu.stanford.nlp.trees.tregex.TregexPattern;

import java.util.ArrayList;
import java.util.List;

class GrammarAnalyzer {

    private int comparativeAJ = 0;
    private int superlativeAJ = 0;
    private int modals = 0;
    private int pastSimple = 0;
    private int existential = 0;
    private int comparativeAD = 0;
    private int superlativeAD = 0;
    private int presSimple = 0;
    int imperative = 0;
    int presentPerfect = 0;


    // check correctness using parser

    // check POS features. First - "vocabulary" features, such as modals and past forms, can do simple lookup

    public static void main(String[] args) {
        //FileContent fcon = new FileContent("data/ket_example.txt");
        //String s = fcon.getContent();
        StanfordPOS sfp = new StanfordPOS();
        List<List<TaggedWord>> taggedList = sfp.getsTaggedSentences("She is coming to the room running from him.");
        System.out.println("Ptinting the tagged list" + taggedList);
        GrammarAnalyzer ga = new GrammarAnalyzer();

        for (int i = 0; i < taggedList.size(); i++) { // iterate over list
            List<TaggedWord> tags = taggedList.get(i);
            //System.out.println(ga.imperatives(tags));
            Tree tr = ga.parsingSentence(tags);
            System.out.println("Printing the tree" + tr);
            tr.pennPrint();
            // String prContinuous = "(VP<<VBP<<VBG)| (VP<<VBZ<<VBG) | (SQ<<VBZ<<VBG) | (SQ<<VBP<<VBG)";// present continuous
            String version2 = "VP|SQ<VBP|VBZ<<VBG";// present continuous

            String pastContinuous = "(VP<<VBD<<VBG)| (SQ<<VBD<<VBG)"; // past continuous
            String presentPerfect = "(VP<<)";
            //System.out.println(ga.tregexExtraction(tr, pattern1).toString());
            System.out.println("The found pattern" + ga.tregexExtraction(tr, version2).toString());
            ArrayList<Tree> continuous = ga.tregexExtraction(tr, version2);
            String newPattern = "NP | PP";
            for (Tree continuous1 : continuous) {
                //System.out.println(continuous.get(z).getChildrenAsList());
                ArrayList<Tree> shit = ga.tregexExtraction(continuous.get(i), newPattern);
                System.out.println(shit);
                for (int l = 0; l < shit.size(); l++) {
                    if (continuous1.toString().equals(shit.get(l).toString())) {
                    }
                }
//                }

            }

//            ArrayList<Tree> prCont = ga.tregexExtraction(tr, prContinuous);
//            ArrayList<Tree> presentCont = new ArrayList<>();
//            for (int n = 0; n < prCont.size(); n++) {
//                Tree tree = prCont.get(n);
//                System.out.println("Children of each of the found expressions" + tree.getChildrenAsList());
//                System.out.println("Tagged words" + tree.taggedYield());
//                System.out.println("Words" + tree.yieldWords());
//                for (int m = 0; m < tree.taggedYield().size(); m++) {
//                    if (tree.taggedYield().get(m).equals("am") || tree.taggedYield().get(m).toString().equals("is/VBZ") | tree.taggedYield().get(m).equals("are")) {
//                        System.out.println(tree.taggedYield().get(m));
//                        // presentCont.add(tree.get(n));

        }

    }

    public ArrayList<String> imperatives(List<TaggedWord> taggedWords) {
        ArrayList<String> imp = new ArrayList<>();
        //check the first word
        if (!taggedWords.get(taggedWords.size() - 1).toString().equals("?/.") && (taggedWords.get(0).tag().equals("VB") || taggedWords.get(0).tag().equals("VBP"))) {
            imp.add(taggedWords.get(0).toString());
        }
        //check all other words starting from the 2nd
        for (int j = 1; j < taggedWords.size(); j++) {//for each word in the sentence
            //if it is the first word in the sentence and the sentence is not a question: e.g. Do your job!
//            TaggedWord taggedWord = taggedWords.get(j);
            if (!taggedWords.get(taggedWords.size() - 1).toString().equals("?/.") &&
                    (taggedWords.get(j).tag().equals("VB") || taggedWords.get(j).tag().equals("VBP")) &&
                    !taggedWords.get(j - 1).tag().equals("TO") && !taggedWords.get(j - 1).tag().equals("MD")) {
                imp.add(taggedWords.get(j).toString());
            }
        }
        return imp;
    }

    private Tree parsingSentence(List<TaggedWord> taggedWords) {
        ShiftReduceParser model = ShiftReduceParser.loadModel("edu/stanford/nlp/models/srparser/englishSR.ser.gz");
        return model.apply(taggedWords);
    }

    private ArrayList<Tree> tregexExtraction(Tree tree, String pattern) {
        TregexPattern VBGpattern = TregexPattern.compile(pattern);
        TregexMatcher matcher = VBGpattern.matcher(tree);
        @SuppressWarnings("unchecked") ArrayList<Tree> matches = new ArrayList();
        while (matcher.findNextMatchingNode()) {
            Tree match = matcher.getMatch();
            matches.add(match);
        }
        return matches;
    }

    public void posAnalysis(String input) {
        StanfordPOS sfp = new StanfordPOS();
        List<List<TaggedWord>> taggedList = sfp.getsTaggedSentences(input);
        for (List<TaggedWord> tags : taggedList) { // iterate over list
            for (TaggedWord taggedWord : tags) {//for each word in the sentence
                if (taggedWord.tag().equals("JJR")) {
                    comparativeAJ++;
                }
                if (taggedWord.tag().equals("JJS")) {
                    superlativeAJ++;
                }
                if (taggedWord.tag().equals("RBR")) {
                    comparativeAD++;
                }
                if (taggedWord.tag().equals("RBS")) {
                    superlativeAD++;
                }
                if (taggedWord.tag().equals("EX")) {
                    existential++;
                }
                if (taggedWord.tag().equals("VBD")) {
                    pastSimple++;
                }
                if (taggedWord.tag().equals("VBP") | taggedWord.tag().equals("VBZ")) {
                    presSimple++;
                }
                if (taggedWord.tag().equals("MD")) {
                    modals++;
                }
            }

        }
    }
//                System.out.println(presentCont.toString());


}
