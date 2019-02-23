package document;

import edu.stanford.nlp.ling.TaggedWord;
import edu.stanford.nlp.parser.nndep.DependencyParser;
import edu.stanford.nlp.trees.GrammaticalStructure;
import edu.stanford.nlp.trees.TypedDependency;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 *  Represents grammar properties of the text and instruments to get them
 * @author Elena Khasanova
 * @version 1.0;
 * **/

class GrammarEvaluation {

    protected DependencyParser model;
    // counters for lexicalized grammar forms
    private int comparativeAJ;
    private int superlativeAJ;
    private int modals;
    private int existential;
    private int comparativeAD;
    private int superlativeAD;

    GrammarEvaluation() {
        this.model = DependencyParser.loadFromModelFile("edu/stanford/nlp/models/parser/nndep/english_UD.gz");
    }

    public int getComparativeAJ() {return this.comparativeAJ;}
    public int getComparativeAD() {return this.comparativeAD;}
    public int getSuperlativeAJ() {return this.superlativeAJ;}
    public int getSuperlativeAD() {return this.superlativeAD;}
    public int getModals() {return this.modals;}
    public int getExistential() {return this.existential;}



//    public static void main(String[] args) {
//        //FileContent fcon = new FileContent("data/ket_example.txt");
//        //String s = fcon.getContent();
//        GrammarEvaluation ud = new GrammarEvaluation();
//        PosTagger sfp = new PosTagger();
//        List<List<TaggedWord>> taggedList = PosTagger.getsTaggedSentences("Will he ever have been coming? I will have ever been coming"); //incorrectly parses cases with "most"; mixes aux with cop
//        System.out.println("Ptinting the tagged list" + taggedList);
//
//        for (List<TaggedWord> tags : taggedList) { // iterate over list
//            ud.posAnalysis(tags);
//            Collection<TypedDependency> td = ud.parsingSentence(tags);
//            System.out.println("Printing the tree" + td);
//            System.out.println(ud.getFuturePerfect(td));
//        }
//        System.out.println(ud.comparativeAD + "\n" + ud.comparativeAJ + "\n" + ud.existential + "\n"
//                            + ud.modals + "\n" + ud.superlativeAD + "\n" + ud.superlativeAJ);
//    }


    /** Parses a sentence using the Stanford Universal Dependency Parser
     * @param taggedWords - a List of TaggedWord objects, output of a PosTagger using Penn Treebank POS labels
     * @return a collection of TypedDependency objects
     * **/

    public Collection<TypedDependency> parsingSentence(List<TaggedWord> taggedWords) {
        GrammaticalStructure gs = this.model.predict(taggedWords);
        return gs.typedDependencies();
    }

    /** Extracts imperative forms from the list of TaggedWord objects.
     *  The accuracy of the method largely depends on the output of the PosTagger
     * @param taggedWords - a List of TaggedWord objects, output of a PosTagger using Penn Treebank POS labels
     * @return ArrayList of strings - imperative forms
     *  **/
    public ArrayList<String> imperatives(List<TaggedWord> taggedWords) {
        ArrayList<String> imp = new ArrayList<>();
        //check the first word: if it is not a question, and the tagged word is a VB (base form)
        // or VBP (non 3rd person present) according to Penn Treebank POS labeling,
        //add the TaggedWord to the list of imperatives
        if (!taggedWords.get(taggedWords.size() - 1).toString().equals("?/.") && (taggedWords.get(0).tag().equals("VB") || taggedWords.get(0).tag().equals("VBP"))) {
            imp.add(taggedWords.get(0).toString());
        }
        //check other TaggedWords starting from the 2nd
        //for each word in the sentence
        for (int j = 1; j < taggedWords.size(); j++) {
            //if the sentence is not a question, the tagged word is a VB or VBP, and the previous word is not "TO"
            // or "MD" (modal verb), add the tagged word to the list of imperatives
            if (!taggedWords.get(taggedWords.size() - 1).toString().equals("?/.") &&
                    (taggedWords.get(j).tag().equals("VB") || taggedWords.get(j).tag().equals("VBP")) &&
                    !taggedWords.get(j - 1).tag().equals("TO") && !taggedWords.get(j - 1).tag().equals("MD")) {
                imp.add(taggedWords.get(j).toString());
            }
        }
        return imp;
    }

