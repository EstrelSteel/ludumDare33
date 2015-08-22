package com.estrelsteel.game3.handler;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import com.estrelsteel.game3.Game;
import com.estrelsteel.game3.block.Map;
import com.estrelsteel.game3.item.Item;
import com.estrelsteel.game3.location.Location;
import com.estrelsteel.game3.error.Error;

public class MouseHandler implements MouseListener, MouseMotionListener {

	Game game;
	int x = 0;
	int y = 0;
	boolean up = false;
	boolean down = false;
	boolean right = false;
	boolean left = false;
	
	public MouseHandler(Game game) {
		this.game = game;
	}
	
	public void mouseClicked(MouseEvent e) {
		//System.out.println(e.getButton());
		//TODO: UPDATE TO BETTER SYSTEM!
		//ADD BLOCK
		if(game.player.getInventory().hasItem(Item.EARTH_PLACE) && e.getButton() == 3) {
			Map map = game.world.getSelectedMap();
			
			if(game.player.getEarth().getMaterialValue() > game.player.getEarth().getMinMaterial()) {
				int clickedX = e.getX() - 10 - map.getXFormat();
				int clickedY = e.getY() - 10 - map.getYFormat();
				if(map.checkColour(clickedX, clickedY, 255, 255, 255) || map.checkColour(clickedX + 20, clickedY, 255, 255, 255) || map.checkColour(clickedX, clickedY + 20, 255, 255, 255) || map.checkColour(clickedX + 20, clickedY + 20, 255, 255, 255)) {
					map.addAddedLoc(new Location(game.world, e.getX() - 10 - map.getXFormat(), e.getY() - 10 - map.getYFormat(), 20, 20));
					game.player.getEarth().setMaterialValue(game.player.getEarth().getMaterialValue() - 1);
					game.errorClick = 0;
					game.error = Error.UNKOWN;
				}
				else {
					game.errorClick = game.errorClick + 1;
					game.error = Error.NULL;
				}
				
				game.world.setSelectedMap(map);
			}
			else {
				game.errorClick = game.errorClick + 1;
				game.error = Error.NULL;
			}
		}
		//CHECK IF PLAYER CLICKED ON SCREEN ARROWS
		boolean clickedArrow = false;
		if(game.showArrow && e.getButton() == 1) {
			if(e.getX() > game.getWidth() - 86 && e.getX() < game.getWidth() - 86 + 39 && e.getY() > game.getHeight() - 86 && e.getY() < game.getHeight() - 86 + 39) {
				//UP
				clickedArrow = true;
			}
			if(e.getX() > game.getWidth() - 86 && e.getX() < game.getWidth() - 86 + 39 && e.getY() > game.getHeight() - 43 && e.getY() < game.getHeight() - 43 + 39) {
				//DOWN
				clickedArrow = true;
			}
			if(e.getX() > game.getWidth() - 43 && e.getX() < game.getWidth() - 43 + 39 && e.getY() > game.getHeight() - 43 && e.getY() < game.getHeight() - 43 + 39) {
				//RIGHT
				clickedArrow = true;
			}
			if(e.getX() > game.getWidth() - 129 && e.getX() < game.getWidth() - 129 + 39 && e.getY() > game.getHeight() - 43 && e.getY() < game.getHeight() - 43 + 39) {
				//LEFT
				clickedArrow = true;
			}
		}
		//REMOVE BLOCK
		if(game.player.getInventory().hasItem(Item.EARTH_REMOVE) && e.getButton() == 1 && !clickedArrow) {
			Map map = game.world.getSelectedMap();
			
			if(game.player.getEarth().getMaterialValue() < game.player.getEarth().getMaxMaterial()) {
				int clickedX = e.getX() - 10 - map.getXFormat();
				int clickedY = e.getY() - 10 - map.getYFormat();
				if(map.checkColour(clickedX, clickedY, 255, 255, 0) || map.checkColour(clickedX + 20, clickedY, 255, 255, 0) || map.checkColour(clickedX, clickedY + 20, 255, 255, 0) || map.checkColour(clickedX + 20, clickedY + 20, 255, 255, 0)) {
					map.addBlockedLoc(new Location(game.world, e.getX() - 10 - map.getXFormat(), e.getY() - 10 - map.getYFormat(), 20, 20));
					game.player.getEarth().setMaterialValue(game.player.getEarth().getMaterialValue() + 1);
					game.errorClick = 0;
					game.error = Error.UNKOWN;
				}
				else {
					game.errorClick = game.errorClick + 1;
					game.error = Error.NULL;
				}
				
				game.world.setSelectedMap(map);
			}
			else {
				game.errorClick = game.errorClick + 1;
				game.error = Error.NULL;
			}
		}
	}

	public void mouseEntered(MouseEvent e) {
		
	}

	public void mouseExited(MouseEvent e) {
		
	}

	public void mousePressed(MouseEvent e) {
		if(game.showArrow && e.getButton() == 1) {
			/*
			ctx.drawImage(upArrow.getImage(), getWidth() - 86, getHeight() - 86, 39, 39, null);
			ctx.drawImage(downArrow.getImage(), getWidth() - 86, getHeight() - 43, 39, 39, null);
			ctx.drawImage(leftArrow.getImage(), getWidth() - 129, getHeight() - 43, 39, 39, null);
			ctx.drawImage(rightArrow.getImage(), getWidth() - 43, getHeight() - 43, 39, 39, null);
			*/
			if(e.getX() > game.getWidth() - 86 && e.getX() < game.getWidth() - 86 + 39 && e.getY() > game.getHeight() - 86 && e.getY() < game.getHeight() - 86 + 39) {
				//UP
				game.input.systemUp(true);
				up = true;
			}
			if(e.getX() > game.getWidth() - 86 && e.getX() < game.getWidth() - 86 + 39 && e.getY() > game.getHeight() - 43 && e.getY() < game.getHeight() - 43 + 39) {
				//DOWN
				game.input.systemDown(true);
				down = true;
			}
			if(e.getX() > game.getWidth() - 43 && e.getX() < game.getWidth() - 43 + 39 && e.getY() > game.getHeight() - 43 && e.getY() < game.getHeight() - 43 + 39) {
				//RIGHT
				game.input.systemRight(true);
				right = true;
			}
			if(e.getX() > game.getWidth() - 129 && e.getX() < game.getWidth() - 129 + 39 && e.getY() > game.getHeight() - 43 && e.getY() < game.getHeight() - 43 + 39) {
				//LEFT
				game.input.systemLeft(true);
				left = true;
			}
		}
	}

	public void mouseReleased(MouseEvent e) {
		if(up) {
			//UP
			game.input.systemUp(false);
			up = false;
		}
		if(down) {
			//DOWN
			game.input.systemDown(false);
			down = false;
		}
		if(right) {
			//RIGHT
			game.input.systemRight(false);
			right = false;
		}
		if(left) {
			//LEFT
			game.input.systemLeft(false);
			left = false;
		}
	}

	public void mouseDragged(MouseEvent e) {
		
	}

	public void mouseMoved(MouseEvent e) {
		this.x = e.getX();
		this.y = e.getY();
	}
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}

}
