package com.mikroysoft.game;
import com.badlogic.gdx.graphics.Texture;

public abstract class BaseWeapon {
    // The number of frames to wait between weapon fires
    private int cooldown;
    // The number of frames since the last weapon fire
    private int cooldownCurrent;
    // The range, in grid cells, around the weapon that a truck must be within in order for the weapon to fire
    protected int range;
    // Position of the weapon (usually inherited from a base, though it COULD be mounted to a FireEngine... hmm...)
    public Coordinate position;
    // Texture for the weapon's projectile/firing method/etc
    protected Texture texture;
    
    /* Constructor.
     * cooldown - int - The number of frames to wait between weapon fires
     * range - int - The range, in grid cells, around the weapon that a
     *               truck must be within in order for the weapon to fire
     * tex - String - the filename of the texture file to use with this weapon
     */
    public BaseWeapon(int cooldown, int range, String tex, Coordinate position) {
        this.cooldown = cooldown;
        this.cooldownCurrent = cooldown;
        this.range = range;
        // Using position by reference rather than by copy to allow dynamic positioning
        this.position = position;
        this.texture = new Texture(tex);
    }
    
    /* Decrease the amount of cooldown left before the weapon can be fired
     */
    public void doCooldown() {
        if (cooldownCurrent > 0) {
            cooldownCurrent--;
        }
    }
    
    /* Is the weapon on cooldown?
     * returns bool; true if the weapon still has to cool down before firing, false if it is ready to fire
     */
    public boolean onCooldown() {
        return cooldownCurrent > 0;
    }
    
    /* Resets the cooldown before this weapon can fire again.
     */
    public void resetCooldown() {
        cooldownCurrent = cooldown;
    }
    
    /* Is the given fire engine within range of the weapon?
     * truck - FireEngine - The FireEngine to test
     * returns bool; true if truck is within weapon range, false otherwise
     */
    protected boolean truckInRange(FireEngine truck) {
        return Math.abs(position.cellDistanceTo(truck.position.invertY())) <= range;
    }
    
    /* Fire the weapon.
     * fireEngines - FireEngine[] - An array containing all currently alive FireEngines
     * returns Object; the spawned object fired from the weapon (if any) to render
     */
    public abstract Object fire(FireEngine[] fireEngines);
}
