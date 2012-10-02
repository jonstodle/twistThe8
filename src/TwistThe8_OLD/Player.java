package TwistThe8_OLD;

import java.util.ArrayList;
import java.util.Iterator;

public class Player {
	private String name;
	private ArrayList<Card> hand;
	
	public Player(String playerName){
		name = playerName;
		hand = new ArrayList<Card>();
	}
	
	public String toString(){
		String outString = name;
		
		Iterator<Card> handIterator = hand.iterator();
		while (handIterator.hasNext()) {
			Card card = (Card) handIterator.next();
			outString += "\n" + card.toString();
		}
		
		return outString;
	}

	public String getName() {
		return name;
	}
	
	public void addCard(Card cardGiven){
		hand.add(cardGiven);
	}
	
	public Card getCard(int cardIndex){
		return hand.get(cardIndex);
	}
	
	public Card removeCard(int cardIndex){
		return hand.remove(cardIndex);
	}
	
	public ArrayList<Card> getHand(){
		return hand;
	}
}
