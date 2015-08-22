package com.estrelsteel.game3.item;

public enum Item {
	
	NULL(-1, "NULL", false),
	UNKNOWN(0, "???", false),
	EARTH_REMOVE(1, "EARTH_REMOVE", false),
	EARTH_PLACE(2, "EARTH_PLACE", false),
	EARTH_RESTORE(3 , "EARTH_RESTORE", false),
	EARTH_BURROW(4, "EARTH_BURROW", false);
	
	private int id;
	private String name;
	private String ctrType;
	private boolean removable;
	
	Item(int id, String name, String ctrType, boolean removable) {
		this.id = id;
		this.name = name;
		this.ctrType = ctrType;
		this.removable = removable;
	}
	
	Item(int id, String name, String ctrType) {
		this.id = id;
		this.name = name;
		this.ctrType = ctrType;
		this.removable = true;
	}
	
	Item(int id, String name) {
		this.id = id;
		this.name = name;
		this.ctrType = "NULL";
		this.removable = true;
	}
	
	Item(int id, String name, boolean removable) {
		this.id = id;
		this.name = name;
		this.ctrType = "NULL";
		this.removable = removable;
	}
	
	public int getID() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public String getControlType() {
		return ctrType;
	}
	
	public boolean getRemovable() {
		return removable;
	}
}
