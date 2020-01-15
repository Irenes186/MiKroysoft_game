package com.mikroysoft.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Random;

public class GameScreen implements Screen {
	Game game;
	SpriteBatch batch;
	CoreLogic coreLogic;
	FireEngine[] fireEngines;
	InputController inputController;
	ProgressBar[] health;
	ProgressBar[] fuel;
	ProgressBar[] volume;
	int engineSelected;

	Map map;
	int MAPWIDTH;
	int MAPHEIGHT;
	int TILEWIDTH;
	int TILEHEIGHT;
	int AMOUNT;
	FireStation fireStation;

	//ProgressBar icon health;
	Texture healthIcon;
	//ProgressBar icon fuel;
	Texture fuelIcon;
	//ProgressBar icon volume;
	Texture volumeIcon;

	Alien[] aliens;
	// used to track the farthest-left empty cell in the aliens array.
	int nextAlien;
	AlienBase[] bases;

	public GameScreen(Game game) {
		create();
	}

	public void create() {
		MAPWIDTH = 20;
		MAPHEIGHT = 20;
		AMOUNT = 4;
		engineSelected = 1;
		nextAlien = 0;
		this.game = game;

		try {
			map = new Map(MAPWIDTH, MAPHEIGHT, "background");
		} catch (Exception e) {
			e.printStackTrace();
		}

		batch = new SpriteBatch();
		coreLogic = new CoreLogic();
		inputController = new InputController();
		Gdx.input.setInputProcessor(inputController);

		//Fire Engines:
		fireEngines = new FireEngine[AMOUNT];
		////health:
		health = new ProgressBar[AMOUNT];
		////fuel:
		fuel = new ProgressBar[AMOUNT];
		///volume:
		volume = new ProgressBar[AMOUNT];
		Random randomGenerator = new Random();
		int randomValueOne = 0;
		int randomValueTwo = 0;
		//looping from 0 to amount of fire engines.
		int[] takenValuesOne = new int[AMOUNT];
		int[] takenValuesTwo = new int[AMOUNT];
		for (int i = 0; i < AMOUNT; i++) {

			int index = -1;
			boolean valueTaken = true;
			while(valueTaken == true) {
				valueTaken = false;
				randomValueOne = randomGenerator.nextInt(3);
				for(int j = 0; j < AMOUNT; j = j + 1) {
					if(takenValuesOne[j] == randomValueOne) {
						valueTaken = true;
						break;
					}
				}
				if(valueTaken == false) {
					index = index + 1;
				}
			}
			takenValuesOne[index] = randomValueOne;

			fireEngines[i] = new FireEngine(MAPWIDTH, MAPHEIGHT);
			fireEngines[i].setPosition(map.getStationX() + 50, map.getStationY() + 50);
			float acceleration = 0.00f;
			float maxSpeed = 0.00f;
			switch(randomValueOne) {
				case 0:
					acceleration = 0.10f;
					maxSpeed = 1.00f;
					break;
				case 1:
					acceleration = 0.50f;
					maxSpeed = 2.00f;
					break;
				case 2:
					acceleration = 0.01f;
					maxSpeed = 0.05f;
					break;
				default:
					acceleration = 0.10f;
					maxSpeed = 2.00f;
					break;

			}


			fireEngines[i].setSpeed(maxSpeed);
			fireEngines[i].setAcceleration(acceleration);
			//fireEngines[i].setmaxPosition(); <-- what is this for?!

			System.out.println(i);
			//setting health stuff.
			health[i] = new ProgressBar(1);
			health[i].setDimensions(100, 10);
			health[i].setMax(fireEngines[i].maxHealth);
			health[i].updateCurrent(100);

			//setting fuel stuff.
			fuel[i] = new ProgressBar(3);
			fuel[i].setDimensions(100, 10);
			fuel[i].setMax(fireEngines[i].maxFuel);
			fuel[i].updateCurrent(100);


			//Getting max volume value for fireEngines[i].
			valueTaken = true;
			index = -1;
			while(valueTaken == true) {
				valueTaken = false;
				randomValueTwo = randomGenerator.nextInt(10);
				for(int j = 0; j < AMOUNT; j = j + 1) {
					if(takenValuesTwo[j] == randomValueTwo) {
						valueTaken = true;
						break;
					}
				}
				if(valueTaken == false) {
					index = index + 1;
				}
			}
			takenValuesTwo[index] = randomValueTwo;

			int maxVolume = 0;
			switch (randomValueTwo) {
				case 0:
					maxVolume = 1;
					break;
				case 1:
					maxVolume = 2;
					break;
				case 2:
					maxVolume = 5;
					break;
				case 3:
					maxVolume = 10;
					break;
				case 4:
					maxVolume = 20;
					break;
				case 5:
					maxVolume = 25;
					break;
				case 6:
					maxVolume = 50;
					break;
				default:
					maxVolume = 100;
					break;

			}

			//Setting volume attributes to the fire engine.
			fireEngines[i].setMaxVolume(maxVolume);
			fireEngines[i].setVolume(fireEngines[i].getMaxVolume());
			volume[i] = new ProgressBar(2);
			volume[i].setDimensions(100,10);
			volume[i].setMax(maxVolume);
			volume[i].updateCurrent(fireEngines[i].getVolume());
		}
		//fireEngines[1] = new FireEngine();
		//fireEngines[2] = new FireEngine();
		//fireEngines[3] = new FireEngine();
		//fireEngines[4] = new FireEngine();


		//aliens = new Alien[1];
		//aliens[0] = new Alien( new Coordinate(100, 100), 2, 2);

		//health progress bar:
		//health = new ProgressBar(1);
		//health.setPosition(20,10);
		//health.setDimensions(100,10);
		//health.setMax(100);
		//health.updateCurrent(100);

		//health icon - next to health progress bar.
		healthIcon = new Texture("health.png");

		//fuel icon - next to fuel progress bar.
		fuelIcon = new Texture("fuel.png");

		//volume icon - next to volume progress bar.
		volumeIcon = new Texture("water_drop.png");
		// initialise aliens array size to the sum of all maxAliens counts.
		int totalMaxAliens = 0;
		for (IRenderable[] row : this.map.grid) {
			for (IRenderable cell : row) {
				if (cell instanceof AlienBase) {
					totalMaxAliens += ((AlienBase) cell).maxAliens;
				}
			}
		}
		aliens = new Alien[totalMaxAliens];
		bases = this.map.getBases();
	}

