package com.mikroysoft.game;

import static org.junit.Assert.*;
import org.junit.Test;
import java.util.HashSet;
import com.badlogic.gdx.graphics.Texture;

import org.junit.runner.RunWith;
import org.junit.Test;
import de.tomgrill.gdxtesting.GdxTestRunner;

@RunWith(GdxTestRunner.class)
public class FireEngineTest {
    FireEngine truck ;

    @Test
    public void testStartZeroSpeed() {
        Map map = new Map("background");
        truck = new FireEngine(map, new FireEngineParameters(1));
        
        assertEquals(truck.getSpeed(), 0, 0.1);
    }
    
    @Test
    public void testIncreaseSpeed() {
        Texture a = new Texture("background.png");
        Map map = new Map("background");
        truck = new FireEngine(map, new FireEngineParameters(1));

        truck.increaseSpeed();
        assertEquals(truck.getSpeed(), truck.getAcceleration(), 0.1);
    }
    
    @Test
    public void testResetSpeed() {
        Map map = new Map("background");
        truck = new FireEngine(map, new FireEngineParameters(1));
        
        truck.increaseSpeed();
        truck.resetSpeed();
        assertEquals(truck.getSpeed(), 0, 0.1);
    }
    
    @Test
    public void testStartMaxVolume() {
        Map map = new Map("background");
        truck = new FireEngine(map, new FireEngineParameters(1));
        
        assertEquals(truck.getVolume(), truck.getMaxVolume(), 0.1);
    }
    
    @Test
    public void testReduceVolume() {
        Map map = new Map("background");
        truck = new FireEngine(map, new FireEngineParameters(1));
        
        truck.reduceVolume();
        assertEquals(truck.getVolume(), truck.getMaxVolume() - 1, 0.1);
    }
    
    @Test
    public void testRefillVolume() {
        Map map = new Map("background");
        truck = new FireEngine(map, new FireEngineParameters(1));
        
        truck.reduceVolume();
        truck.refillVolume();
        assertEquals(truck.getVolume(), truck.getMaxVolume(), 0.1);
    }
    
    @Test
    public void testStartMaxHealth() {
        Map map = new Map("background");
        truck = new FireEngine(map, new FireEngineParameters(1));
        
        assertEquals(truck.getHealth(), truck.getMaxHealth(), 0.1);
    }
    
    @Test
    public void testTakeDamage() {
        Map map = new Map("background");
        truck = new FireEngine(map, new FireEngineParameters(1));
        
        FireEngine truck = new FireEngine(map, new FireEngineParameters(1));
        truck.takeDamage(1);
        assertEquals(truck.getHealth(), truck.getMaxHealth() - 1, 0.1);
    }
    
    @Test
    public void testRepair() {
        Map map = new Map("background");
        truck = new FireEngine(map, new FireEngineParameters(1));
        
        truck.takeDamage(1);
        truck.repair();
        assertEquals(truck.getHealth(), truck.getMaxHealth(), 0.1);
    }
    
    @Test
    public void testStartMaxFuel() {
        Map map = new Map("background");
        truck = new FireEngine(map, new FireEngineParameters(1));
        
        assertEquals(truck.getFuel(), truck.getMaxVolume(), 0.1);
    }
    
    @Test
    public void testReduceFuel() {
        Map map = new Map("background");
        truck = new FireEngine(map, new FireEngineParameters(1));
        
        truck.distanceIncreased();
        truck.fuelReduce();
        assertNotEquals(truck.getFuel(), truck.getMaxFuel(), 0.1);
    }
    
    @Test
    public void testRefuel() {
        Map map = new Map("background");
        truck = new FireEngine(map, new FireEngineParameters(1));
        
        truck.distanceIncreased();
        truck.fuelReduce();
        truck.refillFuel();
        assertEquals(truck.getFuel(), truck.getMaxFuel(), 0.1);
    }
    
    @Test
    public void testMove() {
        Map map = new Map("background");
        truck = new FireEngine(map, new FireEngineParameters(1));
        
        truck.setPosition(0, 0);
        truck.move(new Coordinate(1, 1));
        assertNotEquals(truck.getPosition(), new Coordinate(0, 0));
    }
    
    @Test
    public void testMoveReduceFuel() {
        Map map = new Map("background");
        truck = new FireEngine(map, new FireEngineParameters(1));
        
        truck.setPosition(0, 0);
        truck.move(new Coordinate(1, 1));
        assertNotEquals(truck.getFuel(), truck.getMaxFuel(), 0.1);
    }
    
    @Test
    public void testStartNoProjectiles() {
        Map map = new Map("background");
        truck = new FireEngine(map, new FireEngineParameters(1));

        assertEquals(truck.getProjectileList(), new HashSet<Projectile>());
    }
    
    @Test
    public void testShootProjectile() {
        Map map = new Map("background");
        truck = new FireEngine(map, new FireEngineParameters(1));
        
        truck.setPosition(0, 0);
        truck.shoot(new Coordinate(1, 1));
        assertNotEquals(truck.getProjectileList(), new HashSet<Projectile>());
    }
}
