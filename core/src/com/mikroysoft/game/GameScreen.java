package com.mikroysoft.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Random;

import java.util.HashSet;
import java.util.Set;

public class GameScreen implements Screen {
    Game game;
    SpriteBatch batch;
    CoreLogic coreLogic;
    InputController inputController;
    
    FireEngine[] fireEngines;
    ProgressBar[] health;
    ProgressBar[] fuel;
    ProgressBar[] volume;
    
    Alien[] aliens;
    // used to track the farthest-left empty cell in the aliens array.
    int nextAlien;
    AlienBase[] bases;

    Map map;
    FireStation fireStation;
    int engineSelected;

    //ProgressBar icon health;
    Texture healthIcon;
    //ProgressBar icon fuel;
    Texture fuelIcon;

    //ProgressBar icon volume;
    Texture volumeIcon;

    public GameScreen(final Game game) {
        this.game = game;
        Util.scaleTilesToScreen();
        create();
    }

    public void create() {
        engineSelected = 1;
        nextAlien = 0;

        try {
            map = new Map("background");
        } catch (Exception e) {
            e.printStackTrace();
        }

        batch = new SpriteBatch();
        coreLogic = new CoreLogic();
        inputController = new InputController();
        Gdx.input.setInputProcessor(inputController);

        //Fire Engines:
        fireEngines = new FireEngine[Util.NUMFIREENGINES];
        ////health:
        health = new ProgressBar[Util.NUMFIREENGINES];
        ////fuel:
        fuel = new ProgressBar[Util.NUMFIREENGINES];
        ///volume:
        volume = new ProgressBar[Util.NUMFIREENGINES];

        //health icon - next to health progress bar.
        healthIcon = new Texture("health.png");

        //fuel icon - next to fuel progress bar.
        fuelIcon = new Texture("fuel.png");

        //volume icon - next to volume progress bar.
        volumeIcon = new Texture("water_drop.png");
        
        setUpFireEngines();
        
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

    private void setUpFireEngines() {
        Random randomGenerator = new Random();
        for (int i = 0; i < Util.NUMFIREENGINES; i++) {
            fireEngines[i] = new FireEngine(map, new FireEngineParameters(i));
            fireEngines[i].setPosition(map.getStationX() + 50 * i, map.getStationY() + 50);

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

            int randomValue = randomGenerator.nextInt(7);
            int maxVolume = 0;
            
            switch (randomValue) {
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
    }

    @Override
    public void render(float delta) {
        coreLogic.update();
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        defendBases();
        doProjectileCollision(bases, fireEngines);
        doProjectileCollision(fireEngines, bases);
        doProjectileCollision(aliens, fireEngines);
        doProjectileCollision(fireEngines, aliens);

        batch.begin();
        map.render(batch);

        for (AlienBase base : bases) {
            base.checkHealth();
        }

        if (inputController.moving) {
            selectFireEngine();
            
            if (fireEngines[engineSelected].getFuel() > 0) {
                fireEngines[engineSelected].distanceIncreased();
            }
            fireEngines[engineSelected].move(inputController.getLatestPosition());
            
        } else {
            for(int z = 0; z < Util.NUMFIREENGINES; z = z + 1) {
                fireEngines[z].resetSpeed();
            }
        }
        
        if (inputController.getShotsFired()) {
            if(fireEngines[engineSelected].getVolume() > 0) {
                fireEngines[engineSelected].doWeaponFiring(inputController.getLatestPosition());
            }
        }

        for (int truckIndex = 0; truckIndex < Util.NUMFIREENGINES; truckIndex++) {
            // Refill and repair
            if (map.isInStationRange(fireEngines[truckIndex].getPosition())) {
                if (!fireEngines[truckIndex].isMaxHealth()) {
                    fireEngines[truckIndex].repair();
                } else if (!fireEngines[truckIndex].isMaxFuel()) {
                    fireEngines[truckIndex].refillFuel();
                } else if (!fireEngines[truckIndex].isMaxVolume()) {
                    fireEngines[truckIndex].refillVolume();
                }
            }
            
            fireEngines[truckIndex].render(batch);
            
            // Update bars
            updateBars(truckIndex);
            // Draw bars
            if (!fireEngines[truckIndex].isDead()) {
                health[truckIndex].render(batch, healthIcon);
                fuel[truckIndex].render(batch, fuelIcon);
                volume[truckIndex].render(batch, volumeIcon);
            }
            
        }

        for (int alienIndex = 0; alienIndex<nextAlien; alienIndex++) {
            if (aliens[alienIndex] != null) {
                if (aliens[alienIndex].isDead()) {
                    aliens[alienIndex] = null;
                } else {
                    // update and render aliens
                    aliens[alienIndex].update();
                    aliens[alienIndex].render(batch);
                    // make aliens shoot
                    aliens[alienIndex].doWeaponFiring(fireEngines);
                }
            }
        }
        //ends batch.
        batch.end();

        if (checkGameWon()) {
            //set screen Win
            game.setScreen(new WinnerScreen(game));
        }

        if (checkGameLost()) {
            //set lose screen
            game.setScreen(new LoserScreen(game));
        }

        if (inputController.isEscaped()) {
            game.setScreen (new Menu (game));
        }
    }

    private boolean checkGameWon() {
        for (AlienBase base : bases) {
            if (!base.isDead()) {
                return false;
            }
        }
        return true;
    }

    private boolean checkGameLost() {
        for (FireEngine engine : fireEngines) {
            if (!engine.isDead()) {
                return false;
            }
        }
        return true;
    }

    private void updateBars(int barIndex) {        
        health[barIndex].updateCurrent(fireEngines[barIndex].health);
        fuel[barIndex].updateCurrent(fireEngines[barIndex].fuel);
        volume[barIndex].updateCurrent(fireEngines[barIndex].getVolume());
        
        health[barIndex].setPosition(fireEngines[barIndex].position.x, fireEngines[barIndex].position.invertY().y - 10);
        fuel[barIndex].setPosition(fireEngines[barIndex].position.x, fireEngines[barIndex].position.invertY().y - 25);
        volume[barIndex].setPosition(fireEngines[barIndex].position.x, fireEngines[barIndex].position.invertY().y - 40);
    }

    private void selectFireEngine() {
        for (int engineIndex = 0; engineIndex < Util.NUMFIREENGINES; engineIndex++) {
            double distance = Math.sqrt(Math.pow(inputController.position.x - fireEngines[engineIndex].position.x, 2) + Math.pow(inputController.position.y - fireEngines[engineIndex].position.y, 2));
            if (!fireEngines[engineIndex].isDead() && distance < 40) {
                engineSelected = engineIndex;
                break;
            }
        }
    }

    // TODO: change GameScreen.fireEngines[] and GameScreen.bases[] into HashSets, then remove from sets when dead
    private void doProjectileCollision(Killable[] shooters, Killable[] targets) {
        Set <Projectile> currentProjectiles;
        Set <Projectile> removeProjectiles = new HashSet <Projectile>();

        for (Killable target : targets) {
            if (target != null) {
                for (Killable shooter : shooters) {
                    if (shooter != null) {
                        currentProjectiles = shooter.getProjectiles();
                        for (Projectile projectile : currentProjectiles) {
                            if (projectile != null && !target.isDead() && target.getRect().pointInRectangle(projectile.position)) {
                                removeProjectiles.add(projectile);
                                target.takeDamage(shooter.weapon.weaponDamage);
                            }
                        }

                        currentProjectiles.removeAll(removeProjectiles);
                        shooter.setProjectiles(currentProjectiles);
                    }
                }
            }
        }
    }

    // Alien spawning minorly bugged currently. See AlienBase.doAlienSpawning()
    private void defendBases() {
     // Handle Alien spawning
        for (AlienBase base : this.bases) {
            Alien newAlien = base.doAlienSpawning(this.fireEngines);
            base.doWeaponFiring(fireEngines);
            if (newAlien != null) {
                this.aliens[nextAlien] = newAlien;
                // Theoretically, this should never overflow due to the way i instantiated aliens.
                nextAlien++;
            }
        }
    }

    @Override
    public void show() { }

    @Override
    public void resize(int i, int i1) {Util.scaleTilesToScreen();}

    @Override
    public void pause() { }

    @Override
    public void resume() { }

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        batch.dispose();
    }
}
