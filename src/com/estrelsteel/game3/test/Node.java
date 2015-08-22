package com.estrelsteel.game3.test;

public class Node {
	int x;
	int y;
	Node connectNode;
	int id;
	boolean trigger = false;
	
	public Node(int id, Node connectNode, int x, int y) {
		this.id = id;
		this.connectNode = connectNode;
		this.x = x;
		this.y = y;
	}
}
