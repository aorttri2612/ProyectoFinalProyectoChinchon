package dominio;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase base abstracta que define las propiedades y comportamientos comunes
 * para cualquier participante del juego
 * <p>
 * Da todo para gestionar el nombre, la mano de cartas y la puntuación acumulada
 * del jugador.
 * </p>
 */
public abstract class Player {
	protected String name;
	protected Hand hand;
	protected int score;

	public Player(String name) {
		this.name = name;
		this.hand = new Hand(new ArrayList<>());
		this.score = 0;
	}

	public String getName() {
		return name;
	}

	public Hand getHand() {
		return hand;
	}

	public int getScore() {
		return score;
	}

	/**
	 * Incrementa la puntuación del jugador.
	 * 
	 * @param points Puntos a sumar al marcador actual.
	 */
	public void addPoints(int points) {
		this.score += points;
	}

	/**
	 * Ejecuta la lógica del turno del jugador.
	 * 
	 * @return {@code true} si el jugador decide y puede cerrar el juego en este
	 *         turno.
	 */
	public abstract boolean playTurn(Discard discard, Deck deck, List<Rank> currentDeck, int count);

	/**
	 * Roba la carta superior de la pila de descarte y la añade a la mano.
	 * 
	 * @param discard Pila de descarte.
	 */
	public void steal(Discard discard) {
		hand.AddCards(discard.getRemoveCard());
	}

	/**
	 * Roba una carta del mazo principal y la añade a la mano.
	 * 
	 * @param deck        Instancia del mazo.
	 * @param currentDeck Lista de cartas disponibles en el mazo.
	 */
	public void stealDeck(Deck deck, List<Rank> currentDeck) {
		hand.AddCards(deck.takeCardFromDeck(currentDeck));
	}

	/**
	 * Realiza un descarte dado un índice
	 * 
	 * @param index   Posición de la carta en la mano.
	 * @param discard Pila de descarte de destino.
	 * @return La carta {@link Rank} que ha sido descartada.
	 */
	public Rank discardCard(int index, Discard discard) {
		Rank toDiscard = hand.hand.get(index);
		hand.Discardcard(index);
		discard.getDiscard().add(toDiscard);
		return toDiscard;

	}

	/**
	 * Intenta cerrar para ganar la ronda
	 * <p>
	 * Verifica si la mano cumple las condiciones de cierre. Si el jugador tiene 8
	 * cartas, busca automáticamente cuál descartar para poder cerrar legalmente.
	 * </p>
	 * 
	 * @param discard Pila de descarte necesaria para soltar la carta sobrante.
	 * @return {@code true} si el cierre fue exitoso.
	 */
	public boolean tryClose(Discard discard) {
		if (hand.close() == true) {
			if (hand.SizeHand() == 8) {
				int index = hand.findCardToDiscard();
				Rank toDiscard = discardCard(index, discard);
				System.out.println(name + "Descarto para cerrar " + toDiscard);

			}

			return true;
		}
		return false;

	}

}
