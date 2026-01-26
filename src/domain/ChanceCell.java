package domain;

import java.util.Objects;
public class ChanceCell implements Cell {
	private final int x;
	private final int y;
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

	private String description;
	@Override
	public String action() {
		// TODO - implement ChanceCell.action
		return "Chance_Cell";
	}

	@Override
	public String doAction(SuspectC suspect, WeaponC weapon, Player player) {
		// TODO - implement ChanceCell.doAction
		return "Chance Cell Do Action";
	}

	// GETTERS AND SETTERS
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

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