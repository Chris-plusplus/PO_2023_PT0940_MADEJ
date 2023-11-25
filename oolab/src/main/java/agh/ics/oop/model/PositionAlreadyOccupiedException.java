package agh.ics.oop.model;

public class PositionAlreadyOccupiedException extends Exception{
    public PositionAlreadyOccupiedException(Vector2d pos){
        super("Position " + pos + " is already occupied");
    }
}
