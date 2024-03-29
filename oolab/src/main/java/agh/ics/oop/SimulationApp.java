package agh.ics.oop;

import agh.ics.oop.presenter.MainMenu;
import agh.ics.oop.presenter.SimulationPresenter;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class SimulationApp extends Application {
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("mainMenu.fxml"));
        BorderPane viewRoot = null;
        viewRoot = loader.load();
        MainMenu mainMenu = loader.getController();

        configureMainMenu(primaryStage, viewRoot);
        primaryStage.setOnCloseRequest(event -> {
            event.consume();
            primaryStage.close();
            //System.out.println("Main menu was closed.");
        });

        primaryStage.show();
    }

    private void configureMainMenu(Stage primaryStage, BorderPane viewRoot) {
        var scene = new Scene(viewRoot);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Simulation app main menu");
        primaryStage.minWidthProperty().bind(viewRoot.minWidthProperty());
        primaryStage.minHeightProperty().bind(viewRoot.minHeightProperty());

        SimulationPresenter.readCellSize(viewRoot);
    }
}
