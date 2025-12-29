public class Room implements Cell {

	private String name;

	public Room(String name) {
		this.name = name;
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
}