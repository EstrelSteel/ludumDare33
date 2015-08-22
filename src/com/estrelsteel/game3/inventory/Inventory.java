package com.estrelsteel.game3.inventory;

import java.util.ArrayList;
import java.util.List;

import com.estrelsteel.game3.item.Item;

public class Inventory {
	List<Item> inventory = new ArrayList<Item>();
	String name = "NULL";
	
	public Inventory(String name) {
		this.name = name;
	}
	
	public List<Item> getInventoryItems() {
		return inventory;
	}
	
	public String getName() {
		return name;
	}
	
	public void addInventoryItem(Item item) {
		inventory.add(item);
		return;
	}
	
	public void addInventoryItem(int index, Item item) {
		inventory.add(index, item);
		return;
	}
	
	public boolean hasItem(Item item) {
		for(Item i : inventory) {
			if(i == item) {
				return true;
			}
		}
		return false;
	}
	
	public boolean hasItem(String name) {
		for(Item i : inventory) {
			if(i.getName() == name) {
				return true;
			}
		}
		return false;
	}
	
	public boolean hasItem(int id) {
		for(Item i : inventory) {
			if(i.getID() == id) {
				return true;
			}
		}
		return false;
	}
	
	public void setInventoryItems(List<Item> inventory) {
		this.inventory = inventory;
		return;
	}
	
	public void setName(String name) {
		this.name = name;
		return;
	}
}
