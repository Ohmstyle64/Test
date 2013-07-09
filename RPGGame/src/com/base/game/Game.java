package com.base.game;

import java.awt.Rectangle;
import java.util.ArrayList;

import org.lwjgl.opengl.Display;

import com.base.engine.GameObject;
import com.base.engine.Physics;
import com.base.game.gameobject.Player;
import com.base.game.gameobject.TheCookieMonster;
import com.base.game.gameobject.Wall;
import com.base.game.gameobject.item.Cube;

public class Game {
	
	public static Game game;
	
	private ArrayList<GameObject> objects;
	private ArrayList<GameObject> remove;
	private Player player;
	
	public void generateTestLevel() {
		
		//Generate room 1
		objects.add(new Wall(200,200,1,300));
		objects.add(new Wall(200,200,300,1));
		objects.add(new Wall(500,200,1,300));
		objects.add(new Wall(200,500,100,1));
		objects.add(new Wall(400,500,100,1));
		
		//Generate hallway 1
		objects.add(new Wall(300,500,1,100));
		
		
	}
	
	public Game() {
		
		objects = new ArrayList<GameObject>();
		remove = new ArrayList<GameObject>();
		
		player = new Player(Display.getWidth()/2 - Player.SIZE/2, Display.getHeight()/2 - Player.SIZE/2);
		Cube cube = new Cube(32, 32,"The Cube");
		TheCookieMonster cookieMonster = new TheCookieMonster(300, 500, 1,"The Cookie Monster");
		
		objects.add(player);
		objects.add(cube);
		objects.add(cookieMonster);
		
		generateTestLevel();
	}
	
	public void getInput() {
		player.getInput();
	}
	
	public void update() {
				
		for(GameObject go : objects) {
			if(!go.getRemove())
				go.update();
			else {
				remove.add(go);
			}
		}
		
		for(GameObject go : remove) {
			objects.remove(go);
		}

	}
	
	public void render() {
		
		for(GameObject go : objects)
			go.render();
	}
	
	public ArrayList<GameObject> getObjects() {
		return objects;
	}
	
	public static ArrayList<GameObject> sphereCollide(float x, float y, float radius) {
		
		ArrayList<GameObject> res = new ArrayList<GameObject>();
		
		for(GameObject go : game.getObjects()) {
			if(Util.dist(go.getX(), go.getY(), x, y) < radius)
				res.add(go);
		}
		return res;
	}

	public static ArrayList<GameObject> rectangleCollide(float x1, float y1, float x2, float y2) {
		
		ArrayList<GameObject> res = new ArrayList<GameObject>();
		
		float sx = x2 - x1;
		float sy = y2 - y1;
		
		Rectangle collider = new Rectangle((int)x1,(int)y1,(int)sx,(int)sy);
		
		for(GameObject go : game.getObjects()) {
			if(Physics.checkCollision(collider, go)!=null)
				res.add(go);
		}
		
		return res;
	}

}
