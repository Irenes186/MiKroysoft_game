package com.mikroysoft.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Wrapper for the information on a road
 * used to store the position information.
 */
public class Road implements IRenderable {

    private Texture texture;
    public Coordinate position;

    /**
     * Class Constructor.
     * Finds the asset for the texture and saves the provided position.
     * 
     * @param position The Coordinate of the bottom left hand corner of the road.
     * @param roadString The string for the name of the texture file.
     */
    public Road (Coordinate position, String roadString) {
        this.position = position;
        this.texture = new Texture(roadString + ".png");
    }

    /**
     * Required by Interface.
     * Don't remove.
     */
    public void update() { }

    /**
     * Adds the road to the provided render batch at its position.
     * 
     * @param batch The render batch for the road to be added to.
     */
    @Override
    public void render(SpriteBatch batch) {
        batch.draw(texture, position.x, position.y, Util.TILEWIDTH, Util.TILEHEIGHT);
    }

}
