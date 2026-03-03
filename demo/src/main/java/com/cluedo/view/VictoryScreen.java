package com.cluedo.view;

import com.cluedo.config.GameConfig;

public class VictoryScreen {

    private static final int WIDTH = 60;

    public static void show() {
        printBorder();
        printEmptyLine();
        printCentered(GameConfig.GREEN + "██╗    ██╗██╗███╗   ██╗███╗   ██╗███████╗██████╗ " + GameConfig.RESET);
        printCentered(GameConfig.GREEN + "██║    ██║██║████╗  ██║████╗  ██║██╔════╝██╔══██╗" + GameConfig.RESET);
        printCentered(GameConfig.GREEN + "██║ █╗ ██║██║██╔██╗ ██║██╔██╗ ██║█████╗  ██████╔╝" + GameConfig.RESET);
        printCentered(GameConfig.GREEN + "██║███╗██║██║██║╚██╗██║██║╚██╗██║██╔══╝  ██╔══██╗" + GameConfig.RESET);
        printCentered(GameConfig.GREEN + "╚███╔███╔╝██║██║ ╚████║██║ ╚████║███████╗██║  ██║" + GameConfig.RESET);
        printCentered(GameConfig.GREEN + " ╚══╝╚══╝ ╚═╝╚═╝  ╚═══╝╚═╝  ╚═══╝╚══════╝╚═╝  ╚═╝" + GameConfig.RESET);
        printEmptyLine();
        printBorder();
    }

    private static void printBorder() {
        
        System.out.println("=".repeat(WIDTH));
    }

    private static void printEmptyLine() {
        System.out.println("|" + " ".repeat(WIDTH - 2) + "|");
    }

    private static void printCentered(String text) {
        int padding = (WIDTH - 2 - text.length()) / 2;
        if (padding < 0) padding = 0;
        String line = "|" +
                " ".repeat(padding) +
                text +
                " ".repeat(WIDTH - 2 - padding - text.length()) +
                "|";
        System.out.println(line);
    }
}