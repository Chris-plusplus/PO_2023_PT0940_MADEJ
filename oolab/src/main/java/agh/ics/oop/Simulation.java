package agh.ics.oop;

import agh.ics.oop.model.*;

import java.util.ArrayList;
import java.util.List;

public class Simulation {
    private final List<Animal> animals;
    private final List<MoveDirection> moves;
    private final WorldMap<Animal, Vector2d> map;

    public Simulation(List<Vector2d> startingPositions, List<MoveDirection> moves, WorldMap<Animal, Vector2d> map){
        this.moves = new ArrayList<>(moves);
        this.map = map;
        animals = new ArrayList<>();
        for(Vector2d startingPosition : startingPositions){
            Animal newAnimal = new Animal(startingPosition);
            animals.add(newAnimal);
            this.map.place(newAnimal);
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
