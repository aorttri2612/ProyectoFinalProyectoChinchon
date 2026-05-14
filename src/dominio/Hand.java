package dominio;

import java.util.ArrayList;

/**
 * enseña las cartas del jugador
 * <p>
 * <li>Almacena las cartas actuales del jugador en un {@link ArrayList}.</li>
 * <li>Valida las combinaciones de cartas iguales (tríos/cuartetos).</li>
 * <li>Detecta las escaleras del mismo palo.</li>
 * <li>Calcula los "puntos sueltos" (cartas no combinadas) para determinar si se
 * puede cerrar.</li>
 * <li>Identificar la jugada máxima: el <b>Chinchón</b>.</li>
 * </ul>
 * </p>
 */
public class Hand {

	public ArrayList<Rank> hand;

	public Hand(ArrayList<Rank> hand) {
		super();
		this.hand = hand;

	}

	/**
	 * Obtiene el número total de cartas en la mano.
	 * 
	 * @return Cantidad de cartas actual.
	 */
	public int SizeHand() {
		return hand.size();
	}

	/**
	 * Añade una carta a la mano si hay espacio disponible (máximo 8 cartas).
	 * * @param card La carta {@link Rank} a añadir.
	 */
	public void AddCards(Rank card) {

		if (hand.size() <= 8) {
			hand.add(card);

		} else {
			System.out.println("mano esta llena");
		}

	}

	/**
	 * Elimina una carta de la mano según su posición. * @param num Índice de la
	 * carta a eliminar (0 a tamaño de la mano - 1).
	 */
	public void Discardcard(int numeroCarta) {

		hand.remove(numeroCarta);

	}

	/**
	 * Muestra por consola todas las cartas de la mano actual con sus índices.
	 */
	public void showHand() {

		for (int i = 0; i < hand.size(); i++) {
			System.out.println(hand.get(i));

		}

	}

	/**
	 * Cuenta cuántas cartas forman grupos de 3 o más con el mismo valor numérico.
	 * * @return Cantidad total de cartas que forman parte de grupos combinados.
	 */
	public int countCombinedCards() { // corregido
		int combinedCards = 0;
		for (int i = 0; i < hand.size(); i++) {
			int counter = 0;
			Rank currentCard = hand.get(i);
			for (int j = 0; j < hand.size(); j++) {
				Rank otherCard = hand.get(j);
				if (currentCard.getCardNumber() == otherCard.getCardNumber()) {
					counter++;

				}
			}
			if (counter >= 3) {
				combinedCards = combinedCards + 1;
			}

		}
		return combinedCards;

	}

	/**
	 * Determina cuál es la mejor combinación disponible en la mano.
	 * 
	 * @return El mayor valor entre cartas combinadas por número o cartas en
	 *         escalera.
	 */
	public int choosequals() {
		int sameNumberCards = countCombinedCards();
		int stairsCards = countStairs();
		System.out.println("estos son los numeros por escalera" + stairsCards);
		System.out.println("estos son los numeros por cartas combinadas" + sameNumberCards);
		if (sameNumberCards > stairsCards) {
			return sameNumberCards;
		} else {
			return stairsCards;
		}

	}

	/**
	 * Detecta la secuencia más larga de cartas consecutivas del mismo palo.
	 * 
	 * @return El número de cartas que forman la mejor escalera (mínimo 3).
	 */
	public int countStairs() {
		int bestcounter = 0;
		int counter = 0;
		for (int i = 0; i < hand.size(); i++) {

			Rank currentCard = hand.get(i);
			int currentValue = currentCard.getCardNumber();
			for (int nextValue = currentValue + 1; nextValue < 12; nextValue++) {
				boolean found = false;
				for (int j = 0; j < hand.size(); j++) {
					Rank otherCard = hand.get(j);
					if (currentCard.getSuit() == otherCard.getSuit() && otherCard.getCardNumber() == nextValue) {
						found = true;

					}

				}

				if (found == true) {
					counter++;
					if (counter > bestcounter) {
						bestcounter = counter;
					}

				} else {
					counter = 0;
				}
			}

		}
		if (bestcounter >= 3) {
			return bestcounter;
		}
		return 0;
	}

	/**
	 * Comprueba si el jugador tiene "Chinchón". Es chinchon cuando tienes escalera
	 * de 7 con el mismo palo
	 * 
	 * @return true si es Chinchón, false en caso contrario.
	 */
	public boolean IsChinchon() {
		int count = 0;
		count = countStairs();
		if (count > 5 && count <= 7) {
			return true;
		}
		return false;

	}

