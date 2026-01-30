package domain;

public class ClearKnownCard implements EffectStrategy {
    
    @Override
    public void effect(Player player) {
        player.clearKnownCards();
    }
}