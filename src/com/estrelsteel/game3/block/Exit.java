package com.estrelsteel.game3.block;

import com.estrelsteel.game3.location.Location;

public class Exit {
	Map exitMap;
	Location loc;
	int enterX;
	int enterY;
	
	public Exit(Map exitMap, Location loc, int enterX, int enterY) {
		this.exitMap = exitMap;
		this.loc = loc;
		this.enterX = enterX;
		this.enterY = enterY;
	}
	
	public Exit(Map exitMap, Location loc) {
		this.exitMap = exitMap;
		this.loc = loc;
		this.enterX = exitMap.getStartX();
		this.enterY = exitMap.getStartY();
	}
	
	public Map getExitMap() {
		return exitMap;
	}
	
	public Location getLocation() {
		return loc;
	}
	
	public int getEnterX() {
		return enterX;
	}
	
	public int getEnterY() {
		return enterY;
	}
	
	public void setExitMap(Map exitMap) {
		this.exitMap = exitMap;
		return;
	}
	
	public void setLocation(Location loc) {
		this.loc = loc;
		return;
	}
	
	public void setEnterX(int enterX) {
		this.enterX = enterX;
		return;
	}
	
	public void setEnterY(int enterY) {
		this.enterY = enterY;
		return;
	}
}
