package TwistThe8_OLD;

import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.JFrame;

public class Game extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	ArrayList<Player> players = new ArrayList<Player>();
	Deck freshDeck = new Deck(true);
	Deck playedDeck = new Deck(false);
	Card topCard;
	int chosenAction;
	
	final String ILLEGAL_CHOICE = "Det er ikke et gyldig valg";
	public final String[] suits = new String[]{"Kløver", "Hjerter", "Ruter", "Spar"};
	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new Game();
	}
	
	public Game(){
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(true);
		setTitle("VRI ÅTTER");
		setContentPane(new GamePanel(this));
		setVisible(true);
	}
	
	public void dealCards(){
		freshDeck.shuffleCards();
		
		for (int i = 0; i < 5; i++){
			Iterator<Player> dealCardsIterator = players.iterator();
			
			while (dealCardsIterator.hasNext()){
				dealCardsIterator.next().addCard(freshDeck.takeCard());
			}
		}
		
		playedDeck.addCard(freshDeck.takeCard());
		topCard = playedDeck.topCard();
	}
	
	public Boolean checkForPlayableCard(Player p, Deck d){
		Iterator<Card> cfpcIt = p.getHand().iterator();
		Boolean hasPlayable = false;
		
		while(cfpcIt.hasNext()){
			Card currentCard = cfpcIt.next();
			if (currentCard.getNumber() == 8)							hasPlayable = true;
			if (currentCard.getNumber() == d.topCard().getNumber()) 	hasPlayable = true;
			if (currentCard.getSuit() == d.topCard().getSuit())			hasPlayable = true;
		}
		
		return hasPlayable;
	}
}
