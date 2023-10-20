package agh.ics.oop;

import agh.ics.oop.model.MoveDirection;

import java.util.Arrays;

public class OptionsParser {
    public static MoveDirection[] parse(String[] args){
        MoveDirection[] commands = new MoveDirection[args.length];
        int counter = 0;
        for(int i = 0; i != args.length; ++i){
            switch (args[i]){
                case "f" -> commands[counter++] = MoveDirection.FORWARD;
                case "b" -> commands[counter++] = MoveDirection.BACKWARD;
                case "l" -> commands[counter++] = MoveDirection.LEFT;
                case "r" -> commands[counter++] = MoveDirection.RIGHT;
            }
        }
        return Arrays.copyOfRange(commands, 0, counter);
    }
}
