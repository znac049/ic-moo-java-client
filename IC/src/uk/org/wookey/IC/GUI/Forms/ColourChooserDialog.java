package uk.org.wookey.IC.GUI.Forms;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Event;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.border.LineBorder;

public class ColourChooserDialog extends JFrame implements ActionListener {
	private static final long serialVersionUID = -824371402280686259L;
	JColorChooser chooser;
	JTextField result;
	
	public ColourChooserDialog(JTextField code) {
		super("Worlds");
	
		result = code;
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		setLayout(new BorderLayout());
	
		String value = code.getText().trim();
		int rgbVal = 0xffffff;
		
		if (value.length() > 0) {
			if (value.charAt(0) == '#') {
				rgbVal = Integer.parseInt(value.substring(1), 16);
			}
			else {
				rgbVal = Integer.parseInt(value);
			}
		}
		chooser = new JColorChooser(new Color(rgbVal));		
		add(chooser, BorderLayout.CENTER);
		
		JButton useColour = new JButton("Use Colour");
		useColour.addActionListener(this);
		add(useColour, BorderLayout.SOUTH);
		
		setLocation(300, 300);
		pack();
		setResizable(false);
		setVisible(true);
	}

	public void actionPerformed(ActionEvent arg0) {
		result.setText(colourString());
		
		setVisible(false);
		dispose();
	}
	
	private String colourString() {
		return String.format("#%1$6x", (chooser.getColor().getRGB() & 0x00ffffff));
	}
}
