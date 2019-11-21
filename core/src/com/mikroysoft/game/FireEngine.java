package com.mikroysoft.game;

import com.badlogic.gdx.graphics.Texture;

public class FireEngine {
	public Texture texture;
	public Coordinate position;
	private int waterVolume;
	private float speed;
	private float range;
	private float deliveryRate;
	private int health;
	public float direction;
	
	
	public FireEngine() {
		texture = new Texture("fireengine.png");
		position = new Coordinate(300,300);
		direction = 0;
		speed = 2;
	}
	
	
	public void repair() {
		health += 50;
	}
	
	public void refill() {
		waterVolume += 50;
	}
	
	public void move(Coordinate input) {
		//add some kind of moving rules.
	}

}
