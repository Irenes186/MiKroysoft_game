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
}
