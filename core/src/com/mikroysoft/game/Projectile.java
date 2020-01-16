package com.mikroysoft.game;

import java.lang.Math;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Projectile {
    private Texture texture;
    private float directionX, directionY;
    private int speed;
    public Coordinate position;
    public int damage;
    public boolean ally;
    private Coordinate start;
    private int range;

    public Projectile (Coordinate source, Coordinate destination, boolean friendly, int range) {
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
        position = new Coordinate(source.x, source.y);
        ally = friendly;
        start = new Coordinate (source.x, source.y);
        this.range = range;
    }

    public void render(SpriteBatch batch) {
        this.position.x += directionX;
        this.position.y += directionY;

        batch.draw(texture, this.position.x,Gdx.graphics.getHeight()-this.position.y);
    }

    public boolean inRange () {
        return (start.distanceTo(position) < range);
    }

}
