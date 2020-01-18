package com.mikroysoft.game;
import org.junit.*;
import static org.junit.Assert.assertEquals;

public class AlienBaseTest {
    
    //We needed a delta of 0.1 to allow for imprecisions
    @Test
    public void testtakeDamage(){
        AlienBase alienBase = new AlienBase("Alien", new AlienBaseParameters(1), new Coordinate(30, 40), 20, 20, "aldi.png");
        int damage = 10;
        alienBase.health = 12;
        alienBase.takeDamage(damage);
        int result = alienBase.getHealth();
        assertEquals(2, result, 0.1);
    }
}
