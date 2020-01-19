package com.mikroysoft.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Represents a projectile that can move.
 */
public class Projectile {
    private Texture texture;
    private float directionX, directionY;
    private int speed;
    public Coordinate position;
    public int damage;
    private Coordinate start;
    private int range;
    ProjectileType type;

    /**
     * Class Constructor.
     * Sets the texture of the projectile and saves
     * the relevant coordinates to the object.
     * 
     * @param source The coordinate that the projectile should start from.
     * @param destination A coordinate on the line that the projectile should travel.
     * @param type A value from the ProjectileType ENUM that decides the texture.
     * @param range The value that the projectile should travel before it disappears.
     */
    public Projectile (Coordinate source, Coordinate destination, ProjectileType type, int range) {
        float length = source.distanceTo(destination);
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
        
        start = new Coordinate (source.x, source.y);
        this.range = range;
        this.type = type;
    }

    /**
     * Moves the projectile by one normalized vector along the
     * path of travel and adds the projectile to the render batch
     * 
     * @param batch The spritebatch object that the projectile
     * should be added to.
     */
    public void render(SpriteBatch batch) {
        this.position.x += directionX;
        this.position.y += directionY;
        batch.draw(texture, this.position.x,this.position.invertY().y);
    }

    /**
     * Checks and returns whether the projectile has traveled its preset
     * range away from its start coodinate.
     * 
     * @return true if the object is still within the preset range, otherwise false.
     */
    public boolean inRange () {
        return (start.distanceTo(position) < range);
    }
    
    /**
     * Checks and returns if two projectiles are equivalent.
     * i.e. if all their attributes are the same.
     * 
     * @param other The projectile that shoud be compared to this projectile.
     * 
     * @return true if the projectiles are equivalent, otherwise false.
     */
    public boolean equals(Projectile other) {
        return directionX == other.directionX && directionY == other.directionY && speed == other.speed && 
                position == other.position && damage == other.damage && 
                start == other.start && range == other.range && type == other.type; 
    }

}
