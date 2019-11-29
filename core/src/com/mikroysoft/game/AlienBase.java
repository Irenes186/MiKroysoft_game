package com.mikroysoft.game;

import com.badlogic.gdx.graphics.Texture;

public class AlienBase {
    public Texture texture;
    public float weaponRange;
    public String name;
    public int aliensNumber;
    //Changed from String[] as weapon type does not change.
    public String weaponType;
    public int floodLevel;
    public int attackRange;
    public int attackTimeAfterFirst;
    public Coordinate position;

    // [!] Is it really necessary to store the name of the base? I dont think so...
    public AlienBase(String name, AlienBaseParameters params, Coordinate position, int TILEWIDTH, int TILEHEIGHT, String tex) {
        texture = new Texture(tex + ".png");
        this.name = name;
        this.position = position;
        this.weaponRange = params.weaponRange;
        this.aliensNumber = params.aliensNumber;
        this.weaponType = params.weaponType;
        this.floodLevel = params.floodLevel;
        this.attackRange = params.attackRange;
        this.attackTimeAfterFirst = params.attackTimeAfterFirst;
    }

    public int increaseDefense () {
        return 3;
    }

}
