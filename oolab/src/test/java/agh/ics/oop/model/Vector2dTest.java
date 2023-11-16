package agh.ics.oop.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Vector2dTest {
    @Test
    public void equalsTest(){
        Vector2d v1 = new Vector2d(2, 4);
        Vector2d v2 = new Vector2d(1 + 1, 6 - 2);

        Assertions.assertEquals(v1, v2);
    }
    @Test
    public void toStringTest(){
        Vector2d v1 = new Vector2d(420, 1337);

        Assertions.assertEquals(v1.toString(), "(420, 1337)");
    }
    @Test
    public void precedesTest(){
        Vector2d v1 = new Vector2d(1, 1);
        Vector2d v2 = new Vector2d(2, 4);
        Vector2d v3 = new Vector2d(-1, 3);

        Assertions.assertEquals(v1.precedes(v2), true);
        Assertions.assertEquals(v1.precedes(v3), false);
        Assertions.assertEquals(v3.precedes(v2), true);
    }
    @Test
    public void followsTest(){
        Vector2d v1 = new Vector2d(1, 1);
        Vector2d v2 = new Vector2d(2, 4);
        Vector2d v3 = new Vector2d(-1, 3);

        Assertions.assertEquals(v2.follows(v1), true);
        Assertions.assertEquals(v3.follows(v1), false);
        Assertions.assertEquals(v2.follows(v3), true);
    }

    @Test
    public void upperRightTest(){
        Vector2d v1 = new Vector2d(2, 1);
        Vector2d v2 = new Vector2d(1, 2);

        Vector2d expected = new Vector2d(2, 2);

        Assertions.assertEquals(v1.upperRight(v2), expected);
        Assertions.assertEquals(v2.upperRight(v1), expected);
    }
    @Test
    public void lowerLeftTest(){
        Vector2d v1 = new Vector2d(2, 1);
        Vector2d v2 = new Vector2d(1, 2);

        Vector2d expected = new Vector2d(1, 1);

        Assertions.assertEquals(v1.lowerLeft(v2), expected);
        Assertions.assertEquals(v2.lowerLeft(v1), expected);
    }
    @Test
    public void addTest(){
        Vector2d v1 = new Vector2d(2, 1);
        Vector2d v2 = new Vector2d(1, 2);

        Vector2d expected = new Vector2d(3, 3);

        Assertions.assertEquals(v1.add(v2), expected);
    }
    @Test
    public void subtractTest(){
        Vector2d v1 = new Vector2d(2, 1);
        Vector2d v2 = new Vector2d(1, 2);

        Vector2d expected = new Vector2d(1, -1);

        Assertions.assertEquals(v1.subtract(v2), expected);
    }
    @Test
    public void oppositeTest(){
        Vector2d v1 = new Vector2d(2, -1);

        Vector2d expected = new Vector2d(-2, 1);

        Assertions.assertEquals(v1.opposite(), expected);
    }
}
