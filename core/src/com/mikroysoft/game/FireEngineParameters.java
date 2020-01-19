package com.mikroysoft.game;

/**
 * This class is use to store configuration about the different fire engines that we will be using
 */
public class FireEngineParameters{
    // changed from float, since range can be handled on a per-cell basis
    public float acceleration;
    public float maxSpeed;
    public int shotDamage;
    public int deliveryRate;

    // DEFAULTS
    // UPDATE AS NEEDED
    /**
     * This constructor takes an int as an input and sets the configuration of the fire engine to
     * the associate values that are shown in the switch statement
     *
     * @param baseIndex - This is a integer value that is used to select a certain configuration, if
     * there is not associate configuration with the inputted value then the default value is chosen
     * instead.
     */
    public FireEngineParameters(int baseIndex) {
        switch(baseIndex) {
            case 0:
                acceleration = 0.50f;
                maxSpeed = 2.00f;
                shotDamage = 5;
                deliveryRate = 15;
                break;
            case 1:
                acceleration = 1.00f;
                maxSpeed = 2.00f;
                shotDamage = 1;
                deliveryRate = 18;
                break;
            case 2:
                acceleration = 0.20f;
                maxSpeed = 1.00f;
                shotDamage = 2;
                deliveryRate = 12;
                break;
            case 3:
                acceleration = 0.50f;
                maxSpeed = 1.00f;
                shotDamage = 3;
                deliveryRate = 10;
                break;
        }
    }
}
