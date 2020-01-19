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


/**
 * The screen that that tells the user that they have lost the game.
 */
public class LoserScreen implements Screen {
    private Game game;
    private Texture buttonTexture;
    private TextureRegion textureRegion;
    private TextureRegionDrawable textureRegionDrawable;
    private ImageButton exitButton;
    private Stage stage;
    private BitmapFont font;
    private Label outcomeTitle;
    private Label menuButtonLabel;


    /**
     * Class Constructor.
     * Creates the buttons on the screen and adds text to each button.
     * 
     * @param game The game object that the screen should be displayed on.
     */
    public LoserScreen(final Game game){
        this.game = game;
        font = new BitmapFont();

        //setting label
        Label.LabelStyle labelStyle = makeLabelStyle(Color.WHITE);
        //alternative text style
        Label.LabelStyle labelStyle2 = makeLabelStyle(Color.BLACK);

        outcomeTitle = new Label("you lose :(",labelStyle);
        outcomeTitle.setFontScale(3.0f, 5.0f);
        outcomeTitle.setPosition(475,400);
        outcomeTitle.setAlignment(Align.center);

        menuButtonLabel = new Label("Menu",labelStyle2);
        menuButtonLabel.setFontScale(1.5f, 1.5f);
        menuButtonLabel.setPosition(475,235);
        menuButtonLabel.setAlignment(Align.center);

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
        stage.addActor(menuButtonLabel);
        Gdx.input.setInputProcessor(stage);

        //if instructionButton clicked go to instruction
        exitButton.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {
                //super.clicked(event, x, y);
                game.setScreen(new Menu(game));

            }
        });

        //playButton position and size
        scaleButtonToScreen(exitButton, 0.1f, 0.1f);
        exitButton.setPosition(400,150);
        exitButton.getImage().setFillParent(true);
    }

    /**
     * Helper function that return a LabelStyle object with the 
     * provided colour and font.
     * 
     * @param textColour The Color object that the LabelStyle should take.
     * @return A LabelStyle object that contains the specified requirements
     */
    private Label.LabelStyle makeLabelStyle(Color textColour) {
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = font;
        labelStyle.fontColor = textColour;
        return labelStyle;
    }
    
    /**
     * Changes the size of the provided button object by the factors provided.
     * 
     * @param button The ImageButton object that is to be scaled.
     * @param widthFactor Factor to scale the x direction of the button by.
     * @param heightFactor Factor to scale the y direction of the button by.
     */
    private void scaleButtonToScreen(ImageButton button, float widthFactor, float heightFactor) {
        button.setSize(Gdx.graphics.getWidth() * widthFactor, Gdx.graphics.getHeight() * heightFactor);
    }
    
    
    @Override
    public void show() {

    }

    @Override
    public void render(float v) {
        //Background
        Gdx.gl.glClearColor(0f,0f,0.2f,0);
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
