package com.mikroysoft.game;

// Java imports
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.ArrayList;

//LibGDX Imports
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


/* This class represents the buildings found throughout the map that the player must
 * destroy in order to win the game.
 * FireStations have a finite amount of health, which is depleted using the FireEngine's
 * water projectiles.
 * 
 * AlienBases can defend themselves with their own unique weapon, defined by weaponType
 * and bounded by weaponRange.
 * FireStations can occasionally spawn new Aliens, which will defend the base.
 * 
 * TODO: ADD HEALTH BARS
 */
public class AlienBase implements IRenderable {
	// Texture of this base
    public Texture texture;
    // Range in game grid cells that a fire engine must be within before the base will fire at it.
    public int weaponRange;
    // Name of this base, e.g Aldi, for debugging
    public String name;
    // Maximum number of aliens that can be alive at any time around this base
    public int maxAliens;
    // Number of aliens current alive around this base
    public int currentAliens;
    // Number of frames left to wait until spawning an alien
    public int framesLeftUntilSpawn;
    // Number of frames the base will wait in between spawning aliens
    public int spawnRate;
    // The weapon mounted on the base
    public BaseWeapon weapon;
    // Health - renamed from floodLevel
    public int health;
    // TODO: Is this the same as weaponRange?
    public int attackRange;
    // Once an AlienBase is destroyed, wait this number of frames before destroying the FireStation.
    public int attackTimeAfterFirst;
    // Position in pixels of this base on the screen
    public Coordinate position;
    // game grid cell dimensions
    private int TILEWIDTH, TILEHEIGHT;
    // has this base been destroyed by the player?
    public boolean destroyed;
    // Things created by the BaseWeapon that need rendering and are not projectiles (i.e not using projectile collision). e.g sprites
    // WARNING: Not type checked, since Sprites and Textures do not share a super class.
    // TAKE CARE WHEN CREATING NEW BASEWEAPONS.
    private HashSet<Object> weaponObjects;
    
    public Rectangle rectangle;
    public List <Alien> aliens;
    // projectiles fired by the base
    private Set <Projectile> projectiles;

    /* Constructor.
     * name - String - name of the base for debugging purposes
     * params - AlienBaseParameters - various properties and behaviour of this base
     * position - Coordinate - position in pixels of this base. TODO: Shouldnt this be in grid cells?
     * TILEWIDTH, TILEHEIGHT - int - game grid cell dimensions
     * tex - String - name of the texture file in assets folder to use with this base
     */
    public AlienBase(String name, AlienBaseParameters params, Coordinate position, int TILEWIDTH, int TILEHEIGHT, String tex) {
        // Save parameters to local variables
    	texture = new Texture(tex + ".png");
        this.name = name;
        this.position = position;
        this.weaponRange = params.weaponRange;
        this.maxAliens = params.maxAliens;
        //this.weapon = params.weapon;
        //this.weapon = new WeaponLaser(params.weaponRange, position, TILEWIDTH, TILEHEIGHT);
        this.weapon = new WeaponBullet(10, 1, "laser.png", position, TILEWIDTH, TILEHEIGHT);
        this.health = params.floodLevel;
        this.attackRange = params.attackRange;
        this.attackTimeAfterFirst = params.attackTimeAfterFirst;
        this.TILEWIDTH = TILEWIDTH;
        this.TILEHEIGHT = TILEHEIGHT;
        this.spawnRate = params.spawnRate;
        
        // Initialise base to wait spawnRate before spawning anything
        this.framesLeftUntilSpawn = this.spawnRate;
        weaponObjects = new HashSet<Object>();
        projectiles = new HashSet<Projectile>();
        this.rectangle = new Rectangle (new Coordinate (position.x + TILEWIDTH / 2, position.y + TILEHEIGHT / 2), TILEWIDTH, TILEHEIGHT, 0);
        this.aliens = new ArrayList<Alien>();
    }

