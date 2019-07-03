package warGame;

import java.io.Serializable;

public class Card implements Serializable{
	private static final long serialVersionUID = -7090737737354569978L;
	
	private CardMark mark;
	private CardNumber number;

	public Card(CardMark mark, CardNumber number) {
		this.mark = mark;
		this.number = number;
	}

	public CardMark getMark() {
		return mark;
	}

	public CardNumber getNumber() {
		return number;
	}

	public String toString() {
		return "[" + mark.getName() + number.getName() + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((mark == null) ? 0 : mark.hashCode());
		result = prime * result + ((number == null) ? 0 : number.hashCode());
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
		Card other = (Card) obj;
		if (mark != other.mark)
			return false;
		if (number != other.number)
			return false;
		return true;
	}

}