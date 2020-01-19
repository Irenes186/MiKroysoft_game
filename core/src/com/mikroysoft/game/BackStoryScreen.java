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


/**
 * This is the class that is used to display the backstory of the game as the user selects the story
 * button from the main menu.
 */
public class BackStoryScreen implements Screen {
    //This is the batch that will draw everything to the back story screen
    SpriteBatch batch;
    Game game;
    Texture buttonTexture;
    TextureRegion textureRegion;
    TextureRegionDrawable textureRegionDrawable;
    ImageButton backMenuButton;
    Label backStoryTitle;
    Label menuButtonLabel;
    Stage stage;
    BitmapFont font;
    String textFileString;

    /**
     * This constructor takes an instance of the game which is used to create a back button so the
     * user can exit from the back story screen and back into the main menu, the main graphics
     * elements of this screen is created in this constructor.
     * @param game - Use to set the screen to the menu screen when the back button is pressed
     */
    public BackStoryScreen(final Game game){
        batch = new SpriteBatch();
        this.game = game;
        font = new BitmapFont();

        //setting label
        Label.LabelStyle labelStyle = makeLabelStyle(Color.WHITE);
        //alternative text style
        Label.LabelStyle labelStyle2 = makeLabelStyle(Color.BLACK);

        backStoryTitle = new Label("Back Story!",labelStyle);
        backStoryTitle.setFontScale(3.0f, 5.0f);
        backStoryTitle.setPosition(475,650);
        backStoryTitle.setAlignment(Align.center);

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

        //set stage
        stage = new Stage(new ScreenViewport()); //Set up a stage for the ui
        stage.addActor(backMenuButton);
        stage.addActor(backStoryTitle);
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
        backMenuButton.setPosition(800,20);
        backMenuButton.getImage().setFillParent(true);

        //lore.txt contains the text of the backstory
        textFileString = Gdx.files.internal("lore.txt").readString();
    }

    /**
     * This is a private function that returns a new label style with the font field set in the
     * constructor and the text colour set through a parameter.
     * @param textColour - The colour to make the label sytle in
     * @return Label.LabelStyle - In built libgdx class of label styles, more information is
     * referenced in see also 
     * @see https://libgdx.badlogicgames.com/ci/nightlies/docs/api/com/badlogic/gdx/scenes/scene2d/ui/Label.LabelStyle.html
     */
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
    public void show() {}

    /**
     * This is the render function that is called on every frame to draw things onto the screen,
     * including the font, the background and the stage
     * @param delta - This is the time inbetween each called to this function.
     */
    @Override
    public void render(float delta) {
        //Background
        Gdx.gl.glClearColor(0.2f,0,0.2f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //draw stage with actors
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();

        batch.begin();
        font.draw(batch, textFileString,(Gdx.graphics.getWidth()/2) - 455,(Gdx.graphics.getHeight()/2) + 150);
        batch.end();
    }

    /**
     * This is a function that comes from the parent class screen, in this class no alterations are
     * made to this function
     * @param width - What width to resize the screen too
     * @param height - What height to resize the screen too
     * @return void
     * @see https://libgdx.badlogicgames.com/ci/nightlies/docs/api/com/badlogic/gdx/Screen.html
     */
    @Override
    public void resize(int width, int height) {}

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
