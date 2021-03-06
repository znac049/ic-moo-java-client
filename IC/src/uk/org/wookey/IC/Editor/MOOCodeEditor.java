package uk.org.wookey.IC.Editor;

public class MOOCodeEditor extends GenericEditor {
	private static final long serialVersionUID = 1L;

	public MOOCodeEditor() {
		super();
		
		myName = "Code";
		
		String words[] = {"if", "else", "endif", "for", "endfor", "try", "except", "endtry",
							"return", "in"};
		
		wordSeperators = " ;.+-*/=!\n";
		quoteCharacters = "\"";
		reservedWords.add(words);
	}
}
