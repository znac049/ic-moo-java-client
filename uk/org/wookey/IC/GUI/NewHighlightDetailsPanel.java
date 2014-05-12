package uk.org.wookey.IC.GUI;

import java.awt.Component;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import uk.org.wookey.IC.Utils.Logger;
import uk.org.wookey.IC.Utils.WorldCache;
import uk.org.wookey.IC.Utils.WorldDetail;
import webBoltOns.layoutManager.GridFlowLayout;
import webBoltOns.layoutManager.GridFlowLayoutParameter;

public class NewHighlightDetailsPanel extends JPanel {
	private static final long serialVersionUID = 7832805464430880015L;
	private final Logger _logger = new Logger("NewHighlightDetails");
	private JTextField saveName;
	private JCheckBox mcpSupport;

	public NewHighlightDetailsPanel() {
		super();
		
		setLayout(new GridFlowLayout(6, 4));

		saveName = new JTextField(20);
		addComp("Save As:", saveName);
		
		mcpSupport = new JCheckBox("MCP 2.1 Support");
		add(mcpSupport, new GridFlowLayoutParameter(GridFlowLayoutParameter.NEXT_ROW, 1));
	}
	
	private void addComp(String label, Component comp) {
		JLabel lab = new JLabel(label, JLabel.TRAILING);
		add(lab, new GridFlowLayoutParameter(GridFlowLayoutParameter.NEXT_ROW, 1));

		lab.setLabelFor(comp);
		add(comp, new GridFlowLayoutParameter(GridFlowLayoutParameter.CURRENT_ROW, 2));		
	}

	public void clearDetails() {
		saveName.setText("");
		mcpSupport.setSelected(true);
	}
	
	public void loadDetails(Object o) {
		String worldName = o.toString();
		WorldCache cache = new WorldCache();
		WorldDetail detail = cache.getWorld(worldName);
		
		_logger.logMsg("Load details for: " + worldName);

		saveName.setText(worldName);
		mcpSupport.setSelected(detail.getLocalEcho());
	}

	public void saveDetails() {
		String name = saveName.getText();
		WorldCache cache = new WorldCache();
		WorldDetail detail = cache.getWorld(name);
				
		_logger.logMsg("Save details for: " + name);

		if (!name.equals("")) {
			detail.setWorldName(name);
			detail.setMCPEnabled(mcpSupport.isSelected());

			clearDetails();
		}
	}

	public void deleteWorld() {
		String name = saveName.getText();
		
		_logger.logMsg("Delete details for: " + name);

		if (!name.equals("")) {
			WorldCache cache = new WorldCache();

			cache.deleteEntry(name);
			clearDetails();
			_logger.logMsg("World '" + name + "' deleted.");
		}
	}
}
