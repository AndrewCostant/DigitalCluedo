package domain;

public class ChanceC extends Card {
    public ChanceC() {
        super("Unnamed Chance");
    }
    public ChanceC(String name) {
        super(name);
    }

    @Override
    public String toString() {
        return "Chance[" + this.getName() + "]";
    }
}