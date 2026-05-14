package dominio;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Gestiona las cartas de la partida
 * <p>
 * <ul>
 * <li>crea las instancias de cartas ({@link Rank}).</li>
 * <li>genera mazo completo basado en los Enums {@link Card} y
 * {@link Suit}.</li>
 * <li>baraja las cartas automaticamente.</li>
 * <li>coje las cartas para el robo o el reparto del pricipio</li>
 * </ul>
 * </p>
 */
public class Deck {

	static List<Rank> deck = new ArrayList<>();

	/**
	 * Crea una instancia de Rank uniendo los dos enums usando {@link Rank}
	 * combinando un valor y un palo. * @param value el valor de la crta viene
	 * de{@link Card}
	 * 
	 * @param suit El palo de la carta viene de {@link Suit}
	 * @return devuelve la carta junta
	 */

	public static Rank createRank(Card value, Suit suit) {
		return new Rank(value, suit);
	}

	/**
	 * Genera el mazo de cartas según el número de jugadores. Si hay más de 5
	 * jugadores, utiliza dos barajas automáticamente. Aunque quiero cambiarlo para
	 * que inicialize 2 barajas
	 * 
	 * @param numberOfPlayers determina los jugadores para escoger 1 o 2 mazos
	 * @return Una lista de objetos {@link Rank} barajada y lista para jugar.
	 */
	public static List<Rank> createDeck(int numberOfPlayers) {

		deck.clear();
		// Determinamos cuántas barajas necesitamos (1 o 2)
		int decksNeeded = (numberOfPlayers > 5) ? 2 : 1;

		for (int i = 0; i < decksNeeded; i++) {
			for (Suit suit : Suit.values()) {
				for (Card value : Card.values()) {
					deck.add(new Rank(value, suit));
				}
			}
		}

		// Mezclamos el mazo antes de devolverlo
		Collections.shuffle(deck);
		return deck;
	}

	/**
	 * coje la ultima carta
	 * <p>
	 * Este método reduce el tamaño del mazo
	 * </p>
	 * * @param deck La lista que representa el mazo actual de donde se roba.
	 * 
	 * @return El objeto {@link Rank} extraído del mazo.
	 */
	public Rank takeCardFromDeck(List<Rank> deck) {

		return deck.remove(deck.size() - 1);
	}
}