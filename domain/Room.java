import java.util.Objects;
public class Room implements Cell {

	protected final int x;
	protected final int y;
	private String name;

	public Room(int x, int y, String name) {
		this.name = name;
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
	public String action() {
		// TODO - implement Room.action
		return "Room_Cell";
	}

	@Override
	public String doAction(SuspectC suspect, WeaponC weapon, Player player) {
		RoomC room = new RoomC(name);
		return player.makeAGuess(suspect, weapon, room);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		Room other = (Room) obj;
		return x == other.getX() && y == other.getY();
	}

	@Override
	public int hashCode() {
		return Objects.hash(x, y);
	}
}