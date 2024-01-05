package agh.ics.oop.model;

import java.util.Comparator;

public class AnimalSortingComparator implements Comparator<Animal> {
    @Override
    public int compare(Animal animal1, Animal animal2) {
        if (animal1 == animal2){
            return 0;
        }
        var pos1 = animal1.getPosition();
        var pos2 = animal2.getPosition();
        return pos1.getX() < pos2.getX() || (pos1.getX() == pos2.getX() && pos1.getY() < pos2.getY()) ? -1 : 1;
    }
}
