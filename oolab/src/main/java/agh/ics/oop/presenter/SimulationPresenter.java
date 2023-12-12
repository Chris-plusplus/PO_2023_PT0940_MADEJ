package agh.ics.oop.presenter;

import agh.ics.oop.OptionsParser;
import agh.ics.oop.Simulation;
import agh.ics.oop.SimulationEngine;
import agh.ics.oop.WorldGUI;
import agh.ics.oop.model.*;
import agh.ics.oop.model.util.Boundary;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Font;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SimulationPresenter implements MapChangeListener {
    @FXML
    private GridPane mapGrid;
    //private Label infoLabel;
    @FXML
    private Label moveDescription;
    @FXML
    private TextField movesInput;
    @FXML
    private Button startButton;
    private WorldMap worldMap;
    private final static double CELL_SIZE = 35;

    public void setWorldMap(WorldMap worldMap) {
        this.worldMap = worldMap;
    }

    @Override
    public void mapChanged(WorldMap worldMap, String message) {
        Platform.runLater(() -> {
            moveDescription.setText(message);
            drawMap(worldMap);
        });
    }

    private void clearGrid() {
        mapGrid.getChildren().retainAll(mapGrid.getChildren().get(0)); // hack to retain visible grid lines
        mapGrid.getColumnConstraints().clear();
        mapGrid.getRowConstraints().clear();
    }

    private void drawCoords(Boundary boundary){
        var axisLabel = new Label("Y\\X");
        axisLabel.getStyleClass().add("axis");
        GridPane.setHalignment(axisLabel, HPos.CENTER);
        mapGrid.getColumnConstraints().add(new ColumnConstraints(CELL_SIZE));
        mapGrid.getRowConstraints().add(new RowConstraints(CELL_SIZE));

        mapGrid.add(axisLabel, 0, 0);

        for(int x = boundary.lowerLeftCorner().getX(); x <= boundary.upperRightCorner().getX(); ++x){
            var xLabel = new Label("%d".formatted(x));
            GridPane.setHalignment(xLabel, HPos.CENTER);
            mapGrid.getColumnConstraints().add(new ColumnConstraints(CELL_SIZE));

            mapGrid.add(xLabel, x - boundary.lowerLeftCorner().getX() + 1, 0);
        }
        for(int y = boundary.upperRightCorner().getY(); y >= boundary.lowerLeftCorner().getY(); --y){
            var yLabel = new Label("%d".formatted(y));
            GridPane.setHalignment(yLabel, HPos.CENTER);
            mapGrid.getRowConstraints().add(new RowConstraints(CELL_SIZE));

            mapGrid.add(yLabel, 0, boundary.upperRightCorner().getY() - y + 1);
        }
    }

    private void drawElements(WorldMap worldMap, Boundary boundary){
        for (WorldElement worldElement : worldMap.getElements().
                // wybiera po jednym obiekcie na pozycję, zwierzak ma pierwszeństwo przed trawą
                stream().
                collect(Collectors.toMap(
                        WorldElement::getPosition,
                        Function.identity(),
                        (worldElement1, worldElement2) -> (worldElement1.toString().equals("*") ? worldElement2 : worldElement1)
                ))
                .values()){
            int x = worldElement.getPosition().getX() - boundary.lowerLeftCorner().getX() + 1;
            int y = boundary.upperRightCorner().getY() - worldElement.getPosition().getY() + 1;

            var elementLabel = new Label(worldElement.toString());
            GridPane.setHalignment(elementLabel, HPos.CENTER);
            elementLabel.getStyleClass().addAll(
                    "worldElement",
                    worldElement.toString().equals("*") ? "grass" : "animal"
            );

            mapGrid.add(elementLabel, x, y);
        }
    }

    public void drawMap(WorldMap worldMap){
        Boundary boundary = worldMap.getCurrentBounds();

        clearGrid();
        drawCoords(boundary);
        drawElements(worldMap, boundary);
    }

    public void onSimulationStartClicked(){
        var args = List.of(movesInput.getText().split(" "));
        List<MoveDirection> directions;
        try{
            directions = OptionsParser.parse(args);
        }
        catch (IllegalArgumentException exception){
            moveDescription.getStyleClass().add("errorText");
            moveDescription.setText(exception.getMessage());
            return;
        }

        moveDescription.getStyleClass().remove("errorText");

        GrassField grassField = new GrassField(10);
        System.out.println(grassField.getElements().size());
        List<Vector2d> positions = List.of(
                new Vector2d(2, 2),
                new Vector2d(3, 4)
        );
        grassField.addListener(this);

        SimulationEngine simulationEngine = new SimulationEngine(List.of(new Simulation(positions, directions, grassField)));

        simulationEngine.runAsync();
    }
}
