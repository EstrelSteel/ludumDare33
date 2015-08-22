package com.estrelsteel.game3.event.map;

import javax.swing.event.EventListenerList;

public class MapChange {
	EventListenerList listenerList = new EventListenerList();
	
	public void addMapChangeListener(MapChangeListener listener) {
		listenerList.add(MapChangeListener.class, listener);
		return;
	}
	
	public void removeMapChangeListener(MapChangeListener listener) {
		listenerList.remove(MapChangeListener.class, listener);
		return;
	}
	
	public void fireMapChangeEvent(MapChangeEvent mapChangeEvent) {
		Object[] listeners = listenerList.getListenerList();
		for(int i = 0; i < listeners.length; i = i +2) {
			if(listeners[i] == MapChangeListener.class) {
				((MapChangeListener) listeners[i+1]).mapChangeEvent(mapChangeEvent);
			}
		}
	}
}