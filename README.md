# ENGLISH TEACHER'S EDITOR

English Teacher's Editor is a Computer-Aided Language Learning Tool, a text processor aimed to help language teachers evaluate reading materials to select the most adequate ones for their students' proficiency level and assess students' written assignments. It implements a simple graphic user interface and NLP techniques.


## Features:

* Evaluate readability and the level of complexity: 
*uses Flesch Reading Ease and Flesch-Kincaid Grade Level scores adapted to second language learning;

* Check if the text is useful for a certain level
*shows the percentage of words corresponding to a certain ESL exam;

* See the top ten most frequent words in the document

* Count the word

* Check number of spelling mistakes

* Check the size of vocabulary used in the text

* Check the number of sentences

* Spellchecking

* Autocomplete

* See extended statistics on the text:
	* Vocabulary features: percentage of the words of major parts of speech;
	* Grammar features: percentage of the words with particular grammar properties:
		*  comparative and superlative adjectives;
     		*  modal verbs;
    		*  numImperative forms;
     		*  numExistential;
     		*  Present Simple Active;
     		*  Present Perfect Active;
     		*  Present Continuous Active;
     		*  Past Simple Active;
     		*  Past Perfect Active;
     		*  Past Continuous Active;
     		*  Present Perfect Continuous;
     		*  Future Simple Active;
     		*  Future Perfect Active;
     		*  Future Continuous;
	
* Simple Markov text generator

### The levels of English Proficiency treated (according to Cambridge Exams):
* Young learners:
	* Starters
	* Movers
	* Flyers
* KET
* PET
* FCE

### User Interface


## Usage
* Download the repository
* Install dependencies:

	* Richtext FX: https://mvnrepository.com/artifact/org.fxmisc.richtext/richtextfx/0.6.10
	* Stanford Core NLP: https://stanfordnlp.github.io/CoreNLP/download.html

For testing:
	* JUnit 4.12
	* HamcrestCore 1.3. https://mvnrepository.com/artifact/org.hamcrest/hamcrest-core/1.3
	* Slf4j nop binding https://mvnrepository.com/artifact/org.slf4j/slf4j-nop

