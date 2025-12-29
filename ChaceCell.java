public class ChaceCell implements Cell {

	private String description;
	@Override
	public String action() {
		// TODO - implement ChaceCell.action
		return "Chace_Cell";
	}

	@Override
	public String doAction(SuspectC suspect, WeaponC weapon, Player player) {
		// TODO - implement ChaceCell.doAction
		return "Chace Cell Do Action";
	}

	// GETTERS AND SETTERS
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}