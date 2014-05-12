package uk.org.wookey.IC.Editor;

import java.awt.Color;
import java.awt.Font;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

public class GenericEditor extends JTextPane {
	private static final long serialVersionUID = -2687827440556254316L;
	protected SimpleAttributeSet normalAttributes;
	protected SimpleAttributeSet reservedWordAttributes;
	protected SimpleAttributeSet seperatorAttributes;
	protected SimpleAttributeSet stringAttributes;
	protected ReservedWordList reservedWords;
	protected String wordSeperators;
	protected String quoteCharacters;
	protected String myName;

	public GenericEditor() {
		super();
		
		myName = "Text";
		
		normalAttributes = new SimpleAttributeSet();
		StyleConstants.setForeground(normalAttributes, Color.black);
		
		reservedWordAttributes = new SimpleAttributeSet();
		StyleConstants.setForeground(reservedWordAttributes, new Color(212, 116, 2));
		
		seperatorAttributes = new SimpleAttributeSet();
		StyleConstants.setForeground(seperatorAttributes, Color.blue);
		
		stringAttributes = new SimpleAttributeSet();
		StyleConstants.setForeground(stringAttributes, Color.red);
		StyleConstants.setItalic(stringAttributes, true);
		
		reservedWords = new ReservedWordList(reservedWordAttributes);

		wordSeperators = " \n";
		quoteCharacters = "";

		setBackground(new Color(0xe0, 0xff, 0xf0));
		setFont(new Font("Courier", Font.PLAIN, 12));
	}
	
	public final String editorName() {
		return myName;
	}

	private void append(String msg, SimpleAttributeSet attributes) {
		Document doc = getDocument();
		
		try {
			doc.insertString(doc.getLength(), msg, attributes);
			setCaretPosition(doc.getLength());		
		} catch (BadLocationException e) {
			e.printStackTrace();
		}		
	}
	
	public void colourize(String content) {
		char [] text = content.toCharArray();
		int startingIndex = 0;
		boolean inQuote = false;
		char quoteChar = '\0';
		
		setText("");

		for (int i=0; i<text.length; i++) {
			reservedWords.startSearch();
			
			if (inQuote) {
				if (text[i] == quoteChar) {
					String quotedString = content.substring(startingIndex, i+1);
					append(quotedString, stringAttributes);
					startingIndex = i+1;
					inQuote = false;
				}
			}
			else {
				if (quoteCharacters.indexOf(text[i]) != -1) {
					inQuote = true;
					quoteChar = text[i];
					if (startingIndex < i) {
						String word = content.substring(startingIndex, i);
						append(word, normalAttributes);
					}
					startingIndex = i;
				}
				else {
					int match = reservedWords.isReservedWord(text[i]);
					int j = i;
					while (match == ReservedWordList.MAYBE) {
						j++;
						match = reservedWords.isReservedWord(text[j]);
					}
					
					if (match == ReservedWordList.YES) {
						if (startingIndex < i) {
							String word = content.substring(startingIndex, i);
							append(word, normalAttributes);
							startingIndex = j+1;
						}
						
						append(reservedWords.getMatch(), reservedWordAttributes);
						i = j+1;
					}
				}
			}
		}
				
		append(content.substring(startingIndex), normalAttributes);
	}
}
