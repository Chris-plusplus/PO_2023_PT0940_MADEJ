package agh.ics.oop;

import agh.ics.oop.model.Move;

import java.util.Arrays;

public class OptionsParser {
    public static Move.MoveDirection[] parse(String[] args){
        Move.MoveDirection[] commands = new Move.MoveDirection[args.length];
        int counter = 0;
        for(int i = 0; i != args.length; ++i){
            switch (args[i]){
                case "f" -> commands[counter++] = Move.MoveDirection.FORWARD;
                case "b" -> commands[counter++] = Move.MoveDirection.BACKWARD;
                case "l" -> commands[counter++] = Move.MoveDirection.LEFT;
                case "r" -> commands[counter++] = Move.MoveDirection.RIGHT;
            }
        }
        return Arrays.copyOfRange(commands, 0, counter);
    }
}
