package twistThe8;

public final class Card {
	private String suit;
	private int face;
	private String[] suits = {"Hjerter", "Kløver", "Ruter", "Spar"};
	
	public static final int HEARTS = 0;
	public static final int CLUBS = 1;
	public static final int DIAMONDS = 2;
	public static final int SPADES = 3;
	
	public Card(int paramSuit, int paramNumber){
		if (paramSuit < 0 || paramSuit > 3)
			throw new IllegalArgumentException("Dette er ikke en gyldig verdi!");
		suit = suits[paramSuit];
		
		if (paramNumber < 1 || paramNumber > 13)
			throw new IllegalArgumentException("Det nummeret er ikke i en vanlig kortstokk");
		face = paramNumber;
	}

	public String getSuit() {
		return suit;
	}
	
	public int getSuitAsInt(){
		int value = -1;
		for (int i = 0; i < suits.length; i++) {
			if(suit.equals(suits[i])) value = i;
		}
		return value;
	}

	public int getFace() {
		return face;
	}
	
	public String toString(){
		String strNumber = Integer.toString(face);
		
		if (face == 1) {
			strNumber = "Ess";
		} else if (face == 11) {
			strNumber = "Knekt";
		} else if (face == 12) {
			strNumber = "Dame";
		} else if (face == 13) {
			strNumber = "Kong";
		}
		
		return String.format("%s %s", suit, strNumber);
	}
	
	public boolean equals(Object o) {
		Card c;
		
		if (o.getClass() == this.getClass()) {
			c = (Card)o;
			if (suit.equals(c.getSuit()) && face == c.getFace()) return true;
		}
		
		return false;
	}
}
