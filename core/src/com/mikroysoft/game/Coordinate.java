package com.mikroysoft.game;

public class Coordinate {
    public float x;
    public float y;

    public Coordinate(float x, float y) {
        this.x = x;
        this.y = y;
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
    	return (float) Math.sqrt(Math.pow(this.x-other.x, 2)+Math.pow(this.y-other.y, 2));
    }

}
