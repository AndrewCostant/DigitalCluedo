package domain;

public interface AbstractGameModeFactory {
    // per la configurazione della Game mode
    String mapPath();
    String roomCardPath();
    String weaponCardPath();
    String suspectCardPath();
    String chanceCardPath();
    Dice getDice();
    String getGameModeName();
    String getUiMapPath();

    // for the game logic
    RollDiceStrategy getRollDiceStrategy();
}
