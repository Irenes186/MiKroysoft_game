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
    private float range;
    private float deliveryRate;
    public int health;
    public int fuel;
    public int maxHealth;
    public int maxFuel;
    public int distanceTravelled;
    private List < Projectile > projectiles;
    public float direction;
    public Rectangle hitBox;


    public FireEngine(int MAPWIDTH, int MAPHEIGHT) {
        texture = new Texture("fireengine.png");
        position = new Coordinate(300,300);
        projectiles = new ArrayList < Projectile> ();
        direction = 0;
        speed = 2;
        health = 100;
        fuel = 100;
        distanceTravelled = 0;
        maxFuel = 100;
        maxHealth = 100;
    }

    public void setPosition(int x, int y) {
        position.x = x;
        position.y = y;
    }

    public void repair() {
        health += 1;
    }

    public void refill() {
        waterVolume += 50;
        fuel += 1;
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
        //add some kind of moving rules.
        if (input.x == -1)
            return;

        double xSign = Integer.signum((int)input.x - (int)position.x);
        double ySign = Integer.signum((int)input.y - (int)position.y);

        boolean xThreshold = position.x - 5 <= input.x && position.x + 5 >= input.x;
        boolean yThreshold = position.y - 5 <= input.y && position.y + 5 >= input.y;

        if (xThreshold && yThreshold) {
            return;
        }

        position.x += xSign * speed;
        position.y += ySign * speed;

        direction = (float) Math.toDegrees(Math.atan2((input.y - position.y) * -1, input.x - position.x)) - 90;
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, position.x - 40,Gdx.graphics.getHeight()-position.y - 40,40,40,80,80,1,1,direction,0,0,16,16,false,false);

        for (int i = 0; i < projectiles.size(); i++) {
            projectiles.get(i).render(batch);
        }
    }

    public void shoot(Coordinate destination) {
        projectiles.add(new Projectile (position, destination));
    }
}
