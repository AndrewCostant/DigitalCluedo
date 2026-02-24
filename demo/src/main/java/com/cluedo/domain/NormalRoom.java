package com.cluedo.domain;

import java.util.Objects;
import com.cluedo.domain.dto.*;

public class NormalRoom extends RoomCell {

	public NormalRoom(int x, int y, String name) {
		super(x, y, name, "NORMAL_ROOM");
	}

	@Override
	public ActionResult action() {
		ActionResult result = super.action();
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		NormalRoom other = (NormalRoom) obj;
		return x == other.getX() && y == other.getY();
	}

	@Override
	public int hashCode() {
		return Objects.hash(x, y);
	}
	@Override
	public String toString() {
		return name + " [x= " + x + ", y= " + y + "]";
	}
}