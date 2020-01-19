package com.mikroysoft.game;

// LibGDX Imports
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
// Java Imports
import java.util.Set;
import java.util.HashSet;
import java.lang.Math;

/** This class represents the player-controlled Fire Engines.
 * FireEngines can be moved by dragging with the mouse along a road.
 * FireEngines have finite fuel, health, and water supplies. All are refilled over time when within range of a FireStation.
 */
public class FireEngine extends Killable {
    private int waterVolume;
    private float speed;
    private float maxSpeed;
    private float acceleration;
    private int shotCooldown;
    public Texture texture;
    public Coordinate position;
    public int fuel;
    public int maxFuel;
    public int maxVolume; //this is set in GameScreen
    public int distanceTravelled;
    public int shotDamage;
    public float direction;
    Map map;
    int cellX, cellY;


    /**
     * Constructor to create a Fire engine. Initializes a fire engine on a map with a Coordinate
     * of 300 for both y and x.
     * @param map - This is an instance of the map object that you wish to create the fire engine
     * on
     * @param parameters - This is the configuration for the FireEngine, look at
     * FireEngineParameters for more details
     */
    public FireEngine(Map map, FireEngineParameters parameters) {
        texture = new Texture("fireengine.png");
        position = new Coordinate(300,300);
        projectiles = new HashSet < Projectile> ();
        direction = 0;
        speed = 0;
        maxSpeed = parameters.maxSpeed;
        acceleration = parameters.acceleration;
        health = 100;
        fuel = 100;
        distanceTravelled = 0;
        maxFuel = 100;
        maxHealth = 100;
        maxVolume = 100;
        waterVolume = 100;
        this.map = map;
        range = 500;
        //this.rectangle = new Rectangle (new Coordinate (position.x + map.TILEWIDTH / 2, position.y + map.TILEHEIGHT / 2), map.TILEWIDTH, map.TILEHEIGHT, 0);
        rectangle = new Rectangle (position, Util.TILEWIDTH, Util.TILEHEIGHT, 0);
        shotDamage = parameters.shotDamage;
        dead = false;
        weapon = new WeaponBullet(shotCooldown, range, "water_drop.png", position);
    }

    /**
     * Increases the fire engines speed by its acceleration and esures that speed does not exceed
     * the maximum speed
     * @return void
     */
    public void increaseSpeed() {
        this.speed = this.speed + this.acceleration;

        if (this.speed >= this.maxSpeed) {
            this.speed = this.maxSpeed;
        }
    }

    /**
     * Sets the speed of the fire engine back to zero
     * @return void
     */
    public void resetSpeed() {
        this.speed = 0;
    }

    /**
     * Sets the maximum speed of the fire engine
     * @param s - this is the value to set maxSpeed too
     * @return void
     */
    public void setSpeed(float s) {
        this.maxSpeed = s;
    }

    /**
     * Returns the current speed of the fire engine
     *
     * @return float - the speed of the fire engine
     */
    public float getSpeed() {
        return speed;
    }

    /**
     * Sets the acceleration of the fire engine
     *
     * @param a - This is the value to set the acceleration too
     *
     */
    public void setAcceleration(float a) {
        this.acceleration = a;
    }

    /**
     * Returns the current accleration of the fire engine
     *
     * @return float - the accleration of the fire engine
     */
    public float getAcceleration() {
        return acceleration;
    }

    /**
     * Sets the position of the fire engine to the x and y values provided
     *
     * @param x - an integer to set the x value of the position field
     * @param y - an integer to set the y value of the position field
     */
    public void setPosition(int x, int y) {
        position.x = x;
        position.y = y;
    }

    /**
     * Sets the maximum water volume that the fire engine can hold
     *
     * @param v - an integer value to set the maximum volume too
     */
    public void setMaxVolume(int v) {
        maxVolume = v;
    }

    /**
     * Sets the volume of water the fire engine currently holds
     *
     * @param v - an integer value to set the volume of water too
     */
    public void setVolume(int v) {
        this.waterVolume = v;
    }

    /**
     * Returns the maximum volume of water the fire engine can hold
     *
     * @return int - the maxVolume field of the class
     */
    public int getMaxVolume() {
        return this.maxVolume;
    }

    /**
     * Returns the volume of water the fire engine currently holds
     *
     * @return int - the waterVolume field of the class
     */
    public int getVolume() {
        return this.waterVolume;
    }

    /**
     * Checks if the current volume of water is greater than zero, if it is it decrements it
     *
     */
    public void reduceVolume() {
        if (waterVolume > 0) {
            waterVolume--;
        }
    }

    /**
     * Checks if the current health is smaller than the maximum health, if it is it increments it
     */
    public void repair() {
        if (health < maxHealth) {
            health++;
        }
    }

