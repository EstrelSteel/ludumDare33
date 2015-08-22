package com.estrelsteel.game3.test;

public class KeyNode extends Node{
	int id;
	String key;
	
	public KeyNode(int id, String key) {
		super(id, null, -256, -256);
		this.id = id;
		this.key = key;
	}
}
