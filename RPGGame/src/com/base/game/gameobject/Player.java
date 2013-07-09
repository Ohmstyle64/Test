package com.base.game.gameobject;

import static org.lwjgl.opengl.GL11.glTranslatef;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

import com.base.engine.GameObject;
import com.base.game.Delay;
import com.base.game.Game;
import com.base.game.Util;
import com.base.game.gameobject.item.Item;

public class Player extends StatObject {
	
	public static final int SIZE = 32;
	
	public static final int FORWARD = 0;
	public static final int RIGHT = 1;
	public static final int BACKWARD = 2;
	public static final int LEFT = 3;
	
	private Inventory inventory;
	private Delay attackDelay;
	@SuppressWarnings("unused")
	private Equipment equipment;
	
	private int attackRange;
	private int facingDirection;
	private int attackDamage;
	
	private int moveAmountX;
	private int moveAmountY;
	
	public Player(float x, float y) {
		
		init(x,y,0.1f, 1.0f, 0.25f, SIZE, SIZE,PLAYER_ID);
		this.stats = new Stats(0,true);
		inventory = new Inventory(20);
		equipment = new Equipment(inventory);
		attackDelay = new Delay(500);
		attackDelay.terminate();
		attackRange = 49;
		facingDirection = FORWARD;
		attackDamage = 1;
		
		moveAmountX = 0;
		moveAmountY = 0;
	}
	
	public void update() {

		float newX = x + moveAmountX;
		float newY = y + moveAmountY;
		
		moveAmountX = 0;
		moveAmountY = 0;
		
		ArrayList<GameObject> objects = Game.rectangleCollide(newX, newY, newX + SIZE, newY + SIZE);
		ArrayList<GameObject> items = new ArrayList<GameObject>();
		
		boolean move = true;
		
		for(GameObject go : objects) {
			if(go.getType() == ITEM_ID)
				items.add(go);
			if(go.getSolid())
				move = false;
		}
			
		if(!move)
			return;
		
		x = newX;
		y = newY;
		
		for(GameObject go : items) {
			
			System.out.println("You just picked up "+((Item)go).getName()+"!");
			go.remove();
			addItem((Item)go);
		}
				
	}
	
	public void getInput() {
		
		if(Keyboard.isKeyDown(Keyboard.KEY_W))
			move(0,1);
		if(Keyboard.isKeyDown(Keyboard.KEY_S))
			move(0,-1);
		if(Keyboard.isKeyDown(Keyboard.KEY_A))
			move(-1,0);
		if(Keyboard.isKeyDown(Keyboard.KEY_D))
			move(1,0);
		if(Keyboard.isKeyDown(Keyboard.KEY_SPACE) && attackDelay.isOver())
			attack();
		if(Keyboard.isKeyDown(Keyboard.KEY_H))
			getData();
	}

	private void getData() {
		System.out.println("Player: X: "+x+" Y: "+y);
		//System.out.println("SPEED: "+getSpeed()+" LEVEL: "+getLevel()+" MAXHP: "+getMaxHealth() +" HP: "+getCurrentHealth()+
		//		" STRENGTH: "+getStrength()+" MAGIC: "+getMagic());
		//System.out.println(stats.getStatScale().toString());
		
	}

	public void attack() {
		
		System.out.print("We are attacking ! ");
		
		ArrayList<GameObject> objects = new ArrayList<GameObject>();
		
		if(facingDirection == FORWARD)
			objects = Game.rectangleCollide(x, y, x + SIZE, y + attackRange);
		else if(facingDirection == BACKWARD)
			objects = Game.rectangleCollide(x, y - attackRange + SIZE, x + SIZE, y);
		else if(facingDirection == LEFT)
			objects = Game.rectangleCollide(x - attackRange + SIZE, y, x, y + SIZE);
		else if(facingDirection == RIGHT)
			objects = Game.rectangleCollide(x, y, x + attackRange, y + SIZE);
		
		ArrayList<Enemy> enemies = new ArrayList<Enemy>();
		
		for(GameObject go : objects) {
			if(go.getType() == ENEMY_ID) {
				enemies.add((Enemy)go);
			}
		}
		
		if(enemies.size() > 0) {
			
			Enemy target = enemies.get(0);
			
			if(enemies.size() > 1) {
				for(Enemy e : enemies) {
					if(Util.dist(x, y, e.getX(), e.getY()) < Util.dist(x, y, target.getX(), target.getY()))
						target = e;
				}
				
			}			
			
			//Attack enemy
			target.damage(attackDamage);
			System.out.println(target.getName()+": "+ target.getCurrentHealth() + " / "+ target.getMaxHealth());
			
		}
		else
			System.out.println("No Target");
		attackDelay.restart();
		
	}
	
	public void render() {
		
		glTranslatef(Display.getWidth()/2 - Player.SIZE/2, Display.getHeight()/2 - Player.SIZE/2, 0);
		spr.render();
		glTranslatef(-x,-y, 0);
	}
	
	private void move(int i, int j) {
		
		if(i == 0 && j == 1) facingDirection = FORWARD;
		if(i == 1 && j == 0) facingDirection = RIGHT;
		if(i == 0 && j == -1) facingDirection = BACKWARD;
		if(i == -1 && j == 0) facingDirection = LEFT;
		
		moveAmountX += getSpeed() * i;
		moveAmountY += getSpeed() * j;
		
	}
	

	public void addXp(float amt) {
		stats.addXp(amt);
	}
	
	public void addItem(Item item) {
		inventory.add(item);
	}
}
