package document;

import edu.stanford.nlp.ling.TaggedWord;
import edu.stanford.nlp.parser.nndep.DependencyParser;
import edu.stanford.nlp.trees.GrammaticalStructure;
import edu.stanford.nlp.trees.TypedDependency;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

class ParserUD {
    int comparativeAJ = 0;
    int superlativeAJ = 0;
    int modals = 0;
    int existential = 0;
    int comparativeAD = 0;
    int superlativeAD = 0;



    // check correctness using parser

    // check POS features. First - "vocabulary" features, such as modals and past forms, can do simple lookup

    public static void main(String[] args) {
        //FileContent fcon = new FileContent("data/ket_example.txt");
        //String s = fcon.getContent();
        ParserUD ud = new ParserUD();
        StanfordPOS sfp = new StanfordPOS();
        List<List<TaggedWord>> taggedList = sfp.getsTaggedSentences("Will he ever have been coming? I will have ever been coming"); //incorrectly parses cases with "most"; mixes aux with cop
        System.out.println("Ptinting the tagged list" + taggedList);

        for (List<TaggedWord> tags : taggedList) { // iterate over list
            ud.posAnalysis(tags);
            Collection<TypedDependency> td = ud.parsingSentence(tags);
            //Spliterator spiterator = td.spliterator();
            //System.out.println(td.iterator().next());
            System.out.println("Printing the tree" + td);
            System.out.println(ud.getFuturePerfect(td));
        }
        System.out.println(ud.comparativeAD + "\n" + ud.comparativeAJ + "\n" + ud.existential + "\n" + ud.modals + "\n" + ud.superlativeAD + "\n" + ud.superlativeAJ);
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
            if (!taggedWords.get(taggedWords.size() - 1).toString().equals("?/.") &&
                    (taggedWords.get(j).tag().equals("VB") || taggedWords.get(j).tag().equals("VBP")) &&
                    !taggedWords.get(j - 1).tag().equals("TO") && !taggedWords.get(j - 1).tag().equals("MD")) {
                imp.add(taggedWords.get(j).toString());
            }
        }
        return imp;
    }

    public Collection<TypedDependency> parsingSentence(List<TaggedWord> taggedWords) {
        DependencyParser model = DependencyParser.loadFromModelFile("edu/stanford/nlp/models/parser/nndep/english_UD.gz");

        GrammaticalStructure gs = model.predict(taggedWords);
        return gs.typedDependencies();
    }

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


    public ArrayList<TypedDependency> getPresentContinuous(Collection<TypedDependency> typedDependencies) {
        ArrayList<TypedDependency> presentContinuous = new ArrayList<>();
        Object[] list = typedDependencies.toArray();
        TypedDependency typedDependency;
        for (Object object : list) {
            typedDependency = (TypedDependency) object;
            if (typedDependency.reln().getShortName().equals("aux") && (typedDependency.gov().tag().equals("VBG"))) {
//                if (typedDependency.dep().toString().toLowerCase().equals("am/vbp") | typedDependency.dep().toString().toLowerCase().equals("is/vbz") | typedDependency.dep().toString().toLowerCase().equals("are/vbp")) {
                if (typedDependency.dep().tag().equals("VBP") | typedDependency.dep().tag().equals("VBZ")) {
                    presentContinuous.add(typedDependency);
                }
            }
        }
        return presentContinuous;
    }

    public ArrayList<TypedDependency> getPastContinuous(Collection<TypedDependency> typedDependencies) {
        ArrayList<TypedDependency> pastContinuous = new ArrayList<>();
        Object[] list = typedDependencies.toArray();
        TypedDependency typedDependency;
        for (Object object : list) {
            typedDependency = (TypedDependency) object;
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
        Object[] list = typedDependencies.toArray();
        TypedDependency typedDependency;
        for (Object object : list) {
            typedDependency = (TypedDependency) object;
            if (typedDependency.reln().getShortName().equals("aux") && typedDependency.gov().tag().equals("VBG")) {
                if (typedDependency.dep().toString().toLowerCase().equals("have/vbp") | typedDependency.dep().toString().toLowerCase().equals("has/vbz") || typedDependency.dep().toString().toLowerCase().equals("been/vbn")) {
                    // && typedDependency.dep().toString().toLowerCase().equals("been"))) {
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
    public ArrayList<TypedDependency> getPresentSimple(Collection<TypedDependency> typedDependencies) {
        ArrayList<TypedDependency> presentSimple = new ArrayList<>();
        //TypedDependency[] list = typedDependencies;
        //TypedDependency typedDependency;
        for (TypedDependency typedDependency : typedDependencies) {
            //TypedDependency typedDependency = (TypedDependency) object;
            if (((typedDependency.reln().getShortName().equals("cop")
                    || typedDependency.reln().getShortName().equals("aux")) && (typedDependency.dep().tag().equals("VBP") || typedDependency.dep().tag().equals("VBZ") || typedDependency.dep().tag().equals("VB"))) ||
                    (typedDependency.reln().getShortName().equals("nsubj") && (typedDependency.gov().tag().equals("VBP") || typedDependency.gov().tag().equals("VBZ")))) {
                presentSimple.add(typedDependency);
            }
        }

        return presentSimple;
    }

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
