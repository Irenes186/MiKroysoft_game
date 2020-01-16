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
    private Coordinate basePosition;
    private int countToFire;
    private int currentFireCount;
    private int shootOffset;
    private List < Projectile > projectiles;
    private int range;

    public Alien(Coordinate position, int TILEWIDTH, int TILEHEIGHT) {
        texture = new Texture("alien.png");
        this.position = new Coordinate(position.x,position.y);
        this.TILEHEIGHT = TILEHEIGHT;
        this.TILEWIDTH = TILEWIDTH;
        this.basePosition = position;
        direction = 0;
        speed = 2;
        countToFire = 50; //This is a factor of how much slower alien will shoot compared to fireengine
        currentFireCount = 0;
        shootOffset = 10;
        range = 300;
        projectiles = new ArrayList < Projectile> ();
    }

    public Coordinate getLatestPosition() {
        return this.position;
    }
    public void move(){
        position.x += (int)((Math.random()*10)-5); 
        position.y += (int)((Math.random()*10)-5);
        if (position.x>basePosition.x+100)
            position.x-=5;
        if (position.x<basePosition.x-100)
            position.x+=5;
        if (position.y>basePosition.y+100)
            position.y-=5;
        if (position.y<basePosition.y-100)
            position.y+=5;
    //    direction = (float) Math.toDegrees(Math.atan2((position.y +(Math.random()* 10 + 1)) * -1,  position.x - (Math.random()* 10 + 1))) +45;
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(texture,position.x,position.y,TILEWIDTH/2,TILEHEIGHT/2);
        System.out.println(position.x+position.y);
        for (int i = 0; i < projectiles.size(); i++) {
            projectiles.get(i).render(batch);
        }
    }

    public void shoot(Coordinate destination) {

        if (currentFireCount >= countToFire) {
            projectiles.add(new Projectile (new Coordinate(position.x + shootOffset, position.y), destination, true, range));
            currentFireCount = 0;
        } else {
            currentFireCount++;
        }
    }
    
    public void update() {
        
    };
}
