package agh.ics.oop.model;

import agh.ics.oop.presenter.WorldElementBox;
import javafx.scene.image.Image;

import java.util.Objects;

public class Grass implements WorldElement {
    private final Vector2d position;
    private WorldElementBox box;
    private final static Image texture = new Image(Grass.class.getResourceAsStream("/textures/grass.png"));

    public Grass(Vector2d position){
        this.position = position;
        try {
            box = new WorldElementBox(this);
        }
        // JavaFX sie nie ładuje w testach
        catch (NoClassDefFoundError | ExceptionInInitializerError ignored){
            box = null;
        }
    }

    public Vector2d getPosition() {
        return position;
    }

    @Override
    public boolean isAt(Vector2d position) {
        return Objects.equals(position, this.position);
    }

    @Override
    public String toString() {
        return "*";
    }

    @Override
    public Image getTexture() {
        return texture;
    }

    @Override
    public String getLabel() {
        return "Grass";
    }

    @Override
    public WorldElementBox getBox() {
        return box;
    }
}
