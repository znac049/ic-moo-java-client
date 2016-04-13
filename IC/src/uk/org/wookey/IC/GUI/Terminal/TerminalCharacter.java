package uk.org.wookey.IC.GUI.Terminal;

import java.awt.Color;

public class TerminalCharacter {
	char ch;
	Color colour;
	
	public TerminalCharacter(char c, Color col) {
		ch = c;
		colour = col;
	}
	
	public TerminalCharacter(char c) {
		this(c, Color.GREEN);
	}
	
	public char getChar() {
		return ch;
	}
	
	public void setChar(char c) {
		ch = c;
	}
	
	public String toString() {
		return "Char: '" + ch + "'";
	}

	public void setColour(Color col) {
		colour = col;
	}
	
	public Color getColour() {
		return colour;
	}
	
	@Override
	public boolean equals(Object obj) {
	    if (obj == null) {
	        return false;
	    }
	    
	    if (getClass() != obj.getClass()) {
	        return false;
	    }
	    
	    final TerminalCharacter other = (TerminalCharacter) obj;
	    if ((this.ch == other.ch) && (this.colour == other.colour)) {
	        return true;
	    }
	    
	    return false;
	}
}
