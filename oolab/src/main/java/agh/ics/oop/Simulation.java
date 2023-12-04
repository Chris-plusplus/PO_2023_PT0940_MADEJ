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
            try{
                this.map.place(newAnimal);
                animals.add(newAnimal);
            }
            // polecenie 4. każe tylko pominąć zwierzeta z niepoprawnymi position
            catch (PositionAlreadyOccupiedException ignoredException){}
        }
    }

    public void run(){
        for(int i = 0; i != moves.size(); ++i){
            map.move(animals.get(i % animals.size()), moves.get(i));
        }
    }
}
