package com.mikroysoft.game;

import static org.junit.Assert.*;
import org.junit.Test;
import java.util.HashSet;
import com.badlogic.gdx.graphics.Texture;

import org.junit.runner.RunWith;
import org.junit.Test;
import org.junit.Before;
import de.tomgrill.gdxtesting.GdxTestRunner;

@RunWith(GdxTestRunner.class)
public class FireEngineTest {
    FireEngine truck ;
    Map map;

    @Before
    public void initialize () {
        try {
            this.map = new Map("background");
        } catch (Exception e) {

        }
        this.truck = new FireEngine(map, new FireEngineParameters(1));
    }

    @Test
    public void testStartZeroSpeed() {
        assertEquals(truck.getSpeed(), 0, 0.1);
    }

    @Test
    public void testIncreaseSpeed() {
        Texture a = new Texture("background.png");

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
    public void testRefillVolume() {
        truck.reduceVolume();
        truck.refillVolume();
        assertEquals(truck.getVolume(), truck.getMaxVolume(), 0.1);
    }

    @Test
    public void testStartMaxHealth() {
        assertEquals(truck.getHealth(), truck.getMaxHealth(), 0.1);
    }

    @Test
    public void testTakeDamage() {
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
        for (int i = 0; i < 5; i++) {
            truck.distanceIncreased();
        }

        truck.fuelReduce();
        assertNotEquals(truck.getFuel(), truck.getMaxFuel(), 0.1);
    }

    @Test
    public void testRefuel() {
        truck.distanceIncreased();
        truck.fuelReduce();
        truck.refillFuel();
        assertEquals(truck.getFuel(), truck.getMaxFuel(), 0.1);
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
        assertEquals(truck.getProjectiles(), new HashSet<Projectile>());
    }

    @Test
    public void testShootProjectile() {
        truck.setPosition(0, 0);
        truck.doWeaponFiring(new Coordinate(1, 1));
        assertNotEquals(truck.getProjectiles(), new HashSet<Projectile>());
    }
}
