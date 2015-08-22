package com.estrelsteel.game3.character;

import java.util.ArrayList;

import com.estrelsteel.game3.world.World;

//TODO:
public class AStar extends NPC {

	ArrayList<Node> nodes = new ArrayList<Node>();
	
	public AStar(World worldN, int xN, int yN, double wN, double hN, int walkSpeed, String name) {
		super(worldN, xN, yN, wN, hN, walkSpeed, name);
	}
	
	public ArrayList<Node> getNodes() {
		return nodes;
	}

	@SuppressWarnings("unused")
	@Override
	public void npcAI(int targetX, int targetY) {
		Node node = new Node(getX(), getY());
		node.setLevel(0);
		nodes.add(node);
		int lastLevel = 0;
	}
	
	public void setNodes(ArrayList<Node> nodes) {
		this.nodes = nodes;
		return;
	}

}
