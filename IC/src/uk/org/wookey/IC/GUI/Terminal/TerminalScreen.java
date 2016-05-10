package uk.org.wookey.IC.GUI.Terminal;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
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
	
	//private final Font font = new Font("Monospaced", Font.PLAIN, 14);
	private final Font font = new Font("Courier New", Font.PLAIN, 14);
	
	private int CHAR_HEIGHT;
	private int CHAR_WIDTH;
	private int CELL_WIDTH;
	private int CELL_HEIGHT;
	private int CELL_BASELINE;
	
	private TerminalCharacter screenBuff[][];

	private int numCols;
	private int numRows;
	
	private TerminalCharacteristicsInterface characteristicsHandler;
	
	public TerminalScreen() {
		FontMetrics fm = getFontMetrics(font);
		
		CHAR_HEIGHT = fm.getHeight();
		
		int[] widths = fm.getWidths();
		
		CHAR_WIDTH = widths[0];
		for (int i=1; i<widths.length; i++) {
			CHAR_WIDTH = Integer.max(CHAR_WIDTH, widths[i]);
		}
		
		CELL_HEIGHT = CHAR_HEIGHT+1;
		CELL_WIDTH = CHAR_WIDTH+1;
		CELL_BASELINE = fm.getDescent()+1;
		
		_logger.logInfo(String.format("%dx%d,%d", CELL_WIDTH, CELL_HEIGHT, CELL_BASELINE));
		
		characteristicsHandler = null;
		
		addComponentListener(this);
		
		setMinimumSize(new Dimension(10*CELL_HEIGHT, 10*CELL_WIDTH));
		setMaximumSize(new Dimension(100*CELL_HEIGHT, 200*CELL_WIDTH));
		setPreferredSize(getMinimumSize());
		
		_logger.logInfo("Instantiate TerminalScreen");

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
		
		_logger.logInfo("New screen size in pixels: " + d.getWidth() + " x " + d.getHeight());
		_logger.logSuccess("New matrix size in characters: " + newNumCols + " x " + newNumRows);
		
		if ((numRows != newNumRows) || (numCols != newNumCols)) {
			numRows = newNumRows;
			numCols = newNumCols;
				
			initScreen();
		}
		
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
		_logger.logInfo("Resizing screen matrix");
		screenBuff = new TerminalCharacter[numRows][numCols];

		clearScreen();
	}
	
	public void clearScreen() {
		for (int row=0; row<numRows; row++) {
			for (int col=0; col<numCols; col++) {
				screenBuff[row][col] = new TerminalCharacter(' ');
			}
		}
	}
	
	public void scroll() {
		for (int col=0; col<numCols; col++) {
			for (int row=1; row<numRows; row++) {
				screenBuff[col][row-1] = screenBuff[col][row];
			}
		}
		
		for (int col=0; col<numCols; col++) {
			screenBuff[col][numRows-1] = new TerminalCharacter(' ');
		}
	}
	
	public void checkCoordinates(int row, int col) throws ArrayIndexOutOfBoundsException {
		if ((row < 0) || (row >= numRows)) {
			throw new ArrayIndexOutOfBoundsException("row " + row + " value out of bounds");
		}
		
		if ((col < 0) || (col >= numCols)) {
			throw new ArrayIndexOutOfBoundsException("column " + col + " value out of bounds");
		}
	}

	public void set(int row, int col, char c) {
		try {
			checkCoordinates(row, col);
			
			screenBuff[row][col].setChar(c);
		}
		catch (ArrayIndexOutOfBoundsException e) {
			_logger.logInfo("Bad Row/Col: " + row + ", " + col);
			e.printStackTrace();
		}
	}	
	
	public void addCharacteristicsHandler(TerminalCharacteristicsInterface handler) {
		characteristicsHandler = handler;
	}
	
	public void paint(Graphics g) {
		g.setFont(font);

		g.setColor(Color.BLACK);
		g.fillRect(0, 0, numCols * CELL_WIDTH, numRows * CELL_HEIGHT);

		int cellY = 0;
		for (int row = 0; row < numRows; row++) {
			int cellX = 0;
			
			for (int col = 0; col < numCols; col++) {
				TerminalCharacter cell = screenBuff[row][col];
					
				cellX += CELL_WIDTH;
	
				g.setColor(Color.BLACK);
				//g.fillRect(cellX, cellY, CELL_WIDTH, CELL_HEIGHT);
	
				g.setColor(cell.getColour());
				g.drawChars(new char[] { cell.getChar() }, 0, 1, cellX, cellY+CELL_HEIGHT-CELL_BASELINE);
			}

			cellY += CELL_HEIGHT;
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
