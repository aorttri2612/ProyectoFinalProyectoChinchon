package app;

/**
 * Es la clase principal que actúa como punto de entrada de la aplicación
 * Chinchón. Gestiona la inicialización de la interfaz de consola y el del
 * juego.
 */

public class Main {
	/**
	 * Método de entrada al programa. Configura el número de jugadores y arranca el
	 * GestorGame.
	 * 
	 * @param args Argumentos de la línea de comandos (no utilizados).
	 */

	public static void main(String[] args) {
		// Usamos el Singleton
		ConsoleInput input = ConsoleInput.getInstance();
		GestorGame gestor = new GestorGame();

		System.out.println("=== CHINCHÓN JAVA ===");
		System.out.print("¿Cuántos humanos jugarán? ");
		int humanos = input.readInt();

		System.out.print("¿Cuántas IAs jugarán? ");
		int ais = input.readInt();

		gestor.startGame(humanos, ais);
	}
}