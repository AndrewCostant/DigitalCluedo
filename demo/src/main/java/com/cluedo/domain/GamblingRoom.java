package com.cluedo.domain;
import java.util.Objects;
import com.cluedo.domain.dto.*;

public class GamblingRoom extends RoomCell {

	
	private int condition = 6;
	
	public GamblingRoom(int x, int y, String name) {
		super(x, y, name, "GAMBLING_ROOM_CELL");
	}


	@Override
	public ActionResult action() {
		ActionResult result = super.action();
		int number = CluedoGame.getInstance().gamble();
		if (number == condition){
			Card card = CluedoGame.getInstance().getWinningCard();
			CluedoGame.getInstance().getCurrentPlayer().addKnownCard(card, "Soluzione");
			return new GamblingRoomAction(result.getType(), result.getCell(), number, card);
		} else {
			Card card = CluedoGame.getInstance().getCurrentPlayer().peekRandomCard();
			CluedoGame.getInstance().addKnownCardPlayers(card);
			return new GamblingRoomAction(result.getType(), result.getCell(), number, card);
		}
	}


	// GETTERS AND SETTERS
	public int getCondition() {
		return condition;
	}

	public void setCondition(int condition) {
		this.condition = condition;
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