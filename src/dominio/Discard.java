package dominio;

import java.util.ArrayList;

/**
 * ES la pila de descarte gestiona las cartas jugadas, robadas y descartadas
 * para que no se repitan
 */
public class Discard {

	// guardar la ultima carta del monton de descarte

	private ArrayList<Rank> discard;

	public Discard(ArrayList<Rank> discard) {
		super();
		this.setDiscard(discard);
	}

	/**
	 * coje la carta de descarte que es visible para todos y la muestra sin borrarla
	 * * @return El objeto {@link Rank} puesto al final
	 * 
	 * @throws IndexOutOfBoundsException si la pila de descarte está vacía.
	 */
	public Rank getLastCard() {
		return getDiscard().get(getDiscard().size() - 1);
	}

	/**
	 * muestra donde esta la ultima carta
	 * 
	 */
	public void ShowCardDiscard() {

		System.out.println(getDiscard().size() - 1);

	}

	/**
	 * Extrae y elimina la carta superior de la pila de descarte.
	 * <p>
	 * Este método se utiliza cuando un jugador elige "robar del descarte" en lugar
	 * de robar del mazo principal.
	 * </p>
	 * * @return El objeto {@link Rank} que ha sido quitado de la pila
	 * 
	 * @throws IndexOutOfBoundsException si no hay cartas que quitar
	 */
	public Rank getRemoveCard() { // no se la añadimos a la mano pq eso se hace en el gestorGame
		// ahora solo la
		// quitamos de la pila

		return getDiscard().remove(getDiscard().size() - 1);

	}

	/**
	 * tiene la lista completa de cartas en la pila de descarte. * @return Un
	 * {@link ArrayList} con los objetos {@link Rank} descartados.
	 */
	public ArrayList<Rank> getDiscard() {
		return discard;
	}

	/**
	 * Establece o reemplaza la lista de cartas de la pila de descarte. * @param
	 * discard La nueva lista de descartes a asignar.
	 */
	public void setDiscard(ArrayList<Rank> discard) {
		this.discard = discard;
	}

}
