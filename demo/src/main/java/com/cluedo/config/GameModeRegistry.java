package com.cluedo.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.cluedo.domain.AbstractGameModeFactory;
import com.cluedo.domain.ClassicGameModeFactory;
import com.cluedo.domain.SpeedGameModeFactory;

public class GameModeRegistry {
    private static Map<String, AbstractGameModeFactory> modes = new HashMap<>();
    
    static {
        modes.put("Classic", new ClassicGameModeFactory());
        modes.put("Speed", new SpeedGameModeFactory());
    }

    public static AbstractGameModeFactory getMode(String key) {
        return modes.get(key);
    }

    public static ArrayList<String> getAvailableModes() {
        ArrayList<String> availableModes = new ArrayList<String>();
        for (String mode : modes.keySet()) {
            availableModes.add(mode);
        }
        availableModes.sort(String::compareTo);
        return availableModes;
    }
}