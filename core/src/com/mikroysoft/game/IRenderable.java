package com.mikroysoft.game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Outlines the required methods and attributes for 
 * all the objects that are rendered in the map.
 */
interface IRenderable {
    void render(SpriteBatch batch);
    void update();
    Coordinate position = new Coordinate(0, 0);
}