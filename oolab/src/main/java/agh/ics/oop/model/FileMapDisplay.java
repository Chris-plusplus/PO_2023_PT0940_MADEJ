package agh.ics.oop.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

public class FileMapDisplay implements MapChangeListener {
    @Override
    public synchronized void mapChanged(WorldMap worldMap, String message) {
        File file = new File("map_" + worldMap.getID() + ".log");
        try{
            file.createNewFile();
        }
        catch (IOException exception){
            System.out.println("Error creating file for WorldMap: " + worldMap.getID() + "\n\t" + exception);
            return;
        }

        try(FileWriter writer = new FileWriter(file, true)){
            writer.write(message + '\n');
            writer.write(worldMap.toString() + '\n');
        }
        catch (IOException exception){
            System.out.println("Error writing WorldMap: " + worldMap.getID() + "\n\t" + exception);
        }
    }
}
