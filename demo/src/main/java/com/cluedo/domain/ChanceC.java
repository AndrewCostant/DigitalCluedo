package com.cluedo.domain;

import com.cluedo.config.GameConfig;
import com.cluedo.domain.dto.*;

public class ChanceC extends Card {

    //private String name;
    private EffectStrategy effectStrategy;

    public ChanceC(String name, EffectStrategy effectStrategy) {
        super(name);
        this.effectStrategy = effectStrategy;
    }

    public DoActionResult effect(Player player) {
        return effectStrategy.effect(player);
    }

    @Override
    public String toString() {
        return GameConfig.YELLOW + "Chance[" + this.getName() + "]" + GameConfig.RESET;
    }
}