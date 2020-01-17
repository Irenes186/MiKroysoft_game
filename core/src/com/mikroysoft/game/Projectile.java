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
    ProjectileType type;

    public Projectile (Coordinate source, Coordinate destination, boolean friendly, ProjectileType type, int range) {
        float length = (float) Math.sqrt(Math.pow(destination.y - source.y, 2) + Math.pow(destination.x - source.x, 2));
        speed = 5;
        length /= speed;
        
        switch (type) {
        	case WATER:
        		texture = new Texture("water_drop.png");
        		break;
        	case BULLET:
        		texture = new Texture("bullet.png");
        		break;
        	default:
        		throw new IllegalArgumentException("Requested projectile type '" + type + "' which has not yet been implemeneted");
        }

        directionX = (destination.x - source.x)/length; 
        directionY = (destination.y - source.y)/length;
        position = new Coordinate(source.x, source.y);
        ally = friendly;
        start = new Coordinate (source.x, source.y);
        this.range = range;
        this.type = type;
    }

    public void render(SpriteBatch batch) {
        this.position.x += directionX;
        this.position.y += directionY;
        batch.draw(texture, this.position.x,Gdx.graphics.getHeight()-this.position.y);
    }

    public boolean inRange () {

        return (start.distanceTo(position) < range);

    }
    
    public boolean equals(Projectile other) {
        return directionX == other.directionX && directionY == other.directionY && speed == other.speed && 
                position == other.position && damage == other.damage && ally == other.ally && 
                start == other.start && range == other.range && type == other.type; 
    }

}
