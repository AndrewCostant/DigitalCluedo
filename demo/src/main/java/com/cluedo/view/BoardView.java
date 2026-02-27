package com.cluedo.view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cluedo.domain.CluedoGame;
import com.cluedo.domain.Player;

public class BoardView {
    
    private StringBuilder[] output;
    private Boolean initialized;

    public BoardView() {
        this.initialized = false;
    }

    public boolean isInitialized() {
        return initialized;
    }
    
    /**
     * Initialize the ASCII map by reading it from a file specified by the current game mode's factory. The file is expected to be in the resources folder of the project.
     * @throws IOException
     */
    public void initializeMap() throws IOException {
        String path = CluedoGame.getInstance()
                .getGameModeFactory()
                .getUiMapPath();

        InputStream is = getClass()
                .getClassLoader()
                .getResourceAsStream(path);

        if (is == null) {
            throw new RuntimeException("File not found: " + path);
        }

        List<String> lines = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        }

        output = new StringBuilder[lines.size()];
        for (int i = 0; i < lines.size(); i++) {
            output[i] = new StringBuilder(lines.get(i));
        }
        this.initialized = true;
    }


    /**
     * Print the ASCII map of the game board, including the players' names in their respective positions. 
     * The method takes a list of Player objects as input, where each Player object contains information about the player's name and current position on the board. 
     * The players' names are printed in the lower row of the cell they are currently occupying. If multiple players occupy the same cell, their names are printed separated by spaces.
     * 
     * @param players Lista dei giocatori da stampare sulla mappa.
     */
    public void printBoardWithPlayers(ArrayList<Player> players) {
        StringBuilder[] outputCopy = new StringBuilder[output.length];
        for (int i = 0; i < output.length; i++) {
            outputCopy[i] = new StringBuilder(output[i].toString());
        }
        
        // raggruppa player per cella
        Map<String, List<String>> playersPerCell = new HashMap<>();
        for (Player p : players) {
            int x = p.getPosition().getX();
            int y = p.getPosition().getY();
            playersPerCell
                    .computeIfAbsent(x + "," + y, k -> new ArrayList<>())
                    .add(p.getUsername());
        }

        // scrittura player: SEMPRE riga bassa della cella
        for (var entry : playersPerCell.entrySet()) {

            String[] xy = entry.getKey().split(",");
            int x = Integer.parseInt(xy[0]);
            int y = Integer.parseInt(xy[1]);

            int row = asciiBottomRowForCell(x);
            int col = asciiColForCell(y);

            String text = String.join(" ", entry.getValue());

            for (int i = 0; i < text.length(); i++) {
                if (col + i < outputCopy[row].length()) {
                    outputCopy[row].setCharAt(col + i, text.charAt(i));
                }
            }
        }
        for (StringBuilder line : outputCopy) {
            System.out.println(line);
        }
    }

    private static int asciiBottomRowForCell(int x) {
        // riga bassa della cella
        return 4 + x * 4;
    }

    private static int asciiColForCell(int y) {
        return 5 + y * 10;
    }

}