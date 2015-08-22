package com.estrelsteel.game3.block;

import java.util.ArrayList;

import com.estrelsteel.game3.Game;
import com.estrelsteel.game3.event.map.MapAlterEvent;
import com.estrelsteel.game3.location.Location;
import com.estrelsteel.game3.world.World;

public class Map extends Location {
	
	ArrayList<Location> blocked = new ArrayList<Location>();
	ArrayList<Location> added = new ArrayList<Location>();
	ArrayList<Exit> exits = new ArrayList<Exit>();
	Image collide;
	Image out;
	Image ground;
	Image wall; 
	Image background;
	String name = "NULL";
	boolean scrollX = true;
	boolean scrollY = true;
	int xFormat = 0;
	int yFormat = 0;
	int x = 0;
	int y = 0;
	Game game;
	
	public Map(Game game, Image collide, Image out, String name, boolean scrollX, boolean scrollY, int playerX, int playerY, double width, double height, World world) {
		super(world, 0, 0, width, height);
		this.game = game;
		this.collide = collide;
		this.out = out;
		this.name = name;
		this.scrollX = scrollX;
		this.scrollY = scrollY;
		this.x = playerX;
		this.y = playerY;
	}
	
	public int getStartX() {
		return x;
	}
	
	public int getStartY() {
		return y;
	}
	
	public Image getCollideMap() {
		return collide;
	}
	
	public Image getPlayerMap() {
		return out;
	}
	
	public Image getGroundImage() {
		return ground;
	}
	
	public Image getWallImage() {
		return wall;
	}
	
	public Image getBackgroundImage() {
		return background;
	}
	
	public boolean getScrollX() {
		return scrollX;
	}
	
	public boolean getScrollY() {
		return scrollY;
	}
	
	public int getXFormat() {
		return xFormat;
	}
	
	public int getYFormat() {
		return yFormat;
	}
	
	public ArrayList<Location> getBlockedLoc() {
		return blocked;
	}
	
	public ArrayList<Location> getAddedLoc() {
		return added;
	}
	
	public ArrayList<Exit> getExits() {
		return exits;
	}
	
	public String getName() {
		return name;
	}
	
	public boolean checkBlockedCollide(int x, int y) {
		for(Location loc : blocked) {
			if((loc.getX() <= x && loc.getX() + loc.getW() >= x) && (loc.getY()  <= y && loc.getY() + loc.getH() >= y)) {
				return true;	
			}
		}
		return false;
	}
	
	public boolean checkAddedCollide(int x, int y) {
		for(Location loc : added) {
			if((loc.getX() <= x && loc.getX() + loc.getW() >= x) && (loc.getY()  <= y && loc.getY() + loc.getH() >= y)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean checkCollide(int x, int y) {
		if(game.player.getNoClip()) {
			return false;
		}
		if(!collide.isImageLoaded()) {
			collide.loadImage();
		}
		int width = collide.getImage().getWidth();
		int height = collide.getImage().getHeight();
		
		int trueX = (int) (width / (getW() / x));
		int trueY = (int) (height / (getH() / y));
		if((trueX < 1 || trueY < 1) || (trueX >= width || trueY >= height)) {
			System.out.println("WARNING >>> PLAYER OUT OF MAP!!!");
			return false;
		}
		int rgb = collide.getImage().getRGB(trueX, trueY);
		
		int red = (rgb & 0x00ff0000) >> 16;
		int green = (rgb & 0x0000ff00) >> 8;
		int blue = (rgb & 0x000000ff);
		if(checkAddedCollide(x, y)) {
			return true;
		}
		if(checkBlockedCollide(x, y)) {
			return false;
		}
		if(red == 255 && blue == 255 && green == 255) {
			return false;
		}
		else {
			return true;
		}
	}
	
	public boolean checkColour(int x, int y, int r, int g, int b) {
		if(!collide.isImageLoaded()) {
			collide.loadImage();
		}
		int width = collide.getImage().getWidth();
		int height = collide.getImage().getHeight();
		
		int trueX = (int) (width / (getW() / x));
		int trueY = (int) (height / (getH() / y));
		if((trueX < 1 || trueY < 1) || (trueX >= width || trueY >= height)) {
			System.out.println("WARNING >>> LOCATION OUT OF MAP!!!");
			return false;
		}
		int rgb = collide.getImage().getRGB(trueX, trueY);
		
		int red = (rgb & 0x00ff0000) >> 16;
		int green = (rgb & 0x0000ff00) >> 8;
		int blue = (rgb & 0x000000ff);
		//System.out.println("RED:" + red + " GREEN:" + green + " BLUE:" + blue);
		if(red == r && green == g && blue == b) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public void addBlockedLoc(Location loc) {
		blocked.add(loc);
		game.mapAlter.fireMapAlterEvent(new MapAlterEvent(this, loc, true));
		return;
	}
	
	public void addAddedLoc(Location loc) {
		added.add(loc);
		game.mapAlter.fireMapAlterEvent(new MapAlterEvent(this, loc, false));
		return;
	}
	
	public void addExits(Exit exit) {
		exits.add(exit);
		game.mapAlter.fireMapAlterEvent(new MapAlterEvent(this, exit.getLocation(), false));
	}
	
	public void setStartX(int x) {
		this.x = x;
		return;
	}
	
	public void setStartY(int y) {
		this.y = y;
		return;
	}
	
	public void setCollideMap(Image collide) {
		this.collide = collide;
		return;
	}
	
	public void setPlayerMap(Image out) {
		this.out = out;
		return;
	}
	
	public void setGroundImage(Image ground) {
		this.ground = ground;
		return;
	}
	
	public void setWallImage(Image wall) {
		this.wall = wall;
		return;
	}
	
	public void setBackgroundImage(Image background) {
		this.background = background;
		return;
	}
	
	public void setScrollX(boolean scrollX) {
		this.scrollX = scrollX;
		return;
	}
	
	public void setScrollY(boolean scrollY) {
		this.scrollY = scrollY;
		return;
	}
	
	public void setBlockedLoc(ArrayList<Location> blocked) {
		this.blocked = blocked;
		return;
	}
	
	public void setAddedLoc(ArrayList<Location> added) {
		this.added = added;
		return;
	}
	
	public void setExits(ArrayList<Exit> exits) {
		this.exits = exits;
		return;
	}
	
	public void setXFormat(int xFormat) {
		this.xFormat = xFormat;
		return;
	}
	
	public void setYFormat(int yFormat) {
		this.yFormat = yFormat;
		return;
	}
	
	public void setName(String name) {
		this.name = name;
		return;
	}
}
