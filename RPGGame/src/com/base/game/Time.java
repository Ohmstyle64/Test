package com.base.game;

public class Time {
	
	private static long curTime;
	private static long lastTime;
	
	public static long getTime() {
		return System.nanoTime();
	}
	
	public static double getDelta() {
		return (curTime - lastTime);
	}
	
	public static void update() {
		
		lastTime = curTime;
		curTime = getTime();
	}
	
	public static void init() {
		
		lastTime = getTime();
		curTime = getTime();
	}

}
