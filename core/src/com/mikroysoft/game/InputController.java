package com.mikroysoft.game;

import com.badlogic.gdx.InputProcessor;

public class InputController implements InputProcessor {
	Coordinate position;
	
	public InputController() {
		position = new Coordinate(-1,-1);
	}
	
	
	public boolean keyDown (int keycode) {
		return false;
	}

	public boolean keyUp (int keycode) {
		return false;
	}

	public boolean keyTyped (char character) {
		return false;
	}

	public boolean touchDown (int x, int y, int pointer, int button) {
		position.x = x;
		position.y = y;
		return true;
	}

	public boolean touchUp (int x, int y, int pointer, int button) {
		position.x = -1;
		position.y = -1;
		return false;
	}

	public boolean touchDragged (int x, int y, int pointer) {
		position.x = x;
		position.y = y;
		return true;
	}

	public boolean mouseMoved (int x, int y) {
		return false;
	}

	public boolean scrolled (int amount) {
		return false;
	}
	
	public Coordinate getLatestPosition() {
		return this.position;
	}
}
