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
    private int health;
    private List < Projectile > projectiles;
    public float direction;


    public FireEngine(int MAPWIDTH, int MAPHEIGHT) {
        texture = new Texture("fireengine.png");
        position = new Coordinate(300,300);
        projectiles = new ArrayList < Projectile> ();
        direction = 0;
        speed = 2;
    }


    public void repair() {
        health += 50;
    }

    public void refill() {
        waterVolume += 50;
    }

    public void move(Coordinate input) {
        //add some kind of moving rules.
        if (input.x == -1)
            return;

        double xSign = Integer.signum((int)input.x - (int)position.x - 40);
        double ySign = Integer.signum((int)input.y - (int)position.y + 40);

        boolean xThreshold = position.x - 45 <= input.x && position.x - 35 >= input.x;
        boolean yThreshold = position.y + 35 <= input.y && position.y + 45 >= input.y;

        if (xThreshold && yThreshold) {
            return;
        }

        position.x += xSign * speed;
        position.y += ySign * speed;

        direction = (float) Math.toDegrees(Math.atan2((input.y - position.y + 40) * -1, input.x - position.x - 40)) - 90;
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, position.x,Gdx.graphics.getHeight()-position.y,40,40,80,80,1,1,direction,0,0,16,16,false,false);

        for (int i = 0; i < projectiles.size(); i++) {
            projectiles.get(i).render(batch);
        }


        position.x += xSign * speed;
        position.y += ySign * speed;

        direction = (float) Math.toDegrees(Math.atan2((input.y - position.y + 40) * -1, input.x - position.x - 40)) - 90;
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, position.x,Gdx.graphics.getHeight()-position.y,40,40,80,80,1,1,direction,0,0,16,16,false,false);

        for (int i = 0; i < projectiles.size(); i++) {
            projectiles.get(i).render(batch);
        }
    }

    public void shoot(Coordinate destination) {
        projectiles.add(new Projectile (position, destination));
    }
}
