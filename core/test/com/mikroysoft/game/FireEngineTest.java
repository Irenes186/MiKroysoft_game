package com.mikroysoft.game;

import static org.junit.Assert.*;
import org.junit.Test;
import java.util.HashSet;

public class FireEngineTest {
    Map map;
    FireEngine truck;
    
    @BeforeClass
    public void makeMap() {
        map = new Map(20, 20, "background");
    }
    
    @Before
    public void makeTruck() {
        truck = new FireEngine(map, new FireEngineParameters(1));
    }
    
    @Test
    public void testStartZeroSpeed() {
        assertEquals(truck.getSpeed(), 0, 0.1);
    }
    
    @Test
    public void testIncreaseSpeed() {
        truck.increaseSpeed();
        assertEquals(truck.getSpeed(), truck.getAcceleration(), 0.1);
    }
    
    @Test
    public void testResetSpeed() {
        truck.increaseSpeed();
        truck.resetSpeed();
        assertEquals(truck.getSpeed(), 0, 0.1);
    }
    
    @Test
    public void testStartMaxVolume() {
        assertEquals(truck.getVolume(), truck.getMaxVolume(), 0.1);
    }
    
    @Test
    public void testReduceVolume() {
        truck.reduceVolume();
        assertEquals(truck.getVolume(), truck.getMaxVolume() - 1, 0.1);
    }
    
    @Test
    public void testStartMaxHealth() {
        assertEquals(truck.getHealth(), truck.getMaxHealth(), 0.1);
    }
    
    @Test
    public void testTakeDamage() {
        FireEngine truck = new FireEngine(map, new FireEngineParameters(1));
        truck.takeDamage(1);
        assertEquals(truck.getHealth(), truck.getMaxHealth() - 1, 0.1);
    }
    
    @Test
    public void testRepair() {
        truck.takeDamage(1);
        truck.repair();
        assertEquals(truck.getHealth(), truck.getMaxHealth(), 0.1);
    }
    
    @Test
    public void testStartMaxFuel() {
        assertEquals(truck.getFuel(), truck.getMaxVolume(), 0.1);
    }
    
    @Test
    public void testReduceFuel() {
        truck.distanceIncreased();
        truck.fuelReduce();
        assertNotEquals(truck.getFuel(), truck.getMaxFuel(), 0.1);
    }
    
    @Test
    public void testRefuel() {
        truck.distanceIncreased();
        truck.fuelReduce();
        assertNotEquals(truck.getFuel(), truck.getMaxFuel(), 0.1);
    }
    
    @Test
    public void testMove() {
        truck.setPosition(0, 0);
        truck.move(new Coordinate(1, 1));
        assertNotEquals(truck.getPosition(), new Coordinate(0, 0));
    }
    
    @Test
    public void testMoveReduceFuel() {
        truck.setPosition(0, 0);
        truck.move(new Coordinate(1, 1));
        assertNotEquals(truck.getFuel(), truck.getMaxFuel(), 0.1);
    }
    
    @Test
    public void testStartNoProjectiles() {
        assertEquals(truck.getProjectileList(), new HashSet<Projectile>());
    }
    
    @Test
    public void testShootProjectile() {
        truck.setPosition(0, 0);
        truck.shoot(new Coordinate(1, 1));
        assertNotEquals(truck.getProjectileList(), new HashSet<Projectile>());
    }
}
