package uk.org.wookey.IC.GUI;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import uk.org.wookey.IC.Utils.LED;

public class ColourPicker extends JPanel implements MouseListener {
	private static final long serialVersionUID = -8461439867901501019L;
	private JTextField text;
	
	public ColourPicker() {
		text = new JTextField(8);
		add(text);
		
		LED led = new LED(192, 192, 22);
		JLabel button = new JLabel(led);
		button.addMouseListener(this);
		add(button);
	}

	public void mouseClicked(MouseEvent arg0) {
		new ColourChooserDialog(text);
	}

	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
