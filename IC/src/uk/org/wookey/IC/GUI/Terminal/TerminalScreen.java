package uk.org.wookey.IC.GUI.Terminal;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;

import uk.org.wookey.IC.Utils.Logger;

public class TerminalScreen extends JComponent implements ComponentListener {
	private static final long serialVersionUID = 1L;
	
	private Logger _logger = new Logger("TerminalScreen");
	
	private final Font font = new Font("Monospaced", Font.PLAIN, CHAR_HEIGHT);
	//private final Font font = new Font("Courier New", Font.PLAIN, CELL_HEIGHT);
	
	private static final int CHAR_HEIGHT = 12;
	private static final int CELL_WIDTH = 8;
	private static final int CELL_HEIGHT = CHAR_HEIGHT+0;
	
	private Cell screen[][];

	private int numCols;
	private int numRows;
	
	private int cursorCol;
	private int cursorRow;
	
	private TerminalCharacteristicsInterface characteristicsHandler;
	
	public TerminalScreen() {
		cursorCol = 0;
		cursorRow = 0;
		
		characteristicsHandler = null;
		
		addComponentListener(this);
		
		setMinimumSize(new Dimension(10*CELL_HEIGHT, 10*CELL_WIDTH));
		setMaximumSize(new Dimension(100*CELL_HEIGHT, 200*CELL_WIDTH));
		setPreferredSize(getMinimumSize());

		setSize(new Dimension(CELL_WIDTH, CELL_HEIGHT));
		
		addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                requestFocus();
                super.mouseClicked(e);
            }
        });
		
		setFocusable(false);
	}
	
	public void setSize(Dimension d) {
		if (d.width == 0 || d.height == 0) {
			_logger.logInfo("New size contains one or more dimension of 0");
			return;
		}
		
		super.setSize(d);
		
		int newNumCols = d.width / CELL_WIDTH;
		int newNumRows = d.height / CELL_HEIGHT;
		
		//_logger.logInfo("New screen size: " + d.getWidth() + " x " + d.getHeight());
		//_logger.logInfo("New matrix size: " + newNumCols + " x " + newNumRows);
		
		numRows = newNumRows;
		numCols = newNumCols;
				
		initScreen();
		
		if (characteristicsHandler != null) {
			characteristicsHandler.resized(numRows, numCols);
		}
	}
	
	public int getNumCols() {
		return numCols;
	}
	
	public int getNumRows() {
		return numRows;
	}
	
	private void initScreen() {
		screen = new Cell[numRows][numCols];
		
		for (int row=0; row<numRows; row++) {
			for (int col=0; col<numCols; col++) {
				screen[row][col] = new Cell(' ');
			}
		}
	}
	
	public void clearScreen() {
		for (int row=0; row<numRows; row++) {
			for (int col=0; col<numCols; col++) {
				screen[row][col] = new Cell(' ');
			}
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
	
	public void gotoRC(int row, int col) throws ArrayIndexOutOfBoundsException {
		if ((row < 0) || (row >= numRows)) {
			throw new ArrayIndexOutOfBoundsException("row value out of bounds");
		}
		
		if ((col < 0) || (col >= numCols)) {
			throw new ArrayIndexOutOfBoundsException("column value out of bounds");
		}
		
		cursorRow = row;
		cursorCol = col;
	}

	private void set(int row, int col, char c) {
		try {
			gotoRC(row, col);
			screen[row][col].setChar(c);
		}
		catch (ArrayIndexOutOfBoundsException e) {
			_logger.logInfo("Bad Row/Col: " + row + ", " + col);
			e.printStackTrace();
		}
	}	
	
	public void put(char c) {
		if (c == '\n') {
			cursorCol = 0;
			cursorRow++;
		}
		else {
			set(cursorRow, cursorCol, c);
			cursorRight();
		}
		repaint();
	}
	
	public void put(String str) {
		for (char c: str.toCharArray()) {
			put(c);
		}
	}
	
	public void put(int row, int col, String str) {
		gotoRC(row, col);		
		put(str);
	}
	
	public void addCharacteristicsHandler(TerminalCharacteristicsInterface handler) {
		characteristicsHandler = handler;
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
				int cellY = (row * CELL_HEIGHT) + 0;

				g.setColor(Color.BLACK);
				g.fillRect(cellX, cellY, CELL_WIDTH, CELL_HEIGHT);

				char c = cell.getChar();
				if (c != ' ') {
					//_logger.logInfo("C: '" + c + "'");
					g.setColor(Color.GREEN);
					g.drawChars(new char[] { cell.getChar() }, 0, 1, cellX, cellY + (CELL_HEIGHT / 1));
				}
			}
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

	@Override
	public void componentHidden(ComponentEvent e) {
	}

	@Override
	public void componentMoved(ComponentEvent e) {
	}

	@Override
	public void componentResized(ComponentEvent e) {
		//_logger.logInfo(e.getComponent().getClass().getName() + " - resized\n");
		//_logger.logInfo("New size = " + this.getWidth() + "x" + this.getHeight());
		
		setSize(new Dimension(this.getWidth(), this.getHeight()));
	}

	@Override
	public void componentShown(ComponentEvent e) {
	}
}
