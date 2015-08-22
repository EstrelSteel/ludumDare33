package com.estrelsteel.game3.event.player.health;

import javax.swing.event.EventListenerList;

public class PlayerDamage {
	EventListenerList listenerList = new EventListenerList();
	
	public void addPlayerDamageListener(PlayerDamageListener listener) {
		listenerList.add(PlayerDamageListener.class, listener);
		return;
	}
	
	public void removePlayerDamageListener(PlayerDamageListener listener) {
		listenerList.remove(PlayerDamageListener.class, listener);
		return;
	}
	
	public void firePlayerDamage(PlayerDamageEvent playerDamageEvent) {
		Object[] listeners = listenerList.getListenerList();
		for(int i = 0; i < listeners.length; i = i +2) {
			if(listeners[i] == PlayerDamageListener.class) {
				((PlayerDamageListener) listeners[i+1]).playerDamageEvent(playerDamageEvent);
			}
		}
	}
}