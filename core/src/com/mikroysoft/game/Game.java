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
	Alien[] aliens;
	InputController inputController;
	Map map;
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
		fireEngines = new FireEngine[1];
		fireEngines[0] = new FireEngine();

		aliens = new Alien[1];
		aliens[0] = new Alien( new Coordinate(100, 100), 2, 2);
		//fireEngines[1] = new FireEngine();
		//fireEngines[2] = new FireEngine();
		//fireEngines[3] = new FireEngine();
		//fireEngines[4] = new FireEngine();
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
		for (Alien alien: aliens) {
			batch.draw(alien.texture,alien.position.x,Gdx.graphics.getHeight()-alien.position.y,40,40,40,40,1,1,alien.direction,0,0,16,16,false,false);
		}
		batch.end();
	}
	@Override
	public void dispose () {
		batch.dispose();
	}
}