package com.mikroysoft.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class FireEngine {
	Texture texture;
	Coordinate position;
	
	public FireEngine() {
		texture = new Texture("fireEngine.png");
		position = new Coordinate(0,0);
	}
	public void draw(SpriteBatch batch) {
		batch.draw(texture,position.x,position.y,16,16);
	}
}
