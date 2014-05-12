package uk.org.wookey.IC.GUI;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import webBoltOns.layoutManager.GridFlowLayout;
import webBoltOns.layoutManager.GridFlowLayoutParameter;

public class MCPDetailPanel extends JPanel implements ChangeListener {
	private static final long serialVersionUID = 2719930539464525337L;
	public JCheckBox mcpSupport;
	public JCheckBox awnsSupport;
	public JTextField[] awnsColours;
	
	public MCPDetailPanel() {
		super();
		
		setLayout(new GridFlowLayout(6, 4));
		setBorder(new LineBorder(Color.black));
		
		mcpSupport = new JCheckBox("MCP 2.1 Support");
		mcpSupport.setSelected(true);
		mcpSupport.addChangeListener(this);
		add(mcpSupport, new GridFlowLayoutParameter(GridFlowLayoutParameter.NEXT_ROW, 1));
		
		awnsSupport = new JCheckBox("Awns Support");
		awnsSupport.setSelected(true);
		awnsSupport.addChangeListener(this);
		add(awnsSupport, new GridFlowLayoutParameter(GridFlowLayoutParameter.NEXT_ROW, 1));
		
		awnsColours = new JTextField[3];
		
		awnsColours[0] = new JTextField(10);
		addComp("Active User Colour:", awnsColours[0]);
		
		awnsColours[1] = new JTextField(10);
		addComp("Quiet User Colour:", awnsColours[1]);
		
		awnsColours[2] = new JTextField(10);
		addComp("Idle User Colour:", awnsColours[2]);
		
		ColourPicker cp = new ColourPicker();
		addComp("Flibble:", cp);
	}
	
	private void addComp(String label, Component comp) {
		JLabel lab = new JLabel(label, JLabel.TRAILING);
		add(lab, new GridFlowLayoutParameter(GridFlowLayoutParameter.NEXT_ROW, 1));

		lab.setLabelFor(comp);
		add(comp, new GridFlowLayoutParameter(GridFlowLayoutParameter.CURRENT_ROW, 2));		
	}

	public void stateChanged(ChangeEvent arg0) {
		boolean mcp = mcpSupport.isSelected();
		boolean awns = mcp && awnsSupport.isSelected();
		for (int i=0; i<3; i++) {
			awnsColours[i].setEnabled(awns);
		}
	}
}
