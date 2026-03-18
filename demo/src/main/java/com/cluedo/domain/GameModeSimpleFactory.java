package com.cluedo.domain;

import com.cluedo.config.GameMode;

public class GameModeSimpleFactory {
    public static AbstractGameModeFactory getFactory(GameMode mode) {
        switch (mode) {
            case CLASSIC:
                return new ClassicGameModeFactory();
            case SPEED:
                return new SpeedGameModeFactory();
            default:
                throw new IllegalArgumentException("Invalid game mode: " + mode);
        }
    }
}
