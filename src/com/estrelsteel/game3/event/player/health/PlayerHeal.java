package com.estrelsteel.game3.event.player.health;

import javax.swing.event.EventListenerList;

public class PlayerHeal {
	EventListenerList listenerList = new EventListenerList();
	
	public void addPlayerHealListener(PlayerHealListener listener) {
		listenerList.add(PlayerHealListener.class, listener);
		return;
	}
	
	public void removePlayerHealListener(PlayerHealListener listener) {
		listenerList.remove(PlayerHealListener.class, listener);
		return;
	}
	
	public void firePlayerHeal(PlayerHealEvent playerHealEvent) {
		Object[] listeners = listenerList.getListenerList();
		for(int i = 0; i < listeners.length; i = i +2) {
			if(listeners[i] == PlayerHealListener.class) {
				((PlayerHealListener) listeners[i+1]).playerHealEvent(playerHealEvent);
			}
		}
	}
}