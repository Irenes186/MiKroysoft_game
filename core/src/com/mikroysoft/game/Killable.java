package com.mikroysoft.game;

import java.util.HashSet;
import java.util.Set;

/* Objects that can shoot and be shot.
 */
public abstract class Killable {
    protected int health;
    protected Set<Projectile> projectiles;
    protected int maxHealth;
    protected boolean dead;
    protected Rectangle rectangle;
    protected int range;
    public Weapon weapon;
    
    public Set<Projectile> getProjectiles() {
        return projectiles;
    }
    
    public void setProjectiles(Set<Projectile> projectiles) {
        this.projectiles = projectiles;
    }
    
    public int getHealth() {
        return health;
    }
    
    public int getMaxHealth() {
        return maxHealth;
    }
    
    public boolean isMaxHealth() {
        if(health == maxHealth) {
            return true;
        } else {
            return false;
        }
    }
    
    public boolean isDead() {
        return dead;
    }
    
    public void kill() {
        dead = true;
    }
    
    public void takeDamage (int damage) {
        if (damage > health) {
            health = 0;
            kill();
        } else {
            health -= damage;
        }
    }
    
    public int getRange() {
        return range;
    }
    
    public void deleteOutOfRangeProjectiles () {
        Set <Projectile> removeProjectiles = new HashSet <Projectile>();

        for (Projectile projectile : projectiles) {
            if (!projectile.inRange()) {
                removeProjectiles.add(projectile);
            }
        }
        projectiles.removeAll(removeProjectiles);
    }
    
    public Rectangle getRect() {
        return rectangle;
    }
    
    // public abstract void doWeaponFiring();
}