	@Override
	public void render(float delta) {
		coreLogic.update();
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// Handle Alien spawning
		for (AlienBase base : this.bases) {
			Alien newAlien = base.defend(this.fireEngines);
			if (newAlien != null) {
				this.aliens[nextAlien] = newAlien;
				// Theoretically, this should never overflow due to the way i instantiated aliens.
				nextAlien++;
			}
		}

		batch.begin();
		map.render(batch);

		if (inputController.moving) {
			for (int engineIndex = 0; engineIndex < AMOUNT; engineIndex++) {
				double distance = Math.sqrt(Math.pow(inputController.position.x - fireEngines[engineIndex].position.x, 2) + Math.pow(inputController.position.y - fireEngines[engineIndex].position.y, 2));
				if (distance < 40) {
					engineSelected = engineIndex;
					break;
				}
			}
		}

		for (int barIndex = 0; barIndex < AMOUNT; barIndex++) {

			health[barIndex].updateCurrent(fireEngines[barIndex].health);
			fuel[barIndex].updateCurrent(fireEngines[barIndex].fuel);
			volume[barIndex].updateCurrent(fireEngines[barIndex].getVolume());
			health[barIndex].setPosition(fireEngines[barIndex].position.x, Gdx.graphics.getHeight() - fireEngines[barIndex].position.y - 10);
			fuel[barIndex].setPosition(fireEngines[barIndex].position.x, Gdx.graphics.getHeight() - fireEngines[barIndex].position.y - 25);
			volume[barIndex].setPosition(fireEngines[barIndex].position.x, Gdx.graphics.getHeight() - fireEngines[barIndex].position.y - 40);

			System.out.println(health[0].position);
			//health
			batch.draw(health[barIndex].texture, health[barIndex].position.x, health[barIndex].position.y, health[barIndex].getFill(), health[barIndex].getHeight());
			batch.draw(healthIcon, health[barIndex].position.x - (5 + health[barIndex].getHeight()), health[barIndex].position.y, health[barIndex].getHeight(), health[barIndex].getHeight());
			//fuel
			batch.draw(fuel[barIndex].texture, fuel[barIndex].position.x, fuel[barIndex].position.y, fuel[barIndex].getFill(), fuel[barIndex].getHeight());
			batch.draw(fuelIcon, fuel[barIndex].position.x - (5 + fuel[barIndex].getHeight()), fuel[barIndex].position.y, fuel[barIndex].getHeight(), fuel[barIndex].getHeight());
		}

		for (Alien alien: aliens) {
			//batch.draw(alien.texture, alien.position.x, Gdx.graphics.getHeight() - alien.position.y, 40, 40, 40, 40, 1, 1, alien.direction, 0, 0, 16, 16, false, false);
		}

		//refill and repair
		for (int i = 0; i < AMOUNT; i++) {
			if (map.isInStationRange(fireEngines[i].getPosition())) {
				if (!fireEngines[i].isMaxHealth()) {
					fireEngines[i].repair();
				} else if (!fireEngines[i].isMaxFuel()) {
					fireEngines[i].refillFuel();
				} else if (!fireEngines[i].isMaxVolume()) {
					fireEngines[i].refillVolume();
				}
			}
		}

		if (inputController.getShotsFired()) {
			if(fireEngines[engineSelected].getVolume() > 0) {
				fireEngines[engineSelected].shoot(inputController.getLatestPosition());
				fireEngines[engineSelected].reduceVolume();
			}
		}

		if (inputController.moving) {
			//For testing reasons:
			if (fireEngines[engineSelected].getFuel() > 0) {
				fireEngines[engineSelected].distanceIncreased();
				fireEngines[engineSelected].fuelReduce();
			}
			if(!fireEngines[engineSelected].isMaxSpeed()) {
				fireEngines[engineSelected].increaseSpeed();
			}
			fireEngines[engineSelected].move(inputController.getLatestPosition());
		} else {
			for(int z = 0; z < AMOUNT; z = z + 1) {
				fireEngines[z].resetSpeed();
			}
		}

		// TODO: Remove
		// if(false)) {
		//	if(engineSelected >= AMOUNT - 1) {
		//	engineSelected = 0;
		//} else {
		//engineSelected = engineSelected + 1;
		//}
		//}
		for (FireEngine engine : fireEngines) {
			engine.render(batch);
		}
		//health and fuel drawing.
		for (int i = 0; i < AMOUNT; i = i + 1) {
			health[i].updateCurrent(fireEngines[i].health);
			fuel[i].updateCurrent(fireEngines[i].fuel);
			volume[i].updateCurrent(fireEngines[i].getVolume());
			health[i].setPosition(fireEngines[i].position.x, Gdx.graphics.getHeight() - fireEngines[i].position.y - 10);
			fuel[i].setPosition(fireEngines[i].position.x, Gdx.graphics.getHeight() - fireEngines[i].position.y - 25);
			volume[i].setPosition(fireEngines[i].position.x, Gdx.graphics.getHeight() - fireEngines[i].position.y - 40);
			batch.draw(health[i].texture, health[i].position.x, health[i].position.y, health[i].getFill(), health[i].getHeight());
			batch.draw(healthIcon, health[i].position.x - (5 + health[i].getHeight()), health[i].position.y, health[i].getHeight(), health[i].getHeight());
			batch.draw(fuel[i].texture, fuel[i].position.x, fuel[i].position.y, fuel[i].getFill(), fuel[i].getHeight());
			batch.draw(fuelIcon, fuel[i].position.x - (5 + fuel[i].getHeight()), fuel[i].position.y, fuel[i].getHeight(), fuel[i].getHeight());
			batch.draw(volume[i].texture, volume[i].position.x, volume[i].position.y, volume[i].getFill(), volume[i].getHeight());
			batch.draw(volumeIcon, volume[i].position.x - (5 + volume[i].getHeight()), volume[i].position.y, volume[i].getHeight(), volume[i].getHeight());
		}


		// render aliens
		for (int i = 0; i < nextAlien; i++) {
			Alien alien = aliens[i];
			batch.draw(alien.texture, alien.position.x, alien.position.y, 40, 40, 40, 40, 1, 1, alien.direction, 0, 0, 16, 16, false, false);
		}

		// MAKE ALIEN SHOOT
		for (int alienIndex = 0; alienIndex<nextAlien; alienIndex++) {
			float minimumDistance = 1000;
			int minimumIndex = -1;
			float distance;
			for (int engineIndex = 0; engineIndex < AMOUNT; engineIndex++) {
				distance = fireEngines[engineIndex].position.distanceTo(aliens[alienIndex].position);
				if (distance<minimumDistance) {
					minimumDistance = distance;
					minimumIndex = engineIndex;
				}
			}
			aliens[alienIndex].shoot(fireEngines[minimumIndex].position);

		}

		//ends batch.
		batch.end();
	}

	@Override
	public void show() {

	}

	@Override
	public void resize(int i, int i1) {

	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void hide() {

	}

	@Override
	public void dispose() {
		batch.dispose();
	}

	public void setScreen(GameScreen gameScreen) {

	}
}