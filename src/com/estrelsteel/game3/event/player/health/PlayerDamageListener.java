package com.estrelsteel.game3.event.player.health;

import java.util.EventListener;

public interface PlayerDamageListener extends EventListener {
	public void playerDamageEvent(PlayerDamageEvent e);
}
