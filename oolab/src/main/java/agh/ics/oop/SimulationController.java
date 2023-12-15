package agh.ics.oop;

import java.util.HashMap;
import java.util.Map;

public class SimulationController {
    public static class Starter {
        private final Thread thread;
        private final NotifyingSimulation simulation;

        private Starter(Thread thread, NotifyingSimulation simulation){
            this.thread = thread;
            this.simulation = simulation;
        }

        public void start(){
            thread.start();
        }

        public NotifyingSimulation getSimulation() {
            return simulation;
        }
    }
    Map<NotifyingSimulation, Thread> simulations = new HashMap<>();

    public synchronized Starter add(NotifyingSimulation simulation){
        Thread simulationThread = new Thread(simulation);
        simulations.put(simulation, simulationThread);
        return new Starter(simulationThread, simulation);
    }

    public synchronized void onSimulationEnd(NotifyingSimulation endedSimulation){
        simulations.remove(endedSimulation);
        //System.out.println("Simulation " + endedSimulation.getUUID() + " has ended.");
    }

    public synchronized void interrupt(NotifyingSimulation simulation){
        Thread toInterrupt = simulations.get(simulation);
        if(toInterrupt != null){
            toInterrupt.interrupt();
        }
    }
}
