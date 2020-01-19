package com.mikroysoft.game;
import org.junit.*;
import java.util.HashSet;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.junit.runner.RunWith;
import org.junit.Test;
import de.tomgrill.gdxtesting.GdxTestRunner;

@RunWith(GdxTestRunner.class)
public class AlienTest {
    AlienBase base = new AlienBase ("test", new AlienBaseParameters (1), new Coordinate (1, 1), "aldi");

    @Test
    public void testMove() {
        Alien alien = new Alien(new Coordinate(1, 1), base);
        alien.move();
        assertNotEquals(new Coordinate(1, 1), alien.getLatestPosition());
    }

    @Test
    public void testStartNoProjectiles() {
        Alien alien = new Alien(new Coordinate(1, 1), base);
        assertEquals(alien.getProjectiles(), new HashSet<Projectile>());
    }

}
