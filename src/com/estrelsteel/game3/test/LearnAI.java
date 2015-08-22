package com.estrelsteel.game3.test;

public class LearnAI {
	int x;
	int y;
	double fitness = 0.0;
	double maxFitness = 0.1;
	Gene gene = new Gene(0, "AI_1_GENE");
	
	public LearnAI(int x, int y, double maxFitness) {
		this.x = x;
		this.y = y;
		this.maxFitness = maxFitness;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public double getFitness() {
		return fitness;
	}
	
	public double getMaxFitness() {
		return maxFitness;
	}
}
