package com.mikroysoft.game;

// LibGDX Imports
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
// Java Imports
import java.util.Set;
import java.util.HashSet;
import java.lang.Math;

/* This class represents the player-controlled Fire Engines.
 * FireEngines can be moved by dragging with the mouse along a road. TODO: Constrain momement to roads
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
    public int maxVolume;
    public int distanceTravelled;
    public int shotDamage;
    public float direction;
    Map map;
    int cellX, cellY;


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

    public void increaseSpeed() {
        this.speed = this.speed + this.acceleration;

        if (this.speed >= this.maxSpeed) {
            this.speed = this.maxSpeed;
        }
    }
    
    public void resetSpeed() {
        this.speed = 0;
    }
    
    public void setSpeed(float s) {
        this.maxSpeed = s;
    }
    
    public float getSpeed() {
        return speed;
    }

    public void setAcceleration(float a) {
        this.acceleration = a;
    }
    
    public float getAcceleration() {
        return acceleration;
    }
    
    public void setPosition(int x, int y) {
        position.x = x;
        position.y = y;
    }

    public void setMaxVolume(int v) {
        maxVolume = v;
    }
    
    public void setVolume(int v) {
        this.waterVolume = v;
    }
    
    public int getMaxVolume() {
        return this.maxVolume;
    }
    
    public int getVolume() {
        return this.waterVolume;
    }
    
    public void reduceVolume() {
        if (waterVolume > 0) {
            waterVolume--;
        }
    }
    
    public void repair() {
        if (health < maxHealth) {
            health++;
        }
    }
    
    public void refillFuel() {
        if (fuel < maxFuel) {
            fuel++;
        }
    }
    
    public void refillVolume() {
        if (waterVolume < maxVolume) {
            this.waterVolume++;
        }
    }

    public boolean isMaxFuel() {
        if(this.fuel == this.maxFuel) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isMaxVolume() {
        if(this.waterVolume == this.maxVolume) {
            return true;
        } else {
            return false;
        }
    }

    public int getFuel() {
        return this.fuel;
    }
    
    public void distanceIncreased() {
        distanceTravelled++;
    }
    
    public void fuelReduce() {
        if (fuel > 0 && distanceTravelled % 5 == 0) {
            fuel--;
        }
    }
    
    public int getMaxFuel() {
        return maxFuel;
    }

    public Coordinate getPosition() {
        return position;
    }
    
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

    //@Override
    public void doWeaponFiring(Coordinate destination) {
        if (!dead && !weapon.onCooldown()) {
            reduceVolume();
            projectiles.add((Projectile) weapon.fire(destination));
            weapon.resetCooldown();
        }
    }
}
