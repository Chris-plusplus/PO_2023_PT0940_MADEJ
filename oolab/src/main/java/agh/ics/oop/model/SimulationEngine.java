package agh.ics.oop.model;

import agh.ics.oop.Simulation;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class SimulationEngine {
    private final List<Simulation> simulations;
    private List<Thread> threads = null;
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
        List<Thread> simThreads = new ArrayList<>();
        for (Simulation sim : simulations) {
            Thread newThread = new Thread(sim);
            newThread.start();
            simThreads.add(newThread);
        }
        this.threads = simThreads;
    }

    public void runAsyncInThreadPool(){
        for (Simulation sim : simulations) {
            threadPool.submit(sim);
        }
    }

    public void awaitSimulationsEnd(){
        if(threads != null) {
            for (Thread thread : threads) {
                try {
                    thread.join();
                }
                // można zignorować, raczej nic nie przerwie głównego wątku
                catch (InterruptedException ignored) {}
            }

            // zapobiega powtórzeniu ifa gdy nie korzystano z runAsync(), odnowi się przy wywołaniu runAsync()
            threads = null;
        }

        threadPool.shutdown();
        try{
            threadPool.awaitTermination(10, TimeUnit.SECONDS);
            threadPool.shutdownNow();

            // czeka na niedobitki z threadPool, średnio kilka milisekund
            while (!threadPool.isTerminated()) {}
        }
        // można zignorować, raczej nic nie przerwie głównego wątku
        catch (InterruptedException ignored){}
    }
}
