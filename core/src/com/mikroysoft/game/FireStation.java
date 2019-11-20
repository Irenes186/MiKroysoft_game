package com.mikroysoft.game;

public class FireStation {
	public boolean destroyed;
	private int fillSpeed;
	
	public FireStation(int fillSpeed){
		destroyed = false;
		this.fillSpeed = fillSpeed;
	}
	
	public void destroy() {
		destroyed = true;
	}
	
	
}
