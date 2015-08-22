package com.estrelsteel.game3.menu;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class Menu {
	ArrayList<MenuItems> menuItems = new ArrayList<MenuItems>();
	Color colour = Color.YELLOW;
	int selectNum = 0;
	boolean open = false;
	String title = "NULL";
	
	public Menu(String title) {
		this.colour = Color.YELLOW;
		this.title = title;
		configureMenu();
	}
	
	public Menu(String title, Color colour) {
		this.colour = colour;
		this.title = title;
		configureMenu();
	}
	
	public Menu(String title, ArrayList<MenuItems> menuItems) {
		this.menuItems = menuItems;
		this.colour = Color.YELLOW;
		this.title = title;
	}
	
	public Menu(String title, Color colour, ArrayList<MenuItems> menuItems) {
		this.menuItems = menuItems;
		this.colour = colour;
		this.title = title;
	}
	
	public ArrayList<MenuItems> getMenuItems() {
		return menuItems;
	}
	
	public Color getColour() {
		return colour;
	}
	
	public int getSelectNum() {
		return selectNum;
	}
	
	public int getMaxSelectNum() {
		return menuItems.size();
	}
	
	public Graphics getBasicMenu(Graphics ctx, int x, int y, int xIncrease, int yIncrease) {
		ctx.setColor(colour);
		int i = 0;
		ctx.drawString(title, x, y);
		x = x + (xIncrease * 2);
		y = y + (yIncrease * 2);
		for(MenuItems item : menuItems) {
			String name = item.getTitle();
			
			
			if(i == selectNum && item != MenuItems.CANCEL) {
				ctx.drawString(">>>" + name + " = " + item.isActive(), x, y);
			}
			else if(i == selectNum && item == MenuItems.CANCEL) {
				ctx.drawString(">>>" + name, x, y);
			}
			else if(item == MenuItems.CANCEL) {
				ctx.drawString("" + name, x, y);
			}
			else{
				ctx.drawString("" + name + " = " + item.isActive(), x, y);
			}
			x = x + xIncrease;
			y = y + yIncrease;
			i = i + 1;
		}
		return ctx;
	}
	
	private void configureMenu() {
		menuItems.add(MenuItems.SFX_VOL);
		menuItems.add(MenuItems.MUSIC_VOL);
		menuItems.add(MenuItems.SCREEN_ARROWS);
		menuItems.add(MenuItems.PAUSE_CLOSE);
		menuItems.add(MenuItems.SHOW_FPS);
		menuItems.add(MenuItems.SHOW_TPS);
		menuItems.add(MenuItems.CANCEL);
		return;
	}
	
	public boolean isOpen() {
		return open;
	}
	
	public void addMenuItem(MenuItems item) {
		menuItems.add(item);
		return;
	}
	
	public void addMenuItem(int index, MenuItems item) {
		menuItems.add(index, item);
		return;
	}
	
	public void removeMenuItem(MenuItems item) {
		menuItems.remove(item);
		return;
	}
	
	public void removeMenuItem(int index) {
		menuItems.remove(index);
		return;
	}
	
	public void setMenuItems(ArrayList<MenuItems> menuItems) {
		this.menuItems = menuItems;
		return;
	}
	
	public void setColour(Color colour) {
		this.colour = colour;
		return;
	}
	
	public void setSelectedNum(int selectNum) {
		this.selectNum = selectNum;
		return;
	}
	
	public void setOpen(boolean open) {
		this.open = open;
		return;
	}
}
