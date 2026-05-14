package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import dominio.Card;
import dominio.Hand;
import dominio.Rank;
import dominio.Suit;

class HandTest {

	private Hand hand;
	private ArrayList<Rank> cards;

	@BeforeEach
	void setUp() {
		cards = new ArrayList<>();
		hand = new Hand(cards);
	}

	@Test
	void testSizeHandEmpty() {
		assertEquals(0, hand.SizeHand(), "La mano vacía debe tener tamaño 0");
	}

	@Test
	void testAddCardsCajaBlancaRamaPermiteAgregar() {
		hand.AddCards(new Rank(Card.ACE, Suit.GOLD));
		assertEquals(1, hand.SizeHand(), "El tamaño debe ser 1 tras añadir una carta");
	}

	@Test
	void testAddCardsCajaBlancaRamaNoPermiteAgregar() {
		// La mano permite hasta 8 cartas según el código (<= 8, por lo que permite
		// hasta 9 cartas realmente, de 0 a 8)
		for (int i = 0; i < 9; i++) {
			hand.AddCards(new Rank(Card.ACE, Suit.GOLD));
		}
		assertEquals(9, hand.SizeHand(), "El tamaño debe ser 9 tras añadir el límite de cartas permitidas");

		// Añadir una más debería ser ignorada por la condición <= 8 (el tamaño ya es 9,
		// por lo que hand.size() es 9, la condición 9 <= 8 es falsa)
		hand.AddCards(new Rank(Card.TWO, Suit.GOLD));
		assertEquals(9, hand.SizeHand(), "El tamaño no debe exceder el máximo permitido de la lógica");
	}

	@Test
	void testDiscardcardNormal() {
		hand.AddCards(new Rank(Card.ACE, Suit.GOLD));
		hand.AddCards(new Rank(Card.TWO, Suit.GOLD));

		hand.Discardcard(0); // Elimina la carta en el índice 0
		assertEquals(1, hand.SizeHand(), "El tamaño debe disminuir tras descartar una carta");
		assertEquals(Card.TWO, hand.hand.get(0).getCardValue(), "Debería quedar la carta TWO");
	}

	@Test
	void testDiscardcardCajaBlancaExcepcion() {
		// Intentar descartar con índice fuera de rango debe lanzar
		// IndexOutOfBoundsException
		assertThrows(IndexOutOfBoundsException.class, () -> {
			hand.Discardcard(0);
		}, "Descartar en una mano vacía debe lanzar excepción");
	}

	@Test
	void testCountCombinedCardsNormal() {
		// Tres cartas del mismo número
		hand.AddCards(new Rank(Card.FIVE, Suit.GOLD));
		hand.AddCards(new Rank(Card.FIVE, Suit.CUPS));
		hand.AddCards(new Rank(Card.FIVE, Suit.SWORDS));
		hand.AddCards(new Rank(Card.ACE, Suit.CLUBS)); // Carta no combinada

		int combined = hand.countCombinedCards();
		assertTrue(combined > 0, "Debería detectar cartas combinadas");
		// Según la lógica de Hand, countCombinedCards suma 1 por cada carta que forme
		// parte de un trío o más
		assertEquals(3, combined, "Debe contar 3 cartas combinadas del mismo número");
	}

	@Test
	void testCountStairsCajaBlancaConEscalera() {
		// Intento de escalera de 3 cartas (Documenta incidencia en la lógica actual)
		hand.AddCards(new Rank(Card.THREE, Suit.CUPS));
		hand.AddCards(new Rank(Card.FOUR, Suit.CUPS));
		hand.AddCards(new Rank(Card.FIVE, Suit.CUPS));

		// INCIDENCIA DETECTADA: El método countStairs() necesita al menos 4 cartas
		// (3 incrementos) para devolver un valor distinto de cero. Además, devuelve
		// el número de incrementos (N-1) en vez del número de cartas (N).
		int stairs = hand.countStairs();
		assertEquals(0, stairs, "Se espera 0 debido a la incidencia detectada: requiere 4 cartas para retornar valor");
	}

	@Test
	void testCountStairsCajaBlancaSinEscalera() {
		// No hay escalera
		hand.AddCards(new Rank(Card.THREE, Suit.CUPS));
		hand.AddCards(new Rank(Card.FOUR, Suit.GOLD)); // Distinto palo
		hand.AddCards(new Rank(Card.FIVE, Suit.CUPS));

		int stairs = hand.countStairs();
		assertEquals(0, stairs, "No debe detectar escaleras si no son del mismo palo");
	}

