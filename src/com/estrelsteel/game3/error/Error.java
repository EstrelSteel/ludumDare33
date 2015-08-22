package com.estrelsteel.game3.error;

import com.estrelsteel.game3.block.Image;

public enum Error {
	
	NULL(-1, "NULL", new Image("/com/estrelsteel/game3/res/logo/Estrelsteel.png")),
	UNKOWN(0, "???", new Image("/com/estrelsteel/game3/res/logo/Estrelsteel.png"));
	
	private int id;
	private String name;
	private Image img;
	
	Error(int id, String name, Image img) {
		this.id = id;
		this.name = name;
		this.img = img;
	}
	
	public int getInt() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public Image getImage() {
		return img;
	}
}
