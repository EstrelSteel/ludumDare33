package com.estrelsteel.game3.world;

import java.util.ArrayList;
import java.util.List;

import com.estrelsteel.game3.Game;
import com.estrelsteel.game3.block.Map;
import com.estrelsteel.game3.character.Player;
import com.estrelsteel.game3.event.map.MapChangeEvent;

public class World {
	List<Map> worldMaps = new ArrayList<Map>();
	List<Player> players = new ArrayList<Player>();
	int selectMap = 0;
	Game game;
	
	public World(Game game) {
		this.game = game;
	}
	
	public List<Map> getMaps() {
		return worldMaps;
	}
	
	public List<Player> getCharacters() {
		return players;
	}
	
	public Map getMap(int num) {
		return worldMaps.get(num);
	}
	
	public Player getCharacter(int num) {
		return players.get(num);
	}
	
	public Map getSelectedMap() {
		return worldMaps.get(selectMap);
	}
	
	public int getSelected() {
		return selectMap;
	}
	
	public Map nextMap() {
		Map oldMap = getSelectedMap();
		selectMap = selectMap + 1;
		Map map = worldMaps.get(selectMap);
		resetPos();
		if(oldMap != map) {
			game.mapChange.fireMapChangeEvent(new MapChangeEvent(oldMap, this));
		}
		return map;
	}
	
	public void resetPos() {
		Map map = worldMaps.get(selectMap);
		int x = map.getStartX();
		int y = map.getStartY();
		for(int i = 0; i < players.size(); i++) {
			players.get(i).setX(x);
			players.get(i).setY(y);
		}
	}
	
	public void addMap(Map map) {
		worldMaps.add(map);
		return;
	}
	
	public void addCharacter(Player player) {
		players.add(player);
	}
	
	public Map setSelected(int selectMap) {
		Map oldMap = getSelectedMap();
		this.selectMap = selectMap;
		Map map = worldMaps.get(selectMap);
		resetPos();
		if(oldMap != map) {
			game.mapChange.fireMapChangeEvent(new MapChangeEvent(oldMap, this));
		}
		return map;
	}
	
	public void setMaps(List<Map> worldMaps) {
		this.worldMaps = worldMaps;
		return;
	}
	
	public void setSelectedMap(Map map) {
		for(int i = 0; i < worldMaps.size(); i++) {
			if(worldMaps.get(i) == map) {
				Map oldMap = getSelectedMap();
				selectMap = i;
				if(oldMap != map) {
					game.mapChange.fireMapChangeEvent(new MapChangeEvent(oldMap, this));
				}
				return;
			}
		}
		return;
	}
	
	
}