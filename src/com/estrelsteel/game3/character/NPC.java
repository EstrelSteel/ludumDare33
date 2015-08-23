package com.estrelsteel.game3.character;

import com.estrelsteel.game3.block.Image;
import com.estrelsteel.game3.world.World;

public class NPC extends Mob {

	Image image;
	boolean shadow = true;
	
	public NPC(Image image, World worldN, int xN, int yN, double wN, double hN, int walkSpeed, String name) {
		super(worldN, xN, yN, wN, hN, walkSpeed, name);
		this.image = image;
	}

	public Image getImage() {
		return image;
	}
	
	public boolean hasShadow() {
		return shadow;
	}
	
	public void setImage(Image image) {
		this.image = image;
		return;
	}
	
	public void setShadow(boolean shadow) {
		this.shadow = shadow;
		return;
	}

	@Override
	public void setHealth(double health) {
		this.health = health;
		return;
	}
	
}
