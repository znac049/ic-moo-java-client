package uk.org.wookey.IC.GUI;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JSplitPane;

public class TriPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private JSplitPane leftAndCentre;
	private JSplitPane twoPlusOne;
	
	private Container lhs;
	private Container middle;
	private Container rhs;
	
	public TriPanel() {
		leftAndCentre = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		leftAndCentre.setResizeWeight(0.5);
		
		twoPlusOne = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		twoPlusOne.setResizeWeight(0.5);
		
		lhs = new JPanel();
		lhs.setLayout(new BorderLayout());
		lhs.setMinimumSize(new Dimension(0, 0));
		
		middle = new JPanel();
		middle.setLayout(new BorderLayout());
		
		rhs = new JPanel();
		rhs.setLayout(new BorderLayout());
		rhs.setMinimumSize(new Dimension(0, 0));
		
		leftAndCentre.setLeftComponent(lhs);
		leftAndCentre.setRightComponent(middle);
		leftAndCentre.setContinuousLayout(true);
		
		twoPlusOne.setLeftComponent(leftAndCentre);
		twoPlusOne.setRightComponent(rhs);
		twoPlusOne.setContinuousLayout(true);
		
		setLayout(new BorderLayout());
		add(twoPlusOne, BorderLayout.CENTER);
	}

	public void setLeftPanel(Container c) {
		lhs = c;
		lhs.revalidate();
		lhs.repaint();
	}
	
	public void setMiddlePanel(Container c) {
		middle = c;
		middle.revalidate();
		middle.repaint();
	}
	
	public void setRightPanel(Container c) {
		rhs = c;
		rhs.revalidate();
		rhs.repaint();
	}
	
	public Container getLeftPanel() {
		return lhs;
	}
	
	public Container getMiddlePanel() {
		return middle;
	}
	
	public Container getRightPanel() {
		return rhs;
	}
	
	public void setLeftResizeWeight(double weight) {
		leftAndCentre.setResizeWeight(weight);
	}
	
	public void setRightResizeWeight(double weight) {
		twoPlusOne.setResizeWeight(weight);
	}
	
	public void setResizeWeight(double both) {
		setResizeWeight(both, both);
	}
	
	public void setResizeWeight(double left, double right) {
		leftAndCentre.setResizeWeight(left);
		twoPlusOne.setResizeWeight(right);
	}
}
