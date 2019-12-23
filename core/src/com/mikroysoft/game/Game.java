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
	int MAPWIDTH;
	int MAPHEIGHT;
	
	ProgressBar health;
	Texture healthIcon;
	ProgressBar fuel;
	Texture fuelIcon;
	Alien[] aliens;

	@Override
	public void create () {
        MAPWIDTH = 20;
        MAPHEIGHT = 20;

        try {
            map = new Map(MAPWIDTH, MAPHEIGHT,"background");
        } catch (Exception e) {
            e.printStackTrace();
        }


		batch = new SpriteBatch();
		coreLogic = new CoreLogic();
		inputController = new InputController();
		Gdx.input.setInputProcessor(inputController);
		
		// TODO: After implementing control switching, fix fire engine instantiation here!
		
		//Fire Engines:
		fireEngines = new FireEngine[1];
		fireEngines[0] = new FireEngine(MAPWIDTH, MAPHEIGHT);
		//fireEngines[1] = new FireEngine();
		//fireEngines[2] = new FireEngine();
		//fireEngines[3] = new FireEngine();
		//fireEngines[4] = new FireEngine();
		
		// Set aliens array size to the sum of all maxAliens counts.
		int totalMaxAliens = 0;
		for (IRenderable[] row: this.map.grid) {
			for (IRenderable cell: row) {
				if (cell instanceof AlienBase) {
					totalMaxAliens += ((AlienBase) cell).maxAliens;
				}
			}
		}
		aliens = new Alien[totalMaxAliens];
		// TODO: REMOVE. For testing only.
		// aliens[0] = new Alien( new Coordinate(100, 100), 2, 2);
		
		// TODO: Please move health and fuel bar/icon instantiation to within fire truck instantiation
		
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

        if (inputController.getShotsFired()) {
            fireEngines[0].shoot(inputController.getLatestPosition());
        }
        if (inputController.moving) {
        	fireEngines[0].move(inputController.getLatestPosition());
        }
		for (FireEngine engine: fireEngines) {
            engine.render(batch);
		}
		
		// TODO: REMOVE. For testing only.
		// TODO: Implement alien shooting.
		// aliens[0].Run();
		
		
		// TODO: Please handle health and fuel bar rendering modularly.
		// Drawing should be handled by ProgressBar.render(SpriteBatch batch).
		// This should probably be called by the owner fire truck class.
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