package com.base.game.gameobject;

import com.base.engine.RPGRandom;

public class StatScale {
	
	public static final int NUM_STATS = 8;
	public static final double MIN_STAT = 0.125;
	private static final double MAX_STAT_BOOST = 0.25;
	
	public static final int VITALITY = 0;
	public static final int SPEED = 1;
	//public static final int UNKOWN1 = 2;
	//public static final int UNKNOWN2 = 3;
	public static final int STRENGTH = 4;
	public static final int PHYSICALDEFENSE = 5;
	public static final int MAGIC = 6;
	public static final int MAGICDEFENSE = 7;
	
	
	private double[] scales;
	private double[] scaleBonus;
	private RPGRandom rand;
	
	public StatScale() {
		
		scales = new double[NUM_STATS];
		scaleBonus = new double[NUM_STATS];
		rand = new RPGRandom();
	}
	
	public void generateScale() {
		
		double sum = 0;
		
		for(int i = 0; i < NUM_STATS; i++) {
			
			double val = Math.abs(rand.nextDouble());
			
			scales[i] = val;
			
			sum += val;
		}
		
		for(int i = 0; i < NUM_STATS; i++) {
			
			scales[i] /= sum;
			scales[i] *= (1.0 - MIN_STAT * MIN_STAT * NUM_STATS);
			scales[i] += MIN_STAT * MIN_STAT;
			scales[i] = Math.sqrt(scales[i]);
		}
	}
	
	public double getScale(int stat) {
		return scales[stat] + scaleBonus[stat] * MAX_STAT_BOOST;
	}
	
	public void addScaleBonus(int stat, double bonus) {
		
		if(bonus > 1) {
			bonus = 1;
		}
		
		if(bonus < 0) {
			bonus = 0;
		}
		
		scaleBonus[stat] = bonus;
		
	}
	public String toString() {
		
		String str = "";
		
		for(int i = 0; i < NUM_STATS; i++) {
			str += "STAT "+i+": "+getScale(i)+"\n";
		}
		return str;
	}

}
