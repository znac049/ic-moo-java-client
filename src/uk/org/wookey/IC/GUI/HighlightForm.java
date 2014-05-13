package uk.org.wookey.IC.GUI;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.border.LineBorder;


public class HighlightForm extends JFrame {
	private static final long serialVersionUID = 6022154207602153323L;
	public HighlightList highlightList;
	public NewHighlightDetailsPanel details;

	public HighlightForm() {
		super("Highlights");
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		setLayout(new BorderLayout());
		
		highlightList = new HighlightList();
        highlightList.setBorder(new LineBorder(Color.black));
        highlightList.setSelectedIndex(0);
		add(highlightList, BorderLayout.WEST);
		
		details = new NewHighlightDetailsPanel();
        details.setBorder(new LineBorder(Color.black));
        //_worldList.addMouseListener(new HighlightFormMouseListener(this));
		add(details, BorderLayout.CENTER);
		
		HighlightFormButtons buttonPanel = new HighlightFormButtons(this);
		add(buttonPanel, BorderLayout.SOUTH);

		setLocation(300, 300);
		pack();
		setResizable(false);
		setVisible(true);
	}

}
