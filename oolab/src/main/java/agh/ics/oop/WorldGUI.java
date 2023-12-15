package agh.ics.oop;

import javafx.application.Application;

public class WorldGUI {
    public static void main(String[] args) {
        // f b r l f f r r f f f f f f f f
        try{
            Application.launch(SimulationApp.class, args);
        }
        catch (RuntimeException exception){
            System.out.println(exception);
        }
    }
}
