package uk.org.wookey.IC.Utils;

import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;

public class TextChunk {
	private String text;
	private SimpleAttributeSet attributes;
	
	public TextChunk() {
		text = "";
		attributes = new SimpleAttributeSet();
	}
	
	public void set(String s, SimpleAttributeSet a) {
		text = s;
		attributes = a;
	}
	
	public void set(String s) {
		text = s;
	}
	
	public void set(SimpleAttributeSet a) {
		attributes = a;
	}
	
	public void append(Document doc) {
		try {
			doc.insertString(doc.getLength(), text, attributes);
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
