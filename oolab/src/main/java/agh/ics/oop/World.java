package agh.ics.oop;

import agh.ics.oop.model.Animal;
import agh.ics.oop.model.MapDirection;
import agh.ics.oop.model.MoveDirection;
import agh.ics.oop.model.Vector2d;

import java.util.ArrayList;
import java.util.List;

public class World {
    public static void main(String[] args){
        List<MoveDirection> directions = OptionsParser.parse(args);
        List<Vector2d> positions = List.of(
                new Vector2d(2, 2),
                new Vector2d(3, 4)
        );
        Simulation simulation = new Simulation(positions, directions);
        simulation.run();
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
