package agh.ics.oop;

import agh.ics.oop.model.MoveDirection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class OptionsParser {
    private static MoveDirection convert(String arg) throws IllegalArgumentException {
        return switch (arg){
            case "f" -> MoveDirection.FORWARD;
            case "b" -> MoveDirection.BACKWARD;
            case "l" -> MoveDirection.LEFT;
            case "r" -> MoveDirection.RIGHT;
            default -> throw new IllegalArgumentException("'" + arg + "' is not legal move specification");
        };
    }

    // infomacja dla użytkownika
    public static List<MoveDirection> parse(List<String> args) throws IllegalArgumentException{
        return args
                .stream()
                .map(OptionsParser::convert)
                .toList();
    }
}
