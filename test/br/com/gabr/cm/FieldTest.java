package br.com.gabr.cm;

import br.com.gabr.cm.exception.ExplosionException;
import br.com.gabr.cm.model.Field;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;


public class FieldTest {
    private Field field;


    @BeforeEach
    void startField() {
        field = new Field(3, 3);
    }


    @ParameterizedTest
    @CsvSource({
//            Directt Neighboors
            "3,2,true",
            "3,4,true",
            "2,3,true",
//            Diagonal Neighboors
            "2,2,true",
//            Not Neighboors
            "1,1,false"
    })
    void realNeighboorsTest(int x, int y, boolean isNeighboor) {

        Field neighboor = new Field(x, y);

        boolean result = field.addNeighboor(neighboor);

        if (isNeighboor) {
            assertTrue(result, "Expected: is Neighboor, but got not, for x= " + x + ", y= " + y);
        } else {
            assertFalse(result, "Expected: not a Neighbor, but got true, for x= " + x + ", y= " + y);
        }
    }

    @ParameterizedTest
    @CsvSource({
            "true,1",
            "false,1",
            "false,2"
    })
    void handleMarkedTest(boolean isDefault, int numberOfTimesCallMethod) {

        if (isDefault) {
            assertFalse(field.checkMarked());
        } else {
            for (int i = 0; i < numberOfTimesCallMethod; i++) {
                field.handleMarked();
            }

            if (numberOfTimesCallMethod % 2 == 0) {
                assertFalse(field.checkMarked());

            } else {
                assertTrue(field.checkMarked());
            }
        }
    }

    @ParameterizedTest
    @CsvSource({
            "true,false",
            "false,false",
            "true,true",
            "false, true"
    })
    void handleUserOpenFieldsActionsTest(boolean isMarked, boolean isMined) {
        if (!isMarked && !isMined) {
            assertTrue(field.handleMarked());

        } else if (isMarked && !isMined) {

            field.handleMarked();
            assertFalse(field.handleMarked());

        } else if (isMined && isMarked) {

            field.handleOpen();
            field.handleMineField();
            assertFalse(field.handleOpen());

        } else if (isMined && !isMarked) {
            field.handleMineField();

            assertThrows(ExplosionException.class, () -> {
                field.handleOpen();
            });
        }
    }

    @Test
    void openWithNeighboorsTest() {
        Field n1x1 = new Field(1, 1);
        Field n2x2 = new Field(2, 2);

        n2x2.addNeighboor(n1x1);

        field.addNeighboor(n2x2);
        field.handleOpen();

        assertTrue(n2x2.isOpen() && n1x1.isOpen());
    }

    @Test
    void openWithNeighboorsMinedFieldTest() {
        Field n1x1 = new Field(1, 1);
        Field n1x2 = new Field(1, 2);
        Field n2x2 = new Field(2, 2);

        n1x2.handleMineField();

        n2x2.addNeighboor(n1x1);
        n2x2.addNeighboor(n1x2);

        field.addNeighboor(n2x2);
        field.handleOpen();

        assertTrue(n2x2.isOpen() && n1x1.isClose());
    }


}
