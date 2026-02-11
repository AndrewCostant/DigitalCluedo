package domain;

import domain.dto.*;

public class ChanceC extends Card {

    private EffectStrategy effectStrategy;

    public ChanceC(EffectStrategy effectStrategy) {
        super("Unnamed Chance");
        this.effectStrategy = effectStrategy;
    }

    public DoActionResult effect(Player player) {
        return effectStrategy.effect(player);
    }
    
    @Override
    public String toString() {
        return "Chance[" + this.getName() + "]";
    }
}