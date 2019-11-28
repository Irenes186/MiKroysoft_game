package com.mikroysoft.game;

import com.badlogic.gdx.graphics.Texture;

public class AlienBase {
    public Texture texture;
    public float WeaponRange;
    public String Name;
    public int AliensNumber;
    public String[] WeaponType;
    public int FloodLevel;
    public int AttackRange;
    public int AttackTimeAfterFirst;
    public int[] Position;


    public AlienBase() {
        texture = new Texture("");
    }

    public int increaseDefense () {
        return 3;
    }

}
