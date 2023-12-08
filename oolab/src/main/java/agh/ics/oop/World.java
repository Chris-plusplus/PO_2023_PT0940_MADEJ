package agh.ics.oop;

import agh.ics.oop.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class World {
    public static void main(String[] args){
        try {
            List<MoveDirection> directions = OptionsParser.parse(args);
            List<Vector2d> positions = List.of(
                    new Vector2d(2, 2),
                    new Vector2d(3, 4)
            );

            ConsoleMapDisplay consoleMapDisplay = new ConsoleMapDisplay();

            List<Simulation> simulations = new ArrayList<>();
            Random random = new Random();
            if(random.nextBoolean()){ // generujemy trawki
                for(int i = 0; i != 10000; ++i){ // jeśli nie przekroczy 10s, na koniec wypisze 'Update no. 160000', 16 ruchów * n
                    AbstractWorldMap newMap = new GrassField(10);
                    newMap.addListener(consoleMapDisplay);
                    simulations.add(new Simulation(positions, directions, newMap));
                }
            }
            else{ // kwadraty
                for(int i = 0; i != 10000; ++i){ // jeśli nie przekroczy 10s, na koniec wypisze 'Update no. 120000', 12 ruchów * n
                    AbstractWorldMap newMap = new RectangularMap(5, 5);
                    newMap.addListener(consoleMapDisplay);
                    simulations.add(new Simulation(positions, directions, newMap));
                }
            }

            SimulationEngine simEngine = new SimulationEngine(simulations);
            simEngine.runAsyncInThreadPool();
            simEngine.awaitSimulationsEnd();

            System.out.println("System has terminated.");
        }
        catch (IllegalArgumentException | InterruptedException exception){
            System.out.println(exception);
        }
        // test ręczny wykazał poprawność
    }
    public static void run(MoveDirection[] commands){
        for(int i = 0; i != commands.length; ++i){
            switch (commands[i]){
                case FORWARD -> System.out.print("zwierzak idzie do przodu\n");
                case BACKWARD -> System.out.print("zwierzak idzie do tylu\n");
                case LEFT -> System.out.print("zwierzak skreca w lewo\n");
                case RIGHT -> System.out.print("zwierzak skreca w prawo\n");
            }
        }
    }
}
