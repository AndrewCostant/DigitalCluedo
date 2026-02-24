package com.cluedo.domain;

import com.cluedo.domain.dto.*;
import java.util.*;

public abstract class AbstractGameState implements GameState{

    @Override
    public void setPlayers(ArrayList<String> name) {
        throw new IllegalStateException("You can't add a player now"); 
    }

    @Override
    public void endTurn() {
        throw new IllegalStateException("You can't end turn now");
        
    }

    @Override
    public DoActionResult makeAssumption(Triplet guess) {
        throw new IllegalStateException("You can't make an assumption now");
    }

    @Override
    public ActionResult moveTo(Cell destination) {
        throw new IllegalStateException("You can't move now");
    }

    @Override
    public RollResult rollDice() {
        throw new IllegalStateException("You can't roll dices now");
    }

    @Override
    public void setUpGame() {
        throw new IllegalStateException("You can't set up the game now");
        
    }
    
}
