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
 * Aliens can move in a random direction, and fire towards the nearest fire engine when it is in range.
 * Aliens will die in one hit from a fire engine projectile TODO: To be implemented
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
    // How many frames have passed since the last projectile was fired
    private int currentFireCount;
    private int shootOffset;
    // List of spawned Projectile objects - i.e bullets - to be used in collision detection
    private List < Projectile > projectiles;
    // The maximum distance a fireengine can be from the alien before alien begins firing at it
    private int shootRange;

    /* Constructor.
     * Position - Coordinate - spawn location of Alien
     * TILEWIDTH, TILEHEIGHT - int - game grid tile dimentions
     */
    public Alien(Coordinate position, int TILEWIDTH, int TILEHEIGHT) {
    	// Set the texture to render
        texture = new Texture("alien.png");
        // Save parameters to variables
        this.position = new Coordinate(position);
        this.TILEHEIGHT = TILEHEIGHT;
        this.TILEWIDTH = TILEWIDTH;
        this.basePosition = position;
        direction = 0;
        // Initialise speed to 2
        speed = 2;
        
        // by default, fire 50 times slower than the fire engine.
        countToFire = 50;
        currentFireCount = 0;
        shootOffset = 10;
        // By default, shoot at fire engines within 200px of alien.
        shootRange = 200;
        projectiles = new ArrayList < Projectile> ();
    }

    /* Get the position of the alien
     * returns Coordinate
     */
    public Coordinate getLatestPosition() {
        return this.position;
    }
    
    /* Move the alien in a random direction
     * TODO: Does not take speed into account. Also why does it move in a random direction?
     */
    public void move(){
    	// Move by a random amount along each axis, from 0 to 5 px
        position.x += (int)((Math.random()*10)-5); 
        position.y += (int)((Math.random()*10)-5);
        
        // clamp the maximum distance from spawn position to 100 px in any direction
        // clamp in +ve x direction
        if (position.x>basePosition.x+100)
            position.x-=5;
        // clamp in -ve x direction
        if (position.x<basePosition.x-100)
            position.x+=5;
        // clamp in -ve y direction
        if (position.y>basePosition.y+100)
            position.y-=5;
        // clamp in +ve y direction
        if (position.y<basePosition.y-100)
            position.y+=5;
        
        // TODO: Can we delete this?
    //    direction = (float) Math.toDegrees(Math.atan2((position.y +(Math.random()* 10 + 1)) * -1,  position.x - (Math.random()* 10 + 1))) +45;
    }

    /* Render the alien and all of its fired projectiles onto the screen
     * batch - SpriteBatch - the batch rendering the current frame
     */
    @Override
    public void render(SpriteBatch batch) {
    	// Draw the alien
        batch.draw(texture,position.x,position.y,TILEWIDTH/2,TILEHEIGHT/2);
        // Loop over all fired bullets, and render each one
        for (Projectile currentBullet: this.projectiles) {
        	currentBullet.render(batch);
        }
    }

    /* Fire a new projectile in a linear path towards a given coordinate.
     * destination - Coordinate - the coordinate to aim the projectile at
     */
    public void shoot(Coordinate destination) {
    	// Check we are within the firing rate
        if (currentFireCount >= countToFire) {
        	// Spawn a new projectile
            projectiles.add(new Projectile (new Coordinate(position.x + shootOffset, Gdx.graphics.getHeight() - position.y), destination, true, ProjectileType.BULLET));
            // reset the frames-since-fired tracker
            currentFireCount = 0;
        
        // If we are not within firing rate, wait another frame
        } else {
            currentFireCount++;
        }
    }
    
    /* Update the Alien
     * TODO: Implement (or delete)
     */
    public void update() {
        
    };
}
