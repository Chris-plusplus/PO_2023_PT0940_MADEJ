package agh.ics.oop.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TextMap implements GenericWorldMap<String, Integer>{
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
    public Optional<String> objectAt(Integer position) {
        if(isOccupied(position)){
            return Optional.of(container.get(position));
        }
        else{
            return Optional.empty();
        }
    }

    @Override
    public void move(String obj, MoveDirection direction) {
        int oldIndex = container.indexOf(obj);
        if(oldIndex != -1){
            switch (direction){
                case RIGHT -> orientations.set(oldIndex, orientations.get(oldIndex).next());
                case LEFT -> orientations.set(oldIndex, orientations.get(oldIndex).previous());
                case FORWARD, BACKWARD -> {
                    if(orientations.get(oldIndex) == MapDirection.EAST || orientations.get(oldIndex) == MapDirection.WEST){
                        int deltaIndex = orientations.get(oldIndex).toUnitVector().getX();
                        if (direction == MoveDirection.BACKWARD){
                            deltaIndex = -deltaIndex;
                        }

                        int newIdx = oldIndex + deltaIndex;
                        if(canMoveTo(newIdx)){
                            MapDirection temp = orientations.get(newIdx);
                            orientations.set(newIdx, orientations.get(oldIndex));
                            orientations.set(oldIndex, temp);
                            container.set(oldIndex, container.get(newIdx));
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
