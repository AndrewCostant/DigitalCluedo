package com.cluedo.domain;

public interface AbstractGameModeFactory {
    
    // path for map and cards
    String mapPath();
    String roomCardPath();
    String weaponCardPath();
    String suspectCardPath();
    String chanceCardPath();
    String getGameModeName();
    String getUiMapPath();

    // for the game logic
    RollDiceStrategy getRollDiceStrategy();
    Dice getDice();
}
