package agh.ics.oop;

import agh.ics.oop.model.MoveDirection;
import agh.ics.oop.model.Vector2d;
import agh.ics.oop.model.WorldMap;

import java.util.List;

    public class NotifyingSimulation extends Simulation {
    private final SimulationController controller;
    public NotifyingSimulation(List<Vector2d> startingPositions, List<MoveDirection> moves, WorldMap map, SimulationController controller){
        super(startingPositions, moves, map);
        if(controller == null){
            throw new IllegalArgumentException("SimulationController may not be null");
        }
        this.controller = controller;
        this.controller.add(this);
    }

    @Override
    public void run() {
        super.run();
        controller.onSimulationEnd(this);
    }
}
