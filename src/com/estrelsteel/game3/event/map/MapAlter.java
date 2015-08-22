package com.estrelsteel.game3.event.map;

import javax.swing.event.EventListenerList;

public class MapAlter {
	EventListenerList listenerList = new EventListenerList();
	
	public void addMapAlterListener(MapAlterListener listener) {
		listenerList.add(MapAlterListener.class, listener);
		return;
	}
	
	public void removeMapAlterListener(MapAlterListener listener) {
		listenerList.remove(MapAlterListener.class, listener);
		return;
	}
	
	public void fireMapAlterEvent(MapAlterEvent MapAlterEvent) {
		Object[] listeners = listenerList.getListenerList();
		for(int i = 0; i < listeners.length; i = i +2) {
			if(listeners[i] == MapAlterListener.class) {
				((MapAlterListener) listeners[i+1]).MapAlterEvent(MapAlterEvent);
			}
		}
	}
}