    /** Counts the occurrence of lexicalized grammar features: comparative and superlative adjectives and adverbs,
     * modal verbs, existential "there", by comparing the labels in the PosTagger output with the desired ones.
     * Will be replaced with a method returning an ArrayList of these elements to be displayed in GUI in the next release.
     * The accuracy of the method largely depends on the output of the PosTagger
     * @param taggedWords - a List of TaggedWord objects, output of a PosTagger using Penn Treebank POS labels
     *  **/

    public void posAnalysis(List<TaggedWord> taggedWords) {

        for (TaggedWord taggedWord : taggedWords) {
            String tag = taggedWord.tag();
            switch (tag){

                case "JJR":
                    this.comparativeAJ++;
                case "JJS":
                    this.superlativeAJ++;
                case "RBR":
                    this.comparativeAD++;
                case "RBS":
                    this.superlativeAD++;
                case "EX":
                    this.existential++;
                case "MD":
                    this.modals++;
            }
        }
    }

        // THE FOLLOWING METHODS EXTRACT GRAMMAR FEATURES FROM THE STANFORD DEPENDENCY PARSER OUTPUT,
        // MAINLY MOST COMMON VERB FORMS : PRESENT, PAST, FUTURE TENSES;
        // SIMPLE, CONTINUOUS, PERFECT ASPECTS; ACTIVE VOICE; THESE METHODS DEPEND ON THE PARSER OUTPUT,
        // EXTRA CONSTRAINTS SHOULD BE INTRODUCED IN THE NEXT RELEASE TO LEVERAGE THE MISTAKES OF THE PARSER

    /** Extracts the dependencies corresponding to Present Simple Active form;
     * @param typedDependencies - Collection of typedDependencies (td), output of a UD parser for one sentence
     * @return ArrayList of respective typed dependencies
     * **/

    public ArrayList<TypedDependency> getPresentSimple(Collection<TypedDependency> typedDependencies) {
        ArrayList<TypedDependency> presentSimple = new ArrayList<>();
        for (TypedDependency typedDependency : typedDependencies) {
            // if the td is a copula or auxiliary and the dependent is in a present or a base form
            // OR if td is a nominal subj with the base or present governor, add it to the list of Present Simple Verbs;
            if (((typedDependency.reln().getShortName().equals("cop")|| typedDependency.reln().getShortName().equals("aux"))
                && (typedDependency.dep().tag().equals("VBP") || typedDependency.dep().tag().equals("VBZ")
                || typedDependency.dep().tag().equals("VB"))) || (typedDependency.reln().getShortName().equals("nsubj")
                && (typedDependency.gov().tag().equals("VBP") || typedDependency.gov().tag().equals("VBZ")))) {

                presentSimple.add(typedDependency);
            }
        }
        return presentSimple;
    }

    /** Extracts the dependencies corresponding to Present Continuous Active form;
     * @param typedDependencies - Collection of typedDependencies (td), output of a UD parser for one sentence
     * @return ArrayList of respective typed dependencies
     * **/
    public ArrayList<TypedDependency> getPresentContinuous(Collection<TypedDependency> typedDependencies) {
        ArrayList<TypedDependency> presentContinuous = new ArrayList<>();

        for (TypedDependency typedDependency : typedDependencies) {
            // if the td is an aux with a governor in gerund form
            if (typedDependency.reln().getShortName().equals("aux") && (typedDependency.gov().tag().equals("VBG"))) {
                // and if the td's dependent is in a present form, add the td to the output list
                if (typedDependency.dep().tag().equals("VBP") || typedDependency.dep().tag().equals("VBZ")) {

                    presentContinuous.add(typedDependency);
                }
            }
        }
        return presentContinuous;
    }

