package com.estrelsteel.game3.event.map;

import com.estrelsteel.game3.block.Map;
import com.estrelsteel.game3.event.Event;
import com.estrelsteel.game3.location.Location;

public class MapAlterEvent extends Event {
	
	Map map;
	Location loc;
	boolean removed;
	
	public MapAlterEvent(Map map, Location loc, boolean removed) {
		super("MapAlterEvent", 4);
		this.map = map;
		this.loc = loc;
		this.removed = removed;
	}
	
	public Map getMap() {
		return map;
	}
	
	public Location getLocation() {
		return loc;
	}
	
	public boolean getRemoved() {
		return removed;
	}
}
