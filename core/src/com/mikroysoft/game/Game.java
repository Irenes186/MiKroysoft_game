package com.mikroysoft.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.Random;

public class Game extends ApplicationAdapter {
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

    @Override
    public void create() {
        MAPWIDTH = 20;
        MAPHEIGHT = 20;
        AMOUNT = 4;
        engineSelected = 1;
        nextAlien = 0;

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
        for (int i = 0; i < AMOUNT; i = i + 1) {
        	
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
        	
            fireEngines[i] = new FireEngine(map);
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
            
            //setting health stuff.
            health[i] = new ProgressBar(BarColour.YELLOW);
            health[i].setDimensions(100, 10);
            health[i].setMax(fireEngines[i].maxHealth);
            health[i].updateCurrent(100);

            //setting fuel stuff.
            fuel[i] = new ProgressBar(BarColour.PINK);
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
        	volume[i] = new ProgressBar(BarColour.BLUE);
        	volume[i].setDimensions(100,10);
        	volume[i].setMax(maxVolume);
        	volume[i].updateCurrent(fireEngines[i].getVolume());
        }

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
    public void render() {
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

        
        // render and update aliens
        for (int i = 0; i < nextAlien; i++) {
            aliens[i].update();;
            aliens[i].render(batch);
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
    public void dispose() {
        batch.dispose();

    }
}