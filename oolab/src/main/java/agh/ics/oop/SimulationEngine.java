package agh.ics.oop;

import agh.ics.oop.Simulation;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class SimulationEngine {
    private final List<Simulation> simulations;
    private List<Thread> threads = new ArrayList<>();
    private ExecutorService threadPool = Executors.newFixedThreadPool(4);
    public SimulationEngine(List<Simulation> simulations){
        this.simulations = simulations;
    }

    public void runSync(){
        for(Simulation sim : simulations){
            sim.run();
        }
    }

    public void runAsync() {
        threads.clear();
        for (Simulation sim : simulations) {
            Thread newThread = new Thread(sim);
            newThread.start();
            threads.add(newThread);
        }
    }

    public void runAsyncInThreadPool(){
        for (Simulation sim : simulations) {
            threadPool.submit(sim);
        }
    }

    public void awaitSimulationsEnd() throws InterruptedException{
        for (Thread thread : threads) {
            thread.join();
        }

        threadPool.shutdown();
        if(!threadPool.awaitTermination(10, TimeUnit.SECONDS)){
            threadPool.shutdownNow();
            // czeka na niedobitki z threadPool, średnio kilka milisekund
            while (!threadPool.isTerminated()) {}
        }
    }
}
