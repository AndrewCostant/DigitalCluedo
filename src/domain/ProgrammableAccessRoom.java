package domain;
import java.util.Objects;

public class ProgrammableAccessRoom extends RoomCell {

	public ProgrammableAccessRoom(int x, int y, String name) {
		super(x, y, name);
	}

	private String conditions;

	@Override
	public String action() {
		// TODO - implement ProgrammableAccessRoom.action
		return "ProgrammableAccess_Room";
	}

	@Override
	public String doAction(SuspectC suspect, WeaponC weapon, Player player) {
		// TODO - implement ProgrammableAccessRoom.doAction
		return "ProgrammableAccess_Room Do Action";
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
		ProgrammableAccessRoom other = (ProgrammableAccessRoom) obj;
		return this.x == other.getX() && this.y == other.getY();
	}

	@Override
	public int hashCode() {
		return Objects.hash(x, y);
	}
	@Override
	public String toString() {
		return "ProgrammableAccessRoom [" + name + ", x= " + x + ", y= " + y + "]";
	}
}