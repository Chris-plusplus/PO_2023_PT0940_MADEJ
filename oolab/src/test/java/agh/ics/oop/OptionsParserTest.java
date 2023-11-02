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
        String[] args1 = {"f", "b", "l", "r", "forward", "up", "BACKWARD", "b", "r", "r", "l", "f"};
        List<MoveDirection> results1 = OptionsParser.parse(args1);
        List<MoveDirection> expected1 = new ArrayList<>(Arrays.asList(MoveDirection.FORWARD, MoveDirection.BACKWARD, MoveDirection.LEFT, MoveDirection.RIGHT, MoveDirection.BACKWARD, MoveDirection.RIGHT, MoveDirection.RIGHT, MoveDirection.LEFT, MoveDirection.FORWARD));
        Assertions.assertEquals(results1, expected1);

        String[] args2 = {};
        List<MoveDirection> results2 = OptionsParser.parse(args2);
        List<MoveDirection> expected2 = new ArrayList<>();
        Assertions.assertEquals(results2, expected2);

        String[] args3 = {"do tyłu", "fwd", "L"};
        List<MoveDirection> results3 = OptionsParser.parse(args3);
        List<MoveDirection> expected3 = new ArrayList<>();
        Assertions.assertEquals(results3, expected3);
    }
}
