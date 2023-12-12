package agh.ics.oop;

import agh.ics.oop.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Callable;

public class Simulation implements Runnable{
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
            catch (PositionAlreadyOccupiedException ignored){}
        }
    }

    @Override
    public void run(){
        try{
            for (int i = 0; i != moves.size(); ++i) {
                if(Thread.currentThread().isInterrupted()){
                    return;
                }
                map.move(animals.get(i % animals.size()), moves.get(i));
                if(i != moves.size() - 1){
                    Thread.sleep(500);
                }
            }
        }
        catch (InterruptedException exception){
            System.out.println(exception);
        }
    }

    public UUID getUUID(){
        return map.getID();
    }
}
