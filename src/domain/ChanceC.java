package domain;

import domain.dto.*;

public class ChanceC extends Card {

    private String name;
    private EffectStrategy effectStrategy;

    public ChanceC(String name, EffectStrategy effectStrategy) {
        super(name);
        this.effectStrategy = effectStrategy;
    }

    public DoActionResult effect(Player player) {
        return effectStrategy.effect(player);
    }
    
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Chance[" + this.getName() + "]";
    }
}