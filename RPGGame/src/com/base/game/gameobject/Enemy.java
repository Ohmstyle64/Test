package com.base.game.gameobject;

import java.util.ArrayList;

import com.base.engine.GameObject;
import com.base.engine.Sprite;
import com.base.game.Delay;
import com.base.game.Game;
import com.base.game.Util;

public class Enemy extends StatObject {

	public static final float DAMPENING = 0.5f;
	
	private float sightRange;
	private StatObject target;
	private float attackRange;
	private float beyondRange;
	private Delay attackDelay;
	private int attackDamage;
	protected String name;
	
	public Enemy(int level) {
		
		stats = new Stats(level, false);
		target = null;
		attackDelay = new Delay(500);
		attackDelay.terminate();
		setAttackRange(48f);
		beyondRange = 250f;
		sightRange = 128f;

	}
	
	public void update() {
		
		if(target == null)
			look();
		else
		{
			if(Util.lineOfSight(this, target) &&
					Util.dist(x, y, getTarget().getX(), getTarget().getY()) 
					<= getAttackRange()) {
				if(attackDelay.isOver())
					attack();
			}

			else {
				if(Util.dist(x, y, getTarget().getX(), getTarget().getY()) 
					<= beyondRange)
				chase();
			}
			
				
		}
		if(stats.getCurrentHealth() <= 0)
			death();
	}
	
	protected void chase() {
		
		float speedX = getTarget().getX() - x;
		float speedY = getTarget().getY() - y;
		
		float maxSpeed = getStats().getSpeed()*DAMPENING;
		
		if(speedX> maxSpeed)
			speedX = maxSpeed;
		if(speedX< -maxSpeed)
			speedX = -maxSpeed;
		
		if(speedY> maxSpeed)
			speedY = maxSpeed;
		if(speedY< -maxSpeed)
			speedY = -maxSpeed;
		
		x+= speedX;
		y+= speedY;

	}
	
	protected void attack() {
		
		getTarget().damage(getAttackDamage());
		System.out.println("Player: "+ getTarget().getCurrentHealth()+" / "+ getTarget().getMaxHealth());
		attackDelay.restart();
	}
	
	protected void look() {
		
		ArrayList<GameObject> objects = Game.sphereCollide(x, y, sightRange);
		
		for(GameObject go : objects) {
			if(go.getType() == PLAYER_ID) {
				setTarget((StatObject)go);
			}
		}
	}
	
	protected void death() {
		remove();
	}
	
	protected void setTarget(StatObject go) {
		target = go;
	}
	
	public StatObject getTarget() {
		return target;
	}
	
	public Stats getStats() {
		return stats;
	}
	
	public void setAttackDelay(int time) {
		attackDelay = new Delay(time);
		attackDelay.terminate();
	}

	public float getAttackRange() {
		return attackRange;
	}

	public void setAttackRange(float attackRange) {
		this.attackRange = attackRange;
	}
	
	public int getAttackDamage() {
		return attackDamage;
	}

	public void setAttackDamage(int attackDamage) {
		this.attackDamage = attackDamage;
	}

	public float getSightRange() {
		return sightRange;
	}

	public void setSightRange(float sightRange) {
		this.sightRange = sightRange;
	}

	public String getName() {
		return name;
	}
	
	protected void init(float x, float y, float r, float g, float b, float sx, float sy,String name) {
		
		this.x = x;
		this.y = y;
		this.name = name;
		this.spr = new Sprite(r,g,b,sx,sy);
		this.type = ENEMY_ID;
	}
	
	
}
