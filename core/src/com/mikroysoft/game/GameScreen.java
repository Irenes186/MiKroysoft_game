package com.mikroysoft.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameScreen implements Screen {
	SpriteBatch batch;
	BitmapFont font;
	CoreLogic coreLogic;
	FireEngine[] fireEngines;
	InputController inputController; 
    Map map;
    ProgressBar health;
    Texture healthIcon;
    ProgressBar fuel;
    Texture fuelIcon;
	Alien[] aliens;

	public GameScreen(Game game) {
		this.create();
	}

	public void create () {
		batch = new SpriteBatch();
		//use LibGDXs default font (for menu buttons)
		font = new BitmapFont();
		
       try {
            //map = new Map();
       } catch (Exception e) {
		   e.printStackTrace();
	   }
		coreLogic = new CoreLogic();
		inputController = new InputController();
		Gdx.input.setInputProcessor(inputController);
		fireEngines = new FireEngine[1];
		aliens = new Alien[1];
		aliens[0] = new Alien( new Coordinate(100, 100), 2, 2);
		health = new ProgressBar(1);
		health.setPosition(20,10);
		health.setDimensions(100,10);
		health.setMax(100);
		health.updateCurrent(100);
		healthIcon = new Texture("health.png");
		fuel = new ProgressBar(2);
		fuel.setPosition(20, 25);
		fuel.setDimensions(100,10);
		fuel.setMax(100);
		fuel.updateCurrent(100);
		fuelIcon = new Texture("fuel.png");
	}

	@Override
	public void render (float delta) {
		//super.render(); //menu??
		coreLogic.update();
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
                //map.render(batch);
		fireEngines[0].move(inputController.getLatestPosition());
		for (FireEngine engine: fireEngines) {
			batch.draw(engine.texture,engine.position.x,Gdx.graphics.getHeight()-engine.position.y,40,40,80,80,1,1,engine.direction,0,0,16,16,false,false);
		}
		//Health
		batch.draw(health.texture,health.position.x,health.position.y, health.getFill(), health.getHeight());
		batch.draw(healthIcon,health.position.x - (5 + health.getHeight()), health.position.y, health.getHeight(), health.getHeight());
		//Fuel
		batch.draw(fuel.texture,fuel.position.x,fuel.position.y, fuel.getFill(), fuel.getHeight());
		batch.draw(fuelIcon,fuel.position.x - (5 + fuel.getHeight()), fuel.position.y, fuel.getHeight(), fuel.getHeight());
		for (Alien alien: aliens) {
			batch.draw(alien.texture,alien.position.x,Gdx.graphics.getHeight()-alien.position.y,40,40,40,40,1,1,alien.direction,0,0,16,16,false,false);
		}
		batch.end();
	}

	@Override
	public void show() { }

	@Override
	public void resize(int width, int height) { }

	@Override
	public void pause() { }

	@Override
	public void resume() { }

	@Override
	public void hide() { }

	@Override
	public void dispose () {
		batch.dispose();
		font.dispose();
	}
}