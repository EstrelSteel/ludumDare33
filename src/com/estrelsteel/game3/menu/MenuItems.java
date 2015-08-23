package com.estrelsteel.game3.menu;

public enum MenuItems {
	
	CANCEL("Save/Exit Menu", false),
	SFX_VOL("Sound Effects", false),
	MUSIC_VOL("Music", true),
	SCREEN_ARROWS("On-Screen Arrows", false),
	PAUSE_CLOSE("Pause on Mouse Exit", true),
	SHOW_FPS("Show FPS", false),
	SHOW_TPS("Show TPS", false);
	
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