    /* Increase aggression of base.
     * TODO: Implement. Could increase spawn rate, max aliens (may not be possible), or fire rate of base's weapon
     */
    public int increaseDefense () {
        return 3;
    }

    @Override
    /* Render the base's texture onto the screen.
     * batch - SpriteBatch - the batch rendering the current frame
     */
    public void render(SpriteBatch batch) {
        batch.draw(texture, position.x, position.y, TILEWIDTH, TILEHEIGHT);
        for (Object weaponObj: weaponObjects) {
            if (weaponObj instanceof Sprite) {
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

    /* Test fire truck presence. Decrease alien spawning counter
     * and spawn aliens as appropriate. Returns the spawned alien instance
     * Alien spawning counter is only decreased while a fire truck is in range.
     * 
     * fireEngines - FireEngine[] - array containing all currently alive fire engines
     * returns an Alien instance was spawned, null otherwise
     *
     * TODO: Add progress bars for alien spawning
     */
    public Alien doAlienSpawning(FireEngine[] fireEngines) {
    	// Debug: # of frames until this base spawns a new alien.
    	// System.out.println("Alien cooldown " + this.name + ": " + this.alienSpawnCountDown);
    	// Check all fire engines
    	
    	for (FireEngine currentTruck: fireEngines) {
    		// Is the fire engine within weaponRange?
			if (java.lang.Math.abs(this.position.x - currentTruck.position.x) <= ((this.weaponRange+1) * TILEWIDTH) &&
					java.lang.Math.abs(this.position.y - (Gdx.graphics.getHeight()-currentTruck.position.y)) <= ((this.weaponRange+1) * TILEHEIGHT)) {
				
				// Has the spawning countdown passed?
				if (this.framesLeftUntilSpawn == 0) {
					// Reset the spawning cooldown
					this.framesLeftUntilSpawn = this.spawnRate;
					// spawn and return an alien
					return this.spawnAlien();
				}
				
				// If cooldown has not been passed, decrease it and don't spawn anything.
				//truckInRange = true;
				this.framesLeftUntilSpawn--;
				return null;
			}
		}
    	
    	// If no FireEngines were found in range, cooldown will not have been reduced.
    	// Handle that, and dont't spawn anything.
    	if (this.framesLeftUntilSpawn > 0) {
			this.framesLeftUntilSpawn--;
		}
    	return null;
    }

    public Alien defend(FireEngine[] fireEngines) {
        // Debug: # of frames until this base spawns a new alien.
        // System.out.println("Alien cooldown " + this.name + ": " + this.alienSpawnCountDown);
        boolean truckInRange = false;
        for (FireEngine currentTruck: fireEngines) {
            if (java.lang.Math.abs(this.position.x - currentTruck.position.x) <= ((this.weaponRange+1) * TILEWIDTH) &&
                    java.lang.Math.abs(this.position.y - (Gdx.graphics.getHeight()-currentTruck.position.y)) <= ((this.weaponRange+1) * TILEHEIGHT)) {
                if (this.framesLeftUntilSpawn == 0) {
                    return this.spawnAlien();
                }
                truckInRange = true;
                    }
        }
        if (truckInRange) {
            this.framesLeftUntilSpawn--;
        }
        return null;
    }

    /* spawns an alien around the base (but not on top of it)
     * returns the spawned alien object.
     */
    private Alien spawnAlien() {
        this.framesLeftUntilSpawn = this.spawnRate;
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
    
    public void doWeaponFiring(FireEngine[] fireEngines) {
        if (this.weapon != null) {
            Object firedObject = this.weapon.fire(fireEngines);
            if (firedObject != null) {
                if (firedObject instanceof Projectile) {
                    projectiles.add((Projectile) firedObject);
                } else {
                    weaponObjects.add(firedObject);
                }
            }
        }
    }
    
    public Rectangle getRect() {
        if (this.rectangle == null) {
            throw new NullPointerException("Alien base " + name + " rectangle not initialized before use in collisions");
        }
        return this.rectangle;
    }
    
    public int getHealth() {
        return this.health;
    }
}
