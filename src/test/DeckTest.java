package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import dominio.Card;
import dominio.Deck;
import dominio.Rank;
import dominio.Suit;

class DeckTest {

	@Test
	void testCreateRank() {
		Rank rank = Deck.createRank(Card.ACE, Suit.GOLD);
		assertNotNull(rank, "El rango no debería ser nulo");
		assertEquals(Card.ACE, rank.getCardValue());
		assertEquals(Suit.GOLD, rank.getSuit());
	}

	@ParameterizedTest
	@ValueSource(ints = { 1, 3, 5 })
	void testCreateDeckCajaBlancaUnaBaraja(int numberOfPlayers) {
		List<Rank> deck = Deck.createDeck(numberOfPlayers);
		assertNotNull(deck);
		// 1 baraja tiene 4 palos x 10 cartas = 40 cartas
		assertEquals(40, deck.size(), "Debería crear una baraja de 40 cartas para " + numberOfPlayers + " jugadores");
	}

	@ParameterizedTest
	@CsvSource({ "6, 80", "10, 80" })
	void testCreateDeckCajaBlancaDosBarajas(int numberOfPlayers, int expectedCards) {
		List<Rank> deck = Deck.createDeck(numberOfPlayers);
		assertNotNull(deck);
		assertEquals(expectedCards, deck.size(), "Debería crear dos barajas (80 cartas) para más de 5 jugadores");
	}

	@Test
	void testTakeCardFromDeck() {
		Deck deckInstance = new Deck();
		List<Rank> deck = Deck.createDeck(2);
		int initialSize = deck.size();

		Rank card = deckInstance.takeCardFromDeck(deck);

		assertNotNull(card, "La carta extraída no debería ser nula");
		assertEquals(initialSize - 1, deck.size(), "El tamaño de la baraja debería disminuir en 1");
	}

	@Test
	void testTakeCardFromDeckEmptyThrowsException() {
		Deck deckInstance = new Deck();
		List<Rank> deck = Deck.createDeck(2);
		deck.clear(); // Vaciamos la baraja

		// Debería lanzar IndexOutOfBoundsException al intentar sacar de una baraja
		// vacía
		assertThrows(IndexOutOfBoundsException.class, () -> {
			deckInstance.takeCardFromDeck(deck);
		});
	}
}
