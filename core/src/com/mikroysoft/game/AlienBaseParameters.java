package com.mikroysoft.game;

/**
 * This class only contains fields and is used to configure the alien bases, it accepts an int which
 * is used to select a different config using a switch statement
 */

public class AlienBaseParameters {
    // Distance from the base in px that a FireEngine must be within in order to trigger
    // weapon firing and alien spawning
    public int weaponRange;
    // maximum number of aliens that can be alive around this base at any one time
    public int maxAliens;
    // Type of weapon this base uses to defend itself
    public WeaponType weaponType;
    // health
    public int floodLevel;
    // TODO: Is this a duplicate of weaponRange?
    public int attackRange;
    // Once an AlienBase is destroyed, wait this number of frames before destroying the FireStation.
    // shouldn't this be handled in game??
    public int attackTimeAfterFirst;
    // Number of frames to wait between spawning Aliens
    public int spawnRate;

    // DEFAULTS
    // UPDATE AS NEEDED
    /**
     * This is the constructor for the class that accepts and int from values 1 to 3, if the int is
     * outside of this range the default config will be used.
     * @param baseIndex - This is an integer that will be used to select a certain config
     */
    public AlienBaseParameters(int baseIndex) {
        switch(baseIndex){
            case 1:
                weaponRange = 1;
                maxAliens = 3;
                weaponType = WeaponType.BULLET;
                floodLevel = 20;
                attackTimeAfterFirst = 300;
                spawnRate = 500;
                break;
            case 2:
                weaponRange = 1;
                maxAliens = 1;
                weaponType = WeaponType.LASER;
                floodLevel = 20;
                attackTimeAfterFirst = 300;
                spawnRate = 500;
                break;
            case 3:
                weaponRange = 2;
                maxAliens = 1;
                weaponType = WeaponType.BULLET;
                floodLevel = 20;
                attackTimeAfterFirst = 300;
                spawnRate = 500;
                break;
            default:
                weaponRange = 1;
                maxAliens = 2;
                weaponType = WeaponType.BULLET;
                floodLevel = 20;
                attackRange = 1;
                attackTimeAfterFirst = 300;
                spawnRate = 500;
                break;
        }
    }
}
