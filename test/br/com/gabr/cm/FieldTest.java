package br.com.gabr.cm;

import br.com.gabr.cm.model.Field;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


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
    void realNeighboorTest(int x, int y, boolean isNeighboor) {

        Field neighboor = new Field(x, y);

        boolean result = field.addNeighboor(neighboor);

        if(isNeighboor) {
            assertTrue(result, "Expected: is Neighboor, but got not for x= " + x + ", y= " + y);
        } else {
            assertFalse(result,"Expected: not a Neighbor, but got true for x= " + x + ", y= " + y);
        }
    }
}
