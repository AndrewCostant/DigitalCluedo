package domain;

import java.util.Objects;
public class SecretPassageRoom extends RoomCell {

    public SecretPassageRoom(int x, int y, String name) {
        super(x, y, name);
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

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        SecretPassageRoom other = (SecretPassageRoom) obj;
        return x == other.getX() && y == other.getY();
    }
    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
    @Override
	public String toString() {
		return "SecretPassageRoom [" + name + "," + x + "," + y + "]";
	}
}