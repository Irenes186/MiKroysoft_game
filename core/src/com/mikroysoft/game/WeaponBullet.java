package com.mikroysoft.game;

public class WeaponBullet extends BaseWeapon {
    public WeaponBullet(int cooldown, int range, String tex, Coordinate position) {
        super(cooldown, range, tex, position);
    }
    
    @Override
    public Object fire(FireEngine[] fireEngines) {
        // Check we are within the firing rate
        if (!onCooldown()) {
            if (fireEngines.length < 1) {
                return null;
            }
            FireEngine target = null;
            float currentClosestDist = -1;
            
            for (FireEngine truck: fireEngines) {
                // 
                if (truckInRange(truck) && (currentClosestDist == -1 || position.cellDistanceTo(truck.position) < currentClosestDist)) {
                    currentClosestDist = position.cellDistanceTo(truck.position);
                    target = truck;
                }
            }
            
            if (target == null || currentClosestDist == -1) {
                return null;
            }

            resetCooldown();
            
            resetCooldown();
            
            // Spawn a new projectile
            return new Projectile (new Coordinate(position.x, position.invertY().y), target.position, true, ProjectileType.BULLET, (int) Math.round(currentClosestDist));
            
        
        // If we are not within firing rate, wait another frame
        } else {
            doCooldown();
        }
        return null;
    }

}
