package garbage;

import document.FileContent;
import document.Tokenizer;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

class POStagging {

    public static void main (String [] args) throws IOException {
        FileContent fcon = new FileContent("data/KET_example.txt");
        String s = fcon.getContent();
        Tokenizer tkn = new Tokenizer();
        String[] tokens = tkn.tokenize("[!?.]+|[a-zA-Z]+", s).toArray(new String[0]);
        POStagging ptg = new POStagging();
        System.out.println(ptg.tagging(tokens).toString());
        System.out.println(ptg.simplifiedTags(tokens).toString());
    }

    private HashMap<String , String> simplifiedTags(String[] sent) throws IOException {
        HashMap<String, String> postagged = tagging(sent);

        for (String i : postagged.keySet()) {
            if (postagged.get(i).equals("NN") | postagged.get(i).equals("NNS") |
                    postagged.get(i).equals("NNP") | postagged.get(i).equals("NNPS")) {
                postagged.replace(i, "noun");
            }
            else if (postagged.get(i).equals("MD") | postagged.get(i).equals("VB") |
                    postagged.get(i).equals("VBD") | postagged.get(i).equals("VBG") |
                    postagged.get(i).equals("VBN") | postagged.get(i).equals("VBP")
                    | postagged.get(i).equals("VBZ")) {
                postagged.replace(i, "verb");
            }
            else if (postagged.get(i).equals("JJ") | postagged.get(i).equals("JJR") |
                    postagged.get(i).equals("JJS")) {
                postagged.replace(i, "adj");
            } else if (postagged.get(i).equals("PRP") | postagged.get(i).equals("PRP$")) {
                postagged.replace(i, "pron");
            } else if (postagged.get(i).equals("RB") | postagged.get(i).equals("RBR") |
                    postagged.get(i).equals("RBS")) {
                postagged.replace(i, "adv");
            } else if (postagged.get(i).equals("CD") | postagged.get(i).equals("LS")) {
                postagged.replace(i, "num");
            } else if (postagged.get(i).equals("WDT") | postagged.get(i).equals("WP") |
                    postagged.get(i).equals("WP$") | postagged.get(i).equals("WRB")) {
                postagged.replace(i, "wh");
            } else if (postagged.get(i).equals("DT") | postagged.get(i).equals("PDT")) {
                postagged.replace(i, "det");
            } else if (postagged.get(i).equals("CC") | postagged.get(i).equals("IN")) {
                postagged.replace(i, "prepconj");
            } else if (postagged.get(i).equals("EX")) {
                postagged.replace(i, "there");
            } else {
                postagged.replace(i, "other");
            }
        }
        return postagged;
    }

    // can use other tags to evaluate grammar
    @SuppressWarnings("unchecked")
    private HashMap<String, String> tagging(String[] sent) throws IOException {
        InputStream modelIn = new FileInputStream("data/en-pos-maxent.bin");
        POSModel model = new POSModel(modelIn);
        POSTaggerME tagger = new POSTaggerME(model);

        String[] tags = tagger.tag(sent);
        HashMap<String, String> tagged = new HashMap();
        for (int i = 0; i < sent.length; i++) {
            tagged.put(sent[i], tags[i]);
        }
        return tagged;
    }

}



