package domain;

import config.GameConfig;

public class SpeedGameModeFactory implements AbstractGameModeFactory {

    @Override
    public String mapPath() {
        return GameConfig.MAP_BASE_PATH + "speedMap.json";
    }

    @Override
    public String roomCardPath() {
        return GameConfig.CARDS_BASE_PATH + "SpeedMode/speed_room_cards.txt";
    }

    @Override
    public String weaponCardPath() {
        return GameConfig.CARDS_BASE_PATH + "SpeedMode/speed_weapon_cards.txt";
    }

    @Override
    public String suspectCardPath() {
        return GameConfig.CARDS_BASE_PATH + "SpeedMode/speed_suspect_cards.txt";
    }

    @Override
    public String chanceCardPath() {
        return GameConfig.CARDS_BASE_PATH + "SpeedMode/speed_chance_cards.txt";
    }

    @Override
    public Dice getDice() {
        return new Dice(3);
    }

    @Override
    public String getGameModeName() {
        return "Speed";
    }

    @Override
    public String getUiMapPath() {
        return GameConfig.UI_MAPS_BASE_PATH + "speed_ui_map.txt";
    }

    @Override
    public RollDiceStrategy getRollDiceStrategy() {
        return new SpeedRollDiceStrategy();
    }
    
}
