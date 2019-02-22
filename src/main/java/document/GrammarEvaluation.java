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


    int comparativeAJ;
    int superlativeAJ;
    int modals;
    int existential;
    int comparativeAD;
    int superlativeAD;

    public static void main(String[] args) {
        //FileContent fcon = new FileContent("data/ket_example.txt");
        //String s = fcon.getContent();
        GrammarEvaluation ud = new GrammarEvaluation();
        PosTagger sfp = new PosTagger();
        List<List<TaggedWord>> taggedList = PosTagger.getsTaggedSentences("Will he ever have been coming? I will have ever been coming"); //incorrectly parses cases with "most"; mixes aux with cop
        System.out.println("Ptinting the tagged list" + taggedList);

        for (List<TaggedWord> tags : taggedList) { // iterate over list
            ud.posAnalysis(tags);
            Collection<TypedDependency> td = ud.parsingSentence(tags);
            System.out.println("Printing the tree" + td);
            System.out.println(ud.getFuturePerfect(td));
        }
        System.out.println(ud.comparativeAD + "\n" + ud.comparativeAJ + "\n" + ud.existential + "\n" + ud.modals + "\n" + ud.superlativeAD + "\n" + ud.superlativeAJ);
    }

    /** Parses a sentence using the Stanford Universal Dependency Parser
     * @param taggedWords - a List of TaggedWord objects, output of a PosTagger using Penn Treebank POS labels
     * @return a collection of TypedDependency objects
     * **/

    public Collection<TypedDependency> parsingSentence(List<TaggedWord> taggedWords) {
        DependencyParser model = DependencyParser.loadFromModelFile("edu/stanford/nlp/models/parser/nndep/english_UD.gz");
        GrammaticalStructure gs = model.predict(taggedWords);
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
//
        for (TaggedWord taggedWord : taggedWords) {//for each word in the sentence
            if (taggedWord.tag().equals("JJR")) {
                this.comparativeAJ++;
            }
            if (taggedWord.tag().equals("JJS")) {
                this.superlativeAJ++;
            }
            if (taggedWord.tag().equals("RBR")) {
                this.comparativeAD++;
            }
            if (taggedWord.tag().equals("RBS")) {
                this.superlativeAD++;
            }
            if (taggedWord.tag().equals("EX")) {
                this.existential++;
            }
            if (taggedWord.tag().equals("MD")) {
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
            // if the td is a copula or auxiliary and the dependent is a VBP, VBZ, VB
            // OR if td is a nominal subj with the VB or VBZ governor, add it to the list of Present Simple Verbs;
            if (((typedDependency.reln().getShortName().equals("cop")
                    || typedDependency.reln().getShortName().equals("aux"))
                    && (typedDependency.dep().tag().equals("VBP") || typedDependency.dep().tag().equals("VBZ") || typedDependency.dep().tag().equals("VB"))) ||
                    (typedDependency.reln().getShortName().equals("nsubj") && (typedDependency.gov().tag().equals("VBP") || typedDependency.gov().tag().equals("VBZ")))) {
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
            // if the td is an aux with a VBG governor
            if (typedDependency.reln().getShortName().equals("aux") && (typedDependency.gov().tag().equals("VBG"))) {
                // and if the td's dependent is VBP or VBZ (a present form), add the td to the output list
                if (typedDependency.dep().tag().equals("VBP") | typedDependency.dep().tag().equals("VBZ")) {
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
            if ((typedDependency.reln().getShortName().equals("aux") || (typedDependency.reln().getShortName().equals("cop")) && typedDependency.gov().tag().equals("VBN"))) {
                if (typedDependency.dep().toString().toLowerCase().equals("have/vbp") | typedDependency.dep().toString().toLowerCase().equals("has/vbz")) {
                    presentPerfect.add(typedDependency);
                }
            }
        }
        return presentPerfect;
    }

    public ArrayList<TypedDependency> getPastContinuous(Collection<TypedDependency> typedDependencies) {
        ArrayList<TypedDependency> pastContinuous = new ArrayList<>();
        for (TypedDependency typedDependency : typedDependencies) {
            if (typedDependency.reln().getShortName().equals("aux") && (typedDependency.gov().tag().equals("VBG"))) {
                if (typedDependency.dep().tag().equals("VBD")) {
                    pastContinuous.add(typedDependency);
                }
            }
        }
        return pastContinuous;
    }

    public ArrayList<TypedDependency> getPresentPerfectContinuous(Collection<TypedDependency> typedDependencies) {
        ArrayList<TypedDependency> presentPerfectContinuous = new ArrayList<>();
        for (TypedDependency typedDependency : typedDependencies) {
            if (typedDependency.reln().getShortName().equals("aux") && typedDependency.gov().tag().equals("VBG")) {
                if (typedDependency.dep().toString().toLowerCase().equals("have/vbp") | typedDependency.dep().toString().toLowerCase().equals("has/vbz") || typedDependency.dep().toString().toLowerCase().equals("been/vbn")) {
                    presentPerfectContinuous.add(typedDependency);
                }
            }
        }
        return presentPerfectContinuous;
    }

    // I wanted to extract the AUX dependency(aux(know-4, did-2)), check that the gov() in this dependency (know) in the output list is the same as the gov in the aux,
    //check that dep() in the AUX is tagged as VBD (past tense), and then delete incorrectly inserted nsubj(know-4, you-3)
// Sentence: advmod(know-4, How-1), aux(know-4, did-2), nsubj(know-4, you-3), root(ROOT-0, know-4), mark(exist-7, that-5), nsubj(exist-7, you-6), ccomp(know-4, exist-7), punct(know-4, ?-8)]
    // Output: [nsubj(know-4, you-3), nsubj(exist-7, you-6)]


    public ArrayList<TypedDependency> getPastSimple(Collection<TypedDependency> typedDependencies) {
        ArrayList<TypedDependency> pastSimple = new ArrayList<>();
        Object[] list = typedDependencies.toArray();
        TypedDependency typedDependency;
        for (Object object : list) {
            typedDependency = (TypedDependency) object;
            if ((typedDependency.reln().getShortName().equals("aux") || typedDependency.reln().getShortName().equals("nsubj")
                    || typedDependency.reln().getShortName().equals("cop") || typedDependency.reln().getShortName().equals("root")) && typedDependency.dep().tag().equals("VBD")) {
                //  if(typedDependency.reln().getShortName().equals("aux")&&typedDependency.dep().tag().equals("VBD") && typedDependency.reln().getShortName().equals("nsubj")&&typedDependency.reln().)
                pastSimple.add(typedDependency);
            }
        }
        return pastSimple;
    }



    public ArrayList<TypedDependency> getPastPerfect(Collection<TypedDependency> typedDependencies) {
        ArrayList<TypedDependency> presentPerfect = new ArrayList<>();

        for (TypedDependency typedDependency : typedDependencies) {
            //typedDependency = (TypedDependency) object;

            if ((typedDependency.reln().getShortName().equals("aux") || (typedDependency.reln().getShortName().equals("cop")) && typedDependency.gov().tag().equals("VBN"))) {
                if (typedDependency.dep().toString().toLowerCase().equals("had/vbd")) {
                    // && typedDependency.dep().toString().toLowerCase().equals("been"))) {
                    presentPerfect.add(typedDependency);
                }
            }
        }
        return presentPerfect;
    }

    public ArrayList<TypedDependency> getFutureSimple(Collection<TypedDependency> typedDependencies) {
        ArrayList<TypedDependency> futureSimple = new ArrayList<>();
        for (TypedDependency typedDependency : typedDependencies) {
            if ((typedDependency.reln().getShortName().equals("aux") && typedDependency.gov().tag().equals("VB")) && typedDependency.dep().toString().toLowerCase().equals("will/md")) {
                // && typedDependency.dep().toString().toLowerCase().equals("been"))) {
                futureSimple.add(typedDependency);
            }
        }
        return futureSimple;
    }

    public ArrayList<TypedDependency> getFutureContinuous(Collection<TypedDependency> typedDependencies) {
        ArrayList<TypedDependency> futureContinuous = new ArrayList<>();
        for (TypedDependency typedDependency : typedDependencies) {
            if ((typedDependency.reln().getShortName().equals("aux") && typedDependency.gov().tag().equals("VBG")) && (typedDependency.dep().toString().toLowerCase().equals("will/md") || typedDependency.dep().toString().toLowerCase().equals("be/vb"))) {
                // && typedDependency.dep().toString().toLowerCase().equals("been"))) {
                futureContinuous.add(typedDependency);
            }
        }
        return futureContinuous;
    }

    //confusion with passive voice e.g. I will be pushed; false positives;
    public ArrayList<TypedDependency> getFuturePerfect(Collection<TypedDependency> typedDependencies) {
        ArrayList<TypedDependency> futurePerfect = new ArrayList<>();
        for (TypedDependency typedDependency : typedDependencies) {
            if (typedDependency.reln().getShortName().equals("aux") && typedDependency.gov().tag().equals("VBN") && (typedDependency.dep().toString().toLowerCase().equals("will/md") | typedDependency.dep().toString().toLowerCase().equals("have/vb") | typedDependency.dep().toString().toLowerCase().equals("have/vbp"))) {
                // && typedDependency.dep().toString().toLowerCase().equals("been"))) {
                futurePerfect.add(typedDependency);
            }
        }
        return futurePerfect;
    }



}
