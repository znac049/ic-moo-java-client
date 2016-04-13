package uk.org.wookey.IC.GUI.Terminal;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.KeyStroke;

import uk.org.wookey.IC.Utils.Logger;

public class Terminal extends JPanel implements AdjustmentListener, TerminalCharacteristicsInterface {
	private static final long serialVersionUID = 1L;
	
	private final static String PRINTABLE_CHAR_ACTION = "Printable";
	private final static String ENTER_ACTION = "Enter";

	private Logger _logger = new Logger("CharacterAttribute");
	
	private TerminalScreen terminal;
	private JScrollBar scroller;
	
	private ArrayList<TerminalLinePlus> lines;
	
	private ArrayList<TerminalActivityInterface> inputListeners;
	
	private InputMap inputMap;
	
	public Terminal() {
		super();
		
		lines = new ArrayList<TerminalLinePlus>();
		
		inputListeners = new ArrayList<TerminalActivityInterface>();
		
		inputMap = new TerminalInputMap();
		
		@SuppressWarnings("serial")
		Action injectPrintable = new AbstractAction() {
		    public void actionPerformed(ActionEvent e) {
		    	put(e.getActionCommand());
		    }
		};
		
		@SuppressWarnings("serial")
		Action enterAction = new AbstractAction() {
		    public void actionPerformed(ActionEvent e) {
		    	newline();
		    }
		};
		
		ActionMap am = new ActionMap();
		am.put(PRINTABLE_CHAR_ACTION, injectPrintable);
		am.put(ENTER_ACTION, enterAction);
		
		setInputMap(WHEN_FOCUSED, inputMap);
		
		setActionMap(am);

		setLayout(new BorderLayout());
		
		scroller = new JScrollBar(JScrollBar.VERTICAL);
		scroller.setMinimum(0);
		scroller.addAdjustmentListener(this);
		
		terminal = new TerminalScreen();
		terminal.addCharacteristicsHandler(this);

		add(terminal, BorderLayout.CENTER);
		add(scroller, BorderLayout.LINE_END);
		
		configureScrollBar();
		
		setEnabled(true);
		setFocusable(true);
	}
	
	public void addInputListener(TerminalActivityInterface l) {
		inputListeners.add(l);
	}
	
	protected void newline() {
		characterTyped('\n');
	}

	protected void put(String actionCommand) {
		characterTyped(actionCommand.charAt(0));
	}

	public void characterTyped(char c) {
		for (TerminalActivityInterface listener: inputListeners) {
			listener.characterTyped(c);
		}
	}

	public void addLine(String line) {
		lines.add(new TerminalLinePlus(line));
		configureScrollBar();
		
		updateStatus();
	}
	
	public void append(char ch) {
		int ind = lines.size()-1;
		TerminalLinePlus line;
		
		if (ind < 0) {
			line = new TerminalLinePlus("");
			lines.add(line);
			ind = 0;
		}
		else {
			line = lines.get(ind);
		}
		
		if (ch == '\n') {
			line = new TerminalLinePlus("");
			lines.add(line);
			configureScrollBar();
		}
		else {
			line.add(ch);
			updateScreen(scroller.getValue());
		}
	}
	
	public void append(String str) {
		for (char ch: str.toCharArray()) {
			append(ch);
		}
	}
	
	public void info(String str) {
		append(str);
	}
	
	public void remote(String str) {
		append(str);
	}

	private void configureScrollBar() {
		int numLines = lines.size();
		int screenRows = terminal.getNumRows();
		boolean allFits = false;
		
		_logger.logInfo("Configuring sctollbar. Lines=" + numLines + ", screenLines=" + screenRows);
		
		if (numLines <= screenRows) {
			// it *may* all fit with no need for a scroll bar
			int totalScreenLinesNeeded = 0;
			
			_logger.logInfo("Lines MAY fit on the display");
			
			for (TerminalLinePlus line: lines) {
				line.numScreenLines = line.countWindowLines();
				
				totalScreenLinesNeeded += line.getNumScreenLines();
			}
			
			_logger.logInfo("Total screen lines needed=" + totalScreenLinesNeeded);
			
			if (totalScreenLinesNeeded <= screenRows) {
				_logger.logInfo("Lines fit - no need for a scroll bar");
			
				allFits = true;
			}
		}
		
		if (allFits) {
			_logger.logInfo("Disable scrollBar");
			scroller.setEnabled(false);
			scroller.setVisible(false);
			scroller.setMaximum(0);
			scroller.setMinimum(0);
		}
		else {
			_logger.logInfo("Enable scrollBar");
			
			boolean wasEnabled = scroller.isVisible();
			
			scroller.setMaximum(numLines-1-terminal.getNumRows());
			scroller.setMinimum(0);

			if (!wasEnabled) {
				scroller.setVisible(true);
				scroller.setEnabled(true);
				scroller.setValue(numLines-1);
			}
		}
		
		updateScreen(scroller.getValue());	
	}
	
	private void updateScreen(int startingLine) {
		_logger.logInfo("Update screen starting with line #" + startingLine);
		
		int numDisplayLines = terminal.getNumRows();
		int rowNum = 0;
		
		int numScreenCols = terminal.getNumCols();
		
		for (int i = startingLine; i<lines.size(); i++) {
			TerminalLinePlus line = lines.get(i);
			
			int col=0;
			int row=0;
			
			for (TerminalCharacter ch: line.getChars()) {
				terminal.set(rowNum+row, col++, ch.getChar());
				
				if (col >= numScreenCols) {
					col = 0;
					row++;
				}
			}
			
			rowNum += line.numScreenLines;
			
			if (rowNum >= numDisplayLines) {
				return;
			}
		}
		
		repaint();
	}
	
	private void updateStatus() {
	}

	@Override
	public void adjustmentValueChanged(AdjustmentEvent arg0) {
		_logger.logInfo("Scroll=" + scroller.getValue());
		
		terminal.clearScreen();
		updateScreen(scroller.getValue());
	}
	
	@Override
	public void resized(int rows, int cols) {
		_logger.logInfo("RESIZE: " + cols + "x" + rows);
		
		configureScrollBar();
	}

	public String toString() {
		return "Terminal has " + lines.size() + " lines";
	}

	@SuppressWarnings("serial")
	private class TerminalInputMap extends InputMap {
		private final static String printables = 
				"0123456789" + 
				"abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ" + 
				"!£$%^&*()_-=+[]{}'@#~/?.,<>\\\"| ";
		
		public TerminalInputMap() {
			super();
			
			for (char c: printables.toCharArray()) {
				put(KeyStroke.getKeyStroke(c),  PRINTABLE_CHAR_ACTION);
			}
			
			put(KeyStroke.getKeyStroke('\n'), ENTER_ACTION);
		}
	}
	
	private class TerminalLinePlus extends TerminalLine {
		public int numScreenLines;
		
		public TerminalLinePlus(String str) {
			super(str);
			
			numScreenLines = countWindowLines();
		}
		
		public void setText(String l) {
			super.setText(l);
						
			numScreenLines = countWindowLines();
		}
		
		public int countWindowLines() {
			return (this.getLength() / terminal.getNumCols()) + 1;
		}
		
		public int getNumScreenLines() {
			return numScreenLines;
		}
	}
}
