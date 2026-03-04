package com.cluedo.config;

public final class GameConfig {
    
    // Costruttore privato: nessuno può fare "new GameConfig()"
    private GameConfig() {}

    // Percorsi dei file (molto utili per la tua Factory)
    public static final String MAP_BASE_PATH = "maps/";
    public static final String CARDS_BASE_PATH = "cards/";
    public static final String UI_MAPS_BASE_PATH = "setup/";
    public static final String INTESTAZIONE = "intestazione.txt";
    public static final String GAME_MODE_FILE = "gameMode.txt";

    // Regole del gioco
    public static final int MAX_PLAYERS = 7;
    public static final int MIN_PLAYERS = 2;    
    
    // Altre costanti di gioco (es. numero di carte, posizioni iniziali, ecc.)
    public static final String START_POSITION = "3,3";
    public static final int CLASSIC_DICE_VALUE = 3;

    //colori ANSI per la console
    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String PURPLE = "\u001B[35m";
    public static final String CYAN = "\u001B[36m";
}
