package br.com.gabr.cm.model;

import br.com.gabr.cm.exception.ExplosionException;

import java.util.ArrayList;
import java.util.List;

public class Field {
    private final int line;
    private final int column;
    private boolean open = false;
    private boolean mined = false;
    private boolean marked = false;
    private List<Field> neighbors = new ArrayList<>();

    // COLORS
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_Yellow = "\033[0;33m";

    // COLORS


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

    public boolean handleMarked() {
        if (!open) {
            return marked = !marked;
        } else {
            return marked;
        }
    }

    public boolean handleOpen() {
        if (!open && !marked) {
            open = true;

            if (mined) {
                throw new ExplosionException();
            }

            if (safeNeighboorhood()) {
                // Reference method same as lambda expression ex: (n -> n.handleOpen());
                neighbors.forEach(Field::handleOpen);
            }
            return true;
        } else {
            return false;
        }
    }

    boolean safeNeighboorhood() {
        return neighbors
                .stream()
                .noneMatch(n -> n.mined);
    }

    public boolean checkMarked() {
        return marked;
    }

    public boolean handleMineField() {
        if (!mined) {
            return mined = true;
        } else {
            return mined;
        }
    }
    public boolean isMined() {
        return mined;
    }

    boolean safeField() {
        boolean discoverFields = !mined && open;
        boolean protectedFields = mined && marked;

        return discoverFields || protectedFields;
    }

    long minesInNeighborhood() {
        return neighbors
                .stream()
                .filter(n -> n.mined)
                .count();
    }

    void restar() {
        open = false;
        mined = false;
        marked = false;
    }

    public String toString() {
        String redAsterisk = ANSI_RED + "*" + ANSI_Yellow;
        String greenMark = ANSI_GREEN + "X" + ANSI_Yellow;

        if (marked) {
            return greenMark;
        } else if (open && mined) {
            return redAsterisk;

        } else if (open && minesInNeighborhood() > 0) {
            return Long.toString(minesInNeighborhood());

        } else if (open) {
            return " ";
        } else {
            return ANSI_Yellow+"?";
        }
    }

    public boolean isOpen() {
        return open;
    }

    public boolean isClose() {
        return !isOpen();
    }

    public int getLine() {
        return line;
    }

    public int getColumn() {
        return column;
    }

    void setOpen(boolean open) {
        this.open = open;
    }
}
