package TwistThe8_OLD;

public final class Card {
	private String suit;
	private int number;
	
	public Card(String paramSuit, int paramNumber){
		if (paramSuit != "Kløver" && paramSuit != "Hjerter" && paramSuit != "Spar" && paramSuit != "Ruter")
			throw new IllegalArgumentException("Du har ikke lov å bruke joker!");
		suit = paramSuit;
		
		if (paramNumber < 1 || paramNumber > 13)
			throw new IllegalArgumentException("Det nummeret er ikke i en vanlig kortstokk");
		number = paramNumber;
	}

	public String getSuit() {
		return suit;
	}

	public int getNumber() {
		return number;
	}
	
	public String toString(){
		String strNumber = Integer.toString(number);
		
		if (number == 1) {
			strNumber = "Ess";
		} else if (number == 11) {
			strNumber = "Knekt";
		} else if (number == 12) {
			strNumber = "Dame";
		} else if (number == 13) {
			strNumber = "Kong";
		}
		
		return suit + " " + strNumber;
	}
	
	public boolean equals(Object o) {
		Card c;
		
		if (o.getClass() == this.getClass()) {
			c = (Card)o;
			if (suit.equals(c.getSuit()) && number == c.getNumber()) return true;
		}
		
		return false;
	}
}
