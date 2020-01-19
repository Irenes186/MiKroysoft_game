package com.mikroysoft.game;
import com.badlogic.gdx.InputProcessor;

public class InputController implements InputProcessor {
    public Coordinate position;
    public boolean moving;
    private boolean shotsFired;
    private boolean escaped;

    public InputController() {
        position = new Coordinate(-1,-1);
        shotsFired = false;
        escaped = false;
    }

    public void setMoveFalse() {
        this.moving = false;
    }
    public boolean keyDown (int keycode) {
        switch (keycode) {
            case 131:
                escaped = true;
                break;
        }
        return false;
    }
    public boolean keyUp (int keycode) {
        return false;
    }
    public boolean keyTyped (char character) { return false; }
    public boolean mouseMoved (int x, int y) { return false; }
    public boolean scrolled (int amount) { return false; }
    public Coordinate getLatestPosition() { return this.position; }

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

    public boolean touchUp (int x, int y, int pointer, int button) {
        if (!shotsFired) {
            position.x = -1;
            position.y = -1;
        }
        moving = false;
        return false;
    }

    public boolean touchDragged (int x, int y, int pointer) {
        if (moving) {
            position.x = x;
            position.y = y;
        }
        return true;
    }

    public boolean getShotsFired() {
        if (shotsFired) {
            shotsFired = false;
            return true;
        }
        return false;
    }

    public boolean isEscaped () {
        if (escaped) {
            escaped = false;
            return true;
        } else {
            return false;
        }
    }
}
