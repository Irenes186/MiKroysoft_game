package com.mikroysoft.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Gdx;

import java.util.List;
import java.util.ArrayList;
import java.util.Enumeration;
import java.lang.Math;

public class FireEngine {
    public Texture texture;
    public Coordinate position;
    private int waterVolume;
    private float speed;
    private float maxSpeed;
    private float acceleration;
    private float range;
    private float deliveryRate;
    public int health;
    public int fuel;
    public int maxHealth;
    public int maxFuel;
    public int maxVolume;
    public int distanceTravelled;
    private List < Projectile > projectiles;
    public float direction;
    public Rectangle hitBox;


    public FireEngine(int MAPWIDTH, int MAPHEIGHT) {
        texture = new Texture("fireengine.png");
        position = new Coordinate(300,300);
        projectiles = new ArrayList < Projectile> ();
        direction = 0;
        speed = 0;
        maxSpeed = 0;
        acceleration = 0;
        health = 100;
        fuel = 100;
        distanceTravelled = 0;
        maxFuel = 100;
        maxHealth = 100;
        maxVolume = 100;
        waterVolume = 100;
    }
	
    public boolean isMaxSpeed() {
    	if(this.speed == this.maxSpeed) {
    		return true;
    	} else {
    		return false;
    	}
    }
    
    public void increaseSpeed() {
    	this.speed = this.speed + this.acceleration;
    }
    
    public void resetSpeed() {
    	this.speed = 0;
    }
    
    public void setSpeed(float s) {
    	this.maxSpeed = s;
    }
    
    public void setAcceleration(float a) {
    	this.acceleration = a;
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
    	this.waterVolume = this.waterVolume - 1;
    }
    
    public void repair() {
        this.health += 1;
    }

    public void refillFuel() {
        this.fuel += 1;
    }
    
    public void refillVolume() {
    	this.waterVolume += 1;
    }

    public boolean isMaxHealth() {
        if(this.health == this.maxHealth) {
            return true;
        } else {
            return false;
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
        distanceTravelled = distanceTravelled + 1;
    }

    public void fuelReduce() {
        if(distanceTravelled % 5 == 0) {
            fuel -= 1;
        }
    }

    public Coordinate getPosition() {
        return position;
    }

    public void damage(int amount) {
        health -= amount;
    }

    public void move(Coordinate input) {
        float tempspeed = speed;

        if (fuel == 0) {
            tempspeed = speed;
            this.speed = 1;
        }

        //add some kind of moving rules.
        // TODO: Should this be in curly braces?
        if (input.x == -1)
            return;

        // TODO: I thought we agreed to use floats?
        double xSign = java.lang.Math.signum(input.x - position.x);
        double ySign = java.lang.Math.signum(input.y - position.y);

        boolean xThreshold = position.x - 5 <= input.x && position.x + 5 >= input.x;
        boolean yThreshold = position.y - 5 <= input.y && position.y + 5 >= input.y;

        if (xThreshold && yThreshold) {
            return;
        }

        position.x += xSign * speed;
        position.y += ySign * speed;

        direction = (float) Math.toDegrees(Math.atan2((input.y - position.y) * -1, input.x - position.x)) - 90;

        this.speed = tempspeed;
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, position.x - 40,Gdx.graphics.getHeight()-position.y - 40,40,40,80,80,1,1,direction,0,0,16,16,false,false);

        for (int i = 0; i < projectiles.size(); i++) {
            projectiles.get(i).render(batch);
        }
    }

    public void shoot(Coordinate destination) {
        projectiles.add(new Projectile (position, destination, false, ProjectileType.WATER));
    }
}