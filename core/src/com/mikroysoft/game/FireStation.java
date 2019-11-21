package com.mikroysoft.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class FireStation implements IRenderable {
	public boolean destroyed;
	private int fillSpeed;
	
	public FireStation(int fillSpeed){
		destroyed = false;
		this.fillSpeed = fillSpeed;
	}
	
	public void destroy() {
		destroyed = true;
	}
	
        @Override
        public void render(SpriteBatch batch) {
        }
	
}
