package app;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * 
 * Implementa el patrón de diseño Singleton para garantizar que solo exista una
 * instancia del objeto Scanner y evitar conflictos de lectura. da metodos que
 * se usan mucho para no tener que repetirlos pr todo el codigo
 * 
 * 
 */
public class ConsoleInput {
	/** Instancia única de la clase (Singleton). */
	private static ConsoleInput instance;
	private Scanner keyboard;

	/**
	 * Constructor privado para evitar la instanciación externa.
	 * 
	 * @param keyboard El objeto Scanner a utilizar.
	 */
	private ConsoleInput(Scanner keyboard) {
		this.keyboard = keyboard;
	}

	/**
	 * coje la instancia única de ConsoleInput. Si no , la crea.
	 * 
	 * @return La instancia única (Singleton) de la clase.
	 */
	public static ConsoleInput getInstance() {
		if (instance == null) {
			// Se crea la instancia la primera vez que se solicita
			instance = new ConsoleInput(new Scanner(System.in));
		}
		return instance;
	}

	/**
	 * Limpia el teclado
	 */
	private void cleanInput() {
		keyboard.nextLine();
	}

	/* === VALORES INT === */
	/**
	 * retorna un int que ha puesto el usuario
	 * 
	 * @return El número entero validado.
	 */
	public int readInt() {
		int value = 0;
		boolean error;

		do {
			try {
				value = keyboard.nextInt();
				error = false;
			} catch (InputMismatchException e) {
				// En el readIntInRange queda raro, preguntar
				System.err.printf("¡Error! Eso no es un número entero\n");
				error = true;
			} finally {
				cleanInput();
			}
		} while (error);

		return value;
	}

	/**
	 * devuelve un metodo inferior al del parametro
	 * 
	 * @param upperBound El límite superior .
	 * @return Un entero válido menor que upperBound.
	 */
	public int readIntLessThan(int upperBound) {
		int value;
		do {
			value = readInt();
			if (value >= upperBound) {
				System.err.printf("¡Error! El número debe ser menor que %d\n", upperBound);
			}
		} while (value >= upperBound);
		return value;
	}

	/* === CHAR === */
	// Retorna un carácter introducido por el usuario. Si éste introduce más de un
	// carácter, se le vuelve a solicitar
	public char readChar() {
		while (true) {
			String input = keyboard.nextLine();
			if (input.length() == 1) {
				return input.charAt(0);
			} else {
				System.err.println("¡Error! Debes introducir solo un carácter.");
			}
		}
	}

	/* === STRING === */
	/**
	 * Retorna una cadena de caracteres introducida por el usuario
	 * 
	 * @return La cadena de texto introducida por el usuario.
	 */
	public String readString() {
		return keyboard.nextLine();
	}

	/** Retorna una cadena que no puede ser vacia */
	public String readStringNonEmpty() {
		String input;
		do {
			input = readString().trim();
			if (input.isEmpty()) {
				System.err.printf("¡Error! No puedes dejarlo vacío.");
			}
		} while (input.isEmpty());
		return input;
	}

	/**
	 * Retorna un booleano a partir de un carácter introducido por el usuario, de
	 * manera que si coincide con affirmativeValue (en mayúsculas o minúsculas)
	 * retornará true y si coincide con negativeValue (en mayúsculas o minúsculas),
	 * retornará false
	 * 
	 * @return true si coincide con el valor afirmativo, false si coincide con el
	 *         negativo.
	 */

	public boolean readBooleanUsingChar(char affirmativeValue, char negativeValue) {
		while (true) {
			char c = readChar();
			if (Character.toLowerCase(c) == Character.toLowerCase(affirmativeValue)) {
				return true;
			} else if (Character.toLowerCase(c) == Character.toLowerCase(negativeValue)) {
				return false;
			} else {
				System.err.printf("¡Error! Introduce un carácter válido.\n\"%c\" para sí o \"%c\" para no: ",
						affirmativeValue, negativeValue);
			}
		}
	}

	/**
	 * Introduzca un caracter de un conjunto permitido (usando arrays)
	 * 
	 * @param options Array de caracteres válidos.
	 * @return El carácter validado introducido por el usuario.
	 */
	public char readCharInOptions(char... options) {
		while (true) {
			char c = readChar();
			for (char option : options) {
				if (Character.toLowerCase(c) == Character.toLowerCase(option)) {
					return c;
				}
			}
			System.err.printf("¡Error! Introduce uno de los siguientes caracteres válidos: %s",
					Arrays.toString(options));
		}
	}

}