    /** Extracts the dependencies corresponding to Present Continuous Active form;
     * @param typedDependencies - Collection of typedDependencies (td), output of a UD parser for one sentence
     * @return ArrayList of respective typed dependencies
     * **/
    public ArrayList<TypedDependency> getPresentPerfect(Collection<TypedDependency> typedDependencies) {
        ArrayList<TypedDependency> presentPerfect = new ArrayList<>();
        for (TypedDependency typedDependency : typedDependencies) {
            //if the td is aux or a copula (sometimes aux is mistakenly tagged as cop by the parser)
            // and the governor is a past participle form
            if ((typedDependency.reln().getShortName().equals("aux") || (typedDependency.reln().getShortName().equals("cop"))
                    && typedDependency.gov().tag().equals("VBN"))) {
                // if the dependent is "have" or "has", add the dependency to the list
                if (typedDependency.dep().toString().toLowerCase().equals("have/vbp")||
                    typedDependency.dep().toString().toLowerCase().equals("has/vbz")) {

                    presentPerfect.add(typedDependency);
                }
            }
        }
        return presentPerfect;
    }

    /** Extracts the dependencies corresponding to Past Continuous Active form;
     * @param typedDependencies - Collection of typedDependencies (td), output of a UD parser for one sentence
     * @return ArrayList of respective typed dependencies
     * **/
    public ArrayList<TypedDependency> getPastContinuous(Collection<TypedDependency> typedDependencies) {
        ArrayList<TypedDependency> pastContinuous = new ArrayList<>();
        for (TypedDependency typedDependency : typedDependencies) {
            // if the td is an auxiliary verb and the governor is a gerund
            if (typedDependency.reln().getShortName().equals("aux") && (typedDependency.gov().tag().equals("VBG"))) {
                // if the dependent is a past form, add the td to the list
                if (typedDependency.dep().tag().equals("VBD")) {

                    pastContinuous.add(typedDependency);
                }
            }
        }
        return pastContinuous;
    }

    /** Extracts the dependencies corresponding to Present Perfect Continuous form;
     * @param typedDependencies - Collection of typedDependencies (td), output of a UD parser for one sentence
     * @return ArrayList of respective typed dependencies
     * **/
    public ArrayList<TypedDependency> getPresentPerfectContinuous(Collection<TypedDependency> typedDependencies) {
        ArrayList<TypedDependency> presentPerfectContinuous = new ArrayList<>();
        for (TypedDependency typedDependency : typedDependencies) {
            // if the td is an auxiliary and the governor is a gerund form
            if (typedDependency.reln().getShortName().equals("aux") && typedDependency.gov().tag().equals("VBG")) {
                // if the dependent is "have" or "has"
                if (typedDependency.dep().toString().toLowerCase().equals("have/vbp") ||
                    typedDependency.dep().toString().toLowerCase().equals("has/vbz") ||
                    typedDependency.dep().toString().toLowerCase().equals("been/vbn")) {

                    presentPerfectContinuous.add(typedDependency);
                }
            }
        }
        return presentPerfectContinuous;
    }

    /** Extracts the dependencies corresponding to Past Simple form;
     * @param typedDependencies - Collection of typedDependencies (td), output of a UD parser for one sentence
     * @return ArrayList of respective typed dependencies
     *
     * #TODO: note: sometimes erroneously extracts passive forms
     * **/
    public ArrayList<TypedDependency> getPastSimple(Collection<TypedDependency> typedDependencies) {
        ArrayList<TypedDependency> pastSimple = new ArrayList<>();
        for (TypedDependency typedDependency : typedDependencies) {
            // if the td is an auxiliary, an nonimal subj, a copula, or a root
            if (typedDependency.reln().getShortName().equals("aux") || typedDependency.reln().getShortName().equals("nsubj")
                || typedDependency.reln().getShortName().equals("cop") || typedDependency.reln().getShortName().equals("root"))
                // if the dependent is a verb in the past tense, add td to the list
                if (typedDependency.dep().tag().equals("VBD")) {

                    pastSimple.add(typedDependency);
                }
        }
        return pastSimple;
    }


