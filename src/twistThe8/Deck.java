package twistThe8;

import java.util.ArrayList;
import java.util.Collections;

public class Deck {
	private ArrayList<Card> cardDeck;
	
	public Deck(Boolean fillUpDeck){
		cardDeck = new ArrayList<Card>();
		
		if (fillUpDeck) fillDeck();
	}
	
	public void fillDeck(){
		if (cardDeck.size() > 0) return;
		
		for (int i = 0; i < 4; i++){
			for (int j = 1; j <= 13; j++){
				cardDeck.add(new Card(i, j));
			}
		}
	}
	
	public void shuffleCards(){
		Collections.shuffle(cardDeck);
		Collections.shuffle(cardDeck);
	}
	
	public Card getCard(int index){
		return cardDeck.get(index);
	}
	
	public Card takeCard(){
		if (isEmpty()) return null;
		return cardDeck.remove(cardDeck.size() - 1);
	}
	
	public void addCard(Card paramCard){
		cardDeck.add(paramCard);
	}
	
	public Card topCard(){
		if(cardDeck.isEmpty()) return null;
		return cardDeck.get(cardDeck.size() - 1);
	}
	
	public int cardsLeft(){
		return cardDeck.size();
	}
	
	public Boolean isEmpty(){
		return cardDeck.size() <= 0;
	}
	
	public java.util.Iterator<Card> getCardIterator(){
		return cardDeck.iterator();
	}
	
	public void removeCard(int paramIndex){
		if (paramIndex > cardsLeft()) throw new IndexOutOfBoundsException("Det er kun " + cardsLeft() + " igjen i stokken");
		else cardDeck.remove(paramIndex);
	}
}