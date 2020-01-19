package com.mikroysoft.game;
import java.util.Random;

import com.badlogic.gdx.Gdx;

import java.lang.Math;

/**
 * 
 */
public final class Util {
    public static int MAPWIDTH = 20;
    public static int MAPHEIGHT = 20;
    public static int TILEWIDTH = 51;
    public static int TILEHEIGHT = 42;
    public static int NUMFIREENGINES = 2;
    
	/**
     * Generates two random floats representing coordinates, each between min and max.
     * the coordinate cannot be within the middle (percentDistFromCenter * 100) percent area of
     * the possible square of coordinates. E.g: randomCoordOffset(0.0f, 4.0f, 0.5f)
     * 
     * x = 0 -> x = 4
     * +----+ y = 0
     * |    |	
     * | xx |
     * | xx |
     * |    |
     * +----+ y = 4
     * 
     * Calling the aforementioned parameters gives 4.0x4.0 square of possible values.
     * The returned coordinate will be NOT be within the 'x' marked regions; the middle 50%.
     * 
     * Negative numbers are accepted for min and max.
     * The coordinate is returned as a Float[2] in the format [x, y].
     * 
     * @param min The first coordinate for the generation square.
     * @param min The second coordinate for the generation square.
     * @param percentDistFromCenter The area that is excluded from the generation square.
     */
    public static Float[] randomCoordOffset (float min, float max, float percentDistFromCenter) {
    	Random randomGen = new Random();
    	Float[] offsets = new Float[2];
    	offsets[0] = randomGen.nextFloat(); // generate a number between min and max
    	if (Math.abs(offsets[0]) >= percentDistFromCenter) {
    		offsets[1] = min + randomGen.nextFloat() * (max - min);
    	} else {
    		offsets[1] = percentDistFromCenter + randomGen.nextFloat() * (1.0f - percentDistFromCenter);
    		if (randomGen.nextBoolean()) {
    			offsets[1] = ((max + min)/2) - (offsets[1] * (max - min));
    		} else {
    			offsets[1] = ((max + min)/2) + (offsets[1] * (max - min));
    		}
    	}
    	offsets[0] = min + offsets[0] * (max - min);
    	return offsets;
    }
    
    /**
     * Returns two random numbers, each between min and max.
     * acts as a Wrapper method for randomCoordOffset
     * 
     * @param min The position to pass to RCO
     * @param max The postition to pass to RCO
     */ 
    public static Float[] randomCoordOffset (float min, float max) {
    	return randomCoordOffset(min, max, 0.0f);
    }
    
    /**
     * Changes the internal knowledge of the sizes of the game to
     * match the size of the screen.
     */
    public static void scaleTilesToScreen() {
        TILEWIDTH = Gdx.graphics.getWidth() / MAPWIDTH;
        TILEHEIGHT = Gdx.graphics.getHeight() / MAPHEIGHT;
    }
}
