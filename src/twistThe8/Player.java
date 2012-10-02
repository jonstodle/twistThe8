package twistThe8;

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
		return name;
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
	
	public Boolean hasPlayableCard(Card cardToCompare){
		Iterator<Card> cardIt = hand.iterator();
		while(cardIt.hasNext()){
			Card currentCard = cardIt.next();
			if(currentCard.getFace() == 8) return true;
			else if(currentCard.getFace() == cardToCompare.getFace()) return true;
			else if(currentCard.getSuit() == cardToCompare.getSuit()) return true;
		}
		return false;
	}
}
