package agh.ics.oop;

import agh.ics.oop.model.MapDirection;
import agh.ics.oop.model.MoveDirection;
import agh.ics.oop.model.Vector2d;

import java.awt.*;

public class World {
    public static void main(String[] args){
        /*
        System.out.println("Start");
        run(OptionsParser.parse(args));
        System.out.println("Stop");
        Vector2d position1 = new Vector2d(1, 2);
        System.out.println(position1);
        Vector2d position2 = new Vector2d(-2, 1);
        System.out.println(position2);
        System.out.println(position1.add(position2));

        System.out.println(MapDirection.NORTH);
        System.out.println(MapDirection.NORTH.toUnitVector());
        System.out.println(MapDirection.NORTH.next());
        System.out.println(MapDirection.NORTH.next().toUnitVector());
        System.out.println(MapDirection.NORTH.next().next());
        System.out.println(MapDirection.NORTH.next().next().toUnitVector());
        System.out.println(MapDirection.NORTH.next().next().next());
        System.out.println(MapDirection.NORTH.next().next().next().toUnitVector());
        System.out.println();
        System.out.println(MapDirection.NORTH.previous());
        System.out.println(MapDirection.NORTH.previous().previous());
        System.out.println(MapDirection.NORTH.previous().previous().previous());
         */

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
