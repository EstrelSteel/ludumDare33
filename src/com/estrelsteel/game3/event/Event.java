package com.estrelsteel.game3.event;

public class Event {
	String name = "NULL";
	int id = -1;
	
	public Event(String name, int id) {
		this.name = name;
		this.id = id;
	}
	
	/*
	enum EventType {
		EVENT("EVENT", -1),
		PLAYER_EVENT("PLAYER_EVENT", -1),
		PLAYER_MOVE_EVENT("PLAYER_MOVE_EVENT", 0),
		PLAYER_HEAL_EVENT("PLAYER_HEAL_EVENT", 1),
		PLAYER_DAMAGE_EVENT("PLAYER_DAMAGE_EVENT",2),
		MAP_CHANGE_EVENT("MAP_CHANGE_EVENT", 3);
		
		String name;
		int id;
		
		EventType(String name, int id) {
			this.name = name;
			this.id = id;
		}
	}
	*/
}
