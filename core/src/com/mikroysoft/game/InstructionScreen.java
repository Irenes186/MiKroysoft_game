package com.mikroysoft.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class InstructionScreen implements Screen {

    SpriteBatch batch;
    //Button play;
    CoreLogic coreLogic;
    InputController inputController;
    Game game;
    Texture playButtonTexture;
    TextureRegion textureRegion;
    TextureRegionDrawable textureRegionDrawable;
    ImageButton BackMenuButton;
    Stage stage;
    BitmapFont font;

    public InstructionScreen(Game game){
        this.game = game;
    }

    @Override
    public void show() {



        //set stage
        stage = new Stage(new ScreenViewport()); //Set up a stage for the ui
        //add buttons
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float v) {
        //Background
        Gdx.gl.glClearColor(0,0.2f,0.2f,1);
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
