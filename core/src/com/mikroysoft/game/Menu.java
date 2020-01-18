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
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class Menu implements Screen {
    SpriteBatch batch;
    CoreLogic coreLogic;
    InputController inputController;
    Game game;
    Texture buttonTexture;
    TextureRegion textureRegion;
    TextureRegionDrawable textureRegionDrawable;
    Texture buttonTexture2;
    TextureRegion textureRegion2;
    TextureRegionDrawable textureRegionDrawable2;
    ImageButton playButton;
    ImageButton instructionButton;
    ImageButton backStoryButton;
    ImageButton exitButton;
    Label mainTitle;
    Label playLabel;
    Label instructionLabel;
    Label backStoryLabel;
    Label exitLabel;
    Stage stage;
    BitmapFont font;

    public Menu(final Game game){
        this.game = game;

        //LABEL
        //setting label
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        font = new BitmapFont();
        labelStyle.font = font;
        labelStyle.fontColor = Color.WHITE;
        //alternative text style
        Label.LabelStyle labelStyle2 = new Label.LabelStyle();
        labelStyle2.font = font;
        labelStyle2.fontColor = Color.BLACK;
        //play button text
        playLabel = new Label("Play!",labelStyle2);
        playLabel.setFontScale(3.0f, 3.0f);
        playLabel.setPosition(495,415);
        playLabel.setAlignment(Align.center);
        //instruction button text
        instructionLabel = new Label("Instructions",labelStyle2);
        instructionLabel.setFontScale(1.2f, 1.5f);
        instructionLabel.setPosition(200,305);
        instructionLabel.setAlignment(Align.center);
        //story button text
        backStoryLabel = new Label("Story",labelStyle2);
        backStoryLabel.setFontScale(2.0f, 2.0f);
        backStoryLabel.setPosition(485,155);
        backStoryLabel.setAlignment(Align.center);
        //exit button text
        exitLabel = new Label("Exit",labelStyle2);
        exitLabel.setFontScale(2.0f, 2.0f);
        exitLabel.setPosition(750,305);
        exitLabel.setAlignment(Align.center);
        //Main title
        mainTitle = new Label("KROY! (by MiKroysoft)",labelStyle);
        mainTitle.setFontScale(3.0f, 5.0f);
        mainTitle.setPosition(460,650);
        mainTitle.setAlignment(Align.center);


        //Button image set up
        buttonTexture = new Texture("planet_button_0.png");
        textureRegion = new TextureRegion(buttonTexture);
        textureRegionDrawable = new TextureRegionDrawable(textureRegion);
        //set another texture
        buttonTexture2 = new Texture("planet_button_1.png");
        textureRegion2 = new TextureRegion(buttonTexture2);
        textureRegionDrawable2 = new TextureRegionDrawable(textureRegion2);
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
        //if exit button clicked then exit game
        exitButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                new Game().dispose(); // ref to Game Class
                Gdx.app.exit();
                //super.clicked(event, x, y);
            }
        });

        Button.ButtonStyle style = new Button.ButtonStyle(); //???

        //variables for screen size and button size
        float screenWidth = Util.MAPWIDTH * Util.TILEWIDTH, screenHeight = Util.MAPHEIGHT * Util.TILEHEIGHT;
        float buttonWidth = screenWidth * 0.1f, buttonHeight = screenHeight * 0.1f;
        //playButton position and size
        playButton.setSize(buttonWidth * 1.8f,buttonHeight * 1.8f);
        playButton.setPosition(335, 250);
        playButton.getImage().setFillParent(true);
        //instructionButton position and size
        instructionButton.setSize(buttonWidth * 1.2f,buttonHeight * 1.2f);
        instructionButton.setPosition(120,200);
        instructionButton.getImage().setFillParent(true);
        //backStoryButton position and size
        backStoryButton.setSize(buttonWidth * 1.2f,buttonHeight * 1.2f);
        backStoryButton.setPosition(390, 50);
        backStoryButton.getImage().setFillParent(true);
        //exitButton position and size
        exitButton.setSize(buttonWidth * 1.2f,buttonHeight * 1.2f);
        exitButton.setPosition(650, 200);
        exitButton.getImage().setFillParent(true);

        //set stage
        stage = new Stage(new ScreenViewport()); //Set up a stage for the ui
        //add buttons to stage on menu stage
        stage.addActor(playButton);
        stage.addActor(instructionButton);
        stage.addActor(backStoryButton);
        stage.addActor(exitButton);
        stage.addActor(mainTitle);
        stage.addActor(playLabel);
        stage.addActor(instructionLabel);
        stage.addActor(backStoryLabel);
        stage.addActor(exitLabel);
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

        /*//add writing //DOES NOT WORK
        this.batch.begin();
        this.font.draw(this.batch, "Play", this.playButton.getOriginX(), this.playButton.getOriginY());
*/
    }

    @Override
    public void resize(int width, int height) { }
    @Override
    public void pause() { }
    @Override
    public void resume() { }
    @Override
    public void hide() { }
    @Override
    public void dispose() { }

}
