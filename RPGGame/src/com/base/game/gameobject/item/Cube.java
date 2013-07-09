package com.base.game.gameobject.item;



public class Cube extends Item {
	
	public static final int SIZE = 32;
	
	public Cube(float x, float y,String name) {
		init(x,y,1.0f,0.5f,0.0f,SIZE,SIZE,name);
	}

}
