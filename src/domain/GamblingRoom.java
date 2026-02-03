package domain;
import java.util.Objects;

public class GamblingRoom extends RoomCell {

	public GamblingRoom(int x, int y, String name) {
		super(x, y, name);
	}

	private String conditions;

	@Override
	public String action() {
		int number = CluedoGame.getInstance().getNumber();
		if (number == 6){
			String cardName = CluedoGame.getInstance().getWinningCard();
			CluedoGame.getInstance().getCurrentPlayer().addKnownCard(cardName, "Soluzione");
			return "One of the winning cards is " + cardName + ".Gooooooool ahead";
		} else {
			String cardName = CluedoGame.getInstance().getCurrentPlayer().peekRandomCard();
			CluedoGame.getInstance().addKnownCardPlayers(cardName);
			return "You have shown " + cardName + " to the other players!";
		}
	}


	// GETTERS AND SETTERS
	public String getConditions() {
		return conditions;
	}

	public void setConditions(String conditions) {
		this.conditions = conditions;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		GamblingRoom other = (GamblingRoom) obj;
		return this.x == other.getX() && this.y == other.getY();
	}

	@Override
	public int hashCode() {
		return Objects.hash(x, y);
	}
	@Override
	public String toString() {
		return "GamblingRoom [" + name + ", x= " + x + ", y= " + y + "]";
	}
}