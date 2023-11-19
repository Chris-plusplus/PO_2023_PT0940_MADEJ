package agh.ics.oop.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AnimalTest {
    @Test
    public void upperBoundaryTest(){
        RectangularMap map = new RectangularMap(5, 5);
        Animal animal = new Animal(new Vector2d(0, 4));

        animal.move(MoveDirection.FORWARD, map); // 0, 4

        Assertions.assertEquals(animal.getPosition().getY(), map.getUpperRightCorner().getY());
    }
    @Test
    public void lowerBoundaryTest(){
        RectangularMap map = new RectangularMap(5, 5);
        Animal animal = new Animal(new Vector2d(0, 0));

        animal.move(MoveDirection.RIGHT, map);
        animal.move(MoveDirection.RIGHT, map);
        animal.move(MoveDirection.FORWARD, map); // 0, 0

        Assertions.assertEquals(animal.getPosition().getY(), map.getLowerLeftCorner().getY());
    }
    @Test
    public void leftBoundaryTest(){
        RectangularMap map = new RectangularMap(5, 5);
        Animal animal = new Animal(new Vector2d(0, 0));

        animal.move(MoveDirection.LEFT, map);
        animal.move(MoveDirection.FORWARD, map); // 0, 0

        Assertions.assertEquals(animal.getPosition().getX(), map.getLowerLeftCorner().getX());
    }
    @Test
    public void rightBoundaryTest(){
        RectangularMap map = new RectangularMap(5, 5);
        Animal animal = new Animal(new Vector2d(4, 0));

        animal.move(MoveDirection.RIGHT, map);
        animal.move(MoveDirection.FORWARD, map); // 4, 0

        Assertions.assertEquals(animal.getPosition().getX(), map.getUpperRightCorner().getX());
    }

    @Test
    public void positionOrientationTest(){
        RectangularMap map = new RectangularMap(5, 5);
        Animal animal = new Animal(new Vector2d(0, 0));

        animal.move(MoveDirection.RIGHT, map);   // 0, 0; EAST
        animal.move(MoveDirection.FORWARD, map); // 1, 0; EAST
        animal.move(MoveDirection.RIGHT, map);   // 1, 0; SOUTH
        animal.move(MoveDirection.LEFT, map);    // 1, 0; EAST
        animal.move(MoveDirection.FORWARD, map); // 2, 0; EAST
        animal.move(MoveDirection.BACKWARD, map);// 1, 0; EAST
        animal.move(MoveDirection.BACKWARD, map);// 0, 0; EAST
        animal.move(MoveDirection.BACKWARD, map);// 0, 0; EAST
        animal.move(MoveDirection.LEFT, map);    // 0, 0; NORTH
        animal.move(MoveDirection.LEFT, map);    // 0, 0; WEST
        animal.move(MoveDirection.LEFT, map);    // 0, 0; SOUTH
        animal.move(MoveDirection.FORWARD, map); // 0, 0; SOUTH
        animal.move(MoveDirection.RIGHT, map);   // 0, 0; EAST
        animal.move(MoveDirection.FORWARD, map); // 0, 0; EAST
        animal.move(MoveDirection.LEFT, map);    // 0, 0; SOUTH

        Assertions.assertEquals(animal.getOrientation(), MapDirection.SOUTH);
        Assertions.assertEquals(animal.getPosition(), new Vector2d(0, 0));
        // animal lays egg

        for(int i = 0; i != 5; ++i){
            animal.move(MoveDirection.BACKWARD, map);
        }
        Assertions.assertEquals(animal.getPosition(), new Vector2d(0, 4));
        for(int i = 0; i != 5; ++i){
            animal.move(MoveDirection.RIGHT, map);
        }
        Assertions.assertEquals(animal.getOrientation(), MapDirection.WEST);
        for(int i = 0; i != 5; ++i){
            animal.move(MoveDirection.BACKWARD, map);
        }
        Assertions.assertEquals(animal.getPosition(), new Vector2d(4, 4));
        // Assertions.assertTrue(animal.canMoonwalk());

        // chick hatches
        Animal chick = new Animal(new Vector2d(0, 0));
        chick.move(MoveDirection.RIGHT, map);

        MoveDirection[] GPSData = {
                MoveDirection.FORWARD,
                MoveDirection.FORWARD,
                MoveDirection.FORWARD,
                MoveDirection.LEFT,
                MoveDirection.FORWARD,
                MoveDirection.LEFT,
                MoveDirection.FORWARD,
                MoveDirection.RIGHT,
                MoveDirection.FORWARD
        };

        // two animals try to meet
        for(MoveDirection move : GPSData){
            animal.move(move, map);
            chick.move(move, map);
        }

        // animals should be at same tile and facing each other
        Assertions.assertEquals(animal.getPosition(), chick.getPosition());
        Assertions.assertEquals(animal.getOrientation().next(), chick.getOrientation().previous());
    }
}
