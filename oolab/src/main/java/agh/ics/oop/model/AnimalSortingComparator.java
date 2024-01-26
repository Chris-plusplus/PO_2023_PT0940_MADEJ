package agh.ics.oop.model;

import java.util.Comparator;

public class AnimalSortingComparator { // czemu sorting?
    public static int compare(Animal animal1, Animal animal2) {
        var pos1 = animal1.getPosition();
        var pos2 = animal2.getPosition();
        if (pos1.getX() < pos2.getX() || (pos1.getX() == pos2.getX() && pos1.getY() < pos2.getY())){
            return -1;
        }
        else if (pos1.equals(pos2)) {
            return 0;
        }
        else {
            return 1;
        }
    }
}
