package dominio;

/**
 * Representa el rango de una carta (la unión de su valor y su palo).
 */
public class Rank {
	private final Card cardValue;
	private final Suit suit;

	public Rank(Card cardValue, Suit suit) {
		this.cardValue = cardValue;
		this.suit = suit;
	}

	public Card getCardValue() {
		return cardValue;
	}

	public Suit getSuit() {
		return suit;
	}

	public int getCardNumber() {
		return cardValue.getValue();

	}

	@Override
	public String toString() {
		return String.format("%d %s", cardValue.getValue(), suit.getEmoji());
	}
}