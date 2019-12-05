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

    public Projectile (Coordinate source, Coordinate destination) {
        float length = (float) Math.sqrt(Math.pow(destination.y - source.y, 2) + Math.pow(destination.x - source.x, 2));

        speed = 5;

        length /= speed;
        texture = new Texture("water_drop.png");

        directionX = (destination.x - source.x)/length; 
        directionY = (destination.y - source.y)/length;
        positionX = source.x + 40;
        positionY = source.y - 40;
    }

    public void render(SpriteBatch batch) {
        this.positionX += directionX;
        this.positionY += directionY;

        batch.draw(texture, this.positionX,Gdx.graphics.getHeight()-this.positionY);
    }
}
