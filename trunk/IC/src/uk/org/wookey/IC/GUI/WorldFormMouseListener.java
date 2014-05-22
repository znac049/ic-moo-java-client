package uk.org.wookey.IC.GUI;

import java.awt.event.*;
import javax.swing.*;
import uk.org.wookey.IC.Factories.WorldTabFactory;

public class WorldFormMouseListener extends MouseAdapter {
	private WorldForm _form;
	
	public WorldFormMouseListener(WorldForm form) {
		_form = form;
	}
	
	public void mouseClicked(MouseEvent e) {
		int index = _form._worldList.locationToIndex(e.getPoint());
		
		if (e.getClickCount() == 2) {
			ListModel model = _form._worldList.getModel();
			Object o = model.getElementAt(index);
			WorldTabFactory.getWorldTab(o.toString());
		}
		else {
			ListModel model = _form._worldList.getModel();
			Object o = model.getElementAt(index);
			_form._worldList.ensureIndexIsVisible(index);
			
			_form._details.loadDetails(o);
		}
	}
}
