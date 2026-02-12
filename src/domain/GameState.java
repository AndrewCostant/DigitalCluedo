package domain;

import java.util.ArrayList;
import domain.dto.*;


public interface GameState {
    //metodi di setup
    void setPlayers(ArrayList<String> name);
    void setUpGame();

    //metodi gioco
    RollResult rollDice();
    ActionResult moveTo(Cell destination);
    DoActionResult makeAssumption(Triplet guess);

    //gestion fine turno
    void endTurn();
}
