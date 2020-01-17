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
                acceleration = 0.10f;
                maxSpeed = 1.00f;
                break;
            case 1:
                acceleration = 0.50f;
                maxSpeed = 2.00f;
                break;
            case 2:
                acceleration = 0.01f;
                maxSpeed = 0.05f;
                break;
            default:
                acceleration = 0.10f;
                maxSpeed = 2.00f;
                break;
        }
    }
}