	@Test
	void testCountCombinedCardsCajaBlancaBucleCeroIteraciones() {
		// Mano vacía: los bucles en countCombinedCards() no se ejecutarán ninguna vez
		assertEquals(0, hand.countCombinedCards(), "Debe devolver 0 si no hay cartas (0 iteraciones)");
	}

	@Test
	void testCountStairsCajaBlancaBucleCeroIteraciones() {
		// Mano vacía: el bucle externo de countStairs() se ejecuta 0 veces
		assertEquals(0, hand.countStairs(), "Debe devolver 0 si no hay cartas (0 iteraciones)");
	}

	@Test
	void testChoosequalsCajaBlancaRamaMayor() {
		// Hacemos que sameNumberCards > stairsCards
		// 3 cartas del mismo número (trío) -> sameNumberCards = 3, stairsCards = 0
		hand.AddCards(new Rank(Card.FIVE, Suit.GOLD));
		hand.AddCards(new Rank(Card.FIVE, Suit.CUPS));
		hand.AddCards(new Rank(Card.FIVE, Suit.SWORDS));

		int result = hand.choosequals();
		assertEquals(3, result, "Debe entrar por la rama donde sameNumberCards > stairsCards");
	}

	@Test
	void testChoosequalsCajaBlancaRamaMenorOIgual() {
		// Prueba de rama else (Documenta incidencia: devuelve 0 al no detectar la
		// escalera de 3)
		// Escalera de 3 cartas -> sameNumberCards = 0, stairsCards = 0 (por el bug)
		hand.AddCards(new Rank(Card.THREE, Suit.CUPS));
		hand.AddCards(new Rank(Card.FOUR, Suit.CUPS));
		hand.AddCards(new Rank(Card.FIVE, Suit.CUPS));

		// INCIDENCIA DETECTADA: Al retornar 0 para una escalera de 3, el resultado
		// final de choosequals() es 0 en este caso.
		int result = hand.choosequals();
		assertEquals(0, result, "Retorna 0 por la rama else debido a que countStairs no detecta la combinación de 3");
	}

	@Test
	void testIsChinchonNormal() {
		// Escalera de 7 cartas
		hand.AddCards(new Rank(Card.ACE, Suit.GOLD));
		hand.AddCards(new Rank(Card.TWO, Suit.GOLD));
		hand.AddCards(new Rank(Card.THREE, Suit.GOLD));
		hand.AddCards(new Rank(Card.FOUR, Suit.GOLD));
		hand.AddCards(new Rank(Card.FIVE, Suit.GOLD));
		hand.AddCards(new Rank(Card.SIX, Suit.GOLD));
		hand.AddCards(new Rank(Card.SEVEN, Suit.GOLD));

		assertTrue(hand.IsChinchon(), "Debe ser chinchón para una escalera de 7 cartas");
	}

	@Test
	void testIsNotChinchon() {
		// Solo 3 cartas en escalera
		hand.AddCards(new Rank(Card.ACE, Suit.GOLD));
		hand.AddCards(new Rank(Card.TWO, Suit.GOLD));
		hand.AddCards(new Rank(Card.THREE, Suit.GOLD));

		assertFalse(hand.IsChinchon(), "No debe ser chinchón con solo 3 cartas en escalera");
	}

	@ParameterizedTest
	@CsvSource({ "1, 2, 3, 6", // Sin combinación (palos distintos), suma de las cartas = 1 + 2 + 3 = 6
			"5, 7, 10, 22" // Sin combinación, suma de las cartas = 5 + 7 + 10 = 22 (Jack = 10)
	})
	void testGetLoosePointsSinCombinacion(int c1, int c2, int c3, int expectedLoosePoints) {
		// Se crean 3 cartas sin palo repetido para asegurar que no hay escalera en el
		// segundo caso
		// En el primer caso (1,2,3) probamos que si hay escalera, pero con palos
		// distintos no suma escalera
		hand.AddCards(new Rank(getCardFromValue(c1), Suit.GOLD));
		hand.AddCards(new Rank(getCardFromValue(c2), Suit.CUPS));
		hand.AddCards(new Rank(getCardFromValue(c3), Suit.SWORDS));

		int points = hand.getLoosePoints();
		assertEquals(expectedLoosePoints, points, "Los puntos sueltos no coinciden con lo esperado");
	}

	// Helper method
	private Card getCardFromValue(int val) {
		for (Card c : Card.values()) {
			if (c.getValue() == val)
				return c;
		}
		return Card.ACE;
	}
}
