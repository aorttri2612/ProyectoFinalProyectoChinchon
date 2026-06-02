#  Proyecto Chinchón - Módulo Entornos de Desarrollo

Este repositorio contiene el desarrollo del juego clásico de cartas **Chinchón**, implementado en Java. El proyecto ha sido estructurado siguiendo metodologías de diseño limpio, aplicación de patrones arquitectónicos, documentación estandarizada y validación sistemática mediante pruebas unitarias.

---

##  1. Explicación del Juego

### Objetivo del Juego
El objetivo principal del Chinchón es combinar las 7 cartas de la mano en grupos de valores idénticos (tríos o cuartetos) o en secuencias consecutivas del mismo palo (escaleras). El fin es reducir al mínimo la puntuación de las cartas sueltas (no combinadas). La partida finaliza cuando un jugador consigue cerrar la mano con éxito o realiza un "Chinchón" (escalera perfecta de 7 cartas consecutivas).

### Funcionamiento, Reglas y Jugabilidad
1. **Fase de Reparto:** Cada jugador recibe inicialmente 7 cartas del mazo principal (`Deck`). Se extrae una carta adicional y se coloca boca arriba en la pila de descarte (`Discard`).
2. **Flujo Dinámico del Turno:** En su respectivo turno, cada jugador realiza obligatoriamente las siguientes acciones:
   * **Robar:** El jugador debe elegir estratégicamente entre tomar la carta oculta del mazo principal o la última carta visible y pública de la pila de descarte.
   * **Acción y Evaluación:** La carta se integra en la mano (`Hand`). El sistema evalúa internamente y de forma automática las combinaciones actuales del jugador.
   * **Descarte:** Para finalizar el turno, el jugador debe seleccionar una carta de su mano y arrojarla a la pila de descarte, garantizando terminar su turno exactamente con 7 cartas.
3. **Condición de Cierre:** Un jugador puede solicitar el cierre de la ronda si, tras robar la octava carta, es capaz de realizar combinaciones válidas de tal manera que las cartas no combinadas ("puntos sueltos") sumen una penalización máxima de entre 0 y 5 puntos, o si dispone de una combinación perfecta (Chinchón).

### 📸 Capturas de Pantalla de la Interfaz del Juego
*(Espacio reservado para las capturas que muestran la ejecución del menú en consola, el reparto y las manos de los jugadores)*

*(Añade aquí tus imágenes locales guardadas en el repositorio)*
![Inicio del Juego y Configuración](./docs/captura_inicio.png)
![Flujo del Turno en Consola](./docs/captura_turno.png)

---

##  2. Análisis del Proyecto

###  Estructura del Proyecto
El proyecto se ha organizado de forma modular, separando estrictamente el código de producción de los recursos de desarrollo:

* `src/app/`: Aloja las clases de control de flujo, el arranque de la aplicación y la gestión directa de las lecturas por teclado (`Main`, `GestorGame`, `ConsoleInput`, `BuilderPlayer`).
* `src/dominio/`: Contiene las clases del núcleo del negocio, modelando los componentes físicos de la baraja española (`Card`, `Suit`, `Rank`, `Deck`, `Hand`, `Discard`) y los actores participantes (`Player`, `HumanPlayer`, `AiPlayer`).
* `test/` o `tests/`: **Ubicado estratégicamente fuera de la carpeta `src`**. Contiene las clases de pruebas de JUnit (`HandTest`, `DeckTest`, `RankTest`) garantizando que el código de testeo no se mezcle con los binarios de distribución finales.
* `docs/`: Carpeta destinada a almacenar el material documental del proyecto (diagramas UML, capturas de pantalla de evidencias y la documentación de la API).

---

### Diagrama de Clases UML Actualizado
A continuación se ilustra la arquitectura de clases, herencias y dependencias que dan soporte a la aplicación:

![Diagrama de Clases Chinchón](DiagramaChinchon.drawio.png)

---

###  Descripción de Clases y Responsabilidades

* **`Main`**: Punto de entrada del programa. Inicializa la interfaz y delega el control al orquestador.
* **`GestorGame`**: Clase controladora principal. Coordina los turnos, gestiona el flujo, el reparto de cartas y valida de manera global el final del juego.
* **`ConsoleInput`**: Utilidad encargada de centralizar, capturar y validar sintácticamente todas las entradas del usuario por teclado, evitando caídas inesperadas de la aplicación.
* **`BuilderPlayer`**: Constructor especializado en parametrizar y ensamblar instancias derivadas de la clase jugador de forma limpia.
* **`Player` (Clase Abstracta)**: Entidad base que agrupa los atributos comunes (nombre, mano, puntuación) y prescribe los métodos abstractos que gobernarán el comportamiento polimórfico de los turnos.
* **`HumanPlayer`**: Especialización de `Player` que implementa la toma de decisiones basada en menús interactivos dirigidos al usuario humano.
* **`AiPlayer`**: Especialización de `Player` que automatiza de forma algorítmica las decisiones de robo, descarte e intento de cierre del juego.
* **`Hand`**: Clase crítica que encapsula el comportamiento del conjunto de cartas de un jugador. Ejecuta los algoritmos de detección de combinaciones (`countStairs`, `choosequals`) y el cálculo de penalizaciones.
* **`Deck`**: Entidad encargada de instanciar de manera estática el mazo de cartas de la baraja española y aplicar el algoritmo de barajado.
* **`Discard`**: Gestiona la pila visible de descartes, controlando las inserciones y extracciones de cartas.
* **`Rank`**: Representación concreta de un naipe individual, acoplando un valor ordinal (`Card`) con su respectivo palo (`Suit`).
* **`Card` & `Suit` (Enums)**: Catálogos de constantes estáticas que configuran los valores numéricos y los iconos visuales (emojis) reglamentarios.

---

##  3. Patrones de Diseño Implementados

Diego solicita la justificación explícita de los patrones utilizados, cómo funcionan, por qué se han implementado y el código exacto donde se aplican:

### 1. Patrón Singleton (Instancia Única)
* **Por qué se utiliza:** Para asegurar que solo exista un único flujo activo de entrada de datos (`Scanner(System.in)`) en toda la aplicación, evitando fugas de memoria o conflictos por la apertura concurrente de múltiples flujos de lectura.
* **Cómo funciona:** El constructor es estrictamente privado y el acceso se restringe a un método estático que crea la instancia únicamente en su primera llamada.
* **Código de Aplicación:**
```java
// Ubicado en src/app/ConsoleInput.java
public class ConsoleInput {
    private static ConsoleInput instance;
    private Scanner keyboard;

    private ConsoleInput(Scanner keyboard) {
        this.keyboard = keyboard;
    }

    public static ConsoleInput getInstance() {
        if (instance == null) {
            instance = new ConsoleInput(new Scanner(System.in));
        }
        return instance;
    }
}
