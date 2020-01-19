package com.mikroysoft.game;

import static org.junit.Assert.*;

import org.junit.Test;

public class FireStationTest {
    
    @Test
    public void testStartNotDestroyed() {
        FireStation station = new FireStation(1, new Coordinate(1, 1), 20, 20);
        
        assertEquals(station.destroyed, false);
    }
    
    @Test
    public void testDestroy() {
        FireStation station = new FireStation(1, new Coordinate(1, 1), 20, 20);
        
        station.destroy();
        assertEquals(station.isDestroyed(), true);
    }

}
