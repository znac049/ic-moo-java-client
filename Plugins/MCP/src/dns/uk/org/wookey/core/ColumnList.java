package dns.uk.org.wookey.core;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SpringLayout;
import javax.swing.border.LineBorder;

import uk.org.wookey.IC.Utils.Logger;
import uk.org.wookey.IC.Utils.SpringUtilities;

public class ColumnList extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private Logger _logger = new Logger("ColumnList");
	
	protected MooObject ob = null;
	protected int numCols;
	
	protected JScrollPane scroller;
	protected JPanel content;
	
	private Color background;
	private int colNum;
	
	public ColumnList(int nCols) {
		super();
		
		setLayout(new BorderLayout());		
		setBorder(new LineBorder(Color.black));
		
		ob = null;
		numCols = nCols;

		content = new JPanel();
		content.setLayout(new SpringLayout());
		content.setBackground(Color.white);
		content.setBorder(BorderFactory.createEmptyBorder(2, 4, 0, 4));

		scroller = new JScrollPane();
        scroller.getViewport().add(content);
        scroller.setMinimumSize(new Dimension(20, scroller.getMaximumSize().height));
        scroller.setSize(scroller.getMinimumSize());
		add(scroller, BorderLayout.CENTER);
		
		reset();

		addGUI();
	}
	
	public void setObject(MooObject o) {
		ob = o;
		
		rebuildList();
	}
	
	protected void addGUI() {
	}
	
	protected void reset() {
		background = new Color(255, 0, 128);
		colNum = 0;		
		
		content.removeAll();
	}
	
	protected void rebuildList() {
	}
	
	protected void addColumn(String str) {
		addColumn(new JLabel(str));
	}
	
	protected void addColumn(JComponent comp) {
		comp.setBackground(background);
		comp.setOpaque(true);
		
		content.add(comp);
		
		colNum++;
		
		if (colNum == numCols) {
			background = (background == Color.WHITE)?Color.LIGHT_GRAY:Color.WHITE;
			colNum = 0;
		}
	}
	
	protected void packColumns() {
		_logger.logInfo("Packing " + content.getComponentCount() + " items into " + numCols + " columns");
		
		SpringUtilities.makeCompactGrid(content, content.getComponentCount()/numCols, numCols, 0, 0, 0, 0);
		
		revalidate();
	}
}
