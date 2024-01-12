package agh.ics.oop.model;

import agh.ics.oop.presenter.WorldElementBox;
import javafx.scene.image.Image;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Animal implements WorldElement {
    private MapDirection orientation = MapDirection.NORTH;
    private Vector2d position;
    private WorldElementBox box;
    private int energy;
    private List<Integer> genes;

    private static final Map<MapDirection, Image> textures = Map.of(
            MapDirection.NORTH, new Image(Animal.class.getResourceAsStream("/textures/up.png")),
            MapDirection.SOUTH, new Image(Animal.class.getResourceAsStream("/textures/down.png")),
            MapDirection.EAST, new Image(Animal.class.getResourceAsStream("/textures/right.png")),
            MapDirection.WEST, new Image(Animal.class.getResourceAsStream("/textures/left.png"))
    );

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
        return textures.get(this.orientation);
    }

    @Override
    public String getLabel() {
        return "Z " + position.toString();
    }

    @Override
    public WorldElementBox getBox() {
        return box;
    }

    public int getEnergy() {
        return energy;
    }

    public List<Integer> getGenes() {
        return Collections.unmodifiableList(genes);
    }
}
