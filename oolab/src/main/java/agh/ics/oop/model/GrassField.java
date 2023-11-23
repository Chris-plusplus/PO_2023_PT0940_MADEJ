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
        int xMax = 0;
        int xMin = Integer.MAX_VALUE;
        int yMax = 0;
        int yMin = Integer.MAX_VALUE;

        for (Animal animal : animalMap.values()){
            xMax = Math.max(xMax, animal.getPosition().getX());
            yMax = Math.max(yMax, animal.getPosition().getY());
            xMin = Math.min(xMin, animal.getPosition().getX());
            yMin = Math.min(yMin, animal.getPosition().getY());
        }
        for (Grass grass : grassMap.values()){
            xMax = Math.max(xMax, grass.getPosition().getX());
            yMax = Math.max(yMax, grass.getPosition().getY());
            xMin = Math.min(xMin, grass.getPosition().getX());
            yMin = Math.min(yMin, grass.getPosition().getY());
        }

        lowerLeftCorner = new Vector2d(xMin, yMin);
        upperRightCorner = new Vector2d(xMax, yMax);
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
        return Collections.<WorldElement>unmodifiableList(Stream.concat(super.getElements().stream(), grassMap.values().stream()).toList());
    }
}
