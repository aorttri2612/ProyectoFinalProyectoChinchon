package dominio;

import java.util.List;

/**
 * Es la claseIA
 * <p>
 * Extiende de {@link Player} yntodo esta automatizado
 * <ul>
 * <li><b>Robo:</b> Decide entre el mazo o el descarte según el valor de la
 * carta visible.</li>
 * <li><b>Cierre:</b> Intenta cerrar la partida automáticamente si cumple las
 * condiciones.</li>
 * <li><b>Descarte:</b> Evalúa su mano y se deshace de la carta de mas
 * puntos.</li>
 * </ul>
 * </p>
 */
public class AiPlayer extends Player {

	public AiPlayer(String name) {
		super(name);
	}

	/**
	 * ejecuta el turno de la ia solo
	 * <p>
	 * 1. <b>Fase de Robo:</b> Si la carta del descarte es menor o igual a 4, la
	 * roba; si no, roba del mazo. 2. <b>Intento de Cierre:</b> Verifica si puede
	 * ganar inmediatamente. 3. <b>Fase de Descarte:</b> coje la carta mayor y la
	 * descarta
	 * </p>
	 * * @param discard Instancia de {@link Discard} que es pila descarte
	 * 
	 * @param deck        Objeto {@link Deck} es para el robo de cartas
	 * @param currentDeck lista de las cartas del mazo
	 * @param count       turnos
	 * @return {@code true} si la IA ha cerrado y ganado la partida; {@code false}
	 *         en caso contrario.
	 */
	@Override
	public boolean playTurn(Discard discard, Deck deck, List<Rank> currentDeck, int count) {
		System.out.printf("\n--- TURNO IA: %s ---\n", name);

		// 1. Robo automático
		if (discard.getLastCard().getCardValue().getValue() <= 4) {
			steal(discard);
			System.out.printf("%s robó del descarte.\n", name);
		} else {
			stealDeck(deck, currentDeck);
			System.out.printf("%s robó del mazo.\n", name);
		}
		if (tryClose(discard) == true) {
			System.out.print("Ha ganado la ia con estas cartas: ");
			return true;
		}

		// 2. Descarte automático (la más alta)
		int highestIdx = 0;
		for (int i = 1; i < hand.hand.size(); i++) {
			if (hand.hand.get(i).getCardValue().getValue() > hand.hand.get(highestIdx).getCardValue().getValue()) {
				highestIdx = i;
			}
		}

		Rank toDiscard = hand.hand.get(highestIdx);
		hand.Discardcard(highestIdx);
		discard.getDiscard().add(toDiscard);
		System.out.printf("%s descartó: %s\n", name, toDiscard);

		return false;

	}

	/**
	 * roba una carta de la pila de descarte y la añade a la mano. * @param discard
	 * pila descarte que se ve solo la ultima
	 */
	public void steal(Discard discard) {
		hand.AddCards(discard.getRemoveCard());
	}

	/**
	 * es la que roba y añade * @param deck Objeto Deck para la lógica de robar
	 * 
	 * @param currentDeck Lista de cartas del mazo actual
	 */
	public void stealDeck(Deck deck, List<Rank> currentDeck) {
		hand.AddCards(deck.takeCardFromDeck(currentDeck));
	}

}