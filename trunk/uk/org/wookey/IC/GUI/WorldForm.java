package uk.org.wookey.IC.GUI;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

public class WorldForm extends JFrame {
	private static final long serialVersionUID = 6022154207602153323L;
	public WorldList _worldList;
	public WorldDetailsPanel _details;

	public WorldForm() {
		super("Worlds");
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		setLayout(new BorderLayout());
		
		_worldList = new WorldList();
        _worldList.setBorder(new LineBorder(Color.black));
        _worldList.setSelectedIndex(0);
		add(_worldList, BorderLayout.WEST);
		
		_details = new WorldDetailsPanel();
        _details.setBorder(new LineBorder(Color.black));
        _worldList.addMouseListener(new WorldFormMouseListener(this));
		add(_details, BorderLayout.CENTER);
		
		WorldFormButtons buttonPanel = new WorldFormButtons(this);
		add(buttonPanel, BorderLayout.SOUTH);

		setLocation(300, 300);
		pack();
		setResizable(false);
		setVisible(true);
	}
}
