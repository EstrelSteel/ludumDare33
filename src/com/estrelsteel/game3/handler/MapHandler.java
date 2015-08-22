package com.estrelsteel.game3.handler;

import java.util.ArrayList;

import com.estrelsteel.game3.event.map.MapAlterEvent;
import com.estrelsteel.game3.event.map.MapAlterListener;
import com.estrelsteel.game3.event.map.MapChangeEvent;
import com.estrelsteel.game3.event.map.MapChangeListener;
import com.estrelsteel.game3.location.Location;

public class MapHandler implements MapChangeListener, MapAlterListener {

	@Override
	public void MapAlterEvent(MapAlterEvent e) {
		
	}

	@Override
	public void mapChangeEvent(MapChangeEvent e) {
		if(e.getOldMap().getPlayerMap() != null) {
			e.getOldMap().getPlayerMap().unloadImage();
		}
		if(e.getOldMap().getGroundImage() != null) {
			e.getOldMap().getGroundImage().unloadImage();
		}
		if(e.getOldMap().getWallImage() != null) {
			e.getOldMap().getWallImage().unloadImage();
		}
		if(e.getOldMap().getCollideMap() != null) {
			e.getOldMap().getCollideMap().unloadImage();
		}
		e.getOldMap().setAddedLoc(new ArrayList<Location>());
		e.getOldMap().setBlockedLoc(new ArrayList<Location>());
		
		e.getWorld().getCharacter(0).getEarth().setMaterialValue(0);
		
		if(e.getMap().getPlayerMap() != null) {
			e.getMap().getPlayerMap().loadImage();
		}
		if(e.getMap().getGroundImage() != null) {
			e.getMap().getGroundImage().loadImage();
		}
		if(e.getMap().getWallImage() != null) {
			e.getMap().getWallImage().loadImage();
		}
		if(e.getMap().getCollideMap() != null) {
			e.getMap().getCollideMap().loadImage();
		}
		
		
	}
}
