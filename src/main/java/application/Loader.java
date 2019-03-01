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
 * * */

public class Loader {

    private final String dictFile = "data/dict.txt";
    private final String ketWordlist = "data/KETwordlist.txt";
    private final String petWordlist = "data/PETwordlist.txt";
    private final String startersWordlist = "data/STARTERSwordlist.txt";
    private final String moversWordlist = "data/MOVERSwordlist.txt";
    private final String flyersWordlist = "data/FLYERSwordlist.txt";
    private final String FCEwordlist = "data/FCEwordlist.txt";
    private final String TOEFLwordlist = "data/TOEFLwordlist.txt";
    private final String IELTSwordlist = "data/IELTSwordlist.txt";

    public Vocabulary ketVocab;
    public Vocabulary petVocab;
    public Vocabulary startersVocab;
    public Vocabulary moversVocab;
    public Vocabulary flyersVocab;
    public Vocabulary fceVocab;
    public Vocabulary toeflVocab;
    public Vocabulary ieltsVocab;


    /** Creates autocomplete instance with a trie structure and loaded reference dictionary
     * @return trie to populate with completions
     * **/

    public Autocompleter getAutoComplete() {
        AutocompleteOptions tr = new AutocompleteOptions();
        VocabularyLoader.loadVocabulary(tr, this.dictFile);
        return tr;

    }

    /** Creates a VocabularyBuilder object with a reference dictionary loaded in a tree structure
     * @return  reference dictionary
     * **/
    public VocabularyBuilder getDictionary() {
        VocabularyBuilder d = new DictionaryTree(this.dictFile);
        VocabularyLoader.loadVocabulary(d, this.dictFile);
        return d;
    }

    /** Creates a SuggestionsSpelling object with a reference dictionary
     * @param dict - reference dictionary
     * @return spelling suggestions as a trie
     * **/

    public Spellcheker getSuggestionsSpelling(VocabularyBuilder dict) {
        return new SuggestionsSpelling(dict);
    }

    /** Loads wordlist for KET exam into a HashSet structure
     * @return wordlist as HashSet
     * **/

    public HashSet<String> getKetVocab(){
        if (this.ketVocab == null) {
            this.ketVocab = new Vocabulary();
            VocabularyLoader.loadVocabulary(this.ketVocab, this.ketWordlist);
        }
        return this.ketVocab.getVocab();
    }
    /** Loads wordlist for PET exam into a HashSet structure
     * @return wordlist as HashSet
     * **/
    public HashSet<String> getPetVocab(){
        if (this.petVocab == null) {
            this.petVocab = new Vocabulary();
            VocabularyLoader.loadVocabulary(this.petVocab, this.petWordlist);
        }
        return this.petVocab.getVocab();
    }
    /** Loads wordlist for Starters exam into a HashSet structure
     * @return wordlist as HashSet
     * **/
    public HashSet<String> getStartersVocab(){
        if (this.startersVocab == null) {
            this.startersVocab = new Vocabulary();
            VocabularyLoader.loadVocabulary(this.startersVocab, this.startersWordlist);
        }
        return this.startersVocab.getVocab();
    }

    /** Loads wordlist for Movers exam into a HashSet structure
     * @return wordlist as HashSet
     * **/
    public HashSet<String> getMoversVocab(){
        if (this.moversVocab == null) {
            this.moversVocab = new Vocabulary();
            VocabularyLoader.loadVocabulary(this.moversVocab, this.moversWordlist);
        }
        return this.moversVocab.getVocab();
    }

    /** Loads wordlist for Flyers exam into a HashSet structure
     * @return wordlist as HashSet
     * **/
    public HashSet<String> getFlyersVocab(){
        if (this.flyersVocab == null) {
            this.flyersVocab = new Vocabulary();
            VocabularyLoader.loadVocabulary(this.moversVocab, this.flyersWordlist);
        }
        return this.flyersVocab.getVocab();
    }

    /** Loads wordlist for FCE exam into a HashSet structure
     * @return wordlist as HashSet
     * **/
    public HashSet<String> getFCEVocab(){
        if (this.fceVocab == null) {
            this.fceVocab = new Vocabulary();
            VocabularyLoader.loadVocabulary(this.fceVocab, this.FCEwordlist);
        }
        return this.startersVocab.getVocab();
    }

    /** Loads wordlist for TOEFL exam into a HashSet structure
     * @return wordlist as HashSet
     * **/
    public HashSet<String> getTOEFLvocab(){
        if (this.toeflVocab == null) {
            this.toeflVocab = new Vocabulary();
            VocabularyLoader.loadVocabulary(this.toeflVocab, this.TOEFLwordlist);
        }
        return this.startersVocab.getVocab();
    }

    /** Loads wordlist for TOEFL exam into a HashSet structure
     * @return wordlist as HashSet
     * **/
    public HashSet<String> getIELTSvocab(){
        if (this.ieltsVocab == null) {
            this.ieltsVocab = new Vocabulary();
            VocabularyLoader.loadVocabulary(this.ieltsVocab, this.IELTSwordlist);
        }
        return this.startersVocab.getVocab();
    }
}

