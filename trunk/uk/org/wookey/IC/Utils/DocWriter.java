package uk.org.wookey.IC.Utils;

import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;

public class DocWriter {
	private JTextPane screen;
	private Document document;
	private SimpleAttributeSet lastAttributeSet;
	private char [][] hiLights;
	private SimpleAttributeSet [] attribs;
	private int nHiLights;
	private SimpleAttributeSet lastMatchAttribs;
	
	public DocWriter(JTextPane s) {
		screen = s;
		document = screen.getDocument();
		lastAttributeSet = null;
		
		hiLights = new char[16][];
		attribs = new SimpleAttributeSet[16];
		nHiLights = 0;
	}
	
	public void addHighLight(String match, SimpleAttributeSet a) {
		// Check there's room to store this highlight
		if (nHiLights >= hiLights.length) {
			// Need to grow the arrays
			int newLen = hiLights.length << 1;
			char [][] newHiLights = new char[newLen][];
			SimpleAttributeSet [] newAttribs = new SimpleAttributeSet[newLen];
			
			for (int i=0; i<nHiLights; i++) {
				newHiLights[i] = hiLights[i];
				newAttribs[i] = attribs[i];
			}
			
			hiLights = newHiLights;
			attribs = newAttribs;
		}
		
		hiLights[nHiLights] = match.toCharArray();
		attribs[nHiLights] = a;
		nHiLights++;
	}
	
	public void format(String line, SimpleAttributeSet a) {
		char [] chars = line.toCharArray();
		int start = 0;
		
		for (int i=0; i<chars.length; i++) {
			String hilite = findHighlight(chars, i);
			if (hilite != null) {
				write(String.valueOf(chars, start, i-start), a);
				write(hilite, lastMatchAttribs);
				start = i + hilite.length();
				i = start;
			}
		}
		
		write(String.valueOf(chars, start, chars.length - start), a);
	}
	
	public String findHighlight(char [] chars, int startingAt) {
		boolean foundIt = false;
		for (int i=0; i<nHiLights; i++) {
			char [] match = hiLights[i];
			
			foundIt = true;
			for (int x=0; x<match.length; x++) {
				if (chars[startingAt+x] != match[x]) {
					foundIt = false;
					break;
				}
			}
			
			if (foundIt) {
				lastMatchAttribs = attribs[i];
				return String.valueOf(match);
			}
		}
		
		return null;
	}
	
	public void clearScreen() {
		screen.setText("");
	}
	
	public void write(String s, SimpleAttributeSet a) {
		try {
			document.insertString(document.getLength(), s, a);
			screen.setCaretPosition(document.getLength());
			lastAttributeSet = a;
		} catch (BadLocationException e) {
			e.printStackTrace();
		}		
	}
	
	public void write(char c, SimpleAttributeSet a) {
		write(String.valueOf(c), a);
	}

	public void write(String s) {
		write(s, lastAttributeSet);
	}
	
	public void writeln(String s, SimpleAttributeSet a) {
		write(s+'\n', a);
	}
	
	public void writeln(String s) {
		writeln(s, lastAttributeSet);
	}
}
