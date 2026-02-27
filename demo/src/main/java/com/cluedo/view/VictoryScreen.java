package com.cluedo.view;

public class VictoryScreen {

    private static final int WIDTH = 60;

    public static void show() {
        printBorder();
        printEmptyLine();
        printCentered("██╗    ██╗██╗███╗   ██╗███╗   ██╗███████╗██████╗ ");
        printCentered("██║    ██║██║████╗  ██║████╗  ██║██╔════╝██╔══██╗");
        printCentered("██║ █╗ ██║██║██╔██╗ ██║██╔██╗ ██║█████╗  ██████╔╝");
        printCentered("██║███╗██║██║██║╚██╗██║██║╚██╗██║██╔══╝  ██╔══██╗");
        printCentered("╚███╔███╔╝██║██║ ╚████║██║ ╚████║███████╗██║  ██║");
        printCentered(" ╚══╝╚══╝ ╚═╝╚═╝  ╚═══╝╚═╝  ╚═══╝╚══════╝╚═╝  ╚═╝");
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