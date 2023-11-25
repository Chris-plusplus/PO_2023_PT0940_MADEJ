package agh.ics.oop.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

public class GrassFieldTest {
    @Test
    public void placeTest(){
        GrassField grassField = new GrassField(0);
        Animal animal1 = new Animal(new Vector2d(0, 0));
        Animal animal2 = new Animal(new Vector2d(0, 0));
        Animal animal3 = new Animal(new Vector2d(1, 0));
        Animal animal4 = new Animal(new Vector2d(1, 1));
        Animal animal5 = new Animal(new Vector2d(3 - 4 + 2, 3 - 2));

        Assertions.assertDoesNotThrow(() -> {
            grassField.place(animal1);
            grassField.place(animal3);
            grassField.place(animal4);
        });
        Assertions.assertThrows(PositionAlreadyOccupiedException.class, () -> {
            grassField.place(animal2);
        });
        Assertions.assertThrows(PositionAlreadyOccupiedException.class, () -> {
            grassField.place(animal5);
        });

        int animalCounter = 0;
        for(int x = grassField.getCurrentBounds().lowerLeftCorner().getX(); x <= grassField.getCurrentBounds().upperRightCorner().getX(); ++x){
            for(int y = grassField.getCurrentBounds().lowerLeftCorner().getY(); y <= grassField.getCurrentBounds().upperRightCorner().getY(); ++y){
                WorldElement animal = grassField.objectAt(new Vector2d(x, y));
                if (animal != null && animal.getClass() == Animal.class){
                    ++animalCounter;
                }
            }
        }
        Assertions.assertEquals(3, animalCounter);
        Assertions.assertEquals(animal1, grassField.objectAt(new Vector2d(0, 0)));
        Assertions.assertEquals(animal3, grassField.objectAt(new Vector2d(1, 0)));
        Assertions.assertEquals(animal4, grassField.objectAt(new Vector2d(1, 1)));
    }

    @Test
    public void occupationTest(){
        long seed = -9218197740048019115L;
        /*
        y\x  2 3 4 5 6 7 8 910
         11: -------------------
         10: | | | | | | |*| | |
          9: | | | | | | | | |*|
          8: |*| | | | | | | |*|
          7: | | | | | | | | | |
          6: | | | | |*| | | | |
          5: | | | |*| | | | | |
          4: | | |*| |*| | | | |
          3: | | | | |*| |*| | |
          2: -------------------
         */
        GrassField grassField = new GrassField(10, seed);
        List<Vector2d> expectedPositions = Arrays.asList(
                new Vector2d(8, 10),
                new Vector2d(10, 9),
                new Vector2d(10, 8),
                new Vector2d(2, 8),
                new Vector2d(6, 6),
                new Vector2d(5, 5),
                new Vector2d(4, 4),
                new Vector2d(6, 4),
                new Vector2d(6, 3),
                new Vector2d(8, 3)
        );
        for(Vector2d pos : expectedPositions){
            Assertions.assertTrue(grassField.isOccupied(pos));
        }

        Animal animal1 = new Animal(new Vector2d(5, 4));
        Animal animal2 = new Animal(new Vector2d(6, 4)); // na trawie
        Animal animal3 = new Animal(new Vector2d(32, -12));

        Assertions.assertDoesNotThrow(() -> {
            grassField.place(animal1);
            grassField.place(animal2);
            grassField.place(animal3);
        });

        int occupationCounter = 0;
        for(int x = grassField.getCurrentBounds().lowerLeftCorner().getX(); x <= grassField.getCurrentBounds().upperRightCorner().getX(); ++x){
            for(int y = grassField.getCurrentBounds().lowerLeftCorner().getY(); y <= grassField.getCurrentBounds().upperRightCorner().getY(); ++y){
                if (grassField.isOccupied(new Vector2d(x, y))){
                    ++occupationCounter;
                }
            }
        }
        Assertions.assertEquals(12, occupationCounter);
    }

