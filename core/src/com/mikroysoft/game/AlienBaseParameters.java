package com.mikroysoft.game;

public class AlienBaseParameters {
    // Distance from the base in px that a FireEngine must be within in order to trigger
	// weapon firing and alien spawning
    public int weaponRange;
    // maximum number of aliens that can be alive around this base at any one time
    public int maxAliens;
    // Type of weapon this base uses to defend itself
    public String weaponType;
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
    public AlienBaseParameters() {
        weaponRange = 1;
        maxAliens = 3;
        weaponType = "laser";
        floodLevel = 20;
        attackRange = 1;
        attackTimeAfterFirst = 300;
        spawnRate = 500;
    }
}
