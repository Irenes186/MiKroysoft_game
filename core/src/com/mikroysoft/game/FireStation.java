package com.mikroysoft.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture;

/**
 * This class implements the fire station which serves as a base for the fire engine to replenish
 * health, fuel and water
 */
public class FireStation implements IRenderable {
    public boolean destroyed;
    private int fillSpeed;
    public Texture texture;
    private Coordinate position;

    /**
     * The constructor sets the appropiate texture from assets as well as some of the fields like
     * position, fillSpeed and destroyed
     *
     * @param fillSpeed - This is an integer that determines how fast the fire truck will replenish
     * its resources from the fire station
     * @param position - This is the position of the fire station on the map
     */
    public FireStation(int fillSpeed, Coordinate position){
        destroyed = false;
        this.fillSpeed = fillSpeed;
        this.position = position;
        texture = new Texture("station.png");
    }

    /**
     * Returns the position of the fire station on the map
     *
     * @return Coordinate - The position of the fire engine on the map
     */
    public Coordinate getPosition() {
        return position;
    }

    /**
     * Sets the destroy property to true
     */
    public void destroy() {
        destroyed = true;
    }
    
    /**
     * The function returns the destroyed property
     *
     * @return boolean - the value of the destroyed variable
     */
    public boolean isDestroyed() {
        return this.destroyed;
    }
    
    public void update() { }

    /**
     * Draws the fire station on the map using sprite batch
     *
     * @param batch - This is the SpriteBatch used to draw the firestation on the map
     */
    @Override
    public void render(SpriteBatch batch) {
        batch.draw (texture, position.x, position.y, Util.TILEWIDTH * 2, Util.TILEHEIGHT);
    }
}
