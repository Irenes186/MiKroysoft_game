package com.mikroysoft.game;

import com.badlogic.gdx.graphics.Texture;
import java.lang.Math;

public class FireEngine {
	public Texture texture;
	public Coordinate position;
	private int waterVolume;
	private float speed;
	private float range;
	private float deliveryRate;
	private int health;
	public float direction;
	
	
	public FireEngine() {
		texture = new Texture("fireengine.png");
		position = new Coordinate(300,300);
		direction = 0;
		speed = 2;
	}
	
	
	public void repair() {
		health += 50;
	}
	
	public void refill() {
		waterVolume += 50;
	}
	
	public void move(Coordinate input) {
		//add some kind of moving rules.
                if (input.x == -1)
                    return;

                double xSign = Integer.signum(input.x - position.x - 40);
                double ySign = Integer.signum(input.y - position.y + 40);

                position.x += xSign * speed;
                position.y += ySign * speed;

                if ((input.x - position.x - 40) == 0) {
                    direction = 0;
                    return;
                }


                
                direction = (float) Math.toDegrees(Math.atan2((input.y - position.y + 40) * -1, input.x - position.x - 40)) - 90;


                System.out.println(direction );
	}

}
