package warGame;

public enum CardMark{
	DIAMOND("ダイヤ"), SPADE("スペード");

	private final String name;

	private CardMark(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}