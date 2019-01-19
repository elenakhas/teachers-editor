### teachers-editor
A master project

A text processor for language teachers that helps with the selection of teaching materials and evaluations of students’ essays.

### Files:
txt files that store lexical minimum for Cambridge exams corresponding to several levels of English proficiency:
FCE
PET
KET

Originally they are in pdf format on the official Cambridge website in alphabetic order and classified according to a topic.

### Features:

1) Downloader: follows the link, checks the content type, applies a corresponding parser, returns the content of the file
2) FileReader: read the text from a file, returns the content
3) User input: read the text from user input
4) Evaluator: takes the file and its statistics, returns the level of English
5) Statistics: takes the file, applies various functions, returns statistics about this file
6) Spellcheker
7) Part-Of-Speech tagger
8) Repetition checks: if the same word is repeated within the same paragraph, it is highlighted
9) Simple text generator (?)

### GUI features
1) Space for typing in the text
2) Uploading the file
3) Statistics button
4) Level Button
5) Spellcheck button
6) POS button
7) Highlight the words of a certain level 

### Types of the document:
- vocabulary - PET, KET, FCE, YLE
- texts from URLS
- essays
- dictionary for a spellchecker

### Document treatment
1. Texts for reading:
	Level: count the proportion of the words of a certain level
					highlight the words of a certain level
	POS: highlihgt all words of a certain POS
	Claculate the complexity of the text: Flesch score
	Determine the topic (?)
	
2. Essays:
	Level: count the proportion of the words of a certain level
	Accuracy: highlight and count the misspelled words, suggest corrections
	POS: highlights the words of a certain POS
	Repetitions: highlights the words that are repeated within a certain span
	
### Undecided:
Treatment of grammar