    @Test
    public void moveTest(){
        long seed = -3905361246065163142L;
        GrassField grassField = new GrassField(10, seed);
        /*
         y\x  0 1 2 3 4 5 6 7 8 910
         11: -----------------------
         10: | | | | | | |*| | | | |
          9: | | | |*| | | | | |*| |
          8: |*| | | | | | | | | | |
          7: | | | | | | | | | | | |
          6: | | | | | | | | | | | |
          5: | | |*| | | | | | | |*|
          4: | | | | | | | | | | | |
          3: | | | | | | | | | | | |
          2: |*| | | | |*| | | |*| |
          1: | | | | | | | | | | | |
          0: | |*| | | | | | | | | |
         -1: -----------------------
         */

        Animal jubilat = new Animal(new Vector2d(0, 0));
        Animal rodzina1 = new Animal(new Vector2d(10, 3));
        Animal rodzina2 = new Animal(new Vector2d(-1, 50));
        Animal rodzina3 = new Animal(new Vector2d(12, 5));
        Animal rodzina4 = new Animal(new Vector2d(30, 4));

        Vector2d miejsceSpotkania = new Vector2d(2, 3);

        Assertions.assertDoesNotThrow(() -> {
            grassField.place(jubilat);
            grassField.place(rodzina1);
            grassField.place(rodzina2);
            grassField.place(rodzina3);
            grassField.place(rodzina4);
        });

        while (jubilat.getPosition().getY() != miejsceSpotkania.getY()){
            grassField.move(jubilat, MoveDirection.FORWARD);
        }
        grassField.move(jubilat, MoveDirection.RIGHT);
        while (jubilat.getPosition().getX() != miejsceSpotkania.getX()){
            grassField.move(jubilat, MoveDirection.FORWARD);
        }

        grassField.move(rodzina1, MoveDirection.LEFT);
        while (rodzina1.getPosition().getX() != miejsceSpotkania.getX() + 1){
            grassField.move(rodzina1, MoveDirection.FORWARD);
        }
        grassField.move(rodzina1, MoveDirection.FORWARD);

        grassField.move(rodzina2, MoveDirection.LEFT);
        grassField.move(rodzina2, MoveDirection.LEFT);
        while (rodzina2.getPosition().getY() != miejsceSpotkania.getY()){
            grassField.move(rodzina2, MoveDirection.FORWARD);
        }
        grassField.move(rodzina2, MoveDirection.LEFT);
        while (rodzina2.getPosition().getX() != miejsceSpotkania.getX() - 1){
            grassField.move(rodzina2, MoveDirection.FORWARD);
        }
        grassField.move(rodzina2, MoveDirection.FORWARD);

        grassField.move(rodzina3, MoveDirection.LEFT);
        grassField.move(rodzina3, MoveDirection.LEFT);
        while (rodzina3.getPosition().getY() != miejsceSpotkania.getY()){
            grassField.move(rodzina3, MoveDirection.FORWARD);
        }
        grassField.move(rodzina3, MoveDirection.RIGHT);
        while (rodzina3.getPosition().getX() != miejsceSpotkania.getX() + 2){
            grassField.move(rodzina3, MoveDirection.FORWARD);
        }
        grassField.move(rodzina3, MoveDirection.FORWARD);

        grassField.move(rodzina4, MoveDirection.LEFT);
        grassField.move(rodzina4, MoveDirection.LEFT);
        while (rodzina4.getPosition().getY() != miejsceSpotkania.getY()){
            Vector2d oldPos = rodzina4.getPosition();
            grassField.move(rodzina4, MoveDirection.FORWARD);

        }
        grassField.move(rodzina4, MoveDirection.RIGHT);
        while (rodzina4.getPosition().getX() != miejsceSpotkania.getX() + 3){
            grassField.move(rodzina4, MoveDirection.FORWARD);
        }
        grassField.move(rodzina4, MoveDirection.FORWARD);

        // każdy dotarł tam gdzie powinien
        Assertions.assertEquals(rodzina2, grassField.objectAt(new Vector2d(1, 3)));
        Assertions.assertEquals(jubilat, grassField.objectAt(new Vector2d(2, 3)));
        Assertions.assertEquals(rodzina1, grassField.objectAt(new Vector2d(3, 3)));
        Assertions.assertEquals(rodzina3, grassField.objectAt(new Vector2d(4, 3)));
        Assertions.assertEquals(rodzina4, grassField.objectAt(new Vector2d(5, 3)));
    }

    // objectAt testowany wraz z innymi
    // canMoveTo testowany wraz z move
}
