package com.estrelsteel.game3.event.player.move;

import javax.swing.event.EventListenerList;

public class PlayerMove {
	EventListenerList listenerList = new EventListenerList();
	
	public void addPlayerMoveListener(PlayerMoveListener listener) {
		listenerList.add(PlayerMoveListener.class, listener);
		return;
	}
	
	public void removePlayerMoveListener(PlayerMoveListener listener) {
		listenerList.remove(PlayerMoveListener.class, listener);
		return;
	}
	
	public void firePlayerMove(PlayerMoveEvent playerMoveEvent) {
		Object[] listeners = listenerList.getListenerList();
		for(int i = 0; i < listeners.length; i = i +2) {
			if(listeners[i] == PlayerMoveListener.class) {
				((PlayerMoveListener) listeners[i+1]).playerMoveEvent(playerMoveEvent);
			}
		}
	}
}