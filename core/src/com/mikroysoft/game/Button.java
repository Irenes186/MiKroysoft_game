package com.mikroysoft.game;

import com.badlogic.gdx.graphics.Texture;

public class Button {

    public Texture texture;
    public Coordinate position;
    public int height;
    public int width;
    public boolean hover;

    public Button(int height, int width, String filename, Coordinate position) {
        this.height = height;
        this.width = width;
        this.texture = new Texture(filename);
        this.position = position;
        hover = false;


    }

    public void hover(Coordinate input){
        hover = (input.x <= position.x + width) && (input.x >= position.x) 
            && (input.y <= position.y + height) && (input.y >= position.y);
    }



}
