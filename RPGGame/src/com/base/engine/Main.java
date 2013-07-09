package com.base.engine;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glOrtho;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import com.base.game.Game;
import com.base.game.Time;

public class Main {
	
	public static void main(String[] args) {

		initDisplay();
		initGL();
		initGame();
		
		gameLoop();
		cleanUp();
	}
	
	private static void initGame() {
		Game.game = new Game();
		
	}


	private static void cleanUp() {
		
		Display.destroy();
		Keyboard.destroy();
		Mouse.destroy();
	}


	private static void render() {
		
		glClear(GL_COLOR_BUFFER_BIT);
		glLoadIdentity();
		
		Game.game.render();
		
		Display.update();
		Display.sync(60);
	}

	private static void update() {
		Game.game.update();		
	}

	private static void getInput() {
		Game.game.getInput();
	}

	private static void initGL() {
		
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, Display.getWidth(), 0, Display.getHeight(), -1, 1);
		glMatrixMode(GL_MODELVIEW);
		glDisable(GL_DEPTH_TEST);
		glClearColor(0, 0, 0, 0);
		
	}

	private static void gameLoop() {
		
		Time.init();
		
		while(!Display.isCloseRequested()) {
			
			Time.update();
			getInput();
			update();
			render();
		}
		
	}

	private static void initDisplay() {

		try {
			Display.setDisplayMode(new DisplayMode(800, 600));
			Display.create();
			Display.setVSyncEnabled(true);
			Keyboard.create();
			Mouse.create();
		} catch (LWJGLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
