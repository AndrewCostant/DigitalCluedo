package ui;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import domain.Board;
import domain.Player;

public class IO {

    ArrayList<StringBuilder> output;

    public IO() {
        output = new ArrayList<>();
        String path = "utility/map.txt";
        InputStream is = Board.class.getClassLoader().getResourceAsStream(path);
        if (is == null) {
            throw new RuntimeException("File not found: " + path);
        }
        String[] map = new String[30];
		try (Scanner sc = new Scanner(is)) {
            for (int i = 0; i < map.length; i++) {
                if (sc.hasNextLine()) {
                    map[i] = sc.nextLine();
                } else {
                    throw new RuntimeException("File has fewer lines than expected: " + path);
                }
            }
        }
        StringBuilder[] output = new StringBuilder[map.length];
        for (int i = 0; i < map.length; i++) {
            output[i] = new StringBuilder(map[i]);
        }
    }

    public static String printDigitalCluedo(){
        return 
        "==================================================================================================\n" +
        "██████╗ ██╗ ██████╗ ██╗████████╗ █████╗ ██╗      ██████╗██╗     ██╗   ██╗███████╗██████╗  ██████╗ \n" +
        "██╔══██╗██║██╔════╝ ██║╚══██╔══╝██╔══██╗██║     ██╔════╝██║     ██║   ██║██╔════╝██╔══██╗██╔═══██╗\n" +
        "██║  ██║██║██║  ███╗██║   ██║   ███████║██║     ██║     ██║     ██║   ██║█████╗  ██║  ██║██║   ██║\n" +
        "██║  ██║██║██║   ██║██║   ██║   ██╔══██║██║     ██║     ██║     ██║   ██║██╔══╝  ██║  ██║██║   ██║\n" +
        "██████╔╝██║╚██████╔╝██║   ██║   ██║  ██║███████╗╚██████╗███████╗╚██████╔╝███████╗██████╔╝╚██████╔╝\n" +
        "╚═════╝ ╚═╝ ╚═════╝ ╚═╝   ╚═╝   ╚═╝  ╚═╝╚══════╝ ╚═════╝╚══════╝ ╚═════╝ ╚══════╝╚═════╝  ╚═════╝\n" +
        "==================================================================================================\n" +
        "Hi, this is the first demo of Digital Cluedo.\n" +
        "At the beginning, they are in the Hall.\n" +
        "Each player will take turns to roll the dice and move on the board.\n" +
        "Enjoy the demo!\n";
    }

    /**
     * Stampa la mappa ASCII con i nomi dei player nelle loro posizioni attuali.
     * 
     * @param players Lista dei giocatori da stampare sulla mappa.
     */
    public static void printBoardWithPlayers(ArrayList<Player> players) {

        String path = "utility/map.txt";
        InputStream is = Board.class.getClassLoader().getResourceAsStream(path);
        if (is == null) {
            throw new RuntimeException("File not found: " + path);
        }
        String[] map = new String[30];
		try (Scanner sc = new Scanner(is)) {
            for (int i = 0; i < map.length; i++) {
                if (sc.hasNextLine()) {
                    map[i] = sc.nextLine();
                } else {
                    throw new RuntimeException("File has fewer lines than expected: " + path);
                }
            }
        }
        StringBuilder[] output = new StringBuilder[map.length];
        for (int i = 0; i < map.length; i++) {
            output[i] = new StringBuilder(map[i]);
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
                if (col + i < output[row].length()) {
                    output[row].setCharAt(col + i, text.charAt(i));
                }
            }
        }
        for (StringBuilder line : output) {
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
