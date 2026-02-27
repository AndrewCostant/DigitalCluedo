package com.cluedo.view;

public class LoadingBar {

    public static void showLoadingBar(int totalSteps) throws InterruptedException {
        for (int i = 0; i <= totalSteps; i++) {
            int percent = (i * 100) / totalSteps;
            String bar = "#".repeat(i) + " ".repeat(totalSteps - i);
            System.out.print("\r[" + bar + "] " + percent + "%");
            Thread.sleep(100);
        }
        System.out.println("\nThe game is ready! Let's start!");
    }

    public static void dramaticLoading(String playerName) throws InterruptedException {
        String message = playerName + " is the";
        
        for (int i = 0; i < 3; i++) {
            System.out.print(message);
            for (int j = 0; j <= 3; j++) {
                System.out.print(".");
                Thread.sleep(400);
            }
            System.out.print("\r" + " ".repeat(message.length() + 3) + "\r");
        }
        System.out.println(message + "...");
    }
}