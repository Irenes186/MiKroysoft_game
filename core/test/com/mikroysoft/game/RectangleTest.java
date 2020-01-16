package com.mikroysoft.game;
import org.junit.*;
import static org.junit.Assert.assertEquals;

public class RectangleTest {

    @Test
    public void testpointInRectangle(){
        Coordinate coord = new Coordinate(10, 20);
        Rectangle rect = new Rectangle(coord, 20, 30, (float) 0);
        boolean presence = rect.pointInRectangle(coord);
        assertEquals(true, presence);
    }

}
