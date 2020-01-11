package com.mikroysoft.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.lang.Math;
import java.util.ArrayList;
import java.util.List;

public class Alien implements IRenderable {
    public boolean LocationKnowlegde;
    public Texture texture;
    public Coordinate position;
    private int TILEWIDTH, TILEHEIGHT;
    public float direction;
    private float speed;

    private int countToFire;
    private int currentFireCount;
    private List < Projectile > projectiles;

    public Alien(Coordinate position, int TILEWIDTH, int TILEHEIGHT) {
        texture = new Texture("alien.png");
        this.position = position;
        this.TILEHEIGHT = TILEHEIGHT;
        this.TILEWIDTH = TILEWIDTH;
        direction = 0;
        speed = 2;

        countToFire = 10; //This is a factor of how much slower alien will shoot compared to fireengine
        currentFireCount = 0;
        projectiles = new ArrayList < Projectile> ();
    }

    public Coordinate getLatestPosition() {
        return this.position;
    }
    public void Run(){
    //    position.x += Integer.signum((int)position.y + (int)(Math.random()))*2;
    //    position.y += Integer.signum((int)position.y + (int)(Math.random()))*2;
    //    direction = (float) Math.toDegrees(Math.atan2((position.y +(Math.random()* 10 + 1)) * -1,  position.x - (Math.random()* 10 + 1))) +45;
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(texture,position.x,Gdx.graphics.getHeight()-position.y,TILEWIDTH*20,TILEHEIGHT*20);

        for (int i = 0; i < projectiles.size(); i++) {
            projectiles.get(i).render(batch);
        }
    }

    public void shoot(Coordinate destination) {

        if (currentFireCount == countToFire) {
            projectiles.add(new Projectile (position, destination));
            currentFireCount = 0;
        } else {
            currentFireCount++;
        }
    }
}
