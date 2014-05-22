package uk.org.wookey.IC.Editor;

import java.util.*;
import javax.swing.text.*;

import uk.org.wookey.IC.Utils.Logger;

public class ReservedWordList {
	//private Logger _logger = new Logger("ReservedWordList");
	public final static int NO = 0;
	public final static int YES = 1;
	public final static int MAYBE = 2;	
	private ArrayList<String> words;
	private String searchWord;
	private String wordMatch;
	
	public ReservedWordList(SimpleAttributeSet attributes) {
		words = new ArrayList<String>();
		words.clear();
		searchWord = new String("");
		wordMatch = new String("");
	}
	
	public void add(String word) {
		words.add(word);
	}
	
	public void add(String [] newWords) {
		for (int i=0; i<newWords.length; i++) {
			words.add(newWords[i]);
		}
	}
	
	public void startSearch() {
		searchWord = "";
		wordMatch = null;
	}
	
	public int isReservedWord(char ch) {
		searchWord = searchWord.concat(String.valueOf(ch));
		int len = searchWord.length();
		
		for (int i=0; i<words.size(); i++) {
			String word = words.get(i);
			
			//_logger.logMsg("Compare '" + searchWord + "' and '" + word + "'");
			if (searchWord.compareTo(word) == 0) {
				wordMatch = searchWord;
				return YES;
			}
			
			if (len < word.length()) {
				if (searchWord.compareTo(word.substring(0, len)) == 0) {
					return MAYBE;
				}
			}
		}
		
		return NO;
	}
	
	public String getMatch() {
		return wordMatch;
	}
}
