package com.mikroysoft.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Road implements IRenderable {

    private Texture texture;
    public Coordinate position;
    private int TILEWIDTH, TILEHEIGHT;

    public Road (Coordinate position, int TILEWIDTH, int TILEHEIGHT, String roadString) {
        this.position = position;
        this.texture = new Texture(roadString + ".png");
        this.TILEWIDTH = TILEWIDTH;
        this.TILEHEIGHT = TILEHEIGHT;


    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(texture, position.x, position.y, TILEWIDTH, TILEHEIGHT);
    }

}