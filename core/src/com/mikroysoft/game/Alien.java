package com.mikroysoft.game;

// LibGDX Imports
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
// Java Imports
import java.lang.Math;
import java.util.ArrayList;
import java.util.List;

/* This class represents the small, walking and shooting aliens
 * that are spawned by AlienBases.
 */
public class Alien implements IRenderable {
	// Does This Alien know where the FireStation is?
	// TODO: Shouldn't this be handled in Game?
    public boolean LocationKnowlegde;
    // The rendered Alien texture
    public Texture texture;
    // Position of Alien on the screen
    public Coordinate position;
    // Game grid cell dimensions
    private int TILEWIDTH, TILEHEIGHT;
    // Direction the alien is currently moving
    public float direction;
    private float speed;
    // Point of reference for movement
    private Coordinate basePosition;
    //This is a factor of how much slower alien will shoot compared to fireengine
    private int countToFire;
    // How many times the alien has shot
    private int currentFireCount;
    private int shootOffset;
    // List of spawned Projectile objects - i.e bullets - to be used in collision detection
    private List < Projectile > projectiles;
    // The maximum distance a fireengine can be from the alien before alien begins firing at it
    private int shootRange;

    public Alien(Coordinate position, int TILEWIDTH, int TILEHEIGHT) {
        texture = new Texture("alien.png");
        this.position = new Coordinate(position.x,position.y);
        this.TILEHEIGHT = TILEHEIGHT;
        this.TILEWIDTH = TILEWIDTH;
        this.basePosition = position;
        direction = 0;
        speed = 2;

        countToFire = 50;
        currentFireCount = 0;
        shootOffset = 10;
        shootRange = 200;
        projectiles = new ArrayList < Projectile> ();
    }

    public Coordinate getLatestPosition() {
        return this.position;
    }
    public void move(){
        
        position.x += (int)((Math.random()*10)-5); 
        position.y += (int)((Math.random()*10)-5);
        if (position.x>basePosition.x+100)
            position.x-=5;
        if (position.x<basePosition.x-100)
            position.x+=5;
        if (position.y>basePosition.y+100)
            position.y-=5;
        if (position.y<basePosition.y-100)
            position.y+=5;
    //    direction = (float) Math.toDegrees(Math.atan2((position.y +(Math.random()* 10 + 1)) * -1,  position.x - (Math.random()* 10 + 1))) +45;
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(texture,position.x,position.y,TILEWIDTH/2,TILEHEIGHT/2);
        for (int i = 0; i < projectiles.size(); i++) {
            projectiles.get(i).render(batch);
        }
    }

    public void shoot(Coordinate destination) {
        if (currentFireCount >= countToFire) {
            projectiles.add(new Projectile (new Coordinate(position.x + shootOffset, Gdx.graphics.getHeight() - position.y), destination, true, ProjectileType.BULLET));
            
            currentFireCount = 0;
        } else {
            currentFireCount++;
        }
    }
    
    public void update() {
        
    };
}
