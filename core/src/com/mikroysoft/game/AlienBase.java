package com.mikroysoft.game;

// Java imports
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

import com.badlogic.gdx.Gdx;
//LibGDX Imports
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


/** This class represents the buildings found throughout the map that the player must
 * destroy in order to win the game.
 * FireStations have a finite amount of health, which is depleted using the FireEngine's
 * water projectiles.
 * 
 * AlienBases can defend themselves with their own unique weapon, defined by weaponType
 * and bounded by weaponRange.
 * FireStations can occasionally spawn new Aliens, which will defend the base.
 * 
 */
public class AlienBase extends Killable implements IRenderable {
	// Texture of this base
    public Texture texture;
    // Name of this base, e.g Aldi, for debugging
    public String name;
    // Name of the texture file to populate into texture
    public String tex;
    // Maximum number of aliens that can be alive at any time around this base
    public int maxAliens;
    // Number of aliens current alive around this base
    public int currentAliens;
    // Number of frames left to wait until spawning an alien
    public int framesLeftUntilSpawn;
    // Number of frames the base will wait in between spawning aliens
    public int spawnRate;
    // TODO: Is this the same as weaponRange?
    // Once an AlienBase is destroyed, wait this number of frames before destroying the FireStation.
    public int attackTimeAfterFirst;
    // Position in pixels of this base on the screen
    public Coordinate position;
    // Things created by the BaseWeapon that need rendering and are not projectiles (i.e not using projectile collision). e.g sprites
    // WARNING: Not type checked, since Sprites and Textures do not share a super class.
    // TAKE CARE WHEN CREATING NEW BASEWEAPONS.
    private HashSet<Object> weaponObjects;
    
    public List <Alien> aliens;

    /**
     * @param name - String - name of the base for debugging purposes
     * @param params - AlienBaseParameters - various properties and behaviour of this base
     * @param position - Coordinate - position in pixels of this base. TODO: Shouldnt this be in grid cells?
     * @param tex - String - name of the texture file in assets folder to use with this base
     * @return void
     */
    public AlienBase(String name, AlienBaseParameters params, Coordinate position, String tex) {
        // Save parameters to local variables
    	texture = new Texture(tex + ".png");
        this.tex = tex;
        this.name = name;
        this.position = position;
        currentAliens = 0;
        maxAliens = params.maxAliens;
        
        switch (params.weaponType) {
//            case BULLET:
//                weapon = new WeaponBullet(10, params.weaponRange, "bullet.png", position);
//            case LASER:
//                weapon = new WeaponLaser(params.weaponRange, position);
//            default:
//                weapon = null;
            default:
                weapon = new WeaponBullet(10, params.weaponRange, "bullet.png", position);
        }
        
        health = params.floodLevel;
        attackTimeAfterFirst = params.attackTimeAfterFirst;
        spawnRate = params.spawnRate;

        // Initialise base to wait spawnRate before spawning anything
        framesLeftUntilSpawn = this.spawnRate;
        weaponObjects = new HashSet<Object>();
        projectiles = new HashSet<Projectile>();
        rectangle = new Rectangle (new Coordinate (position.x + Util.TILEWIDTH / 2, position.y + Util.TILEHEIGHT / 2), Util.TILEWIDTH, Util.TILEHEIGHT, 0);
        aliens = new ArrayList<Alien>();
        dead = false;
    }

    /** Increases the aggression of the base, currently it returns a constant of 3 as increase
     * aggression has not yet been implemented.
     * @return int - The constant factor to increase the difficulty by.
     */
    public int increaseDefense () {
        return 3;
    }

    @Override
    /**
     * @param Render the base's texture onto the screen.
     * @param batch - SpriteBatch - the batch rendering the current frame
     * @return void
     */
    public void render(SpriteBatch batch) {
        batch.draw(texture, position.x, position.y, Util.TILEWIDTH, Util.TILEHEIGHT);
        for (Object weaponObj: weaponObjects) {
            if (weaponObj instanceof Sprite) {
                //((Sprite) weaponObj).setScale(Util.TILEWIDTH, Util.TILEHEIGHT);
                ((Sprite) weaponObj).draw(batch);
            } else if (weaponObj instanceof Projectile) {
                ((Projectile) weaponObj).render(batch);
            } else {
                throw new IllegalArgumentException("Base " + name + " attempted to render unrecognised BaseWeapon object");
            }
        }
        
        for (Projectile proj: projectiles) {
            proj.render(batch);
        }
    }

    /* Update the base
     * TODO: Implement
     */
    public void update() {

    }

    /** Test fire truck presence. Decrease alien spawning counter
     * and spawn aliens as appropriate. Returns the spawned alien instance
     * Alien spawning counter is only decreased while a fire truck is in range.
     * 
     *@param fireEngines - FireEngine[] - array containing all currently alive fire engines
     *@return returns an Alien instance was spawned, null otherwise
     */
    //@Override
    public Alien doAlienSpawning(FireEngine[] fireEngines) {
    	// Check all fire engines
    	for (FireEngine currentTruck: fireEngines) {
    		// Is the fire engine within weaponRange?
			if (weapon.truckInRange(currentTruck)) {
			    // Debug: # of frames until this base spawns a new alien.
			    // System.out.println("Alien cooldown " + name + ": " + framesLeftUntilSpawn);
				// Has the spawning countdown passed?
				if (framesLeftUntilSpawn == 0) {
					// Reset the spawning cooldown
					framesLeftUntilSpawn = spawnRate;
					// spawn and return an alien
					if (currentAliens < maxAliens) {
					    currentAliens++;
					    return spawnAlien();
					} else {
					    return null;
					}
				}
				
				// If cooldown has not been passed, decrease it and don't spawn anything.
				//truckInRange = true;
				framesLeftUntilSpawn--;
				return null;
			}
		}
    	
    	// If no FireEngines were found in range, dont't spawn anything.
    	return null;
    }

    /** spawns an alien around the base (but not on top of it)
     * @return returns the spawned alien object.
     */
    private Alien spawnAlien() {
        Float[] offset = Util.randomCoordOffset(-((float)Util.TILEWIDTH/2), ((float)Util.TILEWIDTH/2), 0.8f);
        return new Alien(new Coordinate(position.x + (Util.TILEWIDTH/2) + offset[0], position.y + (Util.TILEHEIGHT/2) + offset[1]), this);
    }
    
    
    /** Fires the weapon that is asscociated with each fire engine in the fireEngines argument
     * @param fireEngines - This is an array of fireEngines
     * @return void
     */
    public void doWeaponFiring(FireEngine[] fireEngines) {
        if (!dead && weapon != null) {
            Object firedObject = weapon.fire(fireEngines);
            if (firedObject != null) {
                if (firedObject instanceof Projectile) {
                    projectiles.add((Projectile) firedObject);
                } else {
                    weaponObjects.add(firedObject);
                }
            }
        }
    }
    /** This checks whether the health is 0 and sets the texture to a flooded version if it is 0.
     * @return void
     */
    public void checkHealth() {
        if (health == 0) {
            this.texture = new Texture ("soggy-" + tex + ".png");
        }
    }
}
