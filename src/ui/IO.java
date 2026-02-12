package ui;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import domain.Board;
import domain.Player;

public class IO {
    
    private static volatile IO instance;

    StringBuilder[] output;
    ArrayList<String> welcome;

    private IO() throws FileNotFoundException {
        this.initializeMap();
        this.initializeWelcome();
    }

    public static IO getInstance() throws FileNotFoundException {
        if (instance == null) {
            instance = new IO();
        }
        return instance;
    }

    private void initializeWelcome() throws FileNotFoundException{
        String path = "utility/intestazione.txt";

        InputStream is = Board.class.getClassLoader().getResourceAsStream(path);
        if (is == null) {
            throw new RuntimeException("File not found: " + path);
        }
		try (Scanner sc = new Scanner(is)) {
            while (sc.hasNextLine()) {
                welcome.add(sc.nextLine());
            }
        }
        catch (Exception e) {
            throw new FileNotFoundException("File intestazione.txt not found");
        }
    }

    private void initializeMap() {
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
        output = new StringBuilder[map.length];
        for (int i = 0; i < map.length; i++) {
            output[i] = new StringBuilder(map[i]);
        }
    }

    /**
     * Stampa la mappa ASCII con i nomi dei player nelle loro posizioni attuali.
     * 
     * @param players Lista dei giocatori da stampare sulla mappa.
     */
    public void printBoardWithPlayers(ArrayList<Player> players) {

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

    public void printWelcome() {
        for (String line : welcome) {
            System.out.print(line);
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
