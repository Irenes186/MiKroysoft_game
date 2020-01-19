package com.mikroysoft.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Creates a bar to store any integer value and display
 * it as a percentage of a provided maximum.
 */
public class ProgressBar {
    public Texture texture;
    private float height;
    private float width;
    public Coordinate position;
    private int maximum;
    private int current;
    private boolean visible;
    private int barFill;

    /**
     * Class Constuctor.
     * Assigns the bar a colour.
     * 
     * @param colour an entry from the BarColour object that
     * determines bar colour.
     */
    public ProgressBar(BarColour colour) {
        switch (colour) {
            case YELLOW:
                texture = new Texture("bar_01.png");
                break;
            case BLUE:
                texture = new Texture("bar_02.png");
                break;
            case PINK:
                texture = new Texture("bar_03.png");
                break;
            default:
                throw new IllegalArgumentException ("ProgressBar given unsupported BarColour: " + colour);
        }
        height = 0;
        width = 0;
        maximum = 100;
        visible = true;
        current = 0;
        barFill = 0;
    }

    /**
     * Returns the height of the bar in pixels
     * 
     * @return The bar height as an integer.
     */
    public float getHeight () {
        return this.height;
    }

    /**
     * Returns the pixel width of the filled part of the bar.
     * 
     * @return The filled width as an integer.
     */
    public int getFill() {
        return this.barFill;
    }

    /**
     * Returns the width of the bar in pixels
     * 
     * @return The bar width as an integer.
     */
    public float getWidth () {
        return this.width;
    }

    /**
     * Sets the bar's height and width to the values provided.
     * 
     * @param w The new width of the bar in pixels.
     * @param h The new Height of the bar in pixels.
     */
    public void setDimensions(float w, float h) {
        this.height = h;
        this.width = w;
    }

    /**
     * Sets the bottom left coordiante of the bar
     * to the provided a and y coordinates.
     * 
     * @param x The new x coordinate of the bar.
     * @param y The new y coordinate of the bar.
     */
    public void setPosition(float x, float y) {
        this.position = new Coordinate(x, y);
    }

    /**
     * Returns the predefined maximum that the bar
     * value can be set to can hold.
     * 
     * @return the maximum of the object as an integer.
     */
    public void setMax (int m) {
        this.maximum = m;
    }

    /**
     * Using the provided value, changes the value for current
     * and updates the bar fill so that the visual display matches
     * the internal value.
     * 
     * @param c The value that current will be set to.
     */
    public void updateCurrent (int c) {
        this.current = c;
        float fraction = this.width/this.maximum;
        int multiplication = Math.round(fraction * this.current);
        this.barFill = multiplication;
    }

    /**
     * Toggle the visibility of the bar when called.
     */
    public void toggleVis () {
        if(this.visible == false) {
            this.visible = true;
        } else {
            this.visible = false;
        }
    }
    
    /**
     * Renders the bar and the icon provided for the bar, at the
     * coordinate of the this object.
     * 
     * @param batch The spritebatch object for the batch that the bar
     * should be rendered with.
     * @param icon The texture object for the icon of the bar.
     */
    public void render(SpriteBatch batch, Texture icon) {
        batch.draw(texture, position.x, position.y, barFill, height);
        batch.draw(icon, position.x - (height + 5), position.y, height, height);
    }
}
