package com.mikroysoft.game;

import com.badlogic.gdx.Gdx;

/**
 * This class is used to position things on the screen using an x and y value, it is worth noting
 * that sometimes the y value is inverted due to libgdx; if you have bug it might be because of this.
 */
public class Coordinate {
    public float x;
    public float y;

    /**
     * This is a construction that creates the coordinate object from two floats by taking them as
     * parameters and then setting the x and y fields to the appropiate argument.
     * @param x - This is the x value that is used to position things along the x axis
     * @param y - This is the y value that is used to poistion things along the y axis
     */
    public Coordinate(float x, float y) {
        this.x = x;
        this.y = y;
    }

    /**
     * This is a constructor that creates the coordinate object from two ints by taking them as
     * parameters and then setting the x and y fields to the appropiate argument.
     * @param x - This is the x value that is used to position things along the x axis
     * @param y - This is the y value that is used to poistion things along the y axis
     */
    public Coordinate(int x, int y) {
        this.x = (float) x;
        this.y = (float) y;
    }
	
    /**
     * This is a copy construtor that creates a coordinate object from another coordinate object
     * parameters and then setting the x and y fields to the appropiate argument.
     * @param x - This is the x value that is used to position things along the x axis
     * @param y - This is the y value that is used to poistion things along the y axis
     */
    public Coordinate(Coordinate coord) {
        this.x = coord.x;
        this.y = coord.y;
    }

    /**
     * This returns a string of the x and y values, mainly useful for debugging purposes
     * @return String - A string of the x and y values
     */
    public String toString() {
        return "(" + Float.toString(this.x) + ", " + Float.toString(this.y) + ")";
    }

    /**
     * This sets the x and y fields of the class
     * @param x - Value to set the x field to
     * @param y - Value to set the y field to
     * @return void
     */
    public void setCoordinate(float x, float y) {
        this.x = x;
        this.y = y;
    }
    
    // Get distance from point to point
    /**
     * This calculates the distance to another coordinate in terms of pixels.
     * @param other - This is the coordinate to calculate the distance too from the coordinate this
     * function is being called from
     * @return float - the distance between the two points
     */
    public float distanceTo(Coordinate other) {
    	return (float) Math.sqrt(Math.pow(Math.abs(this.x-other.x), 2)+Math.pow(Math.abs(this.y-other.y), 2));
    }
    
    /**
     * This calculates the distance between this coordinate and another cooridnate in terms of cells
     * @param other - This is the coordinate to calculate the distance too from the coordinate this
     * function is being called from
     * @return float - the distance represented as a number of cells
     */
    public float cellDistanceTo(Coordinate other) {
        int xDiff = (int) Math.floor(Math.abs(this.x-other.x) / Util.TILEWIDTH);
        int yDiff = (int) Math.floor(Math.abs(this.y-other.y) / Util.TILEHEIGHT);
        return (float) Math.sqrt(Math.pow(xDiff, 2) + Math.pow(yDiff, 2));
    }
    
    /**
     * Adds two coordinated together and returns the result
     * @param other - This is the coordinate to add to the current calling instance of coordinate
     * @return Coordinate
     */
    public Coordinate plus(Coordinate other) {
        return new Coordinate(x + other.x, y + other.y);
    }
    
    /**
     * Takes away the input coordinates x and y values from the calling coordinates x and y values
     * @param other - This is the coordinate to take away from the calling instance of coordinate
     * @return Coordinate
     */
    public Coordinate minus(Coordinate other) {
        return new Coordinate(x - other.x, y - other.y);
    }
    
    /* Calculates the angle in degrees from this point to point other
     * The angle is given with respect to the x axis, i.e:
     * 
     * | B 
     * |<_   A is this point, B is other point, n is the angle (anticlockwise)
     * |+n| 
     * A-----
     */
    /**
     * Returns the angle in degrees from calling coordinate and the input coordinate
     * @param other - This is the coordinate to calculate the angle to
     * @return float - the angle in degrees
     */
    public float angleTo(Coordinate other) {
        return (float) Math.toDegrees(Math.atan2(other.minus(this).y,other.minus(this).x));
        // Old method, less accurate
        //Coordinate vectorToTarget = other.minus(this);
        //return (float) Math.acos(vectorToTarget.x/(Math.sqrt(Math.pow(vectorToTarget.x, 2) + Math.pow(vectorToTarget.y, 2))));
    }
    
    /**
     * Takes the y value away from the height of the screen to invert it
     * @return Coordinate - the angle in degrees
     */
    public Coordinate invertY() {
        return new Coordinate(x, Gdx.graphics.getHeight() - y);
    }

    /**
     * Checks whether the calling coordinate and coordinate as input are equal in x and y values
     * @param other - This is the input coordinate to check whether it is equal to the calling
     * coordinate
     * @return boolean - returns true if the coordinates are equal and false otherwise
     */
    public boolean equals(Coordinate other) {
        return other.x == x && other.y == y;
    }
}
