package uk.org.wookey.IC.Editor;

public class JavascriptEditor extends GenericEditor {
	private static final long serialVersionUID = 1L;

	public JavascriptEditor() {
		super();
		
		myName = "JS";
		
		String words[] = {"if", "else", "endif", "for", "endfor", "try", "except", "endtry",
							"return", "in"};
		
		wordSeperators = " ;.+-*/=!\n";
		quoteCharacters = "\"'";
		reservedWords.add(words);
	}
}
