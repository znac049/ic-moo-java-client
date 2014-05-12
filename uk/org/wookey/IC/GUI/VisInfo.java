package uk.org.wookey.IC.GUI;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

public class VisInfo extends JPanel {
	private static final long serialVersionUID = 1197607003091056336L;
	private PlayerList players;
	
	public VisInfo() {
		super();

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		players = new PlayerList();
		add(players);
	}
	
	public PlayerList getPlayerList() {
		return players;
	}
}
