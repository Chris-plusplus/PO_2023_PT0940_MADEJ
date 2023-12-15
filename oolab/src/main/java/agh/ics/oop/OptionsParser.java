package agh.ics.oop;

import agh.ics.oop.model.MoveDirection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OptionsParser {
    // infomacja dla użytkownika
    public static List<MoveDirection> parse(List<String> args) throws IllegalArgumentException{
        List<MoveDirection> commands = new ArrayList<>();

        for(int i = 0; i != args.size(); ++i){
            switch (args.get(i)){
                case "f" -> commands.add(MoveDirection.FORWARD);
                case "b" -> commands.add(MoveDirection.BACKWARD);
                case "l" -> commands.add(MoveDirection.LEFT);
                case "r" -> commands.add(MoveDirection.RIGHT);
                default -> throw new IllegalArgumentException("'" + args.get(i) + "' is not legal move specification");
            }
        }
        return commands;
    }
}
