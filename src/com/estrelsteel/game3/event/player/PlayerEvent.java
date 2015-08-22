package com.estrelsteel.game3.event.player;

import com.estrelsteel.game3.event.Event;
import com.estrelsteel.game3.character.Player;

public class PlayerEvent extends Event {
	Player player;
	
	public PlayerEvent(Player player, String name, int id) {
		super(name, id);
		this.player = player;
	}
	
	public Player getPlayer() {
		return player;
	}
}
