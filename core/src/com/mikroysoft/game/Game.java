package com.mikroysoft.game;

public class Game extends com.badlogic.gdx.Game {

    @Override
    public void create() {
        this.setScreen(new Menu(this));
    }

    public void render() {
        super.render();
    }
}
