package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import dominio.Card;
import dominio.Rank;
import dominio.Suit;

class RankTest {

	@Test
	void testGetCardValue() {
		Rank rank = new Rank(Card.KING, Suit.SWORDS);
		assertEquals(Card.KING, rank.getCardValue(), "El valor de la carta debe ser KING");
	}

	@Test
	void testGetSuit() {
		Rank rank = new Rank(Card.KING, Suit.SWORDS);
		assertEquals(Suit.SWORDS, rank.getSuit(), "El palo debe ser SWORDS");
	}

	@ParameterizedTest
	@CsvSource({ "ACE, 1", "SEVEN, 7", "JACK, 10", "KING, 12" })
	void testGetCardNumber(String cardName, int expectedValue) {
		Card card = Card.valueOf(cardName);
		Rank rank = new Rank(card, Suit.GOLD);
		assertEquals(expectedValue, rank.getCardNumber(), "El número de la carta no coincide con el esperado");
	}

	@Test
	void testToString() {
		Rank rank = new Rank(Card.THREE, Suit.CUPS);
		String expected = "3 🍷"; // El emoji de CUPS es 🍷 y el valor de THREE es 3
		assertEquals(expected, rank.toString(), "El formato de toString no es correcto");
	}
}
