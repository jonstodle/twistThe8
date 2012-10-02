package twistThe8;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class CardsPanel extends JPanel implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	GameFrame window;
	ArrayList<Card> cards;
	ArrayList<JButton> cardIcons = new ArrayList<JButton>();
	
	public CardsPanel(GameFrame parentWindow){
		window = parentWindow;
		cards = window.currentPlayer.getHand();
		setLayout(new FlowLayout(FlowLayout.CENTER, 10, 0));
		
		for(int i = 0; i < cards.size(); i++){
			JButton card = new JButton(new ImageIcon(getClass().getResource(String.format("cards/%s%s.png", cards.get(i).getSuit(), cards.get(i).getFace()))));
			card.setPreferredSize(new Dimension(card.getIcon().getIconWidth(), card.getIcon().getIconHeight()));
			card.addActionListener(this);
			card.setActionCommand(Integer.toString(i));
			cardIcons.add(card);
			add(card);
		}
	}
	
	public void actionPerformed(ActionEvent e) {
		int selectedCard = Integer.parseInt(e.getActionCommand());
		if(window.isCardPlayable(cards.get(selectedCard))){
			window.playCard(window.currentPlayer.removeCard(selectedCard));
			return;
		} else {
			window.messageLabel.setText(String.format("Dette kortet er ikke et gyldig trekk%s", window.topCard.getFace() == 8 ? ", det ble vridd til " + window.topCard.getSuit() : ""));
			window.buildUI();
			return;
		}
	}
}
