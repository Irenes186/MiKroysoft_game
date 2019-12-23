package com.mikroysoft.game;

public class AlienBaseParameters {
	// changed from float, since range can be handled on a per-cell basis
	public int weaponRange;
	// Renamed from aliensNumber
    public int maxAliens;
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
    	maxAliens = 3;
    	weaponType = "laser";
    	floodLevel = 20;
    	attackRange = 1;
    	attackTimeAfterFirst = 300;
    }
}
