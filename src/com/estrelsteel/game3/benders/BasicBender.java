package com.estrelsteel.game3.benders;

import com.estrelsteel.game3.character.Player;
import com.estrelsteel.game3.inventory.Inventory;

public abstract class BasicBender {
	int maxMaterial = 1;
	int materialValue = 0;
	int minMaterial = 0;
	String type = "NULL";
	
	public BasicBender(int maxMaterial, int minMaterial, int materialValue, String type) {
		this.maxMaterial = maxMaterial;
		this.minMaterial = minMaterial;
		this.materialValue = materialValue;
		this.type = type;
	}
	
	public abstract Inventory configureInventory(Player player);
	
	public int getMaxMaterial() {
		return maxMaterial;
	}
	
	public int getMinMaterial() {
		return minMaterial;
	}
	
	public int getMaterialValue() {
		return materialValue;
	}
	
	public void setMaxMaterial(int maxMaterial) {
		this.maxMaterial = maxMaterial;
		return;
	}
	
	public void setMinMaterial(int minMaterial) {
		this.minMaterial = minMaterial;
		return;
	}
	
	public void setMaterialValue(int materialValue) {
		this.materialValue = materialValue;
		return;
	}
}
