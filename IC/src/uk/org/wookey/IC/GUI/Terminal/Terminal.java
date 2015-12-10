package uk.org.wookey.IC.GUI.Terminal;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.KeyStroke;

import uk.org.wookey.IC.Utils.Logger;

public class Terminal extends JComponent implements TerminalInputInterface {
	private static final long serialVersionUID = 1L;
	private final static Logger _logger = new Logger("Console");
	
	private final static String PRINTABLE_CHAR_ACTION = "Printable";
	private final static String ENTER_ACTION = "Enter";

	//private final Font font = new Font("Monospaced", Font.PLAIN, CELL_HEIGHT);
	private final Font font = new Font("Courier New", Font.PLAIN, CELL_HEIGHT/2);
	
	private static final int CELL_WIDTH = 8;
	private static final int CELL_HEIGHT = 24;
	
	private Cell screen[][];

	private int numCols;
	private int numRows;
	
	private int cursorCol;
	private int cursorRow;
	
	private InputMap inputMap;
	
	private String lineBuffer;
	
	private TerminalInputInterface lineCompleteHandler;
	
	public Terminal() {
		cursorCol = 0;
		cursorRow = 0;
		
		inputMap = new TerminalInputMap();
		
		lineCompleteHandler = this;
		
		@SuppressWarnings("serial")
		Action injectPrintable = new AbstractAction() {
		    public void actionPerformed(ActionEvent e) {
		    	put(e.getActionCommand());
		    	lineBuffer += e.getActionCommand(); 
		    }
		};
		
		@SuppressWarnings("serial")
		Action enterAction = new AbstractAction() {
		    public void actionPerformed(ActionEvent e) {
		    	newline();
		    	
		    	lineCompleteHandler.lineComplete(lineBuffer);
		    	lineBuffer = "";
		    }
		};
		
		ActionMap am = new ActionMap();
		am.put(PRINTABLE_CHAR_ACTION, injectPrintable);
		am.put(ENTER_ACTION, enterAction);
		
		setInputMap(WHEN_FOCUSED, inputMap);
		
		setActionMap(am);
		
		setEnabled(true);
		setFocusable(true);
		
		setMinimumSize(new Dimension(10*CELL_HEIGHT, 10*CELL_WIDTH));
		setMaximumSize(new Dimension(100*CELL_HEIGHT, 200*CELL_WIDTH));
		setPreferredSize(getMinimumSize());

		numRows = 25;
		numCols = 80;
		
		lineBuffer = "";

		initScreen();
		
		addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                requestFocus();
                super.mouseClicked(e);
            }
        });
	}
	
	public void setSize(Dimension d) {
		super.setSize(d);
		
		int newNumCols = (int) (d.getWidth() / CELL_WIDTH);
		int newNumRows = (int) (d.getWidth() / CELL_HEIGHT);
		
		_logger.logInfo("New screen size: " + d.getWidth() + " x " + d.getHeight());
		_logger.logInfo("New matrix size: " + newNumCols + " x " + newNumRows);
		
		numRows = newNumRows;
		numCols = newNumCols;
		
		initScreen();
	}
	
	private void initScreen() {
		screen = new Cell[numRows][numCols];
		
		for (int row=0; row<numRows; row++) {
			for (int col=0; col<numCols; col++) {
				screen[row][col] = new Cell(' ');
			}
		}
	}
	
	public void setLineCompleteHandler(TerminalInputInterface handler) {
		lineCompleteHandler = handler;
	}
	
	private void newline() {
		cursorCol = 0;

		cursorRow++;
		if (cursorRow >= numRows) {
			scroll();
			cursorRow = numRows-1;
		}
	}
	
	private void scroll() {
		for (int col=0; col<numCols; col++) {
			for (int row=1; row<numRows; row++) {
				screen[col][row-1] = screen[col][row];
			}
		}
		
		for (int col=0; col<numCols; col++) {
			screen[col][numRows-1].setChar(' ');
		}
	}
	
	private void cursorRight() {
		cursorCol++;
		if (cursorCol >= numCols) {
			cursorRow++;
			cursorCol = 0;
			if (cursorRow >= numRows) {
				scroll();
				cursorRow = numRows-1;
			}
		}
	}

	private void set(int row, int col, char c) {
		screen[row][col].setChar(c);
	}
	
	public void put(char c) {
		set(cursorRow, cursorCol, c);
		cursorRight();
	}
	
	public void put(String str) {
		for (char c: str.toCharArray()) {
			put(c);
		}
		
		repaint();
	}
	
	@Override
	public void lineComplete(String l) {
		_logger.logInfo("LINE: '" + lineBuffer + "'");
	}

	public void paint(Graphics g) {
		g.setFont(font);

		g.setColor(Color.BLACK);
		g.fillRect(0, 0, numCols * CELL_WIDTH, numRows * CELL_HEIGHT);

		for (int row = 0; row < numRows; row++) {
			for (int col = 0; col < numCols; col++) {
				Cell cell = screen[row][col];
				
				boolean cursorHere = (cursorRow == row) && (cursorCol == col);

				if (cursorHere && cell == null) {
					cell = new Cell('+');
				}

				int cellX = col * CELL_WIDTH;
				int cellY = row * CELL_HEIGHT;

				g.setColor(Color.BLACK);
				g.fillRect(cellX, cellY, CELL_WIDTH, CELL_HEIGHT);

				char c = cell.getChar();
				if (c != ' ') {
					_logger.logInfo("C: '" + c + "'");
					g.setColor(Color.GREEN);
					g.drawChars(new char[] { cell.getChar() }, 0, 1, cellX, cellY + (CELL_HEIGHT / 2));
				}
			}
		}
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
	
	private class Cell {
		private char ch;
		
		public Cell(char c) {
			ch = c;
		}
		
		public char getChar() {
			return ch;
		}
		
		public void setChar(char c) {
			ch = c;
		}
	}
}
