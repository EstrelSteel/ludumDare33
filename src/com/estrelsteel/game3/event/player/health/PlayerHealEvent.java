package com.estrelsteel.game3.event.player.health;

import com.estrelsteel.game3.character.Player;
import com.estrelsteel.game3.event.player.PlayerEvent;

public class PlayerHealEvent extends PlayerEvent {
	
	double oldHealth;
	double change;
	
	public PlayerHealEvent(Player player, double oldHealth, double change) {
		super(player, "PlayerHealEvent", 1);
		this.oldHealth = oldHealth;
		this.change = change;
	}
	
	public double getOldHealth() {
		return oldHealth;
	}
	
	public double getHealthChange() {
		return change;
	}
}
