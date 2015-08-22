package com.estrelsteel.game3.event.player.health;

import java.util.EventListener;

public interface PlayerHealListener extends EventListener{
	public void playerHealEvent(PlayerHealEvent e);
}
