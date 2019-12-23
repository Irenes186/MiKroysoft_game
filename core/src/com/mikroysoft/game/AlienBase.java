package com.mikroysoft.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class AlienBase implements IRenderable {
    public Texture texture;
    public int weaponRange;
    public String name;
    public int maxAliens;
    public int currentAliens;
    public int alienSpawnCountDown;
    public int maxAlienSpawnCountDown;
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
        
        // should this be in AlienBaseParameters?
        // set aliens to spawn every 500 frames that a truck is in range
        this.maxAlienSpawnCountDown = 500;
        
        this.randomGen = new Random();
    }

    public int increaseDefense () {
        return 3;
    }
    
    @Override
    public void render(SpriteBatch batch) {
        batch.draw(texture, position.x, position.y, TILEWIDTH, TILEHEIGHT);
    }
    
    /* Test fire truck presence. Decrease alien spawning counter
     * and spawn aliens as appropriate. Returns the spawned alien instance
     * Alien spawning counter is only decreased while a fire truck is in range.
     */
    public Alien defend(FireEngine[] fireEngines) {
    	if (this.alienSpawnCountDown == 0) {
    		for (FireEngine currentTruck: fireEngines) {
    			if (this.position.x - currentTruck.position.x <= this.weaponRange && 
    					this.position.y - currentTruck.position.y <= this.weaponRange) {
    				return this.spawnAlien();
    			}
    		}
    	}
    	this.alienSpawnCountDown--;
    	return null;
    }
    
    // TODO: IMPLEMENT
    // spawns an alien, and resets this.alienSpawnCountDown to its max.
    private Alien spawnAlien() {
    	this.alienSpawnCountDown = this.maxAlienSpawnCountDown;
    	Float[] offset = Util.randomCoordOffset(-1.0f, 1.0f, 0.8f);
    	return new Alien(new Coordinate(this.position.x + offset[0], this.position.y + offset[1]), this.TILEWIDTH, this.TILEHEIGHT);
    }
}
