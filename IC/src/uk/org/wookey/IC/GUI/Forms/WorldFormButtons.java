package uk.org.wookey.IC.GUI.Forms;

import java.awt.event.*;

import javax.swing.*;



public class WorldFormButtons extends JPanel implements ActionListener {
	private static final long serialVersionUID = 3195670744580756520L;
	WorldDetailsPanel _details;
	WorldForm _form;
	
	public WorldFormButtons(WorldForm form) {
		_form = form;
		
		button("Save");
		button("Clear");
		button("Delete");
		button("Finished");
	}
	
	private void button(String label) {
		JButton button = new JButton(label);
		button.addActionListener(this);
		add(button);
	}

	public void actionPerformed(ActionEvent e) {
		String buttonName = e.getActionCommand();
		if (buttonName.equalsIgnoreCase("Save")) {
			_form._details.saveDetails();
			_form._worldList.resetList();
		}
		else if (buttonName.equalsIgnoreCase("Clear")) {
			_form._details.clearDetails();
		}
		else if (buttonName.equalsIgnoreCase("Delete")) {
			_form._details.deleteWorld();
			_form._worldList.resetList();
		}
		else if (buttonName.equalsIgnoreCase("Finished")) {
			_form.setVisible(false);
			_form.dispose();
		}
	}
}
