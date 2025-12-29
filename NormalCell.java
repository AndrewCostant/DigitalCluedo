public class NormalCell implements Cell {
    @Override
    public String action() {
        // TODO - implement NormalCell.action
        return "Normal_Cell";
    }

    @Override
    public String doAction(SuspectC suspect, WeaponC weapon, Player player) {
        CluedoGame.getInstance().endTurn();
        return "Normal Cell Do Action";
    }
}