package com.mikroysoft.game;

public class WeaponBullet extends Weapon {
    public WeaponBullet(int cooldown, int range, String tex, Coordinate position) {
        super(cooldown, range, tex, position, 15);
    }
    
    @Override
    public Projectile fire(FireEngine[] fireEngines) {
        // Check we are within the firing rate
        if (!onCooldown()) {
            if (fireEngines.length < 1) {
                return null;
            }
            FireEngine target = null;
            float currentClosestDist = -1;
            
            for (FireEngine truck: fireEngines) {
                // 
                if (truckInRange(truck) && !truck.isDead() && (currentClosestDist == -1 || position.cellDistanceTo(truck.position) < currentClosestDist)) {
                    currentClosestDist = position.cellDistanceTo(truck.position);
                    target = truck;
                }
            }
            
            if (target == null || currentClosestDist == -1) {
                return null;
            }
            
            resetCooldown();
            
            // Spawn a new projectile
            return new Projectile (new Coordinate(position.x, position.invertY().y), target.position, ProjectileType.BULLET, (int) Math.round(currentClosestDist));
            
        
        // If we are not within firing rate, wait another frame
        } else {
            doCooldown();
        }
        return null;
    }
    
    public Projectile fire(Coordinate destination) {
        // Check we are within the firing rate
        if (!onCooldown()) {
            resetCooldown();
            
            // Spawn a new projectile
            return new Projectile (new Coordinate(position.x, position.y), destination, ProjectileType.WATER, range);
            
        
        // If we are not within firing rate, wait another frame
        } else {
            doCooldown();
        }
        return null;
    }

}
