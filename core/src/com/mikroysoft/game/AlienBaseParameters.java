package com.mikroysoft.game;

public class AlienBaseParameters {
    public float weaponRange;
    public int aliensNumber;
    //Changed from String[] as weapon type does not change.
    public String weaponType;
    public int floodLevel;
    public int attackRange;
    // shouldn't this be handled in game??
    public int attackTimeAfterFirst;

    // DEFAULTS
    // UPDATE AS NEEDED
    public AlienBaseParameters() {
        weaponRange = 1;
        aliensNumber = 3;
        weaponType = "laser";
        floodLevel = 20;
        attackRange = 1;
        attackTimeAfterFirst = 300;
    }
}
