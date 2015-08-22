package com.estrelsteel.game3.location;

import com.estrelsteel.game3.world.World;

public class Location {
	int x;
	int y;
	double w;
	double h;
	World world;
	
	public Location(World worldN, int xN, int yN, double wN, double hN) {
		x = xN;
		y = yN;
		w = wN;
		h = hN;
		world = worldN;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public double getW() {
		return w;
	}
	
	public double getH() {
		return h;
	}
	
	public World getWorld() {
		return world;
	}
	
	public void setX(int num) {
		x = num;
		return;
	}
	
	public void setY(int num) {
		y = num;
		return;
	}
	
	public void setW(double num) {
		w = num;
		return;
	}
	
	public void setH(double num) {
		h = num;
		return;
	}
	
	public void setWorld(World newWorld) {
		world = newWorld;
		return;
	}
}
