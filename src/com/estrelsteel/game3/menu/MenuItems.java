package com.estrelsteel.game3.menu;

public enum MenuItems {
	
	CANCEL("SAVE", false),
	SFX_VOL("SFX_VOLUME", false),
	MUSIC_VOL("MUSIC_VOLUME", false),
	SCREEN_ARROWS("ON_SCREEN_ARROWS", false),
	PAUSE_CLOSE("PAUSE_ON_MOUSE_EXIT", true),
	SHOW_FPS("SHOW_FPS", false),
	SHOW_TPS("SHOW_TPS", false);
	
	private String title;
	private boolean active;
	
	MenuItems(String title, boolean active) {
		this.title = title;
		this.active = active;
	}
	
	public String getTitle() {
		return title;
	}
	
	public boolean isActive() {
		return active;
	}
	
	public void setActive(boolean active) {
		this.active = active;
		return;
	}
	
 	public void switchActive() {
		active = !active;
		return;
	}
}
