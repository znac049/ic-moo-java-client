package uk.org.wookey.IC.GUI.Forms;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;
import javax.swing.border.*;

import uk.org.wookey.IC.Utils.Logger;

public class WorldForm extends JFrame {
	private static final long serialVersionUID = 6022154207602153323L;
	private Logger _logger = new Logger("WorldForm");
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
        _worldList.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int index = _worldList.locationToIndex(e.getPoint());
				
				if (e.getClickCount() == 2) {
					ListModel model = _worldList.getModel();
					Object o = model.getElementAt(index);
					//WorldTabFactory.getWorldTab(o.toString());
					_logger.logMsg("Dbl-Click PANIC!!!");
				}
				else {
					ListModel model = _worldList.getModel();
					Object o = model.getElementAt(index);
					_worldList.ensureIndexIsVisible(index);
					
					_details.loadDetails(o);
				}
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
			}
        	
        });
		add(_details, BorderLayout.CENTER);
		
		WorldFormButtons buttonPanel = new WorldFormButtons(this);
		add(buttonPanel, BorderLayout.SOUTH);

		setLocation(300, 300);
		pack();
		setResizable(false);
		setVisible(true);
	}
}
