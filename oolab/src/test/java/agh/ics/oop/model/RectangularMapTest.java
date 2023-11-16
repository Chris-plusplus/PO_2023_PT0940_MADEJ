package agh.ics.oop.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class RectangularMapTest {
    @Test
    public void placeTest(){
        RectangularMap map = new RectangularMap(5, 5);
        Animal animal1 = new Animal(new Vector2d(5, 6)); // poza mapą 1
        Animal animal2 = new Animal(new Vector2d(10, -5)); // poza mapą 2
        Animal animal3 = new Animal(new Vector2d(420, -6)); // poza mapą 3
        Animal animal4 = new Animal(new Vector2d(2, 2)); // środek mapy
        Animal animal5 = new Animal(new Vector2d(2, 2)); // środek mapy x2
        Animal animal6 = new Animal(new Vector2d(0, 0)); // róg mapy

        Assertions.assertFalse(map.place(animal1));
        Assertions.assertFalse(map.place(animal2));
        Assertions.assertFalse(map.place(animal3));
        Assertions.assertTrue(map.place(animal4));
        Assertions.assertFalse(map.place(animal5));
        Assertions.assertTrue(map.place(animal6));

        int animalCounter = 0;
        for(int x = map.getLowerLeftCorner().getX(); x <= map.getUpperRightCorner().getX(); ++x){
            for(int y = map.getLowerLeftCorner().getY(); y <= map.getUpperRightCorner().getY(); ++y){
                if (map.objectAt(new Vector2d(x, y)) != null){
                    ++animalCounter;
                }
            }
        }
        Assertions.assertEquals(2, animalCounter);
        Assertions.assertEquals(animal4, map.objectAt(new Vector2d(2, 2)));
        Assertions.assertEquals(animal6, map.objectAt(new Vector2d(0, 0)));
    }

    @Test
    void occupationTest(){
        RectangularMap map = new RectangularMap(5, 5);
        Animal animal1 = new Animal(new Vector2d(5, 6)); // poza mapą 1
        Animal animal2 = new Animal(new Vector2d(10, -5)); // poza mapą 2
        Animal animal3 = new Animal(new Vector2d(1, 2));
        Animal animal4 = new Animal(new Vector2d(2, 1));
        Animal animal5 = new Animal(new Vector2d(4, 0)); // róg mapy
        Animal animal6 = new Animal(new Vector2d(4, 0)); // róg mapy x2

        boolean anyOccupied = false;
        for(int x = map.getLowerLeftCorner().getX(); x <= map.getUpperRightCorner().getX() && !anyOccupied; ++x){
            for(int y = map.getLowerLeftCorner().getY(); y <= map.getUpperRightCorner().getY() && !anyOccupied; ++y){
                if (map.isOccupied(new Vector2d(x, y))){
                    anyOccupied = true;
                }
            }
        }
        Assertions.assertFalse(anyOccupied);

        Assertions.assertFalse(map.place(animal1));
        Assertions.assertFalse(map.place(animal2));
        Assertions.assertTrue(map.place(animal3));
        Assertions.assertTrue(map.place(animal4));
        Assertions.assertTrue(map.place(animal5));
        Assertions.assertFalse(map.place(animal6));

        Assertions.assertFalse(map.isOccupied(animal1.getPosition()));
        Assertions.assertFalse(map.isOccupied(animal2.getPosition()));
        Assertions.assertTrue(map.isOccupied(animal3.getPosition()));
        Assertions.assertTrue(map.isOccupied(animal4.getPosition()));
        Assertions.assertTrue(map.isOccupied(animal5.getPosition()));
        Assertions.assertTrue(map.isOccupied(animal6.getPosition()));
    }

    @Test
    public void moveTest(){
        RectangularMap map = new RectangularMap(5, 5);
        Animal animal1 = new Animal(map.getLowerLeftCorner());
        Animal animal2 = new Animal(map.getUpperRightCorner());

        map.place(animal1);
        map.place(animal2);
        map.move(animal2, MoveDirection.LEFT);
        map.move(animal2, MoveDirection.LEFT);

        while (animal1.getPosition().getY() != animal2.getPosition().getY()){
            map.move(animal1, MoveDirection.FORWARD);
            map.move(animal2, MoveDirection.FORWARD);
        }
        map.move(animal1, MoveDirection.RIGHT);
        map.move(animal2, MoveDirection.RIGHT);
        while (map.objectAt(animal1.getPosition().add(MapDirection.EAST.toUnitVector())) != animal2){
            map.move(animal1, MoveDirection.FORWARD);
            map.move(animal2, MoveDirection.FORWARD);
        }
        Assertions.assertEquals(new Vector2d(2, 2), animal1.getPosition());
        Assertions.assertEquals(new Vector2d(3, 2), animal2.getPosition());
        Assertions.assertFalse(map.isOccupied(map.getLowerLeftCorner()));
        Assertions.assertFalse(map.isOccupied(map.getUpperRightCorner()));
    }
}
