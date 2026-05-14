package app;

import dominio.AiPlayer;
import dominio.HumanPlayer;
import dominio.Player; //crea jugadores pero esta sujeto a cambios

/**
 * implementa el patrón de diseño Builder para la creación de los objetos de
 * tipo {@link Player}. esta permite configurar las propiedades de un jugador
 * antes de instanciar la subclase específica (Humano o IA).
 */

public class BuilderPlayer {
	private String name;
	/**
	 * * Indica si el jugador debe ser controlado por la Inteligencia Artificial.
	 * true para {@link AiPlayer}, false para {@link HumanPlayer}.
	 */
	private boolean isAi;

	/**
	 * dice el nombre del jugador q se va a crear * @param name El nombre del
	 * jugador.
	 * 
	 * @return retorna la variable que le hemos puesto
	 */
	public BuilderPlayer setName(String name) {
		this.name = name;
		return this;
	}

	/**
	 * si esia o humano. * @param isAi true para crear una IA, false para crear un
	 * humano.
	 * 
	 * @return retorna la variable que le hemos puesto
	 */

	public BuilderPlayer setIsAi(boolean isAi) {
		this.isAi = isAi;
		return this;
	}

	/**
	 * Construye e instancia el objeto {@link Player} final basándose si es ia o
	 * humano * @return Una nueva instancia de {@link AiPlayer} si isAi es true, o
	 * una instancia de {@link HumanPlayer} en caso contrario.
	 */

	public Player build() {
		if (isAi) {
			return new AiPlayer(name);
		} else {
			return new HumanPlayer(name);
		}
	}
}