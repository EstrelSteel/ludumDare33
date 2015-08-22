package com.estrelsteel.game3.character;

import com.estrelsteel.game3.location.Location;

//TODO:
public class Node extends Location {
	int level = -1;
	Node lastNode;
	
	public Node(int x, int y) {
		super(null, x, y, 0, 0);
	}
	
	public int getLevel() {
		return level;
	}
	
	public Node getLastNode() {
		return lastNode;
	}
	
	public void setLevel(int level) {
		this.level = level;
	}
	
	public void setLastNode(Node lastNode) {
		this.lastNode = lastNode;
		return;
	}
}
