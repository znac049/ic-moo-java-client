package uk.org.wookey.IC.newGUI;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import uk.org.wookey.IC.Utils.Logger;

public class Screen extends JTextPane {
	private Logger _logger = new Logger("Screeb");
	private static final long serialVersionUID = 1L;
	private Document document;
	private SimpleAttributeSet lastAttributeSet;
	private char [][] hiLights;
	private SimpleAttributeSet [] attribs;
	private int nHiLights;
	private SimpleAttributeSet lastMatchAttribs;
	
	private SimpleAttributeSet remoteTextAttribs;
	private SimpleAttributeSet localTextAttribs;
	private SimpleAttributeSet statusAttribs;
	private SimpleAttributeSet errorAttribs;

	public Screen() {
		super();
		
		setEditable(false);
		setFocusable(false);
		
		setBackground(Color.black);
		
		document = getDocument();

		remoteTextAttribs = new SimpleAttributeSet();
		StyleConstants.setForeground(remoteTextAttribs, Color.white);

		localTextAttribs = new SimpleAttributeSet();
		StyleConstants.setForeground(localTextAttribs, Color.yellow);
		StyleConstants.setBold(localTextAttribs, true);

		statusAttribs = new SimpleAttributeSet();
		StyleConstants.setForeground(statusAttribs, new Color(96, 64, 128));
		StyleConstants.setItalic(statusAttribs, true);

		errorAttribs = new SimpleAttributeSet();
		StyleConstants.setForeground(errorAttribs, new Color(192, 64, 64));
		StyleConstants.setBold(errorAttribs, true);

		Font font = new Font("Courier", Font.PLAIN, 12);
		setFont(font);
		
		lastAttributeSet = errorAttribs;
		
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
		setText("");
	}
	
	private void write(String s, SimpleAttributeSet a) {
		try {
			document.insertString(document.getLength(), s, a);
			setCaretPosition(document.getLength());
			lastAttributeSet = a;
		} catch (BadLocationException e) {
			e.printStackTrace();
		}		
	}
	
	private void write(char c, SimpleAttributeSet a) {
		write(String.valueOf(c), a);
	}

	private void write(String s) {
		write(s, lastAttributeSet);
	}
	
	private void writeln(String s, SimpleAttributeSet a) {
		write(s+'\n', a);
	}
	
	private void writeln(String s) {
		writeln(s, lastAttributeSet);
	}

	public void info(String s) {
		lastAttributeSet = statusAttribs;
		writeln(s, statusAttribs);
	}
	
	public void error(String s) {
		writeln(s, errorAttribs);
	}
	
	public void local(String s) {
		writeln(s, localTextAttribs);
	}
	
	public void remote(String s) {
		writeln(s, remoteTextAttribs);
	}
}
