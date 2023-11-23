package agh.ics.oop;

import agh.ics.oop.model.*;

import java.util.ArrayList;
import java.util.List;

public class Simulation {
    private final List<Animal> animals;
    private final List<MoveDirection> moves;
    private final WorldMap map;

    public Simulation(List<Vector2d> startingPositions, List<MoveDirection> moves, WorldMap map){
        this.moves = new ArrayList<>(moves);
        this.map = map;
        animals = new ArrayList<>();
        for(Vector2d startingPosition : startingPositions){
            Animal newAnimal = new Animal(startingPosition);
            if (this.map.place(newAnimal)){
                animals.add(newAnimal);
            }
        }
    }

    public void run(){
        System.out.println(map);
        for(int i = 0; i != moves.size(); ++i){
            map.move(animals.get(i % animals.size()), moves.get(i));
            System.out.println(map);
        }
    }
}
