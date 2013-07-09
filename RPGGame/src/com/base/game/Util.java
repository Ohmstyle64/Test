package com.base.game;

import com.base.engine.GameObject;

public class Util {
	
	public static boolean lineOfSight(GameObject go1, GameObject go2) {
		return true;
	}
	
	public static float dist(float x1, float y1, float x2, float y2) {
		
		float x = x1 - x2;
		float y = y1 - y2;
		
		return (float)Math.sqrt(x*x + y*y);
		
	}
}
