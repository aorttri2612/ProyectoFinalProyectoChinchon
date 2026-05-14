package app;

import java.util.ArrayList;
import java.util.List;

import dominio.Deck;
import dominio.Discard;
import dominio.Player;
import dominio.Rank;

/**
 * Clase principal de control * Se encarga de:
 * <ul>
 * <li>Inicializar los jugadores (Humanos e IAs) mediante el patrón
 * Builder.</li>
 * <li>Gestionar la creación y el estado del mazo y la pila de descarte.</li>
 * <li>Repartir las cartas iniciales.</li>
 * <li>Controlar el bucle principal de turnos hasta que se cumpla una condición
 * de victoria.</li>
 * </ul>
 */
public class GestorGame {
	private List<Player> players;
	private Discard discard;
	private List<Rank> currentDeck;
	private int count = 0;

	/**
	 * Inicia una nueva partida de Chinchón. configura el entorno de juego: 1.
	 * Instancia los jugadores usando {@link BuilderPlayer}. 2. Crea y mezcla el
	 * mazo {@link Deck#createDeck(int)}. 3. Reparte 7 cartas a cada jugador. 4.
	 * Inicia la pila de descarte con la primera carta del mazo. 5. Ejecuta el bucle
	 * de turnos llamando a {@link Player#playTurn}. * @param humans Número de
	 * jugadores humanos
	 * 
	 * @param ais Número de jugadores ia
	 */
	public void startGame(int humans, int ais) {
		boolean gameFinished = false;
		players = new ArrayList<>();
		for (int i = 0; i < humans; i++)
			players.add(new BuilderPlayer().setName("H" + (i + 1)).setIsAi(false).build());
		for (int i = 0; i < ais; i++)
			players.add(new BuilderPlayer().setName("IA" + (i + 1)).setIsAi(true).build());

		currentDeck = Deck.createDeck(players.size());
		discard = new Discard(new ArrayList<>());

		// Repartir 7 iniciales
		for (Player p : players) {
			for (int j = 0; j < 7; j++) {
				p.getHand().AddCards(currentDeck.remove(currentDeck.size() - 1));
			}
		}

		// Primera carta al descarte
		discard.getDiscard().add(currentDeck.remove(currentDeck.size() - 1));

		// Bucle de juego
		while (gameFinished == false) {
			for (Player p : players) {
				// Pasamos el objeto Deck y Discard
				boolean close = p.playTurn(discard, new Deck(), currentDeck, count);

				count++;
				if (close == true) {
					System.out.println("El juego termino porque cerró" + p.getName());
					gameFinished = true;
				}

				if (currentDeck.size() == 0) {
					System.out.println("El juego se quedo sin cartas");
					gameFinished = true;
				}

			}
		}
	}
}