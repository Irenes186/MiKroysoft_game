package com.mikroysoft.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class WinnerScreen implements Screen {

    Game game;
    Texture buttonTexture;
    TextureRegion textureRegion;
    TextureRegionDrawable textureRegionDrawable;
    ImageButton exitButton;
    Stage stage;
    BitmapFont font;
    Label outcomeTitle;

    public WinnerScreen(final Game game){
        this.game = game;

        //setting label
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        BitmapFont font = new BitmapFont();
        labelStyle.font = font;
        labelStyle.fontColor = Color.WHITE;

        outcomeTitle = new Label("Winner!",labelStyle);
        outcomeTitle.setFontScale(3.0f, 5.0f);
        outcomeTitle.setPosition(475,650);
        outcomeTitle.setAlignment(Align.center);

        //Button image set up
        buttonTexture = new Texture("planet_button_0.png");
        textureRegion = new TextureRegion(buttonTexture);
        textureRegionDrawable = new TextureRegionDrawable(textureRegion);
        //add different buttons
        exitButton = new ImageButton(textureRegionDrawable);

        //set stage
        stage = new Stage(new ScreenViewport()); //Set up a stage for the ui
        stage.addActor(exitButton);
        stage.addActor(outcomeTitle);
        Gdx.input.setInputProcessor(stage);

        //if instructionButton clicked go to instruction
        exitButton.addListener(new ClickListener() {

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
        exitButton.setSize(buttonWidth,buttonHeight);
        exitButton.setPosition(500,500);
        exitButton.getImage().setFillParent(true);


    }


    @Override
    public void show() {

    }

    @Override
    public void render(float v) {
        //Background
        Gdx.gl.glClearColor(0.5f,0,0,1);
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
