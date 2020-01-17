package com.mikroysoft.game;
import org.junit.*;
import java.util.HashSet;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;


public class AlienTest {
    @Test
    public void testMove() {
        Alien alien = new Alien(new Coordinate(1, 1), 1, 1);
        alien.move();
        assertThat(new Coordinate(1, 1), not(equalTo(alien.getLatestPosition())));
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
