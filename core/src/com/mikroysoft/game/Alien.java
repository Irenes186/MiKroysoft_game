package com.mikroysoft.game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.lang.Math;

public class Alien implements IRenderable {
    public boolean LocationKnowlegde;
    public Texture texture;
    public Coordinate position;
    private int TILEWIDTH, TILEHEIGHT;
    public float direction;
    private float speed;
    String x;

    public Alien(Coordinate position, int TILEWIDTH, int TILEHEIGHT) {
        texture = new Texture("alien.png");
        this.position = position;
        this.TILEHEIGHT = TILEHEIGHT;
        this.TILEWIDTH = TILEWIDTH;
        direction = 1;
        speed = 2;
    }

    public void Run(int width, int height){
       position.x += Integer.signum(position.y + (int)(Math.random()))*2;
        position.y += Integer.signum(position.y + (int)(Math.random()))*2;
        direction = (float) Math.toDegrees(Math.atan2((position.y +(Math.random()* 10 + 1)) * -1,  position.x - (Math.random()* 10 + 1))) +45;
        if (position.x >= width * this.TILEWIDTH|| position.x <= 0) {
            //rule to be defined
            direction *= -1;
        }
        if (position.y >= (height * this.TILEHEIGHT) || position.y <= 0) {
            //rule to be defined
            direction *= -1;
        }
        System.out.println(direction);
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw (texture, position.x, position.y, TILEWIDTH * 2, TILEHEIGHT * 2);
    }
}