package com.mikroysoft.game;

import com.badlogic.gdx.Gdx;

public class Coordinate {
    public float x;
    public float y;

    public Coordinate(float x, float y) {
        this.x = x;
        this.y = y;
    }
    
    public Coordinate(int x, int y) {
        this.x = (float) x;
        this.y = (float) y;
    }
	
    public Coordinate(Coordinate coord) {
        this.x = coord.x;
        this.y = coord.y;
    }
	
    public String toString() {
        return "(" + Float.toString(this.x) + ", " + Float.toString(this.y) + ")";
    }

    public void setCoordinate(float x, float y) {
        this.x = x;
        this.y = y;
    }
    
    // Get distance from point to point
    public float distanceTo(Coordinate other) {
    	return (float) Math.sqrt(Math.pow(Math.abs(this.x-other.x), 2)+Math.pow(Math.abs(this.y-other.y), 2));
    }
    
    public float cellDistanceTo(Coordinate other, int TILEWIDTH, int TILEHEIGHT) {
        int xDiff = (int) Math.floor(Math.abs(this.x-other.x) / TILEWIDTH);
        int yDiff = (int) Math.floor(Math.abs(this.y-other.y) / TILEHEIGHT);
        return (float) Math.sqrt(Math.pow(xDiff, 2) + Math.pow(yDiff, 2));
    }
    
    public Coordinate plus(Coordinate other) {
        return new Coordinate(x + other.x, y + other.y);
    }
    
    public Coordinate minus(Coordinate other) {
        return new Coordinate(x - other.x, y - other.y);
    }
    
    /* Calculates the angle in degrees from this point to point other
     * The angle is given with respect to the x axis, i.e:
     * 
     * | B 
     * |_    A is this point, B is other point, n is the angle
     * |n| 
     * A-----
     */
    public float angleTo(Coordinate other) {
        Coordinate vectorToTarget = other.minus(this);
        return (float) Math.acos(vectorToTarget.x/(Math.sqrt(Math.pow(vectorToTarget.x, 2) + Math.pow(vectorToTarget.y, 2))));
    }
    
    public Coordinate invertY() {
        return new Coordinate(x, Gdx.graphics.getHeight() - y);
    }

    public boolean equals(Coordinate other) {
        return other.x == x && other.y == y;
    }
}
