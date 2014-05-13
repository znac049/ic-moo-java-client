package uk.org.wookey.IC.GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;


public class HighlightFormButtons extends JPanel implements ActionListener {
	private static final long serialVersionUID = 3195670744580756520L;
	WorldDetailsPanel _details;
	HighlightForm form;
	
	public HighlightFormButtons(HighlightForm f) {
		form = f;
		
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
			//form._details.saveDetails();
			//form._worldList.resetList();
		}
		else if (buttonName.equalsIgnoreCase("Clear")) {
			//form._details.clearDetails();
		}
		else if (buttonName.equalsIgnoreCase("Delete")) {
			//form._details.deleteWorld();
			//form._worldList.resetList();
		}
		else if (buttonName.equalsIgnoreCase("Finished")) {
			form.setVisible(false);
			form.dispose();
		}
	}

}
