package com.mikroysoft.game;

import static org.junit.Assert.*;
import org.junit.Test;
import java.util.HashSet;

public class FireEngineTest {
    Map map;
    FireEngine truck;
    
//    @BeforeClass
//    public void makeMap() {
//        map = new Map(20, 20, "background");
//    }
//    
//    @Before
//    public void makeTruck() {
//        truck = new FireEngine(map, new FireEngineParameters(1));
//    }
    
    @Test
    public void testStartZeroSpeed() {
        map = new Map(20, 20, "background");
        truck = new FireEngine(map, new FireEngineParameters(1));
        
        assertEquals(truck.getSpeed(), 0, 0.1);
    }
    
    @Test
    public void testIncreaseSpeed() {
        map = new Map(20, 20, "background");
        truck = new FireEngine(map, new FireEngineParameters(1));
        
        truck.increaseSpeed();
        assertEquals(truck.getSpeed(), truck.getAcceleration(), 0.1);
    }
    
    @Test
    public void testResetSpeed() {
        map = new Map(20, 20, "background");
        truck = new FireEngine(map, new FireEngineParameters(1));
        
        truck.increaseSpeed();
        truck.resetSpeed();
        assertEquals(truck.getSpeed(), 0, 0.1);
    }
    
    @Test
    public void testStartMaxVolume() {
        map = new Map(20, 20, "background");
        truck = new FireEngine(map, new FireEngineParameters(1));
        
        assertEquals(truck.getVolume(), truck.getMaxVolume(), 0.1);
    }
    
    @Test
    public void testReduceVolume() {
        map = new Map(20, 20, "background");
        truck = new FireEngine(map, new FireEngineParameters(1));
        
        truck.reduceVolume();
        assertEquals(truck.getVolume(), truck.getMaxVolume() - 1, 0.1);
    }
    
    @Test
    public void testRefillVolume() {
        map = new Map(20, 20, "background");
        truck = new FireEngine(map, new FireEngineParameters(1));
        
        truck.reduceVolume();
        truck.refillVolume();
        assertEquals(truck.getVolume(), truck.getMaxVolume(), 0.1);
    }
    
    @Test
    public void testStartMaxHealth() {
        map = new Map(20, 20, "background");
        truck = new FireEngine(map, new FireEngineParameters(1));
        
        assertEquals(truck.getHealth(), truck.getMaxHealth(), 0.1);
    }
    
    @Test
    public void testTakeDamage() {
        map = new Map(20, 20, "background");
        truck = new FireEngine(map, new FireEngineParameters(1));
        
        FireEngine truck = new FireEngine(map, new FireEngineParameters(1));
        truck.takeDamage(1);
        assertEquals(truck.getHealth(), truck.getMaxHealth() - 1, 0.1);
    }
    
    @Test
    public void testRepair() {
        map = new Map(20, 20, "background");
        truck = new FireEngine(map, new FireEngineParameters(1));
        
        truck.takeDamage(1);
        truck.repair();
        assertEquals(truck.getHealth(), truck.getMaxHealth(), 0.1);
    }
    
    @Test
    public void testStartMaxFuel() {
        map = new Map(20, 20, "background");
        truck = new FireEngine(map, new FireEngineParameters(1));
        
        assertEquals(truck.getFuel(), truck.getMaxVolume(), 0.1);
    }
    
    @Test
    public void testReduceFuel() {
        map = new Map(20, 20, "background");
        truck = new FireEngine(map, new FireEngineParameters(1));
        
        truck.distanceIncreased();
        truck.fuelReduce();
        assertNotEquals(truck.getFuel(), truck.getMaxFuel(), 0.1);
    }
    
    @Test
    public void testRefuel() {
        map = new Map(20, 20, "background");
        truck = new FireEngine(map, new FireEngineParameters(1));
        
        truck.distanceIncreased();
        truck.fuelReduce();
        truck.refillFuel();
        assertEquals(truck.getFuel(), truck.getMaxFuel(), 0.1);
    }
    
    @Test
    public void testMove() {
        map = new Map(20, 20, "background");
        truck = new FireEngine(map, new FireEngineParameters(1));
        
        truck.setPosition(0, 0);
        truck.move(new Coordinate(1, 1));
        assertNotEquals(truck.getPosition(), new Coordinate(0, 0));
    }
    
    @Test
    public void testMoveReduceFuel() {
        map = new Map(20, 20, "background");
        truck = new FireEngine(map, new FireEngineParameters(1));
        
        truck.setPosition(0, 0);
        truck.move(new Coordinate(1, 1));
        assertNotEquals(truck.getFuel(), truck.getMaxFuel(), 0.1);
    }
    
    @Test
    public void testStartNoProjectiles() {
        map = new Map(20, 20, "background");
        truck = new FireEngine(map, new FireEngineParameters(1));
        
        assertEquals(truck.getProjectileList(), new HashSet<Projectile>());
    }
    
    @Test
    public void testShootProjectile() {
        map = new Map(20, 20, "background");
        truck = new FireEngine(map, new FireEngineParameters(1));
        
        truck.setPosition(0, 0);
        truck.shoot(new Coordinate(1, 1));
        assertNotEquals(truck.getProjectileList(), new HashSet<Projectile>());
    }
}
