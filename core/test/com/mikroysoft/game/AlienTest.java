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
    @Test
    public void testMove() {
        Alien alien = new Alien(new Coordinate(1, 1), 1, 1);
        alien.move();
        assertNotEquals(new Coordinate(1, 1), alien.getLatestPosition());
    }
    
    @Test
    public void testStartNoProjectiles() {
        Alien alien = new Alien(new Coordinate(1, 1), 1, 1);
        assertEquals(alien.getProjectiles(), new HashSet<Projectile>());
    }
    
    @Test
    public void testShoot() {
        Alien alien = new Alien(new Coordinate(1, 1), 1, 1);
        alien.shoot(new Coordinate(2, 2));
        HashSet<Projectile> referenceProjSet = new HashSet<Projectile>();
        referenceProjSet.add(new Projectile(new Coordinate(1, 1), new Coordinate(2, 2), false, ProjectileType.BULLET, alien.getRange()));
        assertEquals(alien.getProjectiles(), referenceProjSet);
    }
    
    

}
