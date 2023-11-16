package agh.ics.oop.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TextMap implements WorldMap<String, Integer>{
    private final List<TextMapStringWrapper> container = new ArrayList<>();

    @Override
    public boolean place(String string) {
        container.add(new TextMapStringWrapper(string, container.size()));
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
        try{
            return container.get(position).toString();
        }
        catch (Exception e){
            return null;
        }
    }

    @Override
    public void move(String obj, MoveDirection direction) {
        int myIdx = container.indexOf(new TextMapStringWrapper(obj));
        int newIdx = myIdx;
        if (direction == MoveDirection.FORWARD){
            newIdx += 1;
        }
        else if (direction == MoveDirection.BACKWARD){
            newIdx -= 1;
        }

        if (canMoveTo(newIdx)){
            TextMapStringWrapper temp = container.get(myIdx);
            container.set(myIdx, container.get(newIdx));
            container.set(newIdx, temp);
        }
    }

    @Override
    public String toString() {
        String toReturn = "";
        for(int i = 0; i != container.size(); ++i){
            toReturn += i + ": \"" + container.get(i) + "\"" + (i != container.size() - 1 ? "\n" : "");
        }
        return toReturn;
    }
}
