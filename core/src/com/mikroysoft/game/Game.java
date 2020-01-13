package com.mikroysoft.game;

public class Game extends com.badlogic.gdx.Game {

    public class Game extends ApplicationAdapter {
        SpriteBatch batch;
        CoreLogic coreLogic;
        FireEngine[] fireEngines;
        InputController inputController;
        ProgressBar[] health;
        ProgressBar[] fuel;
        int engineSelected;

        Map map;
        int MAPWIDTH;
        int MAPHEIGHT;
        int TILEWIDTH;
        int TILEHEIGHT;
        int AMOUNT;
        FireStation fireStation;

        //ProgressBar health;
        Texture healthIcon;
        //ProgressBar fuel;
        Texture fuelIcon;
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

            //looping from 0 to amount of fire engines.
            for (int i = 0; i < AMOUNT; i = i + 1) {
                //setting fire engine position.
                fireEngines[i] = new FireEngine(MAPWIDTH, MAPHEIGHT);

                //setting health stuff.
                health[i] = new ProgressBar(1);
                health[i].setDimensions(100, 10);
                health[i].setMax(fireEngines[i].maxHealth);
                health[i].updateCurrent(100);

                //setting fuel stuff.
                fuel[i] = new ProgressBar(2);
                fuel[i].setDimensions(100, 10);
                fuel[i].setMax(fireEngines[i].maxFuel);
                fuel[i].updateCurrent(100);
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
                    aliens[nextAlien] = newAlien;
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
                    System.out.println("hi");
                    if (!fireEngines[i].isMaxHealth()) {
                        fireEngines[i].repair();
                    } else if (!fireEngines[i].isMaxFuel()) {
                        fireEngines[i].refill();
                    }
                }
            }

            if (inputController.getShotsFired()) {
                fireEngines[engineSelected].shoot(inputController.getLatestPosition());
            }
            if (inputController.moving) {
                //For testing reasons:
                if (fireEngines[engineSelected].getFuel() > 0) {
                    fireEngines[engineSelected].distanceIncreased();
                    fireEngines[engineSelected].fuelReduce();
                }
                fireEngines[engineSelected].move(inputController.getLatestPosition());
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
                health[i].setPosition(fireEngines[i].position.x, Gdx.graphics.getHeight() - fireEngines[i].position.y - 10);
                fuel[i].setPosition(fireEngines[i].position.x, Gdx.graphics.getHeight() - fireEngines[i].position.y - 25);
                batch.draw(health[i].texture, health[i].position.x, health[i].position.y, health[i].getFill(), health[i].getHeight());
                batch.draw(healthIcon, health[i].position.x - (5 + health[i].getHeight()), health[i].position.y, health[i].getHeight(), health[i].getHeight());
                batch.draw(fuel[i].texture, fuel[i].position.x, fuel[i].position.y, fuel[i].getFill(), fuel[i].getHeight());
                batch.draw(fuelIcon, fuel[i].position.x - (5 + fuel[i].getHeight()), fuel[i].position.y, fuel[i].getHeight(), fuel[i].getHeight());
            }

            map.updateBases();

            // render aliens
            for (int i = 0; i < nextAlien; i++) {
                Alien alien = aliens[i];
                batch.draw(alien.texture, alien.position.x, alien.position.y, 40, 40, 40, 40, 1, 1, alien.direction, 0, 0, 16, 16, false, false);
            }

            //ends batch.
            batch.end();
        }

        @Override
        public void dispose() {
            batch.dispose();

        }
    }
}