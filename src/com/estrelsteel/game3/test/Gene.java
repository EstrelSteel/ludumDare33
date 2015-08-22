package com.estrelsteel.game3.test;

import java.util.ArrayList;

public class Gene {
	int id;
	ArrayList<Node> nodes = new ArrayList<Node>();
	String name;
	
	public Gene(int id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public int getID() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public ArrayList<Node> getNodes() {
		return nodes;
	}
	
	public void addNode(Node node) {
		nodes.add(node);
		return;
	}
	
	public void removeNode(Node node) {
		nodes.remove(node);
		return;
	}
	
	public void setID(int id) {
		this.id = id;
		return;
	}
	
	public void setName(String name) {
		this.name = name;
		return;
	}
	
	public void setNodes(ArrayList<Node> nodes) {
		this.nodes = nodes;
		return;
	}
}
