package com.mikroysoft.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture;

public class FireStation implements IRenderable {
    public boolean destroyed;
    private int fillSpeed;
    public Texture texture;
    private Coordinate position;
    private int TILEWIDTH, TILEHEIGHT;
    private int destructionTimer;
    private int secondsUntilDestruction;

    public FireStation(int fillSpeed, Coordinate position, int TILEWIDTH, int TILEHEIGHT){
        destroyed = false;
        this.fillSpeed = fillSpeed;
        this.position = position;
        texture = new Texture("station.png");
        this.TILEHEIGHT = TILEHEIGHT;
        this.TILEWIDTH = TILEWIDTH;
        this.secondsUntilDestruction = 5; 
        //not very accurate

    }

    public Coordinate getPosition() {
        return position;
    }

    public void destroy() {
    	System.out.println("BOOOM!");
        destroyed = true;
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw (texture, position.x, position.y, TILEWIDTH * 8, TILEHEIGHT * 3);
    }
    
    public void update() {
        
    }
    
    public void checkDestruction() {
    	
    	if (destructionTimer>secondsUntilDestruction*60) {
    		return;
    	}
    	if (destructionTimer<secondsUntilDestruction*60) {
    		destructionTimer++;
    		return;
    	}
    	this.destroy();
    	this.destructionTimer++;
    	return;
    }

}
