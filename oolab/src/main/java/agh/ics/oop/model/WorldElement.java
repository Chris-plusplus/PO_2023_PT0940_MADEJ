package agh.ics.oop.model;

import javafx.scene.image.Image;

public interface WorldElement {
    public Vector2d getPosition();
    public boolean isAt(Vector2d position);
    public Image getTexture();
    public String getLabel();
    public WorldElementBox getBox();
}
