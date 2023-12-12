package br.com.gabr.cm;

import br.com.gabr.cm.model.Board;
import br.com.gabr.cm.view.BoardConsole;

public class Application {
    public static void main(String[] args) {
        Board board = new Board(6,6,6);
        new BoardConsole(board);
    }
}
