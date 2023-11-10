package agh.ics.oop.model;

import java.util.Objects;

public class Animal {
    public static final Vector2d MAP_LOWER_LEFT_CORNER = new Vector2d(0, 0);
    public static final Vector2d MAP_UPPER_RIGHT_CORNER = new Vector2d(4, 4);

    private MapDirection orientation = MapDirection.NORTH;
    private Vector2d position;

    public Animal(Vector2d position){
        this.position = position;
    }
    public Animal(){
        this(new Vector2d(2, 2));
    }

    public String toString(){
        return '{' + position.toString() + ", " + orientation.toString() + '}';
    }
    public boolean isAt(Vector2d position){
        return Objects.equals(this.position, position);
    }
    public void move(MoveDirection direction){
        switch (direction){
            case FORWARD, BACKWARD -> {
                Vector2d toAdd = orientation.toUnitVector();
                if (direction == MoveDirection.BACKWARD){
                    toAdd = toAdd.opposite();
                }

                Vector2d newPosition = position.add(toAdd);
                if(newPosition.precedes(MAP_UPPER_RIGHT_CORNER) && newPosition.follows(MAP_LOWER_LEFT_CORNER)){
                    position = newPosition;
                }
            }
            case LEFT -> orientation = orientation.previous();
            case RIGHT -> orientation = orientation.next();
        }
    }

    public MapDirection getOrientation() {
        return orientation;
    }

    public Vector2d getPosition() {
        return position;
    }
}
