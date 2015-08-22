package com.estrelsteel.game3.character;

import com.estrelsteel.game3.Game;
import com.estrelsteel.game3.benders.Earth;
import com.estrelsteel.game3.event.player.health.PlayerDamageEvent;
import com.estrelsteel.game3.event.player.health.PlayerHealEvent;
import com.estrelsteel.game3.event.player.move.PlayerMoveEvent;
import com.estrelsteel.game3.world.World;

public class Player extends Mob {
	
	Game game;
	
	int UP = 0;
	int DOWN = 0;
	int RIGHT = 0;
	int LEFT = 0;
	
	public int playerXFormat;
	public int playerYFormat;
	
	Earth earthBender = new Earth(16, 0, 0);
	
	boolean noClip = false;
	
	boolean goUP = false;
	boolean goDOWN = false;
	boolean goRIGHT = false;
	boolean goLEFT = false;
	
	String username = "Player";
	
	public Player(Game game, World world, int x, int y, double w, double h, int walkSpeed) {
		super(world, x, y, w, h, walkSpeed, "PLAYER");
		this.game = game;
		earthBender.configureInventory(this);
		inventory.setName("PLAYER_INVENTORY");
	}

	public int getUP() {
		return this.UP;
	}
	
	public int getDOWN() {
		return this.DOWN;
	}
	
	public int getRIGHT() {
		return this.RIGHT;
	}
	
	public int getLEFT() {
		return this.LEFT;
	}
	
	public String getUsername() {
		return username;
	}
	
	public boolean getNoClip() {
		return this.noClip;
	}
	
	public Earth getEarth() {
		return earthBender;
	}
	
	public int getXFormat() {
		return playerXFormat;
	}
	
	public int getYFormat() {
		return playerYFormat;
	}
	
	public boolean canMoveUP() {
		if(UP <= 0) {
			return true;
		}
		return false;
	}
	
	public boolean canMoveDOWN() {
		if(DOWN <= 0) {
			return true;
		}
		return false;
	}
	
	public boolean canMoveRIGHT() {
		if(RIGHT <= 0) {
			return true;
		}
		return false;
	}
	
	public boolean canMoveLEFT() {
		if(LEFT <= 0) {
			return true;
		}
		return false;
	}
	
	public boolean canGoUP() {
		return goUP;
	}
	
	public boolean canGoDOWN() {
		return goDOWN;
	}
	
	public boolean canGoRIGHT() {
		return goRIGHT;
	}
	
	public boolean canGoLEFT() {
		return goLEFT;
	}
	
	public void switchGoUP() {
		goUP = !goUP;
		return;
	}
	
	public void switchGoDOWN() {
		goDOWN = !goDOWN;
		return;
	}
	
	public void switchGoRIGHT() {
		goRIGHT = !goRIGHT;
		return;
	}
	
	public void switchGoLEFT() {
		goLEFT = !goLEFT;
		return;
	}
	
	public void moveUP() {
		if(canMoveUP()) {
			setY(getY() - walkSpeed);
			game.playerMove.firePlayerMove(new PlayerMoveEvent(this, getX(), getY() + walkSpeed));
		}
		return;
	}
	
	public void moveDOWN() {
		if(canMoveDOWN()) {
			setY(getY() + walkSpeed);
			game.playerMove.firePlayerMove(new PlayerMoveEvent(this, getX(), getY() - walkSpeed));
		}
		return;
	}
	
	public void moveRIGHT() {
		if(canMoveRIGHT()) {
			setX(getX() + walkSpeed);
			game.playerMove.firePlayerMove(new PlayerMoveEvent(this, getX() - walkSpeed, getY()));
		}
		return;
	}
	
	public void moveLEFT() {
		if(canMoveLEFT()) {
			setX(getX() - walkSpeed);
			game.playerMove.firePlayerMove(new PlayerMoveEvent(this, getX() + walkSpeed, getY()));
		}
		return;
	}
	
	public void setUP(int UP) {
		this.UP = UP;
		return;
	}
	
	public void setDOWN(int DOWN) {
		this.DOWN = DOWN;
		return;
	}
	
	public void setRIGHT(int RIGHT) {
		this.RIGHT = RIGHT;
		return;
	}
	
	public void setLEFT(int LEFT) {
		this.LEFT = LEFT;
		return;
	}
	
	public void setGoUP(boolean value) {
		goUP = value;
		return;
	}
	
	public void setGoDOWN(boolean value) {
		goDOWN = value;
		return;
	}
	
	public void setGoRIGHT(boolean value) {
		goRIGHT = value;
		return;
	}
	
	public void setGoLEFT(boolean value) {
		goLEFT = value;
		return;
	}
	
	public void setUsername(String username) {
		this.username = username;
		return;
	}
	
	public void setHealth(double health) {
		double oldHealth = this.getHealth();
		this.health = health;
		if(oldHealth < health) {
			game.playerHeal.firePlayerHeal(new PlayerHealEvent(this, oldHealth, health - oldHealth));
		}
		else {
			game.playerDamage.firePlayerDamage(new PlayerDamageEvent(this, oldHealth, oldHealth - health));
		}
		return;
	}
	
	public void setNoClip(boolean noClip) {
		this.noClip = noClip;
		return;
	}
	
	public void setEarth(Earth earthBender) {
		this.earthBender = earthBender;
		return;
	}
	
	public void setXFormat(int playerXFormat) {
		this.playerXFormat = playerXFormat;
		return;
	}
	
	public void setYFormat(int playerYFormat) {
		this.playerYFormat = playerYFormat;
		return;
	}
}
