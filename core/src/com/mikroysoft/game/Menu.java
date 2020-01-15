package com.mikroysoft.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class Menu implements Screen {

    SpriteBatch batch;
    //Button play;
    CoreLogic coreLogic;
    InputController inputController;
    Game game;
    Texture playButtonTexture;
    TextureRegion textureRegion;
    TextureRegionDrawable textureRegionDrawable;
    ImageButton playButton;
    ImageButton instructionButton;
    ImageButton backStoryButton;
    ImageButton exitButton;
    Stage stage;
    BitmapFont font;

    public Menu(final Game game){
        this.game = game;

        //Font set up
        this.batch = new SpriteBatch();
        this.font = new BitmapFont();
        font.setColor(0.5f,0.4f,0,1);
        font.getData().scale(3);

        //Play Button set up
        playButtonTexture = new Texture("planet_button_0.png");
        textureRegion = new TextureRegion(playButtonTexture);
        textureRegionDrawable = new TextureRegionDrawable(textureRegion);
        //add different buttons
        playButton = new ImageButton(textureRegionDrawable);
        instructionButton = new ImageButton(textureRegionDrawable);
        backStoryButton = new ImageButton(textureRegionDrawable);
        exitButton = new ImageButton(textureRegionDrawable);


        //If playButton clicked go to game screen
        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new GameScreen(game));
            }
        });
        //if instructionButton clicked go to instruction
        instructionButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new InstructionScreen(game));
            }
        });
        //if instructionButton clicked go to instruction
        backStoryButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new BackStoryScreen(game));
            }
        });



        Button.ButtonStyle style = new Button.ButtonStyle(); //???

        //variables for screen size and button size
        float screenWidth = 1024, screenHeight = 1024;
        float buttonWidth = screenWidth * 0.1f, buttonHeight = screenHeight * 0.1f;
        //playButton set up
        playButton.setSize(buttonWidth,buttonHeight);
        playButton.setPosition(screenWidth / 2 - buttonWidth,screenHeight / 2 - buttonHeight);
        playButton.getImage().setFillParent(true);
        //instructionButton set up
        instructionButton.setSize(buttonWidth,buttonHeight);
        instructionButton.setPosition(screenWidth / 4 - buttonWidth,screenHeight / 4 - buttonHeight);
        instructionButton.getImage().setFillParent(true);
        //backStoryButton set up
        backStoryButton.setSize(buttonWidth,buttonHeight);
        backStoryButton.setPosition(screenWidth / 8 - buttonWidth,screenHeight / 8 - buttonHeight);
        backStoryButton.getImage().setFillParent(true);



        //set stage
        stage = new Stage(new ScreenViewport()); //Set up a stage for the ui
        //add buttons to stage on menu stage
        stage.addActor(playButton);
        stage.addActor(instructionButton);
        stage.addActor(backStoryButton);
        Gdx.input.setInputProcessor(stage);


    }

    @Override
    public void show() {
        //Gdx.input.setInputProcessor((InputProcessor) this);
        //this.game.setScreen(new GameScreen(this.game));
    }

    @Override
    public void render(float delta) {
        //Menu background
        Gdx.gl.glClearColor(0,0,0.2f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //draw stage with actors
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();

        //add writing //DOES NOT WORK
        this.batch.begin();
        this.font.draw(this.batch, "Play", this.playButton.getOriginX(), this.playButton.getOriginY());
        this.batch.end();
    }

    @Override
    public void resize(int width, int height) {

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

    }

}
