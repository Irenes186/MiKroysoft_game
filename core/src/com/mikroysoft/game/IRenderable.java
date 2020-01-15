package com.mikroysoft.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

interface IRenderable {
    void render(SpriteBatch batch);
    void update();
    Coordinate position = new Coordinate(0, 0);
}