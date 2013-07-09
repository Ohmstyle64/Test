package com.base.game.gameobject;



public class TheCookieMonster extends Enemy {

	public static final int SIZE = 32;

	public TheCookieMonster(float x, float y, int level,String name) {
		super(level);
		init(x,y,0.2f,0.2f,1.0f,SIZE,SIZE,name);
		setAttackDelay(500);
		setAttackDamage(1);
		setSightRange(150f);
	}
}
