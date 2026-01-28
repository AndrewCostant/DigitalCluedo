package domain;

public class SuspectC extends Card {
    public SuspectC() {
        super("Unnamed Suspect");
    }
    public SuspectC(String name) {
        super(name);
    }

    @Override
    public String toString(){
        return "Suspect[" + this.getName() + "]";
    }
}