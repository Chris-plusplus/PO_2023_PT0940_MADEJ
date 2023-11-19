package agh.ics.oop.model;

import agh.ics.oop.model.util.MapVisualizer;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class RectangularMap implements WorldMap<Animal, Vector2d> {
    private final Map<Vector2d, Animal> animals = new HashMap<>();
    private final int width;
    private final int height;
    private static final Vector2d LOWER_LEFT_CORNER = new Vector2d(0, 0);
    private final Vector2d upperRightCorner;
    private final MapVisualizer visualizer = new MapVisualizer(this);

    public RectangularMap(int width, int height){
        this.width = width;
        this.height = height;
        upperRightCorner = new Vector2d(LOWER_LEFT_CORNER.getX() + this.width - 1, LOWER_LEFT_CORNER.getY() + this.height - 1);
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        return objectAt(position) != null;
    }

    @Override
    public Animal objectAt(Vector2d position) {
        return animals.get(position);
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return position.precedes(upperRightCorner) && position.follows(LOWER_LEFT_CORNER) && !isOccupied(position);
    }

    @Override
    public boolean place(Animal obj) {
        if(canMoveTo(obj.getPosition())){
            animals.put(obj.getPosition(), obj);
            return true;
        }
        return false;
    }

    @Override
    public void move(Animal obj, MoveDirection moveDirection){
        if (animals.get(obj.getPosition()) == obj){
            Vector2d positionBefore = obj.getPosition();
            obj.move(moveDirection, this);
            if(!Objects.equals(positionBefore, obj.getPosition())){
                animals.remove(positionBefore);
                animals.put(obj.getPosition(), obj);
            }
        }
    }

    @Override
    public String toString() {
        return visualizer.draw(LOWER_LEFT_CORNER, upperRightCorner);
    }

    public Vector2d getUpperRightCorner() {
        return upperRightCorner;
    }
    // fajnie jak oba wierzchołki mają gettery
    public Vector2d getLowerLeftCorner() {
        return LOWER_LEFT_CORNER;
    }
}