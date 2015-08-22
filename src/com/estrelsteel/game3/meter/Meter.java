package com.estrelsteel.game3.meter;

import java.awt.Color;

import com.estrelsteel.game3.location.Location;

public abstract class Meter extends Location {
	double max = 1;
	double min = 0;
	double value = 0;
	Color colour = Color.RED;
	boolean outBounds = true;
	
	public Meter(double max, double min, double value, Color colour, int x, int y, double w, double h, boolean outBounds) {
		super(null, x, y, w, h);
		if(min > max) {
			System.out.println("ERROR >>> >>> >>> >>> >>>");
			System.out.println("MINIMUM VALUE IS GREATER THAN MAXIMUM");
			System.out.println("SWITCHING MINIMUM AND MAXIMUM");
			double oldMin = min;
			min = max;
			max = oldMin;
		}
		this.max = max;
		this.min = min;
		this.value = value;
		this.colour = colour;
		this.outBounds = outBounds;
	}
	
	public double getMax() {
		return max;
	}
	
	public double getMin() {
		return min;
	}
	
	public double getValue() {
		return value;
	}
	
	public Color getColour() {
		return colour;
	}
	
	public boolean getOutBounds() {
		return outBounds;
	}
	
	public double getPercentage() {
		if((value > max || value < min) && outBounds) {
			System.out.println("ERROR >>> >>> >>> >>> >>>");
			System.out.println("VALUE OUT OF BOUNDS");
			System.out.println("CONTINUING TO CALCULATE");
		}
		else if((value > max || value < min) && !outBounds) {
			System.out.println("ERROR >>> >>> >>> >>> >>>");
			System.out.println("VALUE OUT OF BOUNDS");
			System.out.println("STOPPING CALCULATION");
			return -999.999;
		}
		double calVal = value - min;
		double calMax = max - min;
		double percentage = calVal / calMax;
		return percentage;
	}
	
	public abstract Location getFillArea();
	
	public void setMax(double max) {
		this.max = max;
		return;
	}
	
	public void setMin(double min) {
		this.min = min;
		return;
	}
	
	public void setValue(double value) {
		this.value = value;
		return;
	}
	
	public void setColour(Color colour) {
		this.colour = colour;
		return;
	}
	
	public void setOutBounds(boolean outBounds) {
		this.outBounds = outBounds;
		return;
	}
}
