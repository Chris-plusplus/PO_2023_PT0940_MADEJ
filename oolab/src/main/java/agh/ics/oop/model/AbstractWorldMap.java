package agh.ics.oop.model;

import agh.ics.oop.model.util.MapVisualizer;

import java.util.*;

public abstract class AbstractWorldMap implements WorldMap {
    protected final Map<Vector2d, Animal> animalMap = new HashMap<>();
    protected final MapVisualizer mapVisualizer = new MapVisualizer(this);
    protected String cachedDrawing = "";

    @Override
    public WorldElement objectAt(Vector2d position){
        return animalMap.get(position);
    }
    // MapVisualizer (linia 78) dla GrassField wymaga aby zwracało true jeśli jest tam albo trawa albo zwierzak
    @Override
    public boolean isOccupied(Vector2d position) {
        return objectAt(position) != null;
    }
    @Override
    public boolean canMoveTo(Vector2d position){
        return !isOccupied(position);
    }

    @Override
    public boolean place(Animal animal){
        if(canMoveTo(animal.getPosition())){
            animalMap.put(animal.getPosition(), animal);
            onPlace(animal);
            return true;
        }
        return false;
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

                onPositionChanged(animal, oldPosition);
            }
            else if(!Objects.equals(oldOrientation, animal.getOrientation())) {
                onOrientationChanged(animal, oldOrientation);
            }
        }
    }

    public abstract Vector2d getLowerLeftCorner();
    public abstract Vector2d getUpperRightCorner();

    // event handlery, domyślnie puste, nie trzeba nadpisywac place() i move()
    // wywołują się tylko gdy zdarzenie faktycznie zajdzie
    protected void onPlace(Animal placed){}
    protected void onPositionChanged(Animal moved, Vector2d oldPosition){}
    protected void onOrientationChanged(Animal moved, MapDirection oldOrientation){}

    @Override
    public String toString(){
        if(cachedDrawing.isEmpty()){
            cachedDrawing = mapVisualizer.draw(getLowerLeftCorner(), getUpperRightCorner());
        }
        return cachedDrawing;
    }

    @Override
    public List<WorldElement> getElements() {
         return Collections.<WorldElement>unmodifiableList(animalMap.values().stream().toList());
    }
}
