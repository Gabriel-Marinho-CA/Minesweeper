package br.com.gabr.cm.model;

import java.util.ArrayList;
import java.util.List;

public class Field {
    private final int line;
    private final int column;
    private boolean open = false;
    private boolean mined = false;
    private boolean marked = false;
    private List<Field> neighbors = new ArrayList<>();

    public Field(int line, int column) {
        this.line = line;
        this.column = column;

    }

    public boolean addNeighboor(Field neighboor) {
        boolean lineDiff = line != neighboor.line;
        boolean columnDiff = column != neighboor.column;
        boolean diagonal = lineDiff && columnDiff;

        int deltaLine = Math.abs(line - neighboor.line);
        int deltaColumn = Math.abs(column - neighboor.column);
        int deltaGeral = deltaColumn + deltaLine;

        if (deltaGeral == 1 && !diagonal) {
            neighbors.add(neighboor);
            return true;
        } else if (deltaGeral == 2 && diagonal) {
            neighbors.add(neighboor);
            return true;
        } else {
            return false;
        }
    }


}
