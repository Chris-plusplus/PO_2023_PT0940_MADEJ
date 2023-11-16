package agh.ics.oop.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TextMapTest {
    @Test
    public void placeTest(){
        TextMap map = new TextMap();
        String s1 = "Krystian";
        String s2 = "ma";
        String s3 = "sowoniedzwiedzia";
        String s4 = "o";
        String s5 = "imieniu";
        String s6 = "Tupcia <3";

        map.place(s1);
        map.place(s2);
        map.place(s3);
        map.place(s4);
        map.place(s5);
        map.place(s6);

        String expected =   "0: (^) \"Krystian\"\n"+
                            "1: (^) \"ma\"\n"+
                            "2: (^) \"sowoniedzwiedzia\"\n"+
                            "3: (^) \"o\"\n"+
                            "4: (^) \"imieniu\"\n"+
                            "5: (^) \"Tupcia <3\"";
        Assertions.assertEquals(expected, map.toString());
    }

    @Test
    public void moveTest(){
        TextMap map = new TextMap();
        String s1 = "Krystian";
        String s2 = "ma";
        String s3 = "sowoniedzwiedzia";
        String s4 = "o";
        String s5 = "imieniu";
        String s6 = "Tupcia <3";

        map.place(s1);
        map.place(s2);
        map.place(s3);
        map.place(s4);
        map.place(s5);
        map.place(s6);

        map.move(s6, MoveDirection.LEFT);
        for(int i = 0; i != 5; ++i){
            map.move(s6, MoveDirection.FORWARD);
        }
        map.move(s1, MoveDirection.RIGHT);
        for(int i = 0; i != 4; ++i){
            map.move(s1, MoveDirection.FORWARD);
        }
        String expected =   "0: (<) \"Tupcia <3\"\n"+
                            "1: (^) \"ma\"\n"+
                            "2: (^) \"sowoniedzwiedzia\"\n"+
                            "3: (^) \"o\"\n"+
                            "4: (^) \"imieniu\"\n"+
                            "5: (>) \"Krystian\"";
        Assertions.assertEquals(expected, map.toString());

        map.place("swinke morskom");
        map.place("a nie");
        for(int i = 0; i != 5; ++i){
            map.move(s1, MoveDirection.BACKWARD);
        }
        for(int i = 0; i != 4; ++i){
            map.move(s6, MoveDirection.BACKWARD);
        }
        map.move("swinke morskom", MoveDirection.RIGHT);
        for(int i = 0; i != 4; ++i) {
            map.move("swinke morskom", MoveDirection.BACKWARD);
        }
        map.move(s3, MoveDirection.RIGHT);
        for(int i = 0; i != 4; ++i){
            map.move(s3, MoveDirection.FORWARD);
        }

        expected =  "0: (>) \"Krystian\"\n"+
                    "1: (^) \"ma\"\n"+
                    "2: (>) \"swinke morskom\"\n"+
                    "3: (^) \"o\"\n"+
                    "4: (^) \"imieniu\"\n"+
                    "5: (<) \"Tupcia <3\"\n"+
                    "6: (^) \"a nie\"\n"+
                    "7: (>) \"sowoniedzwiedzia\"";

        Assertions.assertEquals(expected, map.toString());
    }

    @Test
    public void occupationTest(){
        TextMap map = new TextMap();
        String s1 = "Krystian";
        String s2 = "ma";
        String s3 = "swinke morskom";
        String s4 = "o";
        String s5 = "imieniu";
        String s6 = "Tupcia <3";

        map.place(s1);
        map.place(s2);
        map.place(s3);
        map.place(s4);
        map.place(s5);
        map.place(s6);

        for(int i = 0; i != 6; ++i){
            Assertions.assertTrue(map.isOccupied(i));
            Assertions.assertTrue(map.canMoveTo(i));
        }
        Assertions.assertFalse(map.isOccupied(6));
        Assertions.assertFalse(map.isOccupied(7));
        Assertions.assertFalse(map.isOccupied(-1));
        Assertions.assertFalse(map.isOccupied(-420));
    }

    @Test
    public void getterTest(){
        TextMap map = new TextMap();
        String s1 = "Krystian";
        String s2 = "ma";
        String s3 = "swinke morskom";
        String s4 = "o";
        String s5 = "imieniu";
        String s6 = "Tupcia <3";

        map.place(s1);
        map.place(s2);
        map.place(s3);
        map.place(s4);
        map.place(s5);
        map.place(s6);

        for(int i = 0; i != 6; ++i){
            Assertions.assertNotNull(map.objectAt(i));
        }
        Assertions.assertNull(map.objectAt(-1));
        Assertions.assertNull(map.objectAt(420));
        Assertions.assertNull(map.objectAt(-1337));
        Assertions.assertNull(map.objectAt(6));
        Assertions.assertNull(map.objectAt(7));
    }
}
