package agh.ics.oop;

import agh.ics.oop.model.MoveDirection;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class OptionsParserTest {
    @Test
    public void parseTest() {
        String[] args = {"f", "b", "l", "r", "forward", "up", "BACKWARD", "b", "r", "r", "l", "f"};
        MoveDirection[] results = OptionsParser.parse(args);
        MoveDirection[] expected = {MoveDirection.FORWARD, MoveDirection.BACKWARD, MoveDirection.LEFT, MoveDirection.RIGHT, MoveDirection.BACKWARD, MoveDirection.RIGHT, MoveDirection.RIGHT, MoveDirection.LEFT, MoveDirection.FORWARD};

        Assertions.assertEquals(results.length, expected.length);
        for(int i = 0; i != results.length; ++i){
           Assertions.assertEquals(results[i], expected[i]);
        }
    }
}
