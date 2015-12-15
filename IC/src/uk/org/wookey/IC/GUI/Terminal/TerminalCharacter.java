package uk.org.wookey.IC.GUI.Terminal;

public class TerminalCharacter {
	char ch;
	CharacterAttribute attr;
	
	public TerminalCharacter(char c, CharacterAttribute a) {
		ch = c;
		attr = a;
	}
	
	public TerminalCharacter(char c) {
		this(c, new CharacterAttribute());
	}
	
	public char getChar() {
		return ch;
	}
	
	public void setChar(char c) {
		ch = c;
	}
	
	public CharacterAttribute getAttribute() {
		return attr;
	}
	
	public void setAttribute(CharacterAttribute newAttr) {
		attr = newAttr;
	}
	
	public String toString() {
		return "Char: '" + ch + "', " + attr;
	}
}
