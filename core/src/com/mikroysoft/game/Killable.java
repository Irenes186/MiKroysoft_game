package com.mikroysoft.game;

import java.util.HashSet;
import java.util.Set;

/** 
 * Objects that can shoot and be shot.
 */
public abstract class Killable {
    protected int health;
    protected Set<Projectile> projectiles;
    protected int maxHealth;
    protected boolean dead;
    protected Rectangle rectangle;
    protected int range;
    public Weapon weapon;

    /**
     * Returns all the projectiles that this object has fired that
     * are still in range.
     * 
     * @return  The set of projectiles that belong to this object.
     */
    public Set<Projectile> getProjectiles() {
        return projectiles;
    }

    /**
     * Overwrites the objects projectiles to a provided set of Projectile objects.
     * 
     * @param projectiles  The set of Projectile objects to overwrite
     * the objects current projectiles.
     */
    public void setProjectiles(Set<Projectile> projectiles) {
        this.projectiles = projectiles;
    }

    /**
     * Returns the current health of the object.
     * 
     * @return The health of the object
     */
    public int getHealth() {
        return health;
    }

    /**
     * Returns the Maximum value that this object can have
     * as set when the object constructor os called.
     * 
     * @return The maximum value that this objects health can have.
     */
    public int getMaxHealth() {
        return maxHealth;
    }

    /**
     * Checks and returns whether or not the current health is equal to
     * the value of the preset maxhealth of the object.
     * 
     * @return true if the current health of the object is the same as
     * the max health that this object can have, otherwise false.
     */
    public boolean isMaxHealth() {
        if(health == maxHealth) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Returns a boolean for whether the object is has been killed
     * this occurs primarily when the object has reached 0 health.
     * 
     * @return true if the object is dead, otherwise false.
     */
    public boolean isDead() {
        return dead;
    }

    /** 
     * Sets the dead variable to true 
     */
    public void kill() {
        dead = true;
    }

    /**
     * Causes the object to take the provided amount of damage
     * Prevents the object being on less than 0 health
     * If the object has 0 health, automatically calls the kill()
     * method to kill the object.
     * 
     * @param damage The amount of damage the object should take.
     */
    public void takeDamage (int damage) {
        if (damage > health) {
            health = 0;
            kill();
        } else {
            health -= damage;
        }
    }

    /**
     * Returns the range that the object can fire projectiles at.
     * 
     * @return The range of the object
     */
    public int getRange() {
        return range;
    }

    /**
     * Removes all the projectiles from this objects Set of Projectile objects
     * if the projectile has travelled outside the preset range of the object.
     */
    public void deleteOutOfRangeProjectiles () {
        Set <Projectile> removeProjectiles = new HashSet <Projectile>();

        for (Projectile projectile : projectiles) {
            if (!projectile.inRange()) {
                removeProjectiles.add(projectile);
            }
        }
        projectiles.removeAll(removeProjectiles);
    }
    /**
     * Returns the rectangle that represents where the object can detect
     * collisions against it.
     * 
     * @return The rectangle object for this object.
     */
    public Rectangle getRect() {
        return rectangle;
    }
}
