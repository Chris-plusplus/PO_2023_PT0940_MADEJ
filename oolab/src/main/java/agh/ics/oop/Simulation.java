package agh.ics.oop;

import agh.ics.oop.model.Animal;
import agh.ics.oop.model.MoveDirection;
import agh.ics.oop.model.Vector2d;

import java.util.ArrayList;
import java.util.List;

public class Simulation {
    private List<Animal> animals;
    private List<MoveDirection> moves;

    public Simulation(List<Vector2d> startingPositions, List<MoveDirection> moves){
        this.moves = new ArrayList<>(moves);
        animals = new ArrayList<>();
        for(Vector2d startingPosition : startingPositions){
            animals.add(new Animal(startingPosition));
        }
    }

    public void run(){
        for(int i = 0; i != moves.size(); ++i){
            animals.get(i % animals.size()).move(moves.get(i));
            System.out.println("Zwierzę %d: ".formatted(i % animals.size()) + animals.get(i % animals.size()));
        }
    }
}
