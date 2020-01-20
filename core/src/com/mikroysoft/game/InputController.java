package com.mikroysoft.game;
import com.badlogic.gdx.InputProcessor;

/** This class handles all of the input from the user while the user is within the game screen. This
 * means that it calls all of the methods to control the fire engine as well as escaping from the
 * game to the menu screen.
 *
 */
public class InputController implements InputProcessor {
    public Coordinate position;
    public boolean moving;
    private boolean shotsFired;
    private boolean escaped;

    /** The constructor only intializes default values for all of the fields
     *
     */
    public InputController() {
        position = new Coordinate(-1,-1);
        shotsFired = false;
        escaped = false;
    }

    /** This sets the moving property of the input controller to false
     *
     */
    public void setMoveFalse() {

        this.moving = false;
    }

    /** This is an inbuilt function that is inherited from InputProcessor. It is triggered everytime
     * the user presses a key on the keyboard. We have used it to check wether the escape key has
     * been pressed.
     *
     * @param keycode - this is an integer value that represents the key that was pressed on the
     * keyboard
     *
     * @return boolean - This function always returns false.
     *
     * @see https://libgdx.badlogicgames.com/ci/nightlies/docs/api/com/badlogic/gdx/InputProcessor.html
     */
    public boolean keyDown (int keycode) {
        switch (keycode) {
            case 131: //this is the escape key code to exit to the menu
                escaped = true;
                break;
        }
        return false;
    }

    /** This is an inbuilt function that is inherieted from the InputProcessor. It is triggered
     * everytime a key is released on the keyboard
     *
     * @param keycode - This is an integer that represents the key that was released on the keyboard
     *
     * @return boolean - This function always returns false
     *
     * @see https://libgdx.badlogicgames.com/ci/nightlies/docs/api/com/badlogic/gdx/InputProcessor.html
     */
    public boolean keyUp (int keycode) {
        return false;
    }

    /** This is an inbuilt function that is inherieted from the InputProcessor. It is triggered
     * every time a character is typed
     *
     * @param character - represents the character that was typed on the keyboard
     *
     * @return boolean - This function always returns false
     *
     * @see https://libgdx.badlogicgames.com/ci/nightlies/docs/api/com/badlogic/gdx/InputProcessor.html
     */
    public boolean keyTyped (char character) { return false; }

    /** This is an inbuilt function that is inherieted from the InputProcessor. It is triggered
     * every time the mouse is moved.
     *
     * @param x - The x position value of the mouse
     * @param y - The y position value of the mouse
     *
     * @return boolean - This function always returns false
     *
     * @see https://libgdx.badlogicgames.com/ci/nightlies/docs/api/com/badlogic/gdx/InputProcessor.html
     */
    public boolean mouseMoved (int x, int y) { return false; }

    /** This is an inbuilt function that is inherieted from the InputProcessor. It is triggered
     * every time a scroll is detected
     *
     * @param character - represents the amount the user has scrolled as an integer value
     *
     * @return boolean - This function always returns false
     *
     * @see https://libgdx.badlogicgames.com/ci/nightlies/docs/api/com/badlogic/gdx/InputProcessor.html
     */
    public boolean scrolled (int amount) { return false; }

    /** This returns the latest value that the field position of this class has.
     *
     * @return Coordinate - An instance of the coordinate value with the most recent position
     *
     */
    public Coordinate getLatestPosition() { return this.position; }

    /** This is an inbuilt function that is inherieted from the InputProcessor. It is triggered
     * every time the screen is either pressed or clicked by a pointer device like a mouse. This
     * function is used to update the position field of this class
     *
     * @param x - This represent the x value of the position of the pointer device
     * @param y - This represent the y value of the position of the pointer device
     * @param pointer - This is an integer value that represents the pointer device that was used
     * @param button - This is an integer value that represents the pointer device that was used
     *
     * @return boolean - Always returns true
     *
     * @see https://libgdx.badlogicgames.com/ci/nightlies/docs/api/com/badlogic/gdx/InputProcessor.html
     */
    public boolean touchDown (int x, int y, int pointer, int button) {
        if (button == 1) {
            shotsFired = true;
        } else {
            moving = true;
        }
        position.x = x;
        position.y = y;
        return true;
    }

    /** This is an inbuilt function that is inherieted from the InputProcessor. It is triggered
     * everytime the pointer device is release from being pressed on the screen
     *
     * @param x - This represent the x value of the position of the pointer device
     * @param y - This represent the y value of the position of the pointer device
     * @param pointer - This is an integer value that represents the pointer device that was used
     * @param button - This is an integer value that represents the pointer device that was used
     *
     * @return boolean - Always returns true
     *
     * @see https://libgdx.badlogicgames.com/ci/nightlies/docs/api/com/badlogic/gdx/InputProcessor.html
     */
    public boolean touchUp (int x, int y, int pointer, int button) {
        if (!shotsFired) {
            position.x = -1;
            position.y = -1;
        }
        moving = false;
        return false;
    }

    /** This is an inbuilt function that is inherieted from the InputProcessor. It is triggered
     * when the user is dragging the pointer device across the screen.
     *
     * @param x - This represent the x value of the position of the pointer device
     * @param y - This represent the y value of the position of the pointer device
     * @param pointer - This is an integer value that represents the pointer device that was used
     *
     * @return boolean - Always returns true
     *
     * @see https://libgdx.badlogicgames.com/ci/nightlies/docs/api/com/badlogic/gdx/InputProcessor.html
     */
    public boolean touchDragged (int x, int y, int pointer) {
        if (moving) {
            position.x = x;
            position.y = y;
        }
        return true;
    }

    /** This function is used to return and manage the shotsFired property of the class. If
     * shotsFired is true then it sets it to false and returns true. Otherwise it just returns
     * false.
     *
     * @return boolean - The value of the shotsFired field of the class
     *
     */
    public boolean getShotsFired() {
        if (shotsFired) {
            shotsFired = false;
            return true;
        }
        return false;
    }

    /** This function is used to return and manage the escaped property of the class. If
     * escaped is true then it sets it to false and returns true. Otherwise it just returns
     * false.
     *
     * @return boolean - The value of the shotsFired field of the class
     *
     */
    public boolean isEscaped () {
        if (escaped) {
            escaped = false;
            return true;
        } else {
            return false;
        }
    }
}
