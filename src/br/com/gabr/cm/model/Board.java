package br.com.gabr.cm.model;

import br.com.gabr.cm.exception.ExplosionException;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class Board {
    private int lines;
    private int column;
    private int mines;
    private final List<Field> fields = new ArrayList<>();


    //COLORS
    public static final String ANSI_WHITE = "\u001B[37m";
    public static final String ANSI_Yellow = "\033[0;33m";
    //COLORS

    public Board(int lines, int column, int mines) {
        this.lines = lines;
        this.column = column;
        this.mines = mines;

        generateFields();
        associateNeightboors();
        insertRandomMines();
    }

    public void handleMarkAndOpenField(int line, int column, String type) {
        try {
            fields.parallelStream()
                    .filter(b -> b.getLine() == line && b.getColumn() == column)
                    .findFirst()
                    .ifPresent(c -> {
                        if (type.equals("field")) {
                            c.handleOpen();
                        } else {
                            c.handleMarked();
                        }
                    });
        } catch (ExplosionException e) {
            fields.forEach(c -> c.setOpen(true));
            throw e;
        }
    }

    public boolean safeField() {
        return fields.stream().allMatch(Field::safeField);
    }

    public void restar() {
        fields.stream().forEach(Field::restar);

        insertRandomMines();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("  ");
        for (int c = 0; c < column; c++) {
            sb.append(" ");
            sb.append(ANSI_WHITE).append(c).append(ANSI_Yellow);
            sb.append(" ");
        }

        sb.append("\n");

        int i = 0;

        for (int l = 0; l < lines; l++) {
            sb.append(ANSI_WHITE).append(l).append(ANSI_Yellow);
            sb.append(" ");

            for (int c = 0; c < column; c++) {
                sb.append(" ");
                sb.append(fields.get(i));
                sb.append(" ");
                i++;
            }
            sb.append("\n");
        }


        return sb.toString();
    }

    private void generateFields() {
        for (int l = 0; l < lines; l++) {
            for (int c = 0; c < column; c++) {
                fields.add(new Field(l, c));
            }
        }
    }

    private void associateNeightboors() {
        for (Field c1 : fields) {
            for (Field c2 : fields) {
                c1.addNeighboor(c2);
            }
        }
    }

    private void insertRandomMines() {
        long minesAlreadyGenerated = 0;
        Predicate<Field> mined = Field::isMined;

        do {
            int random = (int) (Math.random() * fields.size());
            fields.get(random).handleMineField();

            minesAlreadyGenerated =
                    fields
                            .stream()
                            .filter(mined)
                            .count();

        } while (minesAlreadyGenerated < mines);
    }

}
