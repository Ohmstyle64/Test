package com.base.game.gameobject.item;

public class SwordOfDebuggery extends EquipableItem {
	
	public static final int SIZE = 32;
	@SuppressWarnings("unused")
	private int damage;
	
	public SwordOfDebuggery(float x, float y,String name, int slot) {
		init(x,y,1.0f,0.5f,0.0f,SIZE,SIZE,name,slot);
		damage  = 3;
	}
}
