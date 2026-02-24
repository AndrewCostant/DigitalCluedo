package com.cluedo;

import com.cluedo.controller.GameController;
import com.cluedo.domain.CluedoGame;
import com.cluedo.ui.TerminalUI;

public class Main {
    public static void main(String[] args) throws Exception {
        System.out.println("Hello world!");
        GameController gameController = new GameController();
        gameController.Game(CluedoGame.getInstance(), TerminalUI.getInstance());
    }
}