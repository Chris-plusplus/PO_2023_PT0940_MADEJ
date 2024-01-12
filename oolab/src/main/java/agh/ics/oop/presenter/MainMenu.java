package agh.ics.oop.presenter;

import agh.ics.oop.NotifyingSimulation;
import agh.ics.oop.OptionsParser;
import agh.ics.oop.SimulationController;
import agh.ics.oop.model.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainMenu {
    @FXML
    private Label errorDescription;
    @FXML
    private TextField movesInput;
    @FXML
    private Button startButton;

    private final SimulationController simulationController = new SimulationController();
    private final Map<Stage, NotifyingSimulation> simulations = new HashMap<>();

    public void onStartButtonClicked() throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("simulation.fxml"));
        BorderPane viewRoot = null;
        viewRoot = loader.load();

        SimulationPresenter presenter = loader.getController();

        SimulationController.Starter starter;
        try{
            starter = createSimulation(presenter);
        }
        catch (IllegalArgumentException exception){
            errorDescription.getStyleClass().add("errorText");
            errorDescription.setText(exception.getMessage());
            return;
        }

        errorDescription.setText("");

        Stage newStage = new Stage();
        configureNewStage(newStage, viewRoot);
        newStage.setOnCloseRequest(event -> {
            event.consume();
            simulationController.interrupt(starter.getSimulation());
            onStageClose(newStage);
            newStage.close();
            //System.out.println("Window of simulataion " + starter.getSimulation().getUUID() + " was closed.");
        });

        simulations.put(newStage, starter.getSimulation());

        newStage.show();

        starter.start();
    }

    private SimulationController.Starter createSimulation(MapChangeListener listener){
        var args = List.of(movesInput.getText().split(" "));
        List<MoveDirection> directions = OptionsParser.parse(args);

        GrassField grassField = new GrassField(10);
        List<Vector2d> positions = List.of(
                new Vector2d(2, 2),
                new Vector2d(3, 4)
        );
        grassField.addListener(listener);
        grassField.addListener(((worldMap, message) -> {
            SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
            Date date = new Date();
            System.out.println(formatter.format(date) + ' ' + message);
        }));
        grassField.addListener(new FileMapDisplay());

        NotifyingSimulation newSimulation = new NotifyingSimulation(positions, directions, grassField, simulationController);

        return simulationController.add(newSimulation);
    }

    private void configureNewStage(Stage newStage, BorderPane viewRoot) {
        var scene = new Scene(viewRoot);
        newStage.setScene(scene);
        newStage.setTitle("Simulation app main menu");
        newStage.minWidthProperty().bind(viewRoot.minWidthProperty());
        newStage.minHeightProperty().bind(viewRoot.minHeightProperty());
    }

    public void onStageClose(Stage closed){
        simulations.remove(closed);
    }
}
