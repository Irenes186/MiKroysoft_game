package com.mikroysoft.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture;

public class FireStation implements IRenderable {
    public boolean destroyed;
    private int fillSpeed;
    public Texture texture;
    private Coordinate position;

    public FireStation(int fillSpeed, Coordinate position){
        destroyed = false;
        this.fillSpeed = fillSpeed;
        this.position = position;
        texture = new Texture("station.png");
    }

    public Coordinate getPosition() {
        return position;
    }

    public void destroy() {
        destroyed = true;
    }
    
    public boolean isDestroyed() {
        return this.destroyed;
    }
    
    public void update() { }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw (texture, position.x, position.y, Util.TILEWIDTH * 2, Util.TILEHEIGHT);
    }
}
