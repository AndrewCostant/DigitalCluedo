import ui.*;
import domain.CluedoGame;
import controller.GameController;

public class App {

    public static void main(String[] args) throws Exception {
        GameController gameController = new GameController();
        gameController.Game(CluedoGame.getInstance(), TerminalUI.getInstance());
    } 
}
