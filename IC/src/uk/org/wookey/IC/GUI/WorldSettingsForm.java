package uk.org.wookey.IC.GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.ListModel;
import javax.swing.border.LineBorder;

public class WorldSettingsForm extends JFrame {
	private static final long serialVersionUID = 1L;

	public WorldSettingsForm() {
		super("Settings");
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);		
		setLayout(new BorderLayout());
		
		JPanel details = new JPanel();
		add(details, BorderLayout.CENTER);
		
		JPanel buttons = new JPanel();
		
		JButton okButton = new JButton("OK");
		buttons.add(okButton);
		
		JButton cancelButton = new JButton("Cancel");
		buttons.add(cancelButton);
		
		add(buttons, BorderLayout.SOUTH);
	
		setSize(500, 400);
		setLocation(300, 300);

		setResizable(false);
		setVisible(true);
	}
}
