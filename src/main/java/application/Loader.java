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


/**
 *  Class to load data from files (reference dictionary and wordlists of different levels of English)
 * @author Elena Khasanova
 * @version 1.0;
 * */

class Loader {

    private final String dictFile = "data/dict.txt";

    /** Creates autocomplete instance with a trie structure and loaded reference dictionary
     * @return trie to populate with completions
     * **/

    public Autocompleter getAutoComplete() {
        AutocompleteOptions tr = new AutocompleteOptions();
        VocabularyLoader.loadVocabulary(tr, dictFile);
        return tr;

    }

    /** Creates a VocabularyBuilder object with a reference dictionary loaded in a tree structure
     * @return  reference dictionary
     * **/
    public VocabularyBuilder getDictionary() {
        VocabularyBuilder d = new DictionaryTree(dictFile);
        VocabularyLoader.loadVocabulary(d, dictFile);
        return d;
    }

    /** Creates a SuggestionsSpelling object with a reference dictionary
     * @return trie
     * **/

    public Spellcheker getSuggestionsSpelling(VocabularyBuilder dict) {
        return new SuggestionsSpelling(dict);
    }

    /** Loads wordlist for KET exam into a HashSet structure
     * @return wordlist as HashSet
     * **/

    public HashSet<String> getKetVocab(){
        Vocabulary vb = new Vocabulary();
        String ketWordlist = "data/KETwordlist.txt";
        VocabularyLoader.loadVocabulary(vb, ketWordlist);
        return vb.getVocab();
    }
    /** Loads wordlist for PET exam into a HashSet structure
     * @return wordlist as HashSet
     * **/
    public HashSet<String> getPetVocab(){
        Vocabulary vb = new Vocabulary();
        String petWordlist = "data/PETwordlist.txt";
        VocabularyLoader.loadVocabulary(vb, petWordlist);
        return vb.getVocab();
    }
    /** Loads wordlist for Starters exam into a HashSet structure
     * @return wordlist as HashSet
     * **/
    public HashSet<String> getStartersVocab(){
        Vocabulary vb = new Vocabulary();
        String startersWordlist = "data/STARTERSwordlist.txt";
        VocabularyLoader.loadVocabulary(vb, startersWordlist);
        return vb.getVocab();
    }

    /** Loads wordlist for Movers exam into a HashSet structure
     * @return wordlist as HashSet
     * **/
    public HashSet<String> getMoversVocab(){
        Vocabulary vb = new Vocabulary();
        String moversWordlist = "data/MOVERSwordlist.txt";
        VocabularyLoader.loadVocabulary(vb, moversWordlist);
        return vb.getVocab();
    }

    /** Loads wordlist for Flyers exam into a HashSet structure
     * @return wordlist as HashSet
     * **/
    public HashSet<String> getFlyersVocab(){
        Vocabulary vb = new Vocabulary();
        String flyersWordlist = "data/FLYERSwordlist.txt";
        VocabularyLoader.loadVocabulary(vb, flyersWordlist);
        return vb.getVocab();
    }

    /** Loads wordlist for FCE exam into a HashSet structure
     * @return wordlist as HashSet
     * **/
    public HashSet<String> getFCEVocab(){
        Vocabulary vb = new Vocabulary();
        String FCEwordlist = "data/FCEwordlist.txt";
        VocabularyLoader.loadVocabulary(vb, FCEwordlist);
        return vb.getVocab();
    }

    /** Loads wordlist for TOEFL exam into a HashSet structure
     * @return wordlist as HashSet
     * **/
    public HashSet<String> getTOEFLvocab(){
        Vocabulary vb = new Vocabulary();
        String TOEFLwordlist = "data/TOEFLwordlist.txt";
        VocabularyLoader.loadVocabulary(vb, TOEFLwordlist);
        return vb.getVocab();
    }
}

