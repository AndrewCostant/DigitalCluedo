package domain;

import java.util.Objects;

public class NormalRoom extends RoomCell {

	public NormalRoom(int x, int y, String name) {
		super(x, y, name);
	}

	@Override
	public String action() {
		// TODO - implement NormalRoom.action
		return "Normal Room Action";
	}

	@Override
	public String doAction(SuspectC suspect, WeaponC weapon, Player player) {
		// TODO - implement NormalRoom.doAction
		return "Normal Room Do Action";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		NormalRoom other = (NormalRoom) obj;
		return x == other.getX() && y == other.getY();
	}

	@Override
	public int hashCode() {
		return Objects.hash(x, y);
	}
	@Override
	public String toString() {
		return "NormalRoom [" + name + ", x= " + x + ", y= " + y + "]";
	}
}