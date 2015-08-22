package com.estrelsteel.game3.meter;

import java.awt.Color;

import com.estrelsteel.game3.location.Location;

public class HorizontalMeter extends Meter {
	boolean leftRight = true;
	
	public HorizontalMeter(boolean leftRight, double max, double min, double value, Color colour, int x, int y, double w, double h, boolean outBounds) {
		super(max, min, value, colour, x, y, w, h, outBounds);
		this.leftRight = leftRight;
	}
	
	public boolean getLeftRight() {
		return leftRight;
	}
	
	public Location getFillArea() {
		Location loc;
		if(leftRight) {
			int x = getX();
			int y = getY();
			double w = getW();
			double h = getH();
			double percentage = getPercentage();
			double newWidth = w * percentage;
			loc = new Location(null, x, y, newWidth, h);
		}
		else {
			double w = getW();
			double h = getH();
			int x = (int) (getX() + w);
			int y = getY();
			double percentage = getPercentage();
			double newWidth = w * percentage;
			loc = new Location(null, (int) (x - newWidth), y, newWidth, h);
		}
		return loc;
	}
	
	public void setLeftRight(boolean leftRight) {
		this.leftRight = leftRight;
		return;
	}
	
}
