package com.estrelsteel.game3.character;

import com.estrelsteel.game3.inventory.Inventory;
import com.estrelsteel.game3.location.Location;
import com.estrelsteel.game3.world.World;

public abstract class Mob extends Location  {

	String name;
	int walkSpeed;

	Inventory inventory = new Inventory("MOB_INVENTORY");
	
	double maxHealth = 100.0;
	double minHealth = 0.0;
	double health = 100.0;
	
	public Mob(World worldN, int xN, int yN, double wN, double hN, int walkSpeed, String name) {
		super(worldN, xN, yN, wN, hN);
		this.name = name;
		this.walkSpeed = walkSpeed;
	}

	public int getWalkSpeed() {
		return this.walkSpeed;
	}
	
	public String getName() {
		return name;
	}
	
	public double getHealth() {
		return health;
	}
	
	public double getMinHealth() {
		return minHealth;
	}
	
	public double getMaxHealth() {
		return maxHealth;
	}
	
	public Inventory getInventory() {
		return inventory;
	}
	
	public void setWalkSpeed(int walkSpeed) {
		this.walkSpeed = walkSpeed;
		return;
	}
	
	public void setName(String name) {
		this.name = name;
		return;
	}
	
	public abstract void setHealth(double health);
	
	public void setMinHealth(double minHealth) {
		this.minHealth = minHealth;
		return;
	}
	
	public void setMaxHealth(double maxHealth) {
		this.maxHealth = maxHealth;
		return;
	}

	public void setInventory(Inventory inventory) {
		this.inventory = inventory;
		return;
	}
	
}
