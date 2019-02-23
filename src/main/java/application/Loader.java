package application;

import spellcheck.AutocompleteOptions;
import spellcheck.Autocompleter;
import spellcheck.Spellcheker;
import spellcheck.SuggestionsSpelling;
import vocabulary.DictionaryTree;
import vocabulary.Vocabulary;
import vocabulary.VocabularyBuilder;
import vocabulary.VocabularyLoader;

import java.util.HashSet;

class Loader {

    private final String dictFile = "data/dict.txt";


    public Autocompleter getAutoComplete() {
        AutocompleteOptions tr = new AutocompleteOptions();
        VocabularyLoader.loadVocabulary(tr, dictFile);
        //VocabularyBuilder dict = new DictionaryTree(dictFile);
        //dict.loadVocabulary();
        return tr;

    }

    public VocabularyBuilder getDictionary() {
        VocabularyBuilder d = new DictionaryTree(dictFile);
        VocabularyLoader.loadVocabulary(d, dictFile);
        return d;
    }

    public Spellcheker getSpellingSuggest(VocabularyBuilder dict) {
        //return new spelling.SpellingSuggestNW(new spelling.NearbyWords(dic));
        return new SuggestionsSpelling(dict);
    }

    public HashSet<String> getKetVocab(){
        Vocabulary vb = new Vocabulary();
        String ketWordlist = "data/KETwordlist.txt";
        VocabularyLoader.loadVocabulary(vb, ketWordlist);
        return vb.getVocab();
    }
    public HashSet<String> getPetVocab(){
        Vocabulary vb = new Vocabulary();
        String petWordlist = "data/PETwordlist.txt";
        VocabularyLoader.loadVocabulary(vb, petWordlist);
        return vb.getVocab();
    }
    public HashSet<String> getStartersVocab(){
        Vocabulary vb = new Vocabulary();
        String startersWordlist = "data/STARTERSwordlist.txt";
        VocabularyLoader.loadVocabulary(vb, startersWordlist);
        return vb.getVocab();
    }
    public HashSet<String> getMoversVocab(){
        Vocabulary vb = new Vocabulary();
        String moversWordlist = "data/MOVERSwordlist.txt";
        VocabularyLoader.loadVocabulary(vb, moversWordlist);
        return vb.getVocab();
    }
    public HashSet<String> getFlyersVocab(){
        Vocabulary vb = new Vocabulary();
        String flyersWordlist = "data/FLYERSwordlist.txt";
        VocabularyLoader.loadVocabulary(vb, flyersWordlist);
        return vb.getVocab();
    }
    public HashSet<String> getFCEVocab(){
        Vocabulary vb = new Vocabulary();
        String FCEwordlist = "data/FCEwordlist.txt";
        VocabularyLoader.loadVocabulary(vb, FCEwordlist);
        return vb.getVocab();
    }
    public HashSet<String> getTOEFLvocab(){
        Vocabulary vb = new Vocabulary();
        String TOEFLwordlist = "data/TOEFLwordlist.txt";
        VocabularyLoader.loadVocabulary(vb, TOEFLwordlist);
        return vb.getVocab();
    }
}

