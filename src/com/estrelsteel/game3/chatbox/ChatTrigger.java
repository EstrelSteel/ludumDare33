package com.estrelsteel.game3.chatbox;

import com.estrelsteel.game3.block.Map;
import com.estrelsteel.game3.location.Location;

public class ChatTrigger extends Location {
	ChatBox chat;
	boolean on = true;
	boolean turnOff = true;
	Map map;
	
	public ChatTrigger(ChatBox chat, Map map, int xN, int yN, double wN, double hN) {
		super(null, xN, yN, wN, hN);
		this.chat = chat;
		this.map = map;
	}
	
	public ChatBox getChat() {
		return chat;
	}
	
	public Map getMap() {
		return map;
	}
	
	public boolean isOn() {
		return on;
	}
	
	public boolean isTurnOff() {
		return turnOff;
	}
	
	public void switchOn() {
		this.on = !this.on;
		return;
	}
	
	public void switchTurnOff() {
		this.turnOff = !this.turnOff;
		return;
	}
	
	public void setChat(ChatBox chat) {
		this.chat = chat;
		return;
	}
	
	public void setOn(boolean on) {
		this.on = on;
		return;
	}
	
	public void setMap(Map map) {
		this.map = map;
		return;
	}
	
	public void setTurnOff(boolean turnOff) {
		this.turnOff = turnOff;
		return;
	}

}
