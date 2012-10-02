package twistThe8;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GamePanel extends JPanel implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	GameFrame window;
	
	public GamePanel(GameFrame parentWindow){
		window = parentWindow;
		setLayout(new FlowLayout(FlowLayout.CENTER, 30, 0));
		
		add(new JLabel(new ImageIcon(getClass().getResource(String.format("cards/%s%s.png", window.playedDeck.topCard().getSuit(), window.playedDeck.topCard().getFace())))));
		
		JButton deck = new JButton(new ImageIcon(getClass().getResource("cards/bluedeck.png")));
		deck.setPreferredSize(new Dimension(deck.getIcon().getIconWidth(), deck.getIcon().getIconHeight()));
		deck.addActionListener(this);
		add(deck);
	}
	
	public void actionPerformed(ActionEvent arg0) {
		if(!window.cardsVisible) return;
		if(window.currentPlayer.hasPlayableCard(window.topCard)){
			window.messageLabel.setText("Du har kort som kan spilles");
			return;
		}
		
		if(window.freshDeck.isEmpty()){
			Deck tempDeck = window.playedDeck;
			window.playedDeck = new Deck(false);
			window.playedDeck.addCard(tempDeck.takeCard());
			window.freshDeck = tempDeck;
			window.freshDeck.shuffleCards();
		}
		
		window.currentPlayer.addCard(window.freshDeck.takeCard());
		window.messageLabel.setText(String.format("%s trakk %s", window.currentPlayer, window.currentPlayer.getCard(window.currentPlayer.getHand().size() - 1)));
		window.sortCards(window.currentPlayer);
		window.cardsDrawn++;

		if(window.cardsDrawn == 3 && !window.currentPlayer.hasPlayableCard(window.topCard)) {
			window.messageLabel.setText(String.format("%s hadde ingen spillbare kort", window.currentPlayer));
			window.playCard(null);
		}
		else {
			window.buildUI();
		}
	}
}
