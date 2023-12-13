package br.com.gabr.cm;

import br.com.gabr.cm.model.Board;
import br.com.gabr.cm.view.BoardConsole;

import java.util.Scanner;

public class Application {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        try {
            System.out.println("\nCreate your own game:\n");

            System.out.print("Lines: ");
            int lines = sc.nextInt();

            System.out.print("Columns: ");
            int columns = sc.nextInt();

            System.out.print("Bombs: ");
            int bombs = sc.nextInt();

            Board board = new Board(lines, columns, bombs);
            System.out.println("Game created :) \n ");

            new BoardConsole(board);

        } finally {
            sc.close();
        }

    }
}
