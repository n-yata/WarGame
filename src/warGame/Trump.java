package warGame;

import java.util.ArrayList;

public class Trump {
	//26枚のデッキ
	ArrayList<Card> trumpList = new ArrayList<>();
	Card card;
	CardMark cardMark;
	CardNumber cardNumber;

	public void createDeck() {
		trumpList.clear();
		for (CardMark i : CardMark.values()) {
			for (CardNumber j : CardNumber.values()) {
				card = new Card(i, j);
				trumpList.add(card);
			}
		}
	}

	public Card distributeByDeck() {
		int i = trumpList.size() - 1;
		return trumpList.remove(i);
	}
}