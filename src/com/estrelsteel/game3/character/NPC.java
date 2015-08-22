package com.estrelsteel.game3.character;

import com.estrelsteel.game3.world.World;

//TODO:
public abstract class NPC extends Mob {

	public NPC(World worldN, int xN, int yN, double wN, double hN, int walkSpeed, String name) {
		super(worldN, xN, yN, wN, hN, walkSpeed, name);
	}

	@Override
	public void setHealth(double health) {
		//TODO:
	}
	
	public abstract void npcAI(int targetX, int targetY);

}
