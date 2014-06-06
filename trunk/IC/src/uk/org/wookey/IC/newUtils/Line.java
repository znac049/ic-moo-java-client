package uk.org.wookey.IC.newUtils;

public class Line {
	private String line;
	
	public Line() {
		set("");
	}
	
	public Line(String str) {
		set(str);
	}
	
	public void set(String newLine) {
		if (newLine == null) {
			newLine = "";
		}
		
		line = newLine;
	}
	
	public String get() {
		return line;
	}
}
