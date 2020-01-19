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
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

/**
 * This screen creates a menu to access the information for the game as
 * well as start the game.
 */
public class Menu implements Screen {
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

    /**
     * Class Constructor.
     * Adds all the buttons to the screen as well as the text for each button
     * also creates listeners for the button so that click detection can take place.
     * 
     * @param game The game object that the screen will be displayed on.
     */
    public Menu(final Game game){
        this.game = game;
        font = new BitmapFont();

        //setting label
        Label.LabelStyle labelStyle = makeLabelStyle(Color.WHITE);
        //alternative text style
        Label.LabelStyle labelStyle2 = makeLabelStyle(Color.BLACK);

        //play button text
        playLabel = new Label("Play!",labelStyle2);
        playLabel.setFontScale(3.0f, 3.0f);
        playLabel.setPosition(495,400);
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

        //playButton position and size
        scaleButtonToScreen(playButton, 0.18f, 0.18f);
        playButton.setPosition(335, 250);
        playButton.getImage().setFillParent(true);

        //instructionButton position and size
        scaleButtonToScreen(instructionButton, 0.12f, 0.12f);
        instructionButton.setPosition(120,200);
        instructionButton.getImage().setFillParent(true);

        //backStoryButton position and size
        scaleButtonToScreen(backStoryButton, 0.12f, 0.12f);
        backStoryButton.setPosition(390, 50);
        backStoryButton.getImage().setFillParent(true);

        //exitButton position and size
        scaleButtonToScreen(exitButton, 0.12f, 0.12f);
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

    
    private Label.LabelStyle makeLabelStyle(Color textColour) {
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = font;
        labelStyle.fontColor = textColour;
        return labelStyle;
    }

    /**
     * This is a method that resizes the button with the screen so that they always appear the right
     * size.
     * @param button - This is an ImageButton that you wish to resize
     * @param widthFactor - This a factor by how much you wish to scale the button in a value of 1
     * would keep it at the same width
     * @param heightFactor - This is factor exactly like widthFactor but for the height
     * @return void
     */
    private void scaleButtonToScreen(ImageButton button, float widthFactor, float heightFactor) {
        button.setSize(Gdx.graphics.getWidth() * widthFactor, Gdx.graphics.getHeight() * heightFactor);
    }

    
    /**
     * This is a function that comes from the parent class screen, in this class no alterations are
     * made to this function
     * @return void
     * @see https://libgdx.badlogicgames.com/ci/nightlies/docs/api/com/badlogic/gdx/Screen.html
     */
    @Override
    public void show() {
    }

    /**
     * This is the render function that is called on every frame to draw things onto the screen.
     * 
     * @param v - This is the time inbetween each called to this function.
     */
    @Override
    public void render(float delta) {
        //Menu background
        Gdx.gl.glClearColor(0,0,0.2f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //draw stage with actors
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    /**
     * This is a function that comes from the parent class screen, in this class no alterations are
     * made to this function
     * @return void
     * @see https://libgdx.badlogicgames.com/ci/nightlies/docs/api/com/badlogic/gdx/Screen.html
     */
    @Override
    public void resize(int width, int height) { }

    /**
     * This is a function that comes from the parent class screen, in this class no alterations are
     * made to this function
     * @return void
     * @see https://libgdx.badlogicgames.com/ci/nightlies/docs/api/com/badlogic/gdx/Screen.html
     */
    @Override
    public void pause() { }

    /**
     * This is a function that comes from the parent class screen, in this class no alterations are
     * made to this function
     * @return void
     * @see https://libgdx.badlogicgames.com/ci/nightlies/docs/api/com/badlogic/gdx/Screen.html
     */
    @Override
    public void resume() { }

    /**
     * This is a function that comes from the parent class screen, in this class no alterations are
     * made to this function
     * @return void
     * @see https://libgdx.badlogicgames.com/ci/nightlies/docs/api/com/badlogic/gdx/Screen.html
     */
    @Override
    public void hide() { }

    /**
     * This is a function that comes from the parent class screen, in this class no alterations are
     * made to this function
     * @return void
     * @see https://libgdx.badlogicgames.com/ci/nightlies/docs/api/com/badlogic/gdx/Screen.html
     */
    @Override
    public void dispose() { }

}
