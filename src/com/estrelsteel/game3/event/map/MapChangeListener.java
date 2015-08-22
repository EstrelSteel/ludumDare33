package com.estrelsteel.game3.event.map;

import java.util.EventListener;

public interface MapChangeListener extends EventListener {
	public void mapChangeEvent(MapChangeEvent e);
}
