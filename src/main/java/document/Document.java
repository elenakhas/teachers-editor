package document;

import statistics.Evaluator;

import java.util.ArrayList;

// a class that represents documents
public abstract class Document implements Evaluator {
    private String filename;
    private String content;

    //
    // a method that takes the content of the file and returns array list of tokens
    private ArrayList tokenizer() {
        //TODO:
        return tokens;
    }



}
