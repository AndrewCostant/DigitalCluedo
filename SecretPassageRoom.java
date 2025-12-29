public class SecretPassageRoom extends Room {

    public SecretPassageRoom(String name) {
        super(name);
    }
    @Override
    public String action() {
        // TODO - implement SecretPassageRoom.action
        return "SecretPassage_Room";
    }

    @Override
    public String doAction(SuspectC suspect, WeaponC weapon, Player player) {
        // TODO - implement SecretPassageRoom.doAction
        return "Secret Passage Room Do Action";
    }
}