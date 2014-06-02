package uk.org.wookey.IC.GUI;

import java.awt.Component;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import uk.org.wookey.IC.Utils.Logger;
import uk.org.wookey.IC.newUtils.Prefs;
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
		Preferences prefs = Prefs.node(Prefs.WorldsRoot, worldName);
		
		_logger.logMsg("Load details for: " + worldName);

		saveName.setText(worldName);
		mcpSupport.setSelected(prefs.getBoolean("MCPSupport", false));
	}

	public void saveDetails() {
		String name = saveName.getText();
		Preferences prefs = Prefs.node(Prefs.WorldsRoot, name);
				
		_logger.logMsg("Save details for: " + name);

		if (!name.equals("")) {
			//detail.setWorldName(name);
			prefs.putBoolean("MCPSupport", mcpSupport.isSelected());

			clearDetails();
		}
	}

	public void deleteWorld() {
		String name = saveName.getText();
		
		_logger.logMsg("Delete details for: " + name);

		if (!name.equals("")) {
			Preferences prefs = Prefs.node(Prefs.WorldsRoot, name);

			try {
				prefs.removeNode();
				prefs.flush();

				clearDetails();
				_logger.logMsg("World '" + name + "' deleted.");
			} catch (BackingStoreException e) {
				_logger.logMsg("Failed to delete prefs node " + prefs.absolutePath());
			}
		}
	}
}
