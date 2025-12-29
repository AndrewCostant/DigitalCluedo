public class NormalRoom extends Room {


	public NormalRoom(String name) {
		super(name);
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
}