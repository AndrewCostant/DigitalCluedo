package com.cluedo.config;

import java.util.ArrayList;

public class GameModeRegistry {
    private static ArrayList<GameMode> modes = new ArrayList<>();
    
    static {
        modes.add(GameMode.CLASSIC);
        modes.add(GameMode.SPEED);
    }

    public static GameMode getMode(int key) {
        return modes.get(key);
    }

    public static ArrayList<String> getAvailableModes() {
        ArrayList<String> availableModes = new ArrayList<String>();
        for (GameMode mode : modes) {
            availableModes.add(mode.toString());
        }
        availableModes.sort(String::compareTo);
        return availableModes;
    }
}