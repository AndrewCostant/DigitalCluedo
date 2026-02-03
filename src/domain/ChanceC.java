package domain;

public class ChanceC extends Card {

    private EffectStrategy effectStrategy;

    public ChanceC(EffectStrategy effectStrategy) {
        super("Unnamed Chance");
        this.effectStrategy = effectStrategy;
    }

    public String effect(Player player) {
        return effectStrategy.effect(player);
    }
    
    @Override
    public String toString() {
        return "Chance[" + this.getName() + "]";
    }
}