package com.mikroysoft.game;

// LibGDX Imports
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
// Java Imports
import java.lang.Math;
import java.util.Set;
import java.util.HashSet;

/* This class represents the small, walking and shooting aliens
 * that are spawned by AlienBases.
 * Aliens can move in a random direction, and fire towards the nearest fire engine when it is in range.
 * Aliens will die in one hit from a fire engine projectile TODO: To be implemented
 */
public class Alien extends Killable implements IRenderable {
	// Does This Alien know where the FireStation is?
	// TODO: Shouldn't this be handled in Game?
    public boolean LocationKnowlegde;
    // The rendered Alien texture
    public Texture texture;
    // Position of Alien on the screen
    public Coordinate position;
    
    // Direction the alien is currently moving
    public float direction;
    // Alien walk speed
    private float speed;
    // Point of reference for movement
    private Coordinate basePosition;
    
    //This is a factor of how much slower alien will shoot compared to fireengine
    private int countToFire;
    // How many frames have passed since the last projectile was fired
    private int currentFireCount;
    
    public AlienBase base;

    /* Constructor.
     * Position - Coordinate - spawn location of Alien
     */
    public Alien(Coordinate position, AlienBase base) {
        health = 1;
        maxHealth = 1;
        this.base = base;
        
    	// Set the texture to render
        texture = new Texture("alien.png");
        rectangle = new Rectangle(new Coordinate(position.x + texture.getWidth()/2, position.y + texture.getHeight()/2), texture.getWidth(), texture.getHeight(), 0);
        
        // Save parameters to variables
        basePosition = position;
        direction = 0;
        // Initialise speed to 2
        speed = 2;
        this.position = position;
        
        // by default, fire 50 times slower than the fire engine.
        countToFire = 50;
        currentFireCount = 0;
        // By default, shoot at fire engines within 300px of alien.
        range = 1;
        projectiles = new HashSet < Projectile> ();
        weapon = new WeaponBullet(countToFire, range, "bullet.png", position);
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
        
    //    direction = (float) Math.toDegrees(Math.atan2((position.y +(Math.random()* 10 + 1)) * -1,  position.x - (Math.random()* 10 + 1))) +45;
    }

    /* Render the alien and all of its fired projectiles onto the screen
     * batch - SpriteBatch - the batch rendering the current frame
     */
    @Override
    public void render(SpriteBatch batch) {
    	// Draw the alien
        batch.draw(texture,position.x,position.y,Util.TILEWIDTH/2,Util.TILEHEIGHT/2);
        // Loop over all fired bullets, and render each one
        for (Projectile currentBullet: projectiles) {
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
            projectiles.add(new Projectile (new Coordinate(position.x + texture.getWidth() / 2, position.invertY().y), destination, ProjectileType.BULLET, range));
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
    public void update() { }
    
    public int getRange() {
        return this.range; }

    public void doWeaponFiring(FireEngine[] fireEngines) {
        if (!dead && weapon != null) {
            Object firedObject = weapon.fire(fireEngines);
            if (firedObject != null) {
                if (firedObject instanceof Projectile) {
                    projectiles.add((Projectile) firedObject);
                }
            }
        }
    }
    
    @Override
    public void kill() {
        dead = true;
        base.currentAliens--;
    }
}
