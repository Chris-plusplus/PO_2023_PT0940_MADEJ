package agh.ics.oop;

import java.util.HashMap;
import java.util.Map;

public class SimulationController {
    public static class Starter {
        private final Thread thread;
        private final ControllableSimulation simulation;

        private Starter(Thread thread, ControllableSimulation simulation){
            this.thread = thread;
            this.simulation = simulation;
        }

        public void start(){
            thread.start();
        }

        public ControllableSimulation getSimulation() {
            return simulation;
        }
    }
    Map<ControllableSimulation, Thread> simulations = new HashMap<>();

    public synchronized Starter add(ControllableSimulation simulation){
        Thread simulationThread = new Thread(simulation);
        simulations.put(simulation, simulationThread);
        return new Starter(simulationThread, simulation);
    }

    public synchronized void onSimulationEnd(ControllableSimulation endedSimulation){
        simulations.remove(endedSimulation);
        //System.out.println("Simulation " + endedSimulation.getUUID() + " has ended.");
    }

    public synchronized void interrupt(ControllableSimulation simulation){
        Thread toInterrupt = simulations.get(simulation);
        if(toInterrupt != null){
            toInterrupt.interrupt();
        }
    }
}
