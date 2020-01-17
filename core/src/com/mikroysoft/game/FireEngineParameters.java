package com.mikroysoft.game;

public class FireEngineParameters{
    // changed from float, since range can be handled on a per-cell basis
    public float acceleration;
    public float maxSpeed;

    // DEFAULTS
    // UPDATE AS NEEDED
    public FireEngineParameters(int baseIndex) {
        switch(baseIndex) {
            case 0:
                acceleration = 0.50f;
                maxSpeed = 2.00f;
                break;
            case 1:
                acceleration = 1.00f;
                maxSpeed = 2.00f;
                break;
            case 2:
                acceleration = 0.20f;
                maxSpeed = 1.00f;
                break;
            case 3:
                acceleration = 0.50f;
                maxSpeed = 1.00f;
                break;
        }
    }
}
