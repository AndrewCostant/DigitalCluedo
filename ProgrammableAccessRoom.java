public class ProgrammableAccessRoom extends Room {

	public ProgrammableAccessRoom(String name) {
		super(name);
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

}