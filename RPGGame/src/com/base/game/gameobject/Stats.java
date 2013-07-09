package com.base.game.gameobject;

public class Stats {
	
	//public static final double LEVEL_CONST = 25.0 * Math.pow(3.0, 3.0/2.0);
	public static final float MAX_XP = 1000000;
	public static final int MAX_LEVEL = 100;
	public static final double LEVEL_CONST = MAX_XP / (MAX_LEVEL * MAX_LEVEL);

	private StatScale scale;
	private float xp;
	private int health;
	private boolean levelable;
	
	public Stats(float xp, boolean levelable) {
		
		scale = new StatScale();
		scale.generateScale();
		
		this.xp = xp;
		this.levelable = levelable;
		this.health = getMaxHealth();
		

		
	}
		public int getLevel() {
		if(!levelable)
			return (int)xp;

		//Calculate xp from level = ax^2
		//Calculate level form xp = sqrt(xp/a)
		return (int)Math.sqrt((double)xp/LEVEL_CONST)+1;
	}
		
	public int getCurrentHealth() {
		
		int max = getMaxHealth();		
		if(health > max) {
			health = max;
		}
		
		return health;
	}
	
	public int getMaxHealth() {
		return (int)Math.ceil((getLevel() * scale.getScale(StatScale.VITALITY) * 10));
	}
	
	public float getSpeed() {
		return 4f;//(float) (getLevel() * scale.getScale(StatScale.SPEED) * 10);
	}
	
	public float getStrength() {
		return (float) (getLevel() * scale.getScale(StatScale.STRENGTH) * 10);
	}
	
	public float getMagic() {
		return (float) (getLevel() * scale.getScale(StatScale.MAGIC) * 10);
	}
	
	public float getPhysicalDefense() {
		return (float) (getLevel() * scale.getScale(StatScale.PHYSICALDEFENSE) * 10);
	}
	
	public float getMagicalDefense() {
		return (float) (getLevel() * scale.getScale(StatScale.MAGICDEFENSE) * 10);
	}
	
	public void addXp(float amt) {
		
		xp += amt;
		if(xp > MAX_XP)
			xp = MAX_XP;

	}
	
	public void damage(int amt) {
		health -= amt;
	}
	
	public StatScale getStatScale() {
		return scale;
	}
}
