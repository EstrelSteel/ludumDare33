package com.estrelsteel.game3.meter;

import java.awt.Color;

import com.estrelsteel.game3.location.Location;

public class VerticleMeter extends Meter {
	boolean downUp = true;
	
	public VerticleMeter(boolean downUp, double max, double min, double value, Color colour, int x, int y, double w, double h, boolean outBounds) {
		super(max, min, value, colour, x, y, w, h, outBounds);
		this.downUp = downUp;
	}
	
	public boolean getDownUp() {
		return downUp;
	}
	
	public Location getFillArea() {
		Location loc;
		if(downUp) {
			double w = getW();
			double h = getH();
			int x = getX();
			int y = (int) (getY() + h);
			double percentage = getPercentage();
			double newHeight = h * percentage;
			loc = new Location(null, x, (int) (y - newHeight), w, newHeight);
		}
		else {
			int x = getX();
			int y = getY();
			double w = getW();
			double h = getH();
			double percentage = getPercentage();
			double newHeight = h * percentage;
			loc = new Location(null, x, y, w, newHeight);
		}
		return loc;
	}
	
	public void setDownUp(boolean downUp) {
		this.downUp = downUp;
		return;
	}
	
}