    /** Extracts the dependencies corresponding to Past Perfect form;
     * @param typedDependencies - Collection of typedDependencies (td), output of a UD parser for one sentence
     * @return ArrayList of respective typed dependencies
     * **/

    public ArrayList<TypedDependency> getPastPerfect(Collection<TypedDependency> typedDependencies) {
        ArrayList<TypedDependency> presentPerfect = new ArrayList<>();

        for (TypedDependency typedDependency : typedDependencies) {
        // if td is a copula or aux, the governor is a past participle
            if ((typedDependency.reln().getShortName().equals("aux") || (typedDependency.reln().getShortName().equals("cop"))
                 && typedDependency.gov().tag().equals("VBN"))) {
                // if the dependent is "had", add td to the list
                if (typedDependency.dep().toString().toLowerCase().equals("had/vbd")) {

                    presentPerfect.add(typedDependency);
                }
            }
        }
        return presentPerfect;
    }

    /** Extracts the dependencies corresponding to Future Simple form;
     * @param typedDependencies - Collection of typedDependencies (td), output of a UD parser for one sentence
     * @return ArrayList of respective typed dependencies
     * **/

    public ArrayList<TypedDependency> getFutureSimple(Collection<TypedDependency> typedDependencies) {
        ArrayList<TypedDependency> futureSimple = new ArrayList<>();
        for (TypedDependency typedDependency : typedDependencies) {
            // if td is an aux, the governor is a base form, and the dependent is "will", add the td
            if ((typedDependency.reln().getShortName().equals("aux") && typedDependency.gov().tag().equals("VB"))
               && typedDependency.dep().toString().toLowerCase().equals("will/md")) {

                futureSimple.add(typedDependency);
            }
        }
        return futureSimple;
    }

    /** Extracts the dependencies corresponding to Future Continuous form;
     * @param typedDependencies - Collection of typedDependencies (td), output of a UD parser for one sentence
     * @return ArrayList of respective typed dependencies
     * **/
    public ArrayList<TypedDependency> getFutureContinuous(Collection<TypedDependency> typedDependencies) {
        ArrayList<TypedDependency> futureContinuous = new ArrayList<>();
        for (TypedDependency typedDependency : typedDependencies) {
            // if td is an auxiliary, the governor is a gerund form, the dependent is "will" or "be"
            if ((typedDependency.reln().getShortName().equals("aux") && typedDependency.gov().tag().equals("VBG"))
                && (typedDependency.dep().toString().toLowerCase().equals("will/md")
                || typedDependency.dep().toString().toLowerCase().equals("be/vb"))) {

                futureContinuous.add(typedDependency);
            }
        }
        return futureContinuous;
    }

    /** Extracts the dependencies corresponding to Future Perfect form;
     * @param typedDependencies - Collection of typedDependencies (td), output of a UD parser for one sentence
     * @return ArrayList of respective typed dependencies
     * **/

    //confusion with passive voice e.g. I will be pushed; false positives;
    public ArrayList<TypedDependency> getFuturePerfect(Collection<TypedDependency> typedDependencies) {
        ArrayList<TypedDependency> futurePerfect = new ArrayList<>();
        for (TypedDependency typedDependency : typedDependencies) {
            // if td is an auxiliary, the governor is past participle and dependents are "will/md", "have"
            // with a base verb tag or a present form, add it to the list
            if (typedDependency.reln().getShortName().equals("aux") && typedDependency.gov().tag().equals("VBN")
               && (typedDependency.dep().toString().toLowerCase().equals("will/md") |
               typedDependency.dep().toString().toLowerCase().equals("have/vb") |
               typedDependency.dep().toString().toLowerCase().equals("have/vbp"))) {

                futurePerfect.add(typedDependency);
            }
        }
        return futurePerfect;
    }


}
