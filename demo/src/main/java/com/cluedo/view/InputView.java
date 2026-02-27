package com.cluedo.view;

import java.util.Scanner;

public class InputView {
    private Scanner scanner;
    public InputView() {
        scanner = new Scanner(System.in);
    }

    public int askInt() {
        while (!scanner.hasNextInt()) {
            System.out.println("Invalid input. Please enter an integer.");
            scanner.nextLine(); // Clear the invalid input
        }
        int value = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character after the integer input
        return value;
    }

    public String askString() {
        String value = scanner.nextLine();
        while (value.trim().isEmpty()) {
            System.out.println("Invalid input. Please enter a non-empty string.");
            value = scanner.nextLine();
        }
        return value;
    }

    public void pressButton() {
        scanner.nextLine();
    }
}
