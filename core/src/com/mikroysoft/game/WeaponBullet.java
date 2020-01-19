package com.mikroysoft.game;

/**
 * Fires all the projectiles by extending the abstract weapon
 */
public class WeaponBullet extends Weapon {

    /**
     * Class Constructor.
     * 
     * @param cooldown Passed to the weapon Constructor.
     * @param range Passed to the weapon Constructor.
     * @param tex Passed to the weapon Constructor.
     * @param position Passed to the weapon Constructor.
     */
    public WeaponBullet(int cooldown, int range, String tex, Coordinate position) {
        super(cooldown, range, tex, position, 15);
    }

    /**
     * Creates a projectile to fire at the closest fireEngine.
     * 
     * @param fireEngines An array of fire engine objects.
     * 
     * @return A projectile that is firing at the nearest fire engine.
     */
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

    /**
     * Creates a projectile that fires at the provided destination.
     * 
     * @param destination The coordiante of a projectile to be created.
     * 
     * @return a projectile that is aimed at the provided destination
     * and starts at this objects position.
     */
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
