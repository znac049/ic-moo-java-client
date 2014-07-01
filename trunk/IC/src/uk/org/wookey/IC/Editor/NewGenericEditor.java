package uk.org.wookey.IC.Editor;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;

public class NewGenericEditor extends RSyntaxTextArea {
	public static final String SYNTAX_STYLE_MOO			= "text/moo";
	
	public NewGenericEditor() {
		super();
		
		setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_NONE);
		setCodeFoldingEnabled(true);
		
		setText("One, two and three\nFour!");
	}
}
