package domain;

import domain.dto.ChanceDoAction;
import domain.dto.*;

public class ClearKnownCard implements EffectStrategy {
    
    @Override
    public DoActionResult effect(Player player) {
        player.clearKnownCards();
        return new ChanceDoAction(player.getPosition(), false, null, this);
    }

    @Override
    public String getDescription() {
        return "You have cleared all your known cards!";
    }
}