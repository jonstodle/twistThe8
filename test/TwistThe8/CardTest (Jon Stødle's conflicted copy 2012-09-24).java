package TwistThe8;

import static org.junit.Assert.*;

import org.junit.Test;

public class CardTest {
	Card card;
	Card card2;

	@Test
	public void testCard() {
		card = new Card("Kløver", 1);
		assertEquals(new Card("Kløver", 1), card);
		
		card2 = new Card("Hjerter", 11);
		assertEquals(new Card("Hjerter", 11), card2);
	}

	@Test
	public void testGetSuit() {
		card = new Card("Kløver", 1);
		assertEquals("Kløver", card.getSuit());
		
		try{
			card2 = new Card("Kløver", 14);
			fail("For høy verdi å¨kortet!");
		} catch(IllegalArgumentException e){

		}
	}

	@Test
	public void testGetNumber() {
		card = new Card("Kløver", 1);
		assertEquals(1, card.getNumber());
	}

	@Test
	public void testToString() {
		card = new Card("Kløver", 1);
		assertEquals("Kløver Ess", card.toString());
	}

}
