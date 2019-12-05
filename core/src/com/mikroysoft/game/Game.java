package com.mikroysoft.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Game extends ApplicationAdapter {
	SpriteBatch batch;
	CoreLogic coreLogic;
	FireEngine[] fireEngines;
	InputController inputController; 
    Map map;
    ProgressBar health;
    Texture healthIcon;
    ProgressBar fuel;
    Texture fuelIcon;
	Alien[] aliens;
	
	@Override
	public void create () {
       try {
            map = new Map();
       } catch (Exception e) {
             e.printStackTrace();
       }
		batch = new SpriteBatch();
		coreLogic = new CoreLogic();
		inputController = new InputController();
		Gdx.input.setInputProcessor(inputController);
		
		//Fire Engines:
		fireEngines = new FireEngine[1];
		fireEngines[0] = new FireEngine();
		//fireEngines[1] = new FireEngine();
		//fireEngines[2] = new FireEngine();
		//fireEngines[3] = new FireEngine();
		//fireEngines[4] = new FireEngine();
		
		aliens = new Alien[1];
		aliens[0] = new Alien( new Coordinate(100, 100), 2, 2);
		
		//health progress bar:
		health = new ProgressBar(1);
		health.setPosition(20,10);
		health.setDimensions(100,10);
		health.setMax(100);
		health.updateCurrent(100);
		
		//health icon - next to health progress bar.
		healthIcon = new Texture("health.png");
		
		
		//fuel progress bar:
		fuel = new ProgressBar(2);
		fuel.setPosition(20, 25);
		fuel.setDimensions(100,10);
		fuel.setMax(100);
		fuel.updateCurrent(100);
		//hiiii
		//fuel icon - next to fuel progress bar.
		fuelIcon = new Texture("fuel.png");
	}

	@Override
	public void render () {
		coreLogic.update();
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
                map.render(batch);
                
		fireEngines[0].move(inputController.getLatestPosition());
		for (FireEngine engine: fireEngines) {
			batch.draw(engine.texture,engine.position.x,Gdx.graphics.getHeight()-engine.position.y,40,40,80,80,1,1,engine.direction,0,0,16,16,false,false);
		}

		aliens[0].Run();
		//health
		batch.draw(health.texture,health.position.x,health.position.y, health.getFill(), health.getHeight());
		batch.draw(healthIcon,health.position.x - (5 + health.getHeight()), health.position.y, health.getHeight(), health.getHeight());
		//fuel
		batch.draw(fuel.texture,fuel.position.x,fuel.position.y, fuel.getFill(), fuel.getHeight());
		batch.draw(fuelIcon,fuel.position.x - (5 + fuel.getHeight()), fuel.position.y, fuel.getHeight(), fuel.getHeight());
		for (Alien alien: aliens) {
			batch.draw(alien.texture,alien.position.x,Gdx.graphics.getHeight()-alien.position.y,40,40,40,40,1,1,alien.direction,0,0,16,16,false,false);
		}
		batch.end();
		//System.out.println(health.getFill());
	}
	@Override
	public void dispose () {
		batch.dispose();
	}
}