	/**
	 * Calcula la penalización de puntos comparando combinaciones de números y
	 * escaleras.
	 * 
	 * @return La puntuación mínima de cartas no combinadas.
	 */
	public int getLoosePoints() {
		int pointSameNumbers;
		int pointStairs;
		pointSameNumbers = getLoosePointsBysameNumbers();
		pointStairs = getLoosePointsByStairs();
		if (pointSameNumbers < pointStairs) {
			return pointSameNumbers;
		} else {
			return pointStairs;
		}

	}

	/**
	 * Calcula los puntos sueltos asumiendo que el jugador prioriza grupos de
	 * números iguales.
	 * 
	 * @return Suma de los valores de las cartas no agrupadas por número.
	 */
	public int getLoosePointsBysameNumbers() {
		int bestNumber = -1;
		int bestCounter = 0;
		for (Card value : Card.values()) {
			int counter = 0;
			for (Rank card : hand) {
				if (card.getCardValue() == value) {
					counter++;
				}

			}
			if (counter >= 3 && counter > bestCounter) {
				bestCounter = counter;
				bestNumber = value.getValue();
			}
		}
		if (bestNumber == -1) {
			return getTotalPoints();
		}
		int loosePoints = 0;
		for (Rank card : hand) {
			if (!(card.getCardNumber() == bestNumber)) {
				loosePoints = loosePoints + card.getCardNumber();
			}
		}
		return loosePoints;
	}

	private int getTotalPoints() {
		int total = 0;
		for (Rank card : hand) {
			total = total + card.getCardNumber();
		}
		return total;
	}

	/**
	 * Calcula los puntos sueltos asumiendo que el jugador prioriza escaleras.
	 * 
	 * @return Suma de los valores de las cartas que no forman parte de la mejor
	 *         escalera.
	 */
	private int getLoosePointsByStairs() {
		Suit bestSuit = null;
		Card bestStart = null;
		Card bestEnd = null;
		int bestcounter = 0;

		for (Suit suit : Suit.values()) {
			int counter = 0;
			Card start = null;

			for (Card value : Card.values()) {
				boolean found = false;

				for (Rank card : hand) {
					if (card.getSuit() == suit && card.getCardValue() == value) {
						found = true;
					}
				}

				if (found == true) {
					if (counter == 0) {
						start = value;
					}

					counter++;

					if (counter > bestcounter) {
						bestcounter = counter;
						bestSuit = suit;
						bestStart = start;
						bestEnd = value;
					}
				} else {
					counter = 0;
					start = null;
				}
			}
		}

		if (bestcounter < 3) {
			return getTotalPoints();
		}

		int loosePoints = 0;

		for (Rank card : hand) {
			boolean isPartOfStair = false;

			if (card.getSuit() == bestSuit) {
				if (card.getCardValue().ordinal() >= bestStart.ordinal()
						&& card.getCardValue().ordinal() <= bestEnd.ordinal()) {
					isPartOfStair = true;
				}
			}

			if (isPartOfStair == false) {
				loosePoints = loosePoints + card.getCardNumber();
			}
		}

		return loosePoints;
	}

	/**
	 * Evalúa si la mano cumple las condiciones para finalizar la partida.
	 * <p>
	 * Las condiciones incluyen tener Chinchón (escalera de 7), tener 7 cartas
	 * combinadas o tener 6 combinadas con puntos sueltos mínimos.
	 * </p>
	 * 
	 * @return {@code true} si el jugador puede cerrar; {@code false} en caso
	 *         contrario.
	 */
	public boolean close() { // metodo creado no terminado ni hecho
		int combinedCards;
		int loosePoints;
		combinedCards = choosequals();
		System.out.println("estas son tus cartas combinadas" + combinedCards); // error en combinedCard
		if (IsChinchon()) {
			System.out.println("Has ganado por chinchon");
			showHand();
			return true;
		}
		if (combinedCards == 7) {

			System.out.println("ganaste por escalera");
			showHand();
			return true;
		}
		if (combinedCards == 6) {
			System.out.println(" Ha combinado 6 cartas");
			loosePoints = getLoosePoints();
			System.out.println("Estos son tus puntos perdidos" + loosePoints);
			if (loosePoints >= 1 && loosePoints <= 5) {
				System.out.println("ganaste por combinacion de cartas");
				showHand();
				return true;
			}
		}

		return false;

	}

	public int findCardToDiscard() {
		if (hand.size() == 7) {
			return -1;

		}
		for (int i = 0; i < hand.size(); i++) {
			ArrayList<Rank> temporalHand = new ArrayList<>(hand);
			temporalHand.remove(i);
			Hand temporal = new Hand(temporalHand);
			if (temporal.close() == true) {
				return i;

			}

		}
		return 0;
	}
}
