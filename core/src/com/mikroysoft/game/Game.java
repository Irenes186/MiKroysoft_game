package com.mikroysoft.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Game extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	CoreLogic coreLogic;
	FireEngine[] fireEngines;
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
		img = new Texture("logo.png");
		coreLogic = new CoreLogic();
		inputController = new InputController();
		Gdx.input.setInputProcessor(inputController);
		fireEngines = new FireEngine[1];
		fireEngines[0] = new FireEngine();
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
                
		batch.draw(img, 0, 0, 100, 100);
		fireEngines[0].move(inputController.getLatestPosition());
                fireEngines[0].direction++;
		for (FireEngine engine: fireEngines) {
			batch.draw(engine.texture,engine.position.x,Gdx.graphics.getHeight()-engine.position.y,40,40,80,80,1,1,engine.direction,0,0,16,16,false,false);
		}
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
