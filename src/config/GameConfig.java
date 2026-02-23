package config;

public final class GameConfig {
    
    // Costruttore privato: nessuno può fare "new GameConfig()"
    private GameConfig() {}

    // Percorsi dei file (molto utili per la tua Factory)
    public static final String MAP_BASE_PATH = "src/utility/maps/";
    public static final String CARDS_BASE_PATH = "utility/cards/";
    public static final String UI_MAPS_BASE_PATH = "utility/setup/";
    public static final String INTESTAZIONE = "utility/intestazione.txt";

    // Regole del gioco
    public static final int MAX_PLAYERS = 7;
    public static final int MIN_PLAYERS = 2;    
    
    // Altre costanti di gioco (es. numero di carte, posizioni iniziali, ecc.)
    public static final String START_POSITION = "HALL";
    public static final int CLASSIC_DICE_VALUE = 3;
}
