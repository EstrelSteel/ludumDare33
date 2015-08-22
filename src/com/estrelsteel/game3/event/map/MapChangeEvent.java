package com.estrelsteel.game3.event.map;

import com.estrelsteel.game3.block.Map;
import com.estrelsteel.game3.event.Event;
import com.estrelsteel.game3.world.World;

public class MapChangeEvent extends Event {
	
	World world;
	Map oldMap;
	
	public MapChangeEvent(Map oldMap, World world) {
		super("MapChangeEvent", 3);
		this.oldMap = oldMap;
		this.world = world;
	}
	
	public World getWorld() {
		return world;
	}
	
	public Map getOldMap() {
		return oldMap;
	}
	
	public Map getMap() {
		return world.getSelectedMap();
	}
}
