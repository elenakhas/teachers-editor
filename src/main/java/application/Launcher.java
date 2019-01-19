package application;


public class Launcher {

    public String dictFile = "data/dict.txt";

    public LaunchClass() {
        super();
    }

    public document.Document getDocument(String text) {
        // Change this to BasicDocument(text) for week 1 only
        return new document.EfficientDocument(text);
    }

//	public textgen.MarkovTextGenerator getMTG() {
//		return new textgen.MarkovTextGeneratorLoL(new Random());
//	}

    public spellcheck.WordPath getWordPath() {
        return new spellcheck.WPTree();
    }

    public spellcheck.AutoComplete getAutoComplete() {
        spellcheck.AutoCompleteDictionaryTrie tr = new spellcheck.AutoCompleteDictionaryTrie();
        spellcheck.DictionaryLoader.loadDictionary(tr, dictFile);
        return tr;
    }

    public spellcheck.Dictionary getDictionary() {
        spellcheck.Dictionary d = new spellcheck.DictionaryBST();
        spellcheck.DictionaryLoader.loadDictionary(d, dictFile);
        return d;
    }

    public spellcheck.SpellingSuggest getSpellingSuggest(spellcheck.Dictionary dic) {
        //return new spellcheck.SpellingSuggestNW(new spellcheck.NearbyWords(dic));
        return new spellcheck.NearbyWords(dic);

    }
}
