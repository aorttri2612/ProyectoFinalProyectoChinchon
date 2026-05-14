package dominio;

/**
 * valores posibles en una baraja española.
 * <p>
 * cada una calcula su valor
 * </p>
 * *
 * <ul>
 * <li>Cartas : ACE (1) a SEVEN (7).</li>
 * <li>Palos: JACK (10), KNIGHT (11), KING (12).</li>
 * </ul>
 */
public enum Card {

	ACE(1), TWO(2), THREE(3), FOUR(4), FIVE(5), SIX(6), SEVEN(7), JACK(10), KNIGHT(11), KING(12);

	private final int value;

	Card(int value) {
		this.value = value;
	}

	/**
	 * Obtiene el valor de la carta.
	 * <p>
	 * Este valor se utiliza para sumar los puntos de las cartas sueltas y para ver
	 * si tienes escalera escaleras.
	 * </p>
	 * * @return El valor entero de la carta.
	 */
	public int getValue() {
		return value;
	}
}
