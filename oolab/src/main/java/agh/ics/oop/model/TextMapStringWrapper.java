package agh.ics.oop.model;

import java.util.Objects;

public class TextMapStringWrapper {
    MapDirection orientation;
    int position;
    private final String string;

    public TextMapStringWrapper(String string, int position){
        this(string);
        this.position = position;
    }

    public TextMapStringWrapper(String string){
        this.string = string;
    }

    public boolean isAt(Integer position){
        return Objects.equals(this.position, position);
    }

    public void move(MoveDirection moveDirection, MoveValidator<Integer> moveValidator){
        if (orientation == MapDirection.WEST || orientation == MapDirection.EAST){
            switch (moveDirection){
                case FORWARD, BACKWARD -> {
                    int di = orientation.toUnitVector().getX();
                    if (moveDirection == MoveDirection.BACKWARD){
                        di = -di;
                    }
                    int newPosition = position + di;
                    if (moveValidator.canMoveTo(newPosition)){
                        position = newPosition;
                    }
                }
                case LEFT -> orientation = orientation.previous();
                case RIGHT -> orientation = orientation.next();
            }
        }
    }

    @Override
    public String toString() {
        return string;
    }

    public MapDirection getOrientation() {
        return orientation;
    }

    public Integer getPosition() {
        return position;
    }

    public boolean equals(Object other){
        if (other.getClass() == getClass() && other != this){
            TextMapStringWrapper otherWrapper = (TextMapStringWrapper) other;
            return Objects.equals(string, otherWrapper.string);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return string.hashCode();
    }
}
