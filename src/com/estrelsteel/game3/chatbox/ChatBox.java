package com.estrelsteel.game3.chatbox;

import java.awt.Color;
import java.awt.Graphics;

import com.estrelsteel.game3.Game;
import com.estrelsteel.game3.location.Location;

public class ChatBox extends Location {

	String name = "???";
	String text = "";
	boolean open = false;
	ChatBox chain;
	boolean bottom = true;
	boolean freeze = true;
	
	public ChatBox(String name, String text, int xN, int yN, double wN, double hN) {
		super(null, xN, yN, wN, hN);
		this.name = name;
		this.text = text;
	}
	
	public ChatBox(String text, int xN, int yN, double wN, double hN) {
		super(null, xN, yN, wN, hN);
		this.text = text;
	}
	
	public ChatBox(ChatBox chain, String name, String text, int xN, int yN, double wN, double hN) {
		super(null, xN, yN, wN, hN);
		this.name = name;
		this.text = text;
		this.chain = chain;
	}
	
	public ChatBox(ChatBox chain, String text, int xN, int yN, double wN, double hN) {
		super(null, xN, yN, wN, hN);
		this.text = text;
		this.chain = chain;
	}
	
	public String getName() {
		return name;
	}
	
	public String getText() {
		return text;
	}
	
	public Graphics getGraphics(Graphics ctx) {
		if(bottom) {
			ctx.setColor(Color.BLACK);
			ctx.fillRect(getX(), Game.HEIGHT - getY(),  Game.WIDTH - (int) getW(), (int) getH());
			ctx.setColor(Color.WHITE);
			ctx.fillRect(getX() + 1, Game.HEIGHT - getY() + 1, Game.WIDTH - (int) getW() + 2, (int) getH() + 2);
			ctx.setColor(Color.BLACK);
			ctx.drawString(name + ": " + text, getX() + 5, Game.HEIGHT - getY() + 14);
			ctx.drawString("[space]", Game.WIDTH - (int) getW() - 22, Game.HEIGHT - getY() + (int) getH() - 2);
		}
		else {
			ctx.setColor(Color.BLACK);
			ctx.fillRect(getX(), getY(),  Game.WIDTH - (int) getW(), (int) getH());
			ctx.setColor(Color.WHITE);
			ctx.fillRect(getX() + 1, getY() + 1, Game.WIDTH - (int) getW() + 2, (int) getH() + 2);
			ctx.setColor(Color.BLACK);
			ctx.drawString(name + ": " + text, getX() + 5, getY() + 14);
			ctx.drawString("[space]", Game.WIDTH - (int) getW() - 22, Game.HEIGHT - getY() + (int) getH() - 2);
		}
		return ctx;
	}
	
	public ChatBox getChain() {
		return chain;
	}
	
	public boolean isOpen() {
		return open;
	}
	
	public boolean isBottom() {
		return bottom;
	}
	
	public boolean isFreeze() {
		return freeze;
	}
	
	public void switchOpen() {
		this.open = !this.open;
		return;
	}
	
	public void switchBottom() {
		this.bottom = !this.bottom;
		return;
	}
	
	public void switchFreeze() {
		this.freeze = !this.freeze;
		return;
	}
	
	public void setName(String name) {
		this.name = name;
		return;
	}
	
	public void setText(String text) {
		this.text = text;
		return;
	}
	
	public void setOpen(boolean open) {
		this.open = open;
		return;
	}
	
	public void setBottom(boolean bottom) {
		this.bottom = bottom;
		return;
	}
	
	public void setFreeze(boolean freeze) {
		this.freeze = freeze;
		return;
	}
	
	public void setChain(ChatBox chain) {
		this.chain = chain;
		return;
	}

}
