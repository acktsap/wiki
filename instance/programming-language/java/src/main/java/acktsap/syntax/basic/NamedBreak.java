package acktsap.syntax.basic;

import java.util.Scanner;

public class NamedBreak {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input;

        // named loop
        outer:
        while (true) {
            while (true) {
                System.out.printf("inner | outer%n> ");
                input = scanner.nextLine();

                if ("inner".equals(input)) {
                    break;
                } else if ("outer".equals(input)) {
                    break outer;
                } else {
                    System.out.println("invalid input");
                }
            }
        }
    }
}
