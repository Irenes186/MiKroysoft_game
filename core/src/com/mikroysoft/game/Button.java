package com.mikroysoft.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class Button extends ImageButton {

	//var
	public Texture texture;
	public Texture hoverTexture;
	public Coordinate position;
	public int height;
	public int width;
	public boolean hover;
	public String text;

	//constructor - assign variables for button qualities
	public Button(int height, int width, Coordinate position, String text) {
		this.height = height;
		this.width = width;
		this.texture = new Texture("planet_button_0.png");
		this.hoverTexture = new Texture("planet_button_1.png");
		this.position = position;
		this.text = text;
		hover = false;
	}

	//checks mouse coordinates are on button
	public void hover(Coordinate input){
		hover = (input.x <= position.x + width) && (input.x >= position.x) 
				&& (input.y <= position.y + height) && (input.y >= position.y);
	}

}
