package com.mikroysoft.game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Alien implements IRenderable {
    public boolean LocationKnowlegde;
    public Texture texture;
    public Coordinate position;
    private int TILEWIDTH, TILEHEIGHT;
    public float direction;

    public Alien(Coordinate position, int TILEWIDTH, int TILEHEIGHT){
        texture = new Texture("alien.png");
        this.position = position;
        this.TILEHEIGHT = TILEHEIGHT;
        this.TILEWIDTH = TILEWIDTH;
    }

    public float Run(){
        return 3.6f;
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw (texture, position.x, position.y, TILEWIDTH * 20, TILEHEIGHT * 20);
    }

}