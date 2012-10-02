package twistThe8;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PlayerPanel extends JPanel implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	GameFrame window;
	Player player;
	
	public PlayerPanel(GameFrame parentWindow){
		window = parentWindow;
		player = window.currentPlayer;
		setLayout(new BorderLayout(0, 10));
		
		JLabel nameLabel = new JLabel(player.toString(), JLabel.CENTER);
		nameLabel.setFont(getFont().deriveFont(Font.BOLD, 16f));
		add(nameLabel, BorderLayout.NORTH);
		
		if(window.currentPlayer != window.previousPlayer){
			JButton coverButton = new JButton("Vis kort");
			coverButton.setPreferredSize(new Dimension(200, 96));
			coverButton.setFont(getFont().deriveFont(16f));
			coverButton.addActionListener(this);
			add(coverButton, BorderLayout.CENTER);
			return;
		}
		
		add(new CardsPanel(window), BorderLayout.CENTER);
	}

	public void actionPerformed(ActionEvent e) {
		window.previousPlayer = window.currentPlayer;
		JButton button = (JButton)e.getSource();
		button.getParent().add(new CardsPanel(window), BorderLayout.CENTER);
		window.cardsVisible = true;
		window.buildUI();
	}
}
