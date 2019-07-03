package warGame;

import java.io.Serializable;
import java.util.ArrayList;

public class Player implements Serializable{
	private static final long serialVersionUID = 138410401500252755L;

	//自分の手札
	private ArrayList<Card> yourHand = new ArrayList<>();
	//獲得したカード
	private ArrayList<Card> acquisCard = new ArrayList<>();

	public void setYourHand(Card c) {
		yourHand.add(c);
	}

	public int AcuisCardSize() {
		return this.acquisCard.size();
	}

	public int YourHandSize() {
		return this.yourHand.size();
	}

	public Card drawCard() {
		return yourHand.remove(yourHand.size() - 1);
	}

	public void addAcuisCard(Card playerCard, Card cpuCard, ArrayList<Card> storeCard) {
		acquisCard.add(playerCard);
		acquisCard.add(cpuCard);
		for (Card c : storeCard) {
			acquisCard.add(c);
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((acquisCard == null) ? 0 : acquisCard.hashCode());
		result = prime * result + ((yourHand == null) ? 0 : yourHand.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Player other = (Player) obj;
		if (acquisCard == null) {
			if (other.acquisCard != null)
				return false;
		} else if (!acquisCard.equals(other.acquisCard))
			return false;
		if (yourHand == null) {
			if (other.yourHand != null)
				return false;
		} else if (!yourHand.equals(other.yourHand))
			return false;
		return true;
	}

}
