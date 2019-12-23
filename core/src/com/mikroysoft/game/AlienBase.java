package com.mikroysoft.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class AlienBase implements IRenderable {
    public Texture texture;
    public float weaponRange;
    public String name;
    public int maxAliens;
    public int currentAliens;
    //Changed from String[] as weapon type does not change.
    public String weaponType;
    public int floodLevel;
    public int attackRange;
    public int attackTimeAfterFirst;
    public Coordinate position;
    private int TILEWIDTH, TILEHEIGHT;

    // [!] Is it really necessary to store the name of the base? I dont think so.
    public AlienBase(String name, AlienBaseParameters params, Coordinate position, int TILEWIDTH, int TILEHEIGHT, String tex) {
        texture = new Texture(tex + ".png");
        this.name = name;
        this.position = position;
        this.weaponRange = params.weaponRange;
        this.maxAliens = params.maxAliens;
        this.weaponType = params.weaponType;
        this.floodLevel = params.floodLevel;
        this.attackRange = params.attackRange;
        this.attackTimeAfterFirst = params.attackTimeAfterFirst;
        this.TILEWIDTH = TILEWIDTH;
        this.TILEHEIGHT = TILEHEIGHT;
    }

    public int increaseDefense () {
        return 3;
    }
    
    @Override
    public void render(SpriteBatch batch) {
        batch.draw(texture, position.x, position.y, TILEWIDTH, TILEHEIGHT);
    }
    
    /* Test fire truck presence. Decrease alien spawning counter
     * and spawn aliens as appropriate.
     * Alien spawning counter is only decreased while a fire truck is in range.
     */
    public void defend() {
    	
    }

}
