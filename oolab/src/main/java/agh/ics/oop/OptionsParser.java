package agh.ics.oop;

import agh.ics.oop.model.MoveDirection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OptionsParser {
    public static List<MoveDirection> parse(String[] args){
        List<MoveDirection> commands = new ArrayList<>();
        //MoveDirection[] commands = new MoveDirection[args.length];
        //int counter = 0;
        for(int i = 0; i != args.length; ++i){
            switch (args[i]){
                case "f" -> commands.add(MoveDirection.FORWARD);
                case "b" -> commands.add(MoveDirection.BACKWARD);
                case "l" -> commands.add(MoveDirection.LEFT);
                case "r" -> commands.add(MoveDirection.RIGHT);
            }
        }
        return commands;
    }
}