    /**
     * Checks if the fuel is smaller than the maximum fuel, if it is it increments it
     */
    public void refillFuel() {
        if (fuel < maxFuel) {
            fuel++;
        }
    }

    /**
     * Checks if the current water volume is smaller than the maximum volume, if it is it increments
     * it
     */
    public void refillVolume() {
        if (waterVolume < maxVolume) {
            this.waterVolume++;
        }
    }

    /**
     * Checks whether the current amount of fuel the fire engine has is equal to the maximum fuel it
     * can hold
     *
     * @return boolean - True if the fire engine is at maximum fuel, otherwise it returns false
     */
    public boolean isMaxFuel() {
        if(this.fuel == this.maxFuel) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Checks whether the current amount of water the fire engine is holding is equal to the maximum
     * amount of water it can hold.
     *
     * @return boolean - True if the fire engine is at maximum water capacity, otherwise it returns
     * false.
     */
    public boolean isMaxVolume() {
        if(this.waterVolume == this.maxVolume) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Returns the current amount of fuel the fire engine holds
     */
    public int getFuel() {
        return this.fuel;
    }

    /**
     * Increments the distance that the fire engine has traveled
     */
    public void distanceIncreased() {
        distanceTravelled++;
    }

    /**
     * Reduces the fuel the fire engine holds if a certain amount of distance has beed travelled
     */
    public void fuelReduce() {
        if (fuel > 0 && distanceTravelled % 5 == 0) {
            fuel--;
        }
    }

    /**
     * Returns the maximum amount of fuel that the fire engine can hold
     *
     * @return int - The maximum amount of fuel that the fire engine holds.
     */
    public int getMaxFuel() {
        return maxFuel;
    }

    /**
     * Returns the curren position
     *
     * @return Coordinate - The position in the map the fire engine currently is in
     */
    public Coordinate getPosition() {
        return position;
    }

    /**
     * Moves the fire engine in the direction of the input coordinate
     *
     * @param input - The position you want the fire engine to move too
     */
    public void move(Coordinate input) {

        if (dead) {
            return;
        }

        fuelReduce();
        increaseSpeed();
        float tempspeed = speed;
        if (fuel == 0) {
            tempspeed = speed;
            this.speed = 1;
        }

        //TODO: Add moving rules
        if (input.x == -1){ return;}

        // TODO: I thought we agreed to use floats?

        double xSign = java.lang.Math.signum(input.x - position.x);
        double ySign = java.lang.Math.signum(input.y - position.y);

        boolean xThreshold = position.x - 5 <= input.x && position.x + 5 >= input.x;
        boolean yThreshold = position.y - 5 <= input.y && position.y + 5 >= input.y;

        if (xThreshold && yThreshold) {
            return;
        }

        cellX = (int) Math.floor(position.x / Util.TILEWIDTH);
        cellY = (int) Math.floor(position.y / Util.TILEHEIGHT) + 1;
        if (cellX < 0 || cellY < 0 || Util.MAPWIDTH <= cellX || Util.MAPHEIGHT <= cellY || !(map.grid[cellY][cellX] instanceof Road)) {
            position.x += xSign * (speed / 2);
            position.y += ySign * (speed / 2);
        } else {
            position.x += xSign * speed;
            position.y += ySign * speed;
        }

        direction = (float) Math.toDegrees(Math.atan2((input.y - position.y) * -1, input.x - position.x)) - 90;

        this.rectangle.updatePosition (new Coordinate (position.x + Util.TILEWIDTH / 2, position.y + Util.TILEHEIGHT / 2), direction);
        this.speed = tempspeed;
    }

    /**
     * This renders the fire engine on the map in the position defined by the position field. This
     * is called every frame. It also renders any projectiles the fire engine has shot and deletes
     * these projectiles when they move outside of the range of the fire engine.
     *
     * @param batch - A spritebatch to draw the engine and projectiles to the screen
     */
    public void render(SpriteBatch batch) {
        batch.draw(texture, position.x - 40,position.invertY().y - 40,40,40,80,80,1,1,direction,0,0,16,16,false,false);
        if (shotCooldown > 0) {
            shotCooldown--;
        }

        for (Projectile projectile : projectiles) {
            projectile.render(batch);
        }

        deleteOutOfRangeProjectiles ();
    }

    /**
     * Fires a projectile from the fire engine in the direction of the input coordinate. This means
     * it reduces the current water volume that the fire engine holds and resets the cooldown
     *
     * @param Coordinate - The coordinate to fire in the direction of
     */
    //@Override
    public void doWeaponFiring(Coordinate destination) {
        if (!dead && !weapon.onCooldown()) {
            reduceVolume();
            projectiles.add((Projectile) weapon.fire(destination));
            weapon.resetCooldown();
        }
    }
}
