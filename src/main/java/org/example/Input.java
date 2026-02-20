package org.example;

import java.util.Scanner;

public class Input {
    private Scanner scanner = new Scanner(System.in);

    public Input() {
        this.scanner = new Scanner(System.in);
    }

    public String getUserInput() {
        System.out.print("Please enter the title: ");
        return scanner.nextLine();
    }
}
