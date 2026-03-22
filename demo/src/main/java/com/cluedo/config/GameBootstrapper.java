package com.cluedo.config;

import com.cluedo.controller.GameController;
import com.cluedo.view.InputView;
import com.cluedo.view.TurnView;
import com.cluedo.view.WelcomeView;

public class GameBootstrapper {
    
    private GameBootstrapper() {
        // Utility class
    }
    
    public static void initialize() throws Exception {

        GameController controller = new GameController();
        controller.setUpGame();
        InputView input = new InputView();
        WelcomeView welcomeView = new WelcomeView(controller);
        TurnView turnView = new TurnView(controller, input);

        // Register event handlers
        EventDispatcher dispatcher = EventDispatcher.getInstance();
        dispatcher.register(GameEvent.WELCOME, welcomeView.getEventHandler());
        dispatcher.register(GameEvent.SET_PLAYERS, welcomeView.getEventHandler());
        dispatcher.register(GameEvent.ROLL_DICES, turnView.getEventHandler());
        dispatcher.register(GameEvent.SELECT_DESTINATION, turnView.getEventHandler());
        dispatcher.register(GameEvent.DO_ACTION, turnView.getEventHandler());
        dispatcher.register(GameEvent.END_TURN, turnView.getEventHandler());
        
        // Start the game
        dispatcher.dispatch(GameEvent.WELCOME);
    }
}
