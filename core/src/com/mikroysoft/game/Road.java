package com.mikroysoft.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Road implements IRenderable {

    private Texture texture;
    public Coordinate position;

    public Road (Coordinate position, String roadString) {
        this.position = position;
        this.texture = new Texture(roadString + ".png");
    }
    public void update() { }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(texture, position.x, position.y, Util.TILEWIDTH, Util.TILEHEIGHT);
    }

}
