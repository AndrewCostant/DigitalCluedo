package domain;

import java.util.Objects;
import domain.dto.*;
public class SecretPassageRoom extends RoomCell {

    private SecretPassageRoom linkedRoom;

    public SecretPassageRoom(int x, int y, String name) {
        super(x, y, name, "SECRET_PASSAGE_ROOM");
        this.linkedRoom = null;
    }

    public SecretPassageRoom getLinkedRoom() {
        return linkedRoom;
    }
    public void setLinkedRoom(SecretPassageRoom linkedRoom) {
        this.linkedRoom = linkedRoom;
    }

    @Override
    public ActionResult action() {
        ActionResult result = super.action();
        return new SecretPassageAction(result.getType(), result.getCell());
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
		return "SecretPassageRoom [" + name + ", x= " + x + ", y= " + y + "]";
	}
}