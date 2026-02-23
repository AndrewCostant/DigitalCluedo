package domain;

import java.util.ArrayList;
import domain.dto.*;

public interface GameState {
    // setup methods
    void setPlayers(ArrayList<String> name);
    void setUpGame();

    //game methods
    RollResult rollDice();
    ActionResult moveTo(Cell destination);
    DoActionResult makeAssumption(Triplet guess);
    void endTurn();
}
