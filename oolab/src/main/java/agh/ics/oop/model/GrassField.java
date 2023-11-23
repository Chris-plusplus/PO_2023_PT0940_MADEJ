package agh.ics.oop.model;

import agh.ics.oop.model.util.RandomPositionGenerator;

import java.util.*;
import java.util.stream.Stream;

public class GrassField extends AbstractWorldMap {
    private final Map<Vector2d, Grass> grassMap = new HashMap<>();
    private Vector2d upperRightCorner;
    private Vector2d lowerLeftCorner;
    private boolean boundsNeedUpdate = true;

    // niedeterministyczny konstruktor
    public GrassField(int n){
        generateField(n, null);
    }

    // deterministyczny konstruktor
    public GrassField(int n, long seed) {
        generateField(n, seed);
    }

    private void generateField(int n, Long seed){
        int flooredBound = (int)Math.floor(Math.sqrt(10.0 * (double)n));

        RandomPositionGenerator randomPositionGenerator;
        if(seed == null){
            randomPositionGenerator = new RandomPositionGenerator(flooredBound, flooredBound, n);
        }
        else{
            randomPositionGenerator = new RandomPositionGenerator(flooredBound, flooredBound, n, seed);
        }

        for(Vector2d pos : randomPositionGenerator){
            grassMap.put(pos, new Grass(pos));
        }
    }

    private void updateBounds(){
        lowerLeftCorner = null;
        upperRightCorner = null;

        for (Animal animal : animalMap.values()){
            if(lowerLeftCorner == null){ // implikuje upperRightCorner równe null
                lowerLeftCorner = animal.getPosition();
                upperRightCorner = animal.getPosition();
            }
            lowerLeftCorner = lowerLeftCorner.lowerLeft(animal.getPosition());
            upperRightCorner = upperRightCorner.upperRight(animal.getPosition());
        }
        for (Grass grass : grassMap.values()){
            if(lowerLeftCorner == null){ // implikuje upperRightCorner równe null
                lowerLeftCorner = grass.getPosition();
                upperRightCorner = grass.getPosition();
            }
            lowerLeftCorner = lowerLeftCorner.lowerLeft(grass.getPosition());
            upperRightCorner = upperRightCorner.upperRight(grass.getPosition());
        }

        if(lowerLeftCorner == null){ // implikuje upperRightCorner równe null
            lowerLeftCorner = new Vector2d(0, 0);
            upperRightCorner = new Vector2d(0, 0);
        }

        boundsNeedUpdate = false;
    }

    @Override
    public boolean canMoveTo(Vector2d position){
        return !animalMap.containsKey(position);
    }

    @Override
    public WorldElement objectAt(Vector2d position){
        WorldElement animal = animalMap.get(position);
        if(animal == null){
            return grassMap.get(position);
        }
        return animal;
    }

    @Override
    public Vector2d getUpperRightCorner() {
        if(boundsNeedUpdate){
            updateBounds();
        }
        return upperRightCorner;
    }

    @Override
    public Vector2d getLowerLeftCorner() {
        if(boundsNeedUpdate){
            updateBounds();
        }
        return lowerLeftCorner;
    }

    @Override
    protected void onPlace() {
        super.onPlace();
        boundsNeedUpdate = true;
    }
    @Override
    protected void onPositionChanged() {
       super.onPositionChanged();
       boundsNeedUpdate = true;
    }

    @Override
    public List<WorldElement> getElements() {
        var elements = super.getElements();
        elements.addAll(grassMap.values());
        return elements;
    }
}
