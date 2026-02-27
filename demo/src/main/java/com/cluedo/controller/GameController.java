package com.cluedo.controller;

import java.util.ArrayList;


import com.cluedo.config.GameModeRegistry;
import com.cluedo.domain.Board;
import com.cluedo.domain.Card;
import com.cluedo.domain.Cell;
import com.cluedo.domain.CluedoGame;
import com.cluedo.domain.SuspectC;
import com.cluedo.domain.WeaponC;
import com.cluedo.domain.dto.DoActionResult;


public class GameController {

    public static boolean checkIfSuspectCardExist(String name) {
        return CluedoGame.getInstance().checkIfASuspectCardExist(name);
    }

    public static boolean checkIfWeaponCardExist(String name) {
        return CluedoGame.getInstance().checkIfAWeaponCardExist(name);
    }

    public void setGameMode(String string) {
        CluedoGame.getInstance().setGameMode(GameModeRegistry.getMode(string));
    }

    public void setPlayers(ArrayList<String> players) {
        CluedoGame.getInstance().setPlayers(players);
        CluedoGame.getInstance().startGame();
    }

    public void rollDices() {
        CluedoGame.getInstance().rollDices();
    }

    public void goToCell(Cell destination) {
        CluedoGame.getInstance().goToCell(destination);
    }

    public void doAction(ArrayList<Card> assumption) {
        if (assumption.isEmpty()) {
            Board.getInstance().doAction(null, null);
        } 
        else {
            SuspectC suspectedPerson = (SuspectC) assumption.get(0);
            WeaponC suspectedWeapon = (WeaponC) assumption.get(1);
            Board.getInstance().doAction(suspectedPerson, suspectedWeapon);
        }
    }

    public void endTurn(DoActionResult doActionResult) {
        CluedoGame.getInstance().endTurn(doActionResult.isGameEnded());
    }
}
