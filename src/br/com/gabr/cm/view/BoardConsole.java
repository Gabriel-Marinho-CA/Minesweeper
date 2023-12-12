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

    public BoardConsole(Board board) {
        this.board = board;

        startGame();
    }

    private void startGame() {
        try {

            boolean Continue = true;

            while (Continue) {

                lifeCycleGame();

                System.out.println("Another game? (S/n)");
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

                boolean checkValuesTyped = CheckUserInput(typed);

            }
            System.out.println("You Won !");


        } catch (ExplosionException e) {
            System.out.println("You loose !");
        }
    }

    private String getUserInput(String text) {
        String typed = sc.nextLine();

        if ("exit".equalsIgnoreCase(typed)) {
            throw new ExitException();
        }

        return typed;
    }

    private boolean CheckUserInput(String input) {

        String regex = "\\d+,\\d+";
        boolean inputCheckValues;

        if(input.isEmpty()) {
            return false;

        } else if(!input.matches(regex)) {
            return false;

        } else {
             inputCheckValues =
                    Arrays.stream(input.split(","))
                            .map(n -> Integer.parseInt(n))
                            .anyMatch(n -> n < 0);

             if(inputCheckValues = false) {
                 return false;
             } else {
                 return true;
             }
        }

    }
}
