package br.com.gabr.cm.view;

import br.com.gabr.cm.exception.ExitException;
import br.com.gabr.cm.exception.ExplosionException;
import br.com.gabr.cm.model.Board;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Scanner;
import java.util.stream.Stream;

public class BoardConsole {
    private Board board;
    private Scanner sc = new Scanner(System.in);

    // COLORS
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_Yellow = "\033[0;33m";
    // COLORS

    public BoardConsole(Board board) {
        this.board = board;

        startGame();
    }

    private void startGame() {
        try {

            boolean Continue = true;

            while (Continue) {

                lifeCycleGame();

                System.out.println("Another game? (Y/n)");
                String resp = sc.nextLine();

                if ("n".equalsIgnoreCase(resp)) {
                    Continue = false;
                } else {
                    board.restar();
                }

            }

        } catch (ExitException e) {
            System.out.println("Goodbye!!!");

        } finally {
            sc.close();
        }
    }

    private void lifeCycleGame() {
        try {
            while (!board.safeField()) {
                System.out.println(board);

                String typed = getUserInput("Type coords (x,y): ");

                boolean checkValuesTyped = CheckCoords(typed);

                if (checkValuesTyped) {

                    Iterator<Integer> xy = Arrays.stream(typed.split(",")).map(c -> Integer.parseInt(c.trim())).iterator();

                    typed = getUserInput("1 - to Open or 2 - to mark on/off");

//                     if(checkValuesTyped) {
                    if ("1".equalsIgnoreCase(typed)) {
                        board.handleMarkAndOpenField(xy.next(), xy.next(), "field");
                    } else if ("2".equals(typed)) {
                        board.handleMarkAndOpenField(xy.next(), xy.next(), "mark");
                    }
//                     }
                } else {
                    System.out.println("Type a valid input");
                }

            }
            System.out.println(board);
            System.out.println(ANSI_GREEN + "You Won !" + ANSI_Yellow);


        } catch (ExplosionException e) {
            System.out.println(board);
            System.out.println(ANSI_RED + "You loose !" + ANSI_Yellow);
        }
    }

    private String getUserInput(String text) {
        System.out.println(text);
        String typed = sc.nextLine();

        if ("exit".equalsIgnoreCase(typed)) {
            throw new ExitException();
        }

        return typed;
    }

    private boolean CheckCoords(String input) {

        String regex = "\\d+,\\d+";
        boolean inputCheckValues;

        if (input.isEmpty()) {
            return false;

        } else if (!input.matches(regex)) {
            return false;

        } else {
            inputCheckValues =
                    Arrays.stream(input.split(","))
                            .map(n -> Integer.parseInt(n))
                            .anyMatch(n -> n < 0);

            if (inputCheckValues = false) {
                return false;
            } else {
                return true;
            }
        }

    }
}
