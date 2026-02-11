package domain;

import java.util.Objects;

import domain.dto.ActionResult;
import domain.dto.ChanceAction;
public class ChanceCell implements Cell {
	private final int x;
	private final int y;
	private final String type = "CHANCECELL";

	public ChanceCell(int x, int y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public int getX() {
		return x;
	}

	@Override
	public int getY() {
		return y;
	}
	
	@Override
	public ActionResult action() {
		ChanceC card = Board.getInstance().DrawChanceC();
		CluedoGame.getInstance().getCurrentPlayer().setChanceCard(card);
		return new ChanceAction(this.type, this, card);
	}

	@Override
	public String doAction(SuspectC suspect, WeaponC weapon, Player player) {
		String shownEffect = player.getChanceCard().effect(player);
		CluedoGame.getInstance().endTurn();
		return shownEffect;
	}

	// GETTERS AND SETTERS
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		ChanceCell other = (ChanceCell) obj;
		return x == other.x && y == other.y;
	}

	@Override
	public int hashCode() {
		return Objects.hash(x, y);
	}

	@Override
	public String toString() {
		return "ChanceCell [x= " + x + ", y= " + y + "]";
	}
}