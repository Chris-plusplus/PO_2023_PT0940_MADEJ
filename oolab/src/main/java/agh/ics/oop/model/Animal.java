package agh.ics.oop.model;

import javafx.scene.image.Image;

import java.util.Comparator;
import java.util.Objects;

public class Animal implements WorldElement {
    private MapDirection orientation = MapDirection.NORTH;
    private Vector2d position;
    private WorldElementBox box;

    private static final Image textureUp = new Image(Animal.class.getResourceAsStream("/textures/up.png"));
    private static final Image textureDown = new Image(Animal.class.getResourceAsStream("/textures/down.png"));
    private static final Image textureLeft = new Image(Animal.class.getResourceAsStream("/textures/left.png"));
    private static final Image textureRight = new Image(Animal.class.getResourceAsStream("/textures/right.png"));

    public Animal(Vector2d position){
        this.position = position;
        try {
            box = new WorldElementBox(this);
        }
        // JavaFX sie nie ładuje w testach
        catch (NoClassDefFoundError ignored){
            box = null;
        }
    }
    public Animal(){
        this(new Vector2d(2, 2));
    }

    @Override
    public String toString(){
        return orientation.shortString();
    }
    @Override
    public boolean isAt(Vector2d position){
        return Objects.equals(this.position, position);
    }
    public void move(MoveDirection direction, MoveValidator<Vector2d> moveValidator){
        switch (direction){
            case FORWARD, BACKWARD -> {
                Vector2d toAdd = orientation.toUnitVector();
                if (direction == MoveDirection.BACKWARD){
                    toAdd = toAdd.opposite();
                }

                Vector2d newPosition = position.add(toAdd);
                if(moveValidator.canMoveTo(newPosition)){
                    position = newPosition;
                }
            }
            case LEFT -> orientation = orientation.previous();
            case RIGHT -> orientation = orientation.next();
        }

        if(box != null){
            box.updateVBox();
        }
    }

    public MapDirection getOrientation() {
        return orientation;
    }

    @Override
    public Vector2d getPosition() {
        return position;
    }

    @Override
    public Image getTexture() {
        return switch (orientation){
            case NORTH -> textureUp;
            case EAST -> textureRight;
            case SOUTH -> textureDown;
            case WEST -> textureLeft;
        };
    }

    @Override
    public String getLabel() {
        return "Z " + position.toString();
    }

    @Override
    public WorldElementBox getBox() {
        return box;
    }
}
