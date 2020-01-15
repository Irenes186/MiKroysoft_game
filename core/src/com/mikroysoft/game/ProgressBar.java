package com.mikroysoft.game;

import com.badlogic.gdx.graphics.Texture;

public class ProgressBar {
    public Texture texture;
    private float height;
    private float width;
    public Coordinate position;
    private int maximum;
    private int current;
    private boolean visible;
    private int barFill;

    public ProgressBar(BarColour colour) {
        if (colour == BarColour.YELLOW) {
            texture = new Texture("bar_01.png");
        }
        else if (colour == BarColour.BLUE) {
            texture = new Texture("bar_02.png");
        }
        else if (colour == BarColour.PINK) {
        	texture = new Texture("bar_03.png");
        }
        height = 0;
        width = 0;
        maximum = 100;
        visible = true;
        current = 0;
        barFill = 0;
    }

    public float getHeight () {
        return this.height;
    }

    public int getFill() {
        return this.barFill;
    }
    public float getWidth () {
        return this.width;
    }

    public void setDimensions(float w, float h) {
        this.height = h;
        this.width = w;
    }

    public void setPosition(float x, float y) {
        this.position = new Coordinate(x, y);
    }

    public void setMax (int m) {
        this.maximum = m;
    }

    public void updateCurrent (int c) {
        this.current = c;
        float fraction = this.width/this.maximum;
        int multiplication = Math.round(fraction * this.current);
        this.barFill = multiplication;
    }

    public void toggleVis () {
        if(this.visible == false) {
            this.visible = true;
        } else {
            this.visible = false;
        }
    }
}
