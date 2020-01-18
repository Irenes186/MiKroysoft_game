package com.mikroysoft.game;

public class Game extends com.badlogic.gdx.Game {
    public void create() {
        this.setScreen(new Menu(this));
        //this.setScreen(new GameScreen(this));
    }
    public void render() {
        super.render();
    }
}