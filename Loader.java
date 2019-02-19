package application;

import document.DictionaryBST;
import document.Vocabulary;
import document.VocabularyBuilder;
import document.VocabularyLoader;
import spellcheck.AutocompleteOptions;
import spellcheck.Autocompleter;
import spellcheck.Spellcheker;
import spellcheck.SuggestionsSpelling;

import java.util.HashSet;

public class Loader {

    public String dictFile = "data/dict.txt";
    public String ketWordlist = "data/KETwordlist.txt";
    public String petWordlist = "data/PETwordlist.txt";
    public String startersWordlist = "data/STARTERSwordlist.txt";
    public String moversWordlist = "data/MOVERSwordlist.txt";
    public String flyersWordlist = "data/FLYERSwordlist.txt";



    public Autocompleter getAutoComplete() {
        AutocompleteOptions tr = new AutocompleteOptions();
        VocabularyLoader.loadVocabulary(tr, dictFile);
        //VocabularyBuilder dict = new DictionaryBST(dictFile);
        //dict.loadVocabulary();
        return tr;

    }

    public VocabularyBuilder getDictionary() {
        VocabularyBuilder d = new DictionaryBST(dictFile);
        VocabularyLoader.loadVocabulary(d, dictFile);
        return d;
    }

    public Spellcheker getSpellingSuggest(VocabularyBuilder dict) {
        //return new spelling.SpellingSuggestNW(new spelling.NearbyWords(dic));
        return new SuggestionsSpelling(dict);
    }

    public HashSet<String> getKetVocab(){
        Vocabulary vb = new Vocabulary();
        VocabularyLoader.loadVocabulary(vb, ketWordlist);
        return vb.getVocab();
    }
    public HashSet<String> getPetVocab(){
        Vocabulary vb = new Vocabulary();
        VocabularyLoader.loadVocabulary(vb, petWordlist);
        return vb.getVocab();
    }
    public HashSet<String> getStartersVocab(){
        Vocabulary vb = new Vocabulary();
        VocabularyLoader.loadVocabulary(vb, startersWordlist);
        return vb.getVocab();
    }
    public HashSet<String> getMoversVocab(){
        Vocabulary vb = new Vocabulary();
        VocabularyLoader.loadVocabulary(vb, moversWordlist);
        return vb.getVocab();
    }
    public HashSet<String> getFlyersVocab(){
        Vocabulary vb = new Vocabulary();
        VocabularyLoader.loadVocabulary(vb, flyersWordlist);
        return vb.getVocab();
    }
}

