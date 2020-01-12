package com.mikroysoft.game;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class AlienBase implements IRenderable {
    public Texture texture;
    public float weaponRange;
    public String name;
    public int aliensNumber;
    //Changed from String[] as weapon type does not change.
    public String weaponType;
    public int floodLevel;
    public int attackRange;
    public int attackTimeAfterFirst;
    public Coordinate position;
    private int TILEWIDTH, TILEHEIGHT;
    public List <Alien> aliens;
    public boolean destroyed;
    private int spawnRate;
    private int maxAliens;

    // [!] Is it really necessary to store the name of the base? I dont think so...
    public AlienBase(String name, AlienBaseParameters params, Coordinate position, int TILEWIDTH, int TILEHEIGHT, String tex) {
        texture = new Texture(tex + ".png");
        this.name = name;
        this.position = position;
        this.weaponRange = params.weaponRange;
        this.aliensNumber = params.aliensNumber;
        this.weaponType = params.weaponType;
        this.floodLevel = params.floodLevel;
        this.attackRange = params.attackRange;
        this.attackTimeAfterFirst = params.attackTimeAfterFirst;
        this.TILEWIDTH = TILEWIDTH;
        this.TILEHEIGHT = TILEHEIGHT;
        this.spawnRate = 50;
        this.aliens = new ArrayList<Alien>();
        this.maxAliens=5;
    }

    public int increaseDefense () {
        return 3;
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(texture, position.x, position.y, TILEWIDTH, TILEHEIGHT);
        for (Alien alien:this.aliens) {
            alien.render(batch);
        }
    }
    
    public void spawnAlien() {
        if ((int)(Math.random()*this.spawnRate) != 1 || aliens.size()>=maxAliens) {
            return;
        }
        aliens.add(new Alien(this.position,this.TILEWIDTH,this.TILEHEIGHT));
        System.out.println(aliens);
    }
    
    public void update() {
        this.spawnAlien();
        for (Alien alien:this.aliens) {
            alien.move();
        }
    }
}
