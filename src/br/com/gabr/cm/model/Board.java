package br.com.gabr.cm.model;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class Board {
    private int lines;
    private int column;
    private int mines;
    private final List<Field> fields = new ArrayList<>();


    public Board(int lines, int column, int mines) {
        this.lines = lines;
        this.column = column;
        this.mines = mines;

        generateFields();
        associateNeightboors();
        insertRandomMines();
    }

    public void handleMarkAndOpenField(int line, int column, String type ) {
        fields.parallelStream()
                .filter(b -> b.getLine() == line && b.getColumn() == column)
                .findFirst()
                .ifPresent(c -> {
                    if(type.equals("field")) {
                        c.handleOpen();
                    } else {
                        c.handleMarked();
                    }
                });
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
            minesAlreadyGenerated =
                    fields
                            .stream()
                            .filter(mined)
                            .count();

            int random = (int) (Math.random() * fields.size());
            fields.get(random).handleMineField();

        } while (minesAlreadyGenerated < mines);
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
        int i = 0;

        for (int l = 0; l < lines; l++) {
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


}
