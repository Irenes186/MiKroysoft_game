package com.mikroysoft.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.HashSet;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

// TODO: Add health bars
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
    public boolean destroyed;
    public Rectangle rectangle;
    public List <Alien> aliens;
    private Set <Projectile> projectiles;
    private int spawnRate;
    private int TILEWIDTH, TILEHEIGHT;
    private int health;

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
        this.rectangle = new Rectangle (new Coordinate (position.x + TILEWIDTH / 2, position.y - TILEHEIGHT / 2), TILEWIDTH, TILEHEIGHT, 0);

        this.spawnRate = 50;
        this.aliens = new ArrayList<Alien>();
        this.maxAliens=5;

        // should this be in AlienBaseParameters?
        // set aliens to spawn every 500 frames that a truck is in range
        this.maxAlienSpawnCountDown = 500;
        this.alienSpawnCountDown = this.maxAlienSpawnCountDown;

        //        this.randomGen = new Random();

        this.projectiles = new HashSet <Projectile>();
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

    //    public void spawnAlien() {
    //        if ((int)(Math.random()*this.spawnRate) != 1 || aliens.size()>=maxAliens) {
    //            return;
    //        }
    //        aliens.add(new Alien(this.position,this.TILEWIDTH,this.TILEHEIGHT));
    //        System.out.println(aliens);
    //    }

    public void update() {
        this.spawnAlien();
        for (Alien alien:this.aliens) {
            alien.move();
        }
    }

    /* Test fire truck presence. Decrease alien spawning counter
     * and spawn aliens as appropriate. Returns the spawned alien instance
     * Alien spawning counter is only decreased while a fire truck is in range.
     *
     * TODO: Add progress bars for alien spawning
     */
    public Alien defend(FireEngine[] fireEngines) {
        // Debug: # of frames until this base spawns a new alien.
        // System.out.println("Alien cooldown " + this.name + ": " + this.alienSpawnCountDown);
        boolean truckInRange = false;
        for (FireEngine currentTruck: fireEngines) {
            if (java.lang.Math.abs(this.position.x - currentTruck.position.x) <= ((this.weaponRange+1) * TILEWIDTH) &&
                    java.lang.Math.abs(this.position.y - (Gdx.graphics.getHeight()-currentTruck.position.y)) <= ((this.weaponRange+1) * TILEHEIGHT)) {
                if (this.alienSpawnCountDown == 0) {
                    return this.spawnAlien();
                }
                truckInRange = true;
                    }
        }
        if (truckInRange) {
            this.alienSpawnCountDown--;
        }
        return null;
    }

    // TODO: IMPLEMENT
    // spawns an alien, and resets this.alienSpawnCountDown to its max.
    private Alien spawnAlien() {
        this.alienSpawnCountDown = this.maxAlienSpawnCountDown;
        Float[] offset = Util.randomCoordOffset(-((float)TILEWIDTH/2), ((float)TILEWIDTH/2), 0.8f);
        return new Alien(new Coordinate(this.position.x + (TILEWIDTH/2) + offset[0], this.position.y + (TILEHEIGHT/2) + offset[1]), this.TILEWIDTH, this.TILEHEIGHT);
    }

    public Set <Projectile> getProjectileList() {
        return projectiles;
    }

    public void takeDamage (int damage) {
        if (damage > this.health) {
            this.health = 0;
        } else {
            this.health -= damage;
        }
    }

    public void setProjectiles (Set <Projectile> projectiles) {
        this.projectiles = projectiles;
    }
}
