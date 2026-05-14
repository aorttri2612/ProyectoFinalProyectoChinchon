package dominio;

import java.util.List;

import app.ConsoleInput;

/**
 * Extensión de {@link Player} que permite la interacción humana.
 * <p>
 * Gestiona la toma de decisiones a través de menús en consola, permitiendo:
 * <ul>
 * <li>Robar del mazo o del descarte.</li>
 * <li>Intentar cerrar la mano.</li>
 * <li>Solicitar sugerencias de descarte a la lógica interna (IA básica).</li>
 * </ul>
 * </p>
 */
public class HumanPlayer extends Player {

	public HumanPlayer(String name) {
		super(name);
	}

	/**
	 * Ejecuta el flujo del turno: Robo -> Acción -> Descarte. Utiliza
	 * {@link ConsoleInput} para capturar las decisiones del usuario.
	 */
	@Override
	public boolean playTurn(Discard discard, Deck deck, List<Rank> currentDeck, int count) {
		ConsoleInput input = ConsoleInput.getInstance();
		System.out.printf("\n--- TURN OF: %s ---\n", name.toUpperCase());
		hand.showHand();

		// 1. FASE OBLIGATORIA DE ROBO
		boolean hasDrawn = false;
		while (!hasDrawn) {
			System.out.printf("Pila de descarte (Visible): %s\n", discard.getLastCard());
			System.out.println("¿De dónde quieres robar?");
			System.out.println("1. Mazo (Boca abajo)");
			System.out.println("2. Descarte (Pila visible)");
			if (count > 1) {
				System.out.println("3. cerrar (posibilidad ganar juego)");

			}

			int choice = input.readInt();
			if (choice == 1) {
				stealDeck(deck, currentDeck);
				System.out.println("Has robado del mazo.");
				hasDrawn = true;
			} else if (choice == 2) {
				steal(discard);
				System.out.println("Has robado del descarte.");
				hasDrawn = true;
			} else if (choice == 3) {

				if (hand.close() == true) {
					System.out.println("juego terminado");
					return true;
				} else {
					System.out.println("No puedes cerrar te faltan puntos");
				}
			} else {
				System.out.println("Opción no válida. Debes robar para empezar tu turno.");
			}
		}

		// Mostrar mano actualizada con la 8ª carta
		System.out.println("\nTu mano tras robar:");
		hand.showHand();

		// 2. FASE DE DESCARTE Y SUGERENCIA
		boolean hasDiscarded = false;
		while (!hasDiscarded) {
			System.out.println("\nMENÚ DE DESCARTE");
			System.out.println("1. Elegir carta para descartar");
			System.out.println("2. Pedir sugerencia a la IA");

			int option = input.readInt();
			if (option == 1) {
				discardCard(input, discard);
				hasDiscarded = true; // Aquí termina el turno
			} else if (option == 2) {
				showSuggestion();
				// No ponemos hasDiscarded = true, así que el bucle repite el menú
				// pero ahora el usuario ya sabe qué hacer.
			} else {
				System.out.println("Opción no válida.");
			}
		}
		return false;
	}

	/**
	 * descarta la carta según el usuario
	 * <p>
	 * Este método solicita al usuario un índice (1-8), valida que el número esté
	 * dentro del rango de la mano actual y mueve la carta seleccionada de la mano
	 * del jugador a la pila de descarte.
	 * </p>
	 *
	 * @param input   Objeto {@link ConsoleInput} para leer la entrada del usuario.
	 * @param discard La instancia de {@link Discard} donde se depositará la carta.
	 */
	public void discardCard(ConsoleInput input, Discard discard) {
		System.out.println("Introduce el índice de la carta a descartar (1-8):");
		int index = input.readInt();
		index = index - 1;

		// Validar que el índice existe en la mano de 8 cartas
		if (index >= 0 && index < hand.hand.size()) {
			Rank toDiscard = hand.hand.get(index);
			hand.Discardcard(index);
			discard.getDiscard().add(toDiscard);
			System.out.printf("Has descartado: %s\n", toDiscard);
		} else {
			System.out.println("Índice incorrecto. Inténtalo de nuevo.");
			discardCard(input, discard); // Reintento
		}
	}

	/**
	 * Analiza la mano actual y sugiere descartar la carta con el valor nominal más
	 * alto.
	 */
	public void showSuggestion() {
		Rank suggestion = hand.hand.get(0);
		for (Rank r : hand.hand) {
			if (r.getCardValue().getValue() > suggestion.getCardValue().getValue()) {
				suggestion = r;
			}
		}
		System.out.printf("💡 IA Sugiere: Descarta el %s (es tu carta más alta).\n", suggestion);
	}

}