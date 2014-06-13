package uk.org.wookey.IC.Editor;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.*;
import javax.swing.text.AbstractDocument;
import javax.swing.text.BadLocationException;
import javax.swing.text.BoxView;
import javax.swing.text.ComponentView;
import javax.swing.text.Document;
import javax.swing.text.Element;
import javax.swing.text.IconView;
import javax.swing.text.LabelView;
import javax.swing.text.ParagraphView;
import javax.swing.text.PlainDocument;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledEditorKit;
import javax.swing.text.View;
import javax.swing.text.ViewFactory;

import uk.org.wookey.IC.Utils.Logger;

public class GenericEditor extends JTextPane {
	private Logger _logger = new Logger("GenericEditor");
	private static final long serialVersionUID = 1L;
	protected SimpleAttributeSet normalAttributes;
	protected SimpleAttributeSet reservedWordAttributes;
	protected SimpleAttributeSet seperatorAttributes;
	protected SimpleAttributeSet stringAttributes;
	protected ReservedWordList reservedWords;
	protected String wordSeperators;
	protected String quoteCharacters;
	protected String myName;

	public GenericEditor() {
		super();
		
		myName = "Text";
		
		setEditorKit(new NumberedEditorKit());
		
		normalAttributes = new SimpleAttributeSet();
		StyleConstants.setForeground(normalAttributes, Color.black);
		
		reservedWordAttributes = new SimpleAttributeSet();
		StyleConstants.setForeground(reservedWordAttributes, new Color(0x73, 0x04, 0x73));
		StyleConstants.setBold(reservedWordAttributes, true);
		
		seperatorAttributes = new SimpleAttributeSet();
		StyleConstants.setForeground(seperatorAttributes, Color.red);
		
		stringAttributes = new SimpleAttributeSet();
		StyleConstants.setForeground(stringAttributes, Color.blue);
		StyleConstants.setItalic(stringAttributes, true);
		
		reservedWords = new ReservedWordList(reservedWordAttributes);

		wordSeperators = " \n";
		quoteCharacters = "";

		setBackground(new Color(0xe0, 0xff, 0xf0));
		setFont(new Font("Courier", Font.PLAIN, 12));
	}
	
	public final String editorName() {
		return myName;
	}

	private void append(String msg, SimpleAttributeSet attributes) {
		Document doc = getDocument();
		
		try {
			doc.insertString(doc.getLength(), msg, attributes);
			setCaretPosition(doc.getLength());		
		} catch (BadLocationException e) {
			e.printStackTrace();
		}		
	}
	
	public void colourize(String content) {
		char [] text = content.toCharArray();
		int startingIndex = 0;
		boolean inQuote = false;
		char quoteChar = '\0';
		
		setText("");

		for (int i=0; i<text.length; i++) {
			reservedWords.startSearch();
			
			if (inQuote) {
				if (text[i] == quoteChar) {
					String quotedString = content.substring(startingIndex, i+1);
					append(quotedString, stringAttributes);
					startingIndex = i+1;
					inQuote = false;
				}
			}
			else {
				if (quoteCharacters.indexOf(text[i]) != -1) {
					inQuote = true;
					quoteChar = text[i];
					if (startingIndex < i) {
						String word = content.substring(startingIndex, i);
						append(word, normalAttributes);
					}
					startingIndex = i;
				}
				else {
					int match = reservedWords.isReservedWord(text[i]);
					int j = i;
					while (match == ReservedWordList.MAYBE) {
						j++;
						match = reservedWords.isReservedWord(text[j]);
					}
					
					if (match == ReservedWordList.YES) {
						if (startingIndex < i) {
							String word = content.substring(startingIndex, i);
							append(word, normalAttributes);
							startingIndex = j+1;
						}
						
						append(reservedWords.getMatch(), reservedWordAttributes);
						i = j+1;
					}
				}
			}
		}
				
		append(content.substring(startingIndex), normalAttributes);
	}
	
	public String sanitizeMOOName(String target) {
		target = target.replaceAll("^Verb: ", "");
		target = target.replaceAll("[: ]", "-");
		
		_logger.logInfo("Sanitised name: '" + target + "'");
		
		return target;
	}
	
	class NumberedEditorKit extends StyledEditorKit {
	    public ViewFactory getViewFactory() {
	        return new NumberedViewFactory();
	    }
	}
	
	class NumberedViewFactory implements ViewFactory {
		@Override
		public View create(Element elem) {
	        String kind = elem.getName();
	        if (kind != null) {
	            if (kind.equals(AbstractDocument.ContentElementName)) {
	                return new LabelView(elem);
	            }
	            else if (kind.equals(AbstractDocument.ParagraphElementName)) {
	                return new NumberedParagraphView(elem);
	            }
	            else if (kind.equals(AbstractDocument.SectionElementName)) {
	                return new BoxView(elem, View.Y_AXIS);
	            }
	            else if (kind.equals(StyleConstants.ComponentElementName)) {
	                return new ComponentView(elem);
	            }
	            else if (kind.equals(StyleConstants.IconElementName)) {
	                return new IconView(elem);
	            }
	        }
	        
	        // default to text display
	        return new LabelView(elem);
	    }
	}
	
	class NumberedParagraphView extends ParagraphView {
	    public static final short NUMBERS_WIDTH=25;

	    public NumberedParagraphView(Element e) {
	        super(e);
	        short top = 0;
	        short left = 0;
	        short bottom = 0;
	        short right = 0;
	        this.setInsets(top, left, bottom, right);
	    }

	    protected void setInsets(short top, short left, short bottom, short right) {
	    	super.setInsets(top,(short)(left+NUMBERS_WIDTH),bottom,right);
	    }

	    public void paintChild(Graphics g, Rectangle r, int n) {
	        super.paintChild(g, r, n);
	        
	        int previousLineCount = getPreviousLineCount();
	        int numberX = r.x - getLeftInset();
	        int numberY = r.y + r.height - 5;
	        g.drawString(Integer.toString(previousLineCount + n + 1),
	                                      numberX, numberY);
	    }

	    public int getPreviousLineCount() {
	        int lineCount = 0;
	        
	        View parent = this.getParent();
	        int count = parent.getViewCount();
	        for (int i = 0; i < count; i++) {
	            if (parent.getView(i) == this) {
	                break;
	            }
	            else {
	                lineCount += parent.getView(i).getViewCount();
	            }
	        }
	        return lineCount;
	    }
	}
}
