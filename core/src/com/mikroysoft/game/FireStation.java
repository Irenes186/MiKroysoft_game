package com.mikroysoft.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture;

public class FireStation implements IRenderable {
	public boolean destroyed;
	private int fillSpeed;
	public Texture texture;
	public Coordinate position;
	private int TILEWIDTH, TILEHEIGHT;
	
	public FireStation(int fillSpeed, Coordinate position, int TILEWIDTH, int TILEHEIGHT){
		destroyed = false;
		this.fillSpeed = fillSpeed;
		this.position = position;
		texture = new Texture("station.png");
		this.TILEHEIGHT = TILEHEIGHT;
		this.TILEWIDTH = TILEWIDTH;

	}
	
	public boolean isInRange(Coordinate engineCoordinates) {
		int tempX = engineCoordinates.x - this.position.x;
		int tempY = engineCoordinates.y - this.position.y;
		if(tempX < 0) {
			tempX = tempX * -1;
		}
		if (tempY < 0) {
			tempY = tempY * -1;
		}
		if((tempX < TILEWIDTH) && (tempY < TILEHEIGHT)) {
			return true;
		} else { 
			return false;
		}
		
	}
	
	public Coordinate getPosition() {
		return position;
	}
	public void destroy() {
		destroyed = true;
	}
	
        @Override
        public void render(SpriteBatch batch) {
            batch.draw (texture, position.x, position.y, TILEWIDTH * 8, TILEHEIGHT * 3);
        }
	
}
