package uk.org.wookey.IC.GUI;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class HilightsFormMouseListener extends MouseAdapter {
	private HighlightForm form;
	
	public HilightsFormMouseListener(HighlightForm f) {
		form = f;
	}
	
	public void mouseClicked(MouseEvent e) {
		//int index = form._worldList.locationToIndex(e.getPoint());
		
		//if (e.getClickCount() == 2) {
		//	ListModel model = form._worldList.getModel();
		//	Object o = model.getElementAt(index);
		//	WorldTabFactory.getWorldTab(o.toString());
		//}
		//else {
		//	ListModel model = form._worldList.getModel();
		//	Object o = model.getElementAt(index);
		//	form._worldList.ensureIndexIsVisible(index);
		//	
		//	form._details.loadDetails(o);
		//}
	}
}
