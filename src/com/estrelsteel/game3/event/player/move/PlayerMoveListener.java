package com.estrelsteel.game3.event.player.move;

import java.util.EventListener;

public interface PlayerMoveListener extends EventListener{
	public void playerMoveEvent(PlayerMoveEvent e);
}
