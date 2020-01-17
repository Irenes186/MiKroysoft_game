package com.mikroysoft.game;

import com.badlogic.gdx.graphics.g2d.Sprite;

/* Lasers have no cooldown and perfect aim, but take time to move aim towards a target,
 * meaning they are easy to evade.
 * Shhhhh... super secret strat: have 2 trucks at different positions around a laser tower, rotating each one in and out of range. Since aim can only move so fast, it's possible to avoid getting hit all together!
 */
public class WeaponLaser extends BaseWeapon {
    // The angle in which the weapon is currently facing.
    private float currentAimAngle;
    // The speed at which currentAimAngle will be altered towards a target
    private float aimSpeed;
    // Texture sprite to render the laser with
    Sprite laserSprite;
    
    public WeaponLaser(int range, Coordinate position, int TILEWIDTH, int TILEHEIGHT, float aimSpeed) {
        super(0, range, "laser.png", position, TILEWIDTH, TILEHEIGHT);
        currentAimAngle = 0;
        this.aimSpeed = aimSpeed;
        laserSprite = new Sprite(texture);
        laserSprite.setPosition(position.x, position.y);
    }
    
    public WeaponLaser(int range, Coordinate position, int TILEWIDTH, int TILEHEIGHT) {
        super(0, range, "laser.png", position, TILEWIDTH, TILEHEIGHT);
        currentAimAngle = 0;
        this.aimSpeed = 0.1f;
        laserSprite = new Sprite(texture);
        laserSprite.setPosition(position.x, position.y);
    }
    
    @Override
    /* IM A FIRIN MY LASER
     */
    public Object fire(FireEngine[] fireEngines) {
        if (fireEngines.length < 1) {
            return null;
        }
        FireEngine target = null;
        float currentClosestDist = -1;
        
        for (FireEngine truck: fireEngines) {
            if (currentClosestDist == -1 || position.cellDistanceTo(truck.position, TILEWIDTH, TILEHEIGHT) < currentClosestDist) {
                currentClosestDist = position.cellDistanceTo(truck.position, TILEWIDTH, TILEHEIGHT);
                target = truck;
            }
        }
        
        if (target == null || currentClosestDist == -1) {
            return null;
        }
        
        laserSprite.setSize(currentClosestDist, texture.getHeight());
        laserSprite.rotate(position.angleTo(target.position));
        return laserSprite;
    }

}
