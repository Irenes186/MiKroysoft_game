package com.mikroysoft.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class BackStoryScreen implements Screen {

    SpriteBatch batch;
    CoreLogic coreLogic;
    InputController inputController;
    Game game;
    Texture buttonTexture;
    TextureRegion textureRegion;
    TextureRegionDrawable textureRegionDrawable;
    ImageButton backMenuButton;
    Label backStoryTitle;
    Stage stage;
    BitmapFont font;


    public BackStoryScreen(final Game game){
        this.game = game;

        //setting label
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        BitmapFont font = new BitmapFont();
        labelStyle.font = font;
        labelStyle.fontColor = Color.WHITE;

        backStoryTitle = new Label("Back Story!",labelStyle);
        backStoryTitle.setFontScale(3.0f, 5.0f);
        backStoryTitle.setPosition(475,650);
        backStoryTitle.setAlignment(Align.center);

        //Button image set up
        buttonTexture = new Texture("planet_button_0.png");
        textureRegion = new TextureRegion(buttonTexture);
        textureRegionDrawable = new TextureRegionDrawable(textureRegion);
        //add different buttons
        backMenuButton = new ImageButton(textureRegionDrawable);

        //set stage
        stage = new Stage(new ScreenViewport()); //Set up a stage for the ui
        stage.addActor(backMenuButton);
        stage.addActor(backStoryTitle);
        Gdx.input.setInputProcessor(stage);

        //if instructionButton clicked go to instruction
        backMenuButton.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {
                //super.clicked(event, x, y);
                game.setScreen(new Menu(game));
            }
        });

        //variables for screen size and button size
        float screenWidth = 1024, screenHeight = 1024;
        float buttonWidth = screenWidth * 0.1f, buttonHeight = screenHeight * 0.1f;
        //playButton position ans size
        backMenuButton.setSize(buttonWidth,buttonHeight);
        backMenuButton.setPosition(screenWidth / 2 - buttonWidth,screenHeight / 2 - buttonHeight);
        backMenuButton.getImage().setFillParent(true);

    }

    @Override
    public void show() {
    }

    @Override
    public void render(float v) {
        //Background
        Gdx.gl.glClearColor(0.2f,0,0.2f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //draw stage with actors
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();

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

    }
}
