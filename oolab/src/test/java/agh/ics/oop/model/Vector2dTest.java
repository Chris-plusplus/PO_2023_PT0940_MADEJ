package agh.ics.oop.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Vector2dTest {
    @Test
    public void equalsTest(){
        Vector2d _1 = new Vector2d(2, 4);
        Vector2d _2 = new Vector2d(1 + 1, 6 - 2);

        Assertions.assertEquals(_1, _2);
    }
    @Test
    public void toStringTest(){
        Vector2d _1 = new Vector2d(420, 1337);

        Assertions.assertEquals(_1.toString(), "(420,1337)");
    }
    @Test
    public void precedesTest(){
        Vector2d _1 = new Vector2d(1, 1);
        Vector2d _2 = new Vector2d(2, 4);
        Vector2d _3 = new Vector2d(-1, 3);

        Assertions.assertEquals(_1.precedes(_2), true);
        Assertions.assertEquals(_1.precedes(_3), false);
        Assertions.assertEquals(_3.precedes(_2), true);
    }
    @Test
    public void followsTest(){
        Vector2d _1 = new Vector2d(1, 1);
        Vector2d _2 = new Vector2d(2, 4);
        Vector2d _3 = new Vector2d(-1, 3);

        Assertions.assertEquals(_2.follows(_1), true);
        Assertions.assertEquals(_3.follows(_1), false);
        Assertions.assertEquals(_2.follows(_3), true);
    }

    @Test
    public void upperRightTest(){
        Vector2d _1 = new Vector2d(2, 1);
        Vector2d _2 = new Vector2d(1, 2);

        Vector2d expected = new Vector2d(2, 2);

        Assertions.assertEquals(_1.upperRight(_2), expected);
        Assertions.assertEquals(_2.upperRight(_1), expected);
    }
    @Test
    public void lowerLeftTest(){
        Vector2d _1 = new Vector2d(2, 1);
        Vector2d _2 = new Vector2d(1, 2);

        Vector2d expected = new Vector2d(1, 1);

        Assertions.assertEquals(_1.lowerLeft(_2), expected);
        Assertions.assertEquals(_2.lowerLeft(_1), expected);
    }
    @Test
    public void addTest(){
        Vector2d _1 = new Vector2d(2, 1);
        Vector2d _2 = new Vector2d(1, 2);

        Vector2d expected = new Vector2d(3, 3);

        Assertions.assertEquals(_1.add(_2), expected);
    }
    @Test
    public void subtractTest(){
        Vector2d _1 = new Vector2d(2, 1);
        Vector2d _2 = new Vector2d(1, 2);

        Vector2d expected = new Vector2d(1, -1);

        Assertions.assertEquals(_1.subtract(_2), expected);
    }
    @Test
    public void oppositeTest(){
        Vector2d _1 = new Vector2d(2, -1);

        Vector2d expected = new Vector2d(-2, 1);

        Assertions.assertEquals(_1.opposite(), expected);
    }
}
