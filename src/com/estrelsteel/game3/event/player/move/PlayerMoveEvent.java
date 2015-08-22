package com.estrelsteel.game3.event.player.move;

import com.estrelsteel.game3.character.Player;
import com.estrelsteel.game3.event.player.PlayerEvent;

public class PlayerMoveEvent extends PlayerEvent {
	
	int oldPlayerX;
	int oldPlayerY;
	int playerX;
	int playerY;
	int playerWalkSpeed;
	boolean noClip = false;
	
	public PlayerMoveEvent(Player player, int oldPlayerX, int oldPlayerY) {
		super(player, "PlayerMoveEvent", 0);
		this.oldPlayerX = oldPlayerX;
		this.oldPlayerY = oldPlayerY;
		this.playerX = player.getX();
		this.playerY = player.getY();
		this.playerWalkSpeed = player.getWalkSpeed();
		this.noClip = player.getNoClip();
	}
	
	public int getOldPlayerX() {
		return oldPlayerX;
	}
	
	public int getOldPlayerY() {
		return oldPlayerY;
	}
	
	public int getPlayerX() {
		return playerX;
	}
	
	public int getPlayerY() {
		return playerY;
	}
	
	public int getPlayerWalkSpeed() {
		return playerWalkSpeed;
	}
	
	public boolean getNoClip() {
		return noClip;
	}
}
