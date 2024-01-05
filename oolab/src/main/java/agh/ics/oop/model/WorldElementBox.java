package agh.ics.oop.model;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class WorldElementBox {
    private final VBox vBox;
    private final Label label;
    private final ImageView imageView;
    private final WorldElement worldElement;

    public WorldElementBox(WorldElement worldElement){
        this.worldElement = worldElement;

        imageView = new ImageView(worldElement.getTexture());
        imageView.getStyleClass().addAll("centered");
        imageView.setFitHeight(20);
        imageView.setFitWidth(20);

        label = new Label(worldElement.getLabel());
        label.getStyleClass().addAll("centered", "worldElementBoxFont");

        vBox = new VBox(imageView, label);
        vBox.getStyleClass().add("centered");
    }

    public VBox getvBox() {
        return vBox;
    }

    public void updateVBox(){
        Platform.runLater(() -> {
            label.setText(worldElement.getLabel());
            imageView.setImage(worldElement.getTexture());
        });
    }
}
