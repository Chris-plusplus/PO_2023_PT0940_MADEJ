package agh.ics.oop;

import agh.ics.oop.model.MoveDirection;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OptionsParserTest {
    @Test
    public void parseTest() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            List<String> args1 = List.of("f", "b", "l", "r", "forward", "up", "BACKWARD", "b", "r", "r", "l", "f");
            List<MoveDirection> results1 = OptionsParser.parse(args1);
            List<MoveDirection> expected1 = new ArrayList<>(Arrays.asList(MoveDirection.FORWARD, MoveDirection.BACKWARD, MoveDirection.LEFT, MoveDirection.RIGHT, MoveDirection.BACKWARD, MoveDirection.RIGHT, MoveDirection.RIGHT, MoveDirection.LEFT, MoveDirection.FORWARD));
            Assertions.assertEquals(results1, expected1);
        });

        Assertions.assertDoesNotThrow(() -> {
            List<String> args2 = List.of();
            List<MoveDirection> results2 = OptionsParser.parse(args2);
            List<MoveDirection> expected2 = new ArrayList<>();
            Assertions.assertEquals(results2, expected2);
        });

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            List<String> args3 = List.of("do tyłu", "fwd", "L");
            List<MoveDirection> results3 = OptionsParser.parse(args3);
            List<MoveDirection> expected3 = new ArrayList<>();
            Assertions.assertEquals(results3, expected3);
        });

        Assertions.assertDoesNotThrow(() -> {
            List<String> args4 = List.of("f", "b", "l", "r", "b", "r", "r", "l", "f");
            List<MoveDirection> results1 = OptionsParser.parse(args4);
            List<MoveDirection> expected1 = new ArrayList<>(Arrays.asList(MoveDirection.FORWARD, MoveDirection.BACKWARD, MoveDirection.LEFT, MoveDirection.RIGHT, MoveDirection.BACKWARD, MoveDirection.RIGHT, MoveDirection.RIGHT, MoveDirection.LEFT, MoveDirection.FORWARD));
            Assertions.assertEquals(results1, expected1);
        });
    }
}
