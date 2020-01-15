package com.mikroysoft.game;

import java.lang.Math;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Projectile {
    private Texture texture;
    private float directionX, directionY;
    private float positionX, positionY;
    private int speed;
    public int damage;
    public boolean ally;
    Coordinate source;
    float range;

    public Projectile (Coordinate source, Coordinate destination, boolean friendly, float range) {
    	this.source = new Coordinate(source.x,source.y);
        float length = (float) Math.sqrt(Math.pow(destination.y - source.y, 2) + Math.pow(destination.x - source.x, 2));

        speed = 5;
        
        length /= speed;
        if (friendly) {
            texture = new Texture("water_drop.png");
        }else {
            texture = new Texture("water_drop.png");
        }

        directionX = (destination.x - source.x)/length; 
        directionY = (destination.y - source.y)/length;
        positionX = source.x;
        positionY = source.y;
        ally = friendly;
    }

    public void render(SpriteBatch batch) {
        this.positionX += directionX;
        this.positionY += directionY;

        batch.draw(texture, this.positionX,Gdx.graphics.getHeight()-this.positionY);
    }
    public boolean isProjectileInRange() {
    	return source.distanceTo(new Coordinate(positionX,positionY))<range;
    }
}
