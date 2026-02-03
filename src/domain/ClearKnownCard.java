package domain;

public class ClearKnownCard implements EffectStrategy {
    
    @Override
    public String effect(Player player) {
        player.clearKnownCards();
        return "Your knownCards deck has been deleted";
    }
}