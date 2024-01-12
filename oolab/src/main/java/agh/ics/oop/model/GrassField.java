package agh.ics.oop.model;

import agh.ics.oop.model.util.Boundary;
import agh.ics.oop.model.util.RandomPositionGenerator;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GrassField extends AbstractWorldMap {
    private final Map<Vector2d, Grass> grassMap = new HashMap<>();
    private Boundary boundary;
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
        Vector2d lowerLeftCorner = null;
        Vector2d upperRightCorner = null;

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

        boundary = new Boundary(lowerLeftCorner, upperRightCorner);

        boundsNeedUpdate = false;
    }

    @Override
    public boolean canMoveTo(Vector2d position){
        return !animalMap.containsKey(position);
    }

    @Override
    public Optional<WorldElement> objectAt(Vector2d position){
        WorldElement animal = animalMap.get(position);
        if(animal == null){
            return Optional.ofNullable(grassMap.get(position));
        }
        return Optional.of(animal);
    }

    @Override
    protected void mapChanged(String message){
        if(message.startsWith(ANIMAL_PLACED_PREFIX) || message.startsWith(ANIMAL_MOVED_PREFIX)){
            boundsNeedUpdate = true;
        }

        super.mapChanged(message);
    }

    @Override
    public List<WorldElement> getElements() {
        return Stream
                .concat(
                        super.getElements().stream(),
                        grassMap.values().stream()
                )
                .toList();
    }

    @Override
    public Boundary getCurrentBounds() {
        if(boundsNeedUpdate){
            updateBounds();
        }
        return boundary;
    }
}
