package domain;

import java.util.Objects;

public class NormalCell implements Cell {

    private final int x;
	private final int y;

    public NormalCell(int x, int y) {
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
        return "Normal_Cell";
    }

    @Override
    public String doAction(SuspectC suspect, WeaponC weapon, Player player) {
        CluedoGame.getInstance().endTurn();
        return "Normal Cell Do Action";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        NormalCell other = (NormalCell) obj;
        return x == other.x && y == other.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
	public String toString() {
		return "NormalCell [x= " + x + ", y= " + y + "]";
	}

}