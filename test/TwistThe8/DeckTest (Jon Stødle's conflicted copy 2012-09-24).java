package TwistThe8;

import static org.junit.Assert.*;

import org.junit.Test;

public class DeckTest {

	@Test
	public void testDeck() {
		Deck deck = new Deck();
		assertTrue(deck.isEmpty());
	}

	@Test
	public void testFillDeck() {
		Deck deck = new Deck();
		deck.fillDeck();
		assertEquals(52, deck.cardsLeft());
	}

	@Test
	public void testTakeCard() {
		Deck deck = new Deck();
		deck.addCard(new Card("Spar", 13));
		assertEquals("Spar Kong", deck.takeCard().toString());
	}

	@Test
	public void testAddCard() {
		Deck deck = new Deck();
		Card card = new Card("Hjerter", 5);
		deck.addCard(card);
		assertEquals(card, deck.topCard());
	}

	@Test
	public void testTopCard() {
		Deck deck = new Deck();
		deck.fillDeck();
		assertEquals("Spar Kong", deck.topCard().toString());
	}

	@Test
	public void testCardsLeft() {
		Deck deck = new Deck();
		assertEquals(0, deck.cardsLeft());
		
		deck.fillDeck();
		assertEquals(52, deck.cardsLeft());
	}

	@Test
	public void testIsEmpty() {
		Deck deck = new Deck();
		assertTrue(deck.isEmpty());
		
		deck.fillDeck();
		assertFalse(deck.isEmpty());
	}

	@Test
	public void testRemoveCard() {
		Deck deck = new Deck();
		deck.fillDeck();
		deck.removeCard(8);
		assertEquals(51, deck.cardsLeft());
	}

}
