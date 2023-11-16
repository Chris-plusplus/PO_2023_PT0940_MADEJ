package agh.ics.oop.model;

import java.util.ArrayList;
import java.util.List;

public class TextMap implements WorldMap<String, Integer>{
    private final List<String> container = new ArrayList<>();
    private final List<MapDirection> orientations = new ArrayList<>();

    @Override
    public boolean place(String string) {
        container.add(string);
        orientations.add(MapDirection.NORTH);
        return true;
    }

    @Override
    public boolean canMoveTo(Integer position) {
        return 0 <= position && position < container.size();
    }

    @Override
    public boolean isOccupied(Integer position) {
        return canMoveTo(position);
    }

    @Override
    public String objectAt(Integer position) {
        if(isOccupied(position)){
            return container.get(position);
        }
        else{
            return null;
        }
    }

    @Override
    public void move(String obj, MoveDirection direction) {
        int myIdx = container.indexOf(obj);
        if(myIdx != -1){
            switch (direction){
                case RIGHT -> orientations.set(myIdx, orientations.get(myIdx).next());
                case LEFT -> orientations.set(myIdx, orientations.get(myIdx).previous());
                case FORWARD, BACKWARD -> {
                    if(orientations.get(myIdx) == MapDirection.EAST || orientations.get(myIdx) == MapDirection.WEST){
                        int di = orientations.get(myIdx).toUnitVector().getX();
                        if (direction == MoveDirection.BACKWARD){
                            di = -di;
                        }

                        int newIdx = myIdx + di;
                        if(canMoveTo(newIdx)){
                            MapDirection temp = orientations.get(newIdx);
                            orientations.set(newIdx, orientations.get(myIdx));
                            orientations.set(myIdx, temp);
                            container.set(myIdx, container.get(newIdx));
                            container.set(newIdx, obj);
                        }
                    }
                }
            }
        }
    }

    @Override
    public String toString() {
        String toReturn = "";
        for(int i = 0; i != container.size(); ++i){
            toReturn += i + ": (" + orientations.get(i).shortString() + ") \"" + container.get(i) + "\"" + (i != container.size() - 1 ? "\n" : "");
        }
        return toReturn;
    }
}
