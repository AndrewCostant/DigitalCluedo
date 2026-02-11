package domain;
import java.util.Objects;
import domain.dto.*;
public class RoomCell implements Cell {

	protected final int x;
	protected final int y;
	protected String name;
	protected final String type;

	public RoomCell(int x, int y, String name) {
		this.name = name;
		this.x = x;
		this.y = y;
		this.type = "ROOM_CELL";
	}

	public RoomCell(int x, int y, String name, String type) {
		this.name = name;
		this.x = x;
		this.y = y;
		this.type = type;
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
		return new NormalRoomAction(type, this);
	}

	@Override
	public DoActionResult doAction(SuspectC suspect, WeaponC weapon, Player player) {
		RoomC room = new RoomC(name);
		return player.makeAGuess(suspect, weapon, room);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		RoomCell other = (RoomCell) obj;
		return x == other.getX() && y == other.getY();
	}

	@Override
	public int hashCode() {
		return Objects.hash(x, y);
	}

	@Override
	public String toString() {
		return "RoomCell [" + name + ", x= " + x + ", y= " + y + "]";
	}
}