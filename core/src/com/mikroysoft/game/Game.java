package com.mikroysoft.game;

/**
 * This is used as the backend of the game by inheriting the Game class from libgdx and using it to
 * set the different screens
 * @see https://libgdx.badlogicgames.com/ci/nightlies/docs/api/com/badlogic/gdx/Game.html
 */
public class Game extends com.badlogic.gdx.Game {
    /**
     * This method is called as the Game class is created. All it does is set the current screen to
     * Menu so the user can interact with the application
     */
    public void create() {
        this.setScreen(new Menu(this));
        //this.setScreen(new GameScreen(this));
    }

    /**
     * This is the render method that is called every frame, in this class all it does is call the
     * parent render method
     */
    public void render() {
        super.render();
    }
}
