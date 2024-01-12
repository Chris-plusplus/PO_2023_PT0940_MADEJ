package agh.ics.oop.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

public class AbstractMapTest {
    private static boolean isSorted(List<Animal> animals){
        AnimalSortingComparator animalSortingComparator = new AnimalSortingComparator();
        for (int i = 1; i != animals.size(); ++i){
            if(animalSortingComparator.compare(animals.get(i - 1), animals.get(i)) >= 0){
                return false;
            }
        }
        return true;
    }
    @Test
    public void getOrderedAnimalsTest(){
        for(int i = 1; i <= 10; ++i){
            GrassField map = new GrassField(100 * i);

            Assertions.assertEquals(0, map.getOrderedAnimals().size());

            try{
                for(WorldElement grass : map.getElements()) {
                    map.place(new Animal(grass.getPosition()));
                }
            }
            // można zignorować, GrassField gwarantuje unikalność pozycji
            catch (PositionAlreadyOccupiedException ignored){}

            Assertions.assertTrue(isSorted(map.getOrderedAnimals()));
        }
    }
}
