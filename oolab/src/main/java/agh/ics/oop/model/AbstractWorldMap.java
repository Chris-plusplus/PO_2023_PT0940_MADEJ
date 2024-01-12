package agh.ics.oop.model;

import agh.ics.oop.model.util.Boundary;
import agh.ics.oop.model.util.MapVisualizer;

import java.util.*;
import java.util.stream.Collectors;

public abstract class AbstractWorldMap implements WorldMap {
    protected final Map<Vector2d, Animal> animalMap = new HashMap<>();
    private final MapVisualizer mapVisualizer = new MapVisualizer(this);
    protected String cachedDrawing = "";
    protected List<MapChangeListener> mapChangeListeners = new ArrayList<>();
    public static final String ANIMAL_MOVED_PREFIX = "Animal moved";
    public static final String ANIMAL_ROTATED_PREFIX = "Animal rotated";
    public static final String ANIMAL_PLACED_PREFIX = "Animal placed";
    // nie trzeba dodawać do konstruktora
    private final UUID uuid = UUID.randomUUID();

    @Override
    public Optional<WorldElement> objectAt(Vector2d position){
        return Optional.ofNullable(animalMap.get(position));
    }
    // MapVisualizer (linia 78) dla GrassField wymaga aby zwracało true jeśli jest tam albo trawa albo zwierzak
    @Override
    public boolean isOccupied(Vector2d position) {
        return objectAt(position).isPresent();
    }
    @Override
    public boolean canMoveTo(Vector2d position){
        return !isOccupied(position);
    }

    @Override
    public void place(Animal animal) throws PositionAlreadyOccupiedException{
        if(canMoveTo(animal.getPosition())){
            animalMap.put(animal.getPosition(), animal);
            mapChanged(ANIMAL_PLACED_PREFIX + " at " + animal.getPosition());
        }
        else {
            throw new PositionAlreadyOccupiedException(animal.getPosition());
        }
    }
    @Override
    public void move(Animal animal, MoveDirection moveDirection) {
        if (animalMap.get(animal.getPosition()) == animal){
            Vector2d oldPosition = animal.getPosition();
            MapDirection oldOrientation = animal.getOrientation();
            animal.move(moveDirection, this);
            if(!Objects.equals(oldPosition, animal.getPosition())){
                animalMap.remove(oldPosition);
                animalMap.put(animal.getPosition(), animal);

                mapChanged(ANIMAL_MOVED_PREFIX + " " + moveDirection + "; " + oldPosition + " -> " + animal.getPosition());
            }
            else if(!Objects.equals(oldOrientation, animal.getOrientation())) {
                mapChanged(ANIMAL_ROTATED_PREFIX + " " + moveDirection + "; " + oldOrientation + " -> " + animal.getOrientation());
            }
        }
    }

    public void addListener(MapChangeListener listener){
        mapChangeListeners.add(listener);
    }
    public void removeListener(MapChangeListener listener){
        mapChangeListeners.remove(listener);
    }

    protected void mapChanged(String message){
        cachedDrawing = "";

        for(MapChangeListener listener : mapChangeListeners){
            listener.mapChanged(this, message);
        }
    }

    @Override
    public String toString(){
        if(cachedDrawing.isEmpty()){
            Boundary bounds = getCurrentBounds();
            cachedDrawing = mapVisualizer.draw(bounds.lowerLeftCorner(), bounds.upperRightCorner());
        }
        return cachedDrawing;
    }

    @Override
    public UUID getID() {
        return uuid;
    }

    @Override
    public List<WorldElement> getElements() {
        return new ArrayList<>(animalMap.values());
    }

    @Override
    public List<Animal> getOrderedAnimals() {
        return animalMap
                .values()
                .stream()
                .sorted(AnimalSortingComparator::compare)
                .toList();
    }
}
