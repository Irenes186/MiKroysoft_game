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
	
	
	@Override
	public void create () {
                MAPWIDTH = 20;
                MAPHEIGHT = 20;

                try {
                    map = new Map(MAPWIDTH, MAPHEIGHT);
                } catch (Exception e) {
                    e.printStackTrace();
                }


		batch = new SpriteBatch();
		coreLogic = new CoreLogic();
		inputController = new InputController();
		Gdx.input.setInputProcessor(inputController);
		fireEngines = new FireEngine[1];
		fireEngines[0] = new FireEngine(MAPWIDTH, MAPHEIGHT);
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

                if (inputController.getShotsFired()) {
                    fireEngines[0].shoot(inputController.getLatestPosition());
                }
                if (inputController.moving) {

		    fireEngines[0].move(inputController.getLatestPosition());
                }
		for (FireEngine engine: fireEngines) {
                    engine.render(batch);
		}
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
