package dominio;

public enum Suit {

	GOLD("🟡"), CUPS("🍷"), SWORDS("⚔️"), CLUBS("🌳");

	private final String emoji;

	Suit(String emoji) {
		this.emoji = emoji;
	}

	public String getEmoji() {
		return emoji;
	}
}
