package agh.ics.oop;

import agh.ics.oop.model.*;

import java.util.ArrayList;
import java.util.List;

public class World {
    public static void main(String[] args){
        try {
            List<MoveDirection> directions = OptionsParser.parse(args);
            List<Vector2d> positions = List.of(
                    new Vector2d(2, 2),
                    new Vector2d(3, 4)
            );
            GrassField map = new GrassField(10);
            ConsoleMapDisplay consoleMapDisplay = new ConsoleMapDisplay();
            map.addListener(consoleMapDisplay);
            Simulation simulation = new Simulation(positions, directions, map);
            simulation.run();
        }
        catch (IllegalArgumentException e){
            System.out.println(e);
        }
        // test ręczny wykazał poprawność
    }
    public static void run(MoveDirection[] commands){
        for(int i = 0; i != commands.length; ++i){
            switch (commands[i]){
                case FORWARD -> System.out.print("zwierzak idzie do przodu\n");
                case BACKWARD -> System.out.print("zwierzak idzie do tylu\n");
                case LEFT -> System.out.print("zwierzak skreca w lewo\n");
                case RIGHT -> System.out.print("zwierzak skreca w prawo\n");
            }
        }
    }
}
