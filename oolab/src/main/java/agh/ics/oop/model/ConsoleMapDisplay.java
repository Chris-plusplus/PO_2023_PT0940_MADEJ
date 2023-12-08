package agh.ics.oop.model;

public class ConsoleMapDisplay implements MapChangeListener{
    private int updateCounter = 1;
    @Override
    public synchronized void mapChanged(WorldMap worldMap, String message) {
        System.out.println("UUID: " + worldMap.getID());
        System.out.println(message);
        System.out.println(worldMap);
        System.out.println("^ Update no. " + updateCounter + " ^");
        System.out.println(); // oddzielenie, wyglądało jakby dotyczyło następnej aktualizacji
        ++updateCounter;
    }
}
