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
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class InstructionScreen implements Screen {
    Game game;
    Texture buttonTexture;
    TextureRegion textureRegion;
    TextureRegionDrawable textureRegionDrawable;
    ImageButton backMenuButton;
    Stage stage;
    BitmapFont font;
    Label instructionTitle;
    Label menuButtonLabel;
    Skin mySkin;
    private SpriteBatch batch;
    String textFileString;

    public InstructionScreen(final Game game) {
        this.game = game;
        batch = new SpriteBatch();
        font = new BitmapFont();

        //setting label
        Label.LabelStyle labelStyle = makeLabelStyle(Color.WHITE);
        //alternative text style
        Label.LabelStyle labelStyle2 = makeLabelStyle(Color.BLACK);

        //Title label
        instructionTitle = new Label("Instructions!", labelStyle);
        instructionTitle.setFontScale(3.0f, 5.0f);
        instructionTitle.setPosition(475, 650);
        instructionTitle.setAlignment(Align.center);
        
        //Button text
        menuButtonLabel = new Label("Menu",labelStyle2);
        menuButtonLabel.setFontScale(1.5f, 1.5f);
        menuButtonLabel.setPosition(875,105);
        menuButtonLabel.setAlignment(Align.center);

        //Button image set up
        buttonTexture = new Texture("planet_button_0.png");
        textureRegion = new TextureRegion(buttonTexture);
        textureRegionDrawable = new TextureRegionDrawable(textureRegion);
        
        //add different buttons
        backMenuButton = new ImageButton(textureRegionDrawable);
        
        //set stage with actors
        stage = new Stage(new ScreenViewport()); //Set up a stage for the ui
        stage.addActor(backMenuButton);
        stage.addActor(instructionTitle);
        stage.addActor(menuButtonLabel);
        Gdx.input.setInputProcessor(stage);

        //if instructionButton clicked go to instruction
        backMenuButton.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {
                //super.clicked(event, x, y);
                game.setScreen(new Menu(game));

            }
        });

        //playButton position and size
        scaleButtonToScreen(backMenuButton, 0.1f, 0.1f);
        backMenuButton.setPosition(800, 20);
        backMenuButton.getImage().setFillParent(true);

        textFileString = Gdx.files.internal("instructions.txt").readString();
    }
    
    private Label.LabelStyle makeLabelStyle(Color textColour) {
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = font;
        labelStyle.fontColor = textColour;
        return labelStyle;
    }
    
    private void scaleButtonToScreen(ImageButton button, float widthFactor, float heightFactor) {
        button.setSize(Gdx.graphics.getWidth() * widthFactor, Gdx.graphics.getHeight() * heightFactor);
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float v) {
        //Background
        Gdx.gl.glClearColor(0,0.2f,0.2f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        //draw stage with actors
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
        
        batch.begin();
        font.draw(batch, textFileString,(Gdx.graphics.getWidth()/2) - 300,(Gdx.graphics.getHeight()/2) + 150);
        batch.end();
    }

    @Override
    public void resize(int i, int i1) { }

    @Override
    public void pause() { }

    @Override
    public void resume() { }

    @Override
    public void hide() { }

    @Override
    public void dispose() { }
}
