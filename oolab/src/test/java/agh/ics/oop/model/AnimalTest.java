package agh.ics.oop.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AnimalTest {
    @Test
    public void upperBoundaryTest(){
        Animal animal = new Animal(new Vector2d(0, 4));

        animal.move(MoveDirection.FORWARD); // 0, 4

        Assertions.assertEquals(animal.getPosition().getY(), Animal.MAP_UPPER_RIGHT_CORNER.getY());
    }
    @Test
    public void lowerBoundaryTest(){
        Animal animal = new Animal(new Vector2d(0, 0));

        animal.move(MoveDirection.RIGHT);
        animal.move(MoveDirection.RIGHT);
        animal.move(MoveDirection.FORWARD); // 0, 0

        Assertions.assertEquals(animal.getPosition().getY(), Animal.MAP_LOWER_LEFT_CORNER.getY());
    }
    @Test
    public void leftBoundaryTest(){
        Animal animal = new Animal(new Vector2d(0, 0));

        animal.move(MoveDirection.LEFT);
        animal.move(MoveDirection.FORWARD); // 0, 0

        Assertions.assertEquals(animal.getPosition().getX(), Animal.MAP_LOWER_LEFT_CORNER.getX());
    }
    @Test
    public void rightBoundaryTest(){
        Animal animal = new Animal(new Vector2d(4, 0));

        animal.move(MoveDirection.RIGHT);
        animal.move(MoveDirection.FORWARD); // 4, 0

        Assertions.assertEquals(animal.getPosition().getX(), Animal.MAP_UPPER_RIGHT_CORNER.getX());
    }

    @Test
    public void positionOrientationTest(){
        Animal animal = new Animal(new Vector2d(0, 0));

        animal.move(MoveDirection.RIGHT);   // 0, 0; EAST
        animal.move(MoveDirection.FORWARD); // 1, 0; EAST
        animal.move(MoveDirection.RIGHT);   // 1, 0; SOUTH
        animal.move(MoveDirection.LEFT);    // 1, 0; EAST
        animal.move(MoveDirection.FORWARD); // 2, 0; EAST
        animal.move(MoveDirection.BACKWARD);// 1, 0; EAST
        animal.move(MoveDirection.BACKWARD);// 0, 0; EAST
        animal.move(MoveDirection.BACKWARD);// 0, 0; EAST
        animal.move(MoveDirection.LEFT);    // 0, 0; NORTH
        animal.move(MoveDirection.LEFT);    // 0, 0; WEST
        animal.move(MoveDirection.LEFT);    // 0, 0; SOUTH
        animal.move(MoveDirection.FORWARD); // 0, 0; SOUTH
        animal.move(MoveDirection.RIGHT);   // 0, 0; EAST
        animal.move(MoveDirection.FORWARD); // 0, 0; EAST
        animal.move(MoveDirection.LEFT);    // 0, 0; SOUTH

        Assertions.assertEquals(animal.getOrientation(), MapDirection.SOUTH);
        Assertions.assertEquals(animal.getPosition(), new Vector2d(0, 0));
        // animal lays egg

        for(int i = 0; i != 5; ++i){
            animal.move(MoveDirection.BACKWARD);
        }
        Assertions.assertEquals(animal.getPosition(), new Vector2d(0, 4));
        for(int i = 0; i != 5; ++i){
            animal.move(MoveDirection.RIGHT);
        }
        Assertions.assertEquals(animal.getOrientation(), MapDirection.WEST);
        for(int i = 0; i != 5; ++i){
            animal.move(MoveDirection.BACKWARD);
        }
        Assertions.assertEquals(animal.getPosition(), new Vector2d(4, 4));
        // Assertions.assertTrue(animal.canMoonwalk());

        // chick hatches
        Animal chick = new Animal(new Vector2d(0, 0));
        chick.move(MoveDirection.RIGHT);

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
            animal.move(move);
            chick.move(move);
        }

        // animals should be at same tile and facing each other
        Assertions.assertEquals(animal.getPosition(), chick.getPosition());
        Assertions.assertEquals(animal.getOrientation().next(), chick.getOrientation().previous());
    }
}
