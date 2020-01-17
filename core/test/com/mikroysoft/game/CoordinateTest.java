package com.mikroysoft.game;
import org.junit.*;
import static org.junit.Assert.assertEquals;

public class CoordinateTest {
    //We needed a delta of 0.1 to allow for imprecisions
    @Test
    public void testdistanceTo(){
        Coordinate pointOne = new Coordinate((float)10, (float)20);
        Coordinate pointTwo = new Coordinate((float)30, (float)40);
        float result = pointOne.distanceTo(pointTwo);
        assertEquals(Math.sqrt(800), result, 0.1);
    }
    
    @Test
    public void testCellDistanceTo() {
        Coordinate pointOne = new Coordinate(20, 20);
        Coordinate pointTwo = new Coordinate(80, 20);
        assertEquals(pointOne.cellDistanceTo(pointTwo, 20, 20), 3);
    }

    @Test
    public void testtoString(){
        Coordinate point = new Coordinate((float)10, (float)20);
        String test = point.toString();
        assertEquals("(10.0, 20.0)", test);
    }

}
