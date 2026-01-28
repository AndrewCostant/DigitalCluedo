package domain;

public class WeaponC extends Card {
    public WeaponC() {
        super("Unnamed Weapon");
    }
    public WeaponC(String name) {
        super(name);
    }

    @Override
    public String toString(){
        return "Weapon[" + this.getName() + "]";
    }
}