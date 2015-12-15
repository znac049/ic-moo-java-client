package uk.org.wookey.IC.GUI.Terminal;

import java.util.ArrayList;

public class TerminalLine {
	private ArrayList<TerminalCharacter> line;
	
	private int caret;
	
	public TerminalLine(String str) {
		caret = 0;
		
		line = new ArrayList<TerminalCharacter>();
		
		setText(str);
	}
	
	public void add(TerminalCharacter c) {
		line.add(caret,  c);
		caret++;
	}
	
	public void add(char c) {
		add(new TerminalCharacter(c));
	}
	
	public void setCaret(int newCaret) throws ArrayIndexOutOfBoundsException {
		if ((newCaret < 0) || (newCaret >= line.size())) {
			throw new ArrayIndexOutOfBoundsException("Crazy value for Caret");
		}
		
		caret = newCaret;
	}
	
	public void setText(String l) {
		line.clear();
		caret = 0;
		
		for (int i=0; i<l.length(); i++) {
			add(new TerminalCharacter(l.charAt(i)));
		}
	}
	
	public String getText() {
		char[] res = new char[line.size()];
		int i=0;
		
		for (TerminalCharacter ch: line) {
			res[i] = ch.getChar();
			i++;
		}
		
		return new String(res);
	}
	
	public String toString() {
		String res = "TerminalString: caret=" + caret + ", length=" + line.size() + "\n";
		
		for (TerminalCharacter ch: line) {
			res += "  " + ch;
		}
		
		return res;
	}
	
	public int getLength() {
		return line.size();
	}
}
