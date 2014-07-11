package uk.org.wookey.ICPlugin.MCP;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SpringLayout;
import javax.swing.border.LineBorder;

import uk.org.wookey.IC.Utils.SpringUtilities;
import uk.org.wookey.IC.Utils.TimeUtils;

public class PlayerList extends JPanel {
    private static final long serialVersionUID = 1L;

	private ArrayList<Player> players;
	private JPanel connectedPlayers;

	public PlayerList() {
		super();
		
		setLayout(new BorderLayout());
		setBorder(new LineBorder(Color.black)); 
		
		//setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5));
		
		JLabel title = new JLabel("Connected Players", JLabel.CENTER);
		add(title, BorderLayout.NORTH);
		
		players = new ArrayList<Player>();
		connectedPlayers = new JPanel();
		
		connectedPlayers.setLayout(new SpringLayout());
		connectedPlayers.setBackground(Color.white);
		connectedPlayers.setBorder(BorderFactory.createEmptyBorder(2, 4, 0, 4));
		
		JScrollPane scroller = new JScrollPane();
        scroller.getViewport().add(connectedPlayers);
        scroller.setMinimumSize(new Dimension(50, scroller.getMaximumSize().height));
        scroller.setSize(scroller.getMinimumSize());
		add(scroller, BorderLayout.CENTER);}
	
	public void add(Player p) {
		players.add(p);
		Sorter.sort(players);
		
		buildPlayerList();
	}
	
	public void setData(ArrayList<Player> p) {
		players = p;
		Sorter.sort(players);
		
		buildPlayerList();
	}
	
	private void buildPlayerList() {
		connectedPlayers.removeAll();
		
		for (Player p: players) {
			JLabel pName = new JLabel(p.getName());
			JLabel pIdle = new JLabel(TimeUtils.approxString(p.getIdle()));
			
			pName.setOpaque(false);
			pIdle.setOpaque(false);
			
			connectedPlayers.add(pName);
			connectedPlayers.add(pIdle);
		}
		
		SpringUtilities.makeCompactGrid(connectedPlayers, players.size(), 2, 0, 0, 2, 2);
		
		connectedPlayers.revalidate();
	}
}
