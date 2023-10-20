package agh.ics.oop;

import agh.ics.oop.model.MoveDirection;

import java.awt.*;

public class World {
    public static void main(String[] args){
        System.out.println("Start");
        run(OptionsParser.parse(args));
        System.out.println("Stop");
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
