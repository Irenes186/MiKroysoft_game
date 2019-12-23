package com.mikroysoft.game;

public class Coordinate {
	public int x;
	public int y;
	public Coordinate(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public Coordinate(Coordinate coord) {
		this.x = coord.x;
		this.y = coord.y;
	}
}
