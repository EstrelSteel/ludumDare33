package com.estrelsteel.game3.handler;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import com.estrelsteel.game3.Game;
import com.estrelsteel.game3.block.Map;
import com.estrelsteel.game3.chatbox.ChatBox;
import com.estrelsteel.game3.item.Item;
import com.estrelsteel.game3.location.Location;
import com.estrelsteel.game3.menu.MenuItems;
import com.estrelsteel.game3.error.Error;

public class InputHandler implements KeyListener {
	
	Game game;
	boolean keyB = false;
	boolean keyU = false;
	boolean keyG = false;
	boolean shift = false;
	
	public InputHandler(Game game) {
		this.game = game;
	}
	
	public void systemUp(boolean on) {
		if(game.openChat != null) {
			if(game.openChat.isOpen() && game.openChat.isFreeze()) {
				on = false;
			}
		}
		if(on) {
			if(game.options.isOpen()) {
				if(game.options.getSelectNum() > 0) {
					game.options.setSelectedNum(game.options.getSelectNum() - 1);
				}
				return;
			}
			game.player.setGoUP(true);
			if(game.tutorial) {
				if(game.errorActive && game.error == Error.NULL) {
					game.errorActive = false;
					game.error = Error.UNKOWN;
				}
			}
		}
		else {
			game.player.setGoUP(false);
		}
	}
	
	public void systemDown(boolean on) {
		if(game.openChat != null) {
			if(game.openChat.isOpen() && game.openChat.isFreeze()) {
				on = false;
			}
		}
		if(on) {
			if(game.options.isOpen()) {
				if(game.options.getSelectNum() < game.options.getMaxSelectNum() - 1) {
					game.options.setSelectedNum(game.options.getSelectNum() + 1);
				}
				return;
			}
			game.player.setGoDOWN(true);
			if(game.tutorial) {
				if(game.errorActive && game.error == Error.NULL) {
					game.errorActive = false;
					game.error = Error.UNKOWN;
				}
			}
		}
		else {
			game.player.setGoDOWN(false);
		}
	}
	
	public void systemRight(boolean on) {
		if(game.openChat != null) {
			if(game.openChat.isOpen() && game.openChat.isFreeze()) {
				on = false;
			}
		}
		if(on) {
			game.player.setGoRIGHT(true);
			if(game.tutorial) {
				if(game.errorActive && game.error == Error.NULL) {
					game.errorActive = false;
					game.error = Error.UNKOWN;
				}
			}
		}
		else {
			game.player.setGoRIGHT(false);
		}
	}
	
	public void systemLeft(boolean on) {
		if(game.openChat != null) {
			if(game.openChat.isOpen() && game.openChat.isFreeze()) {
				on = false;
			}
		}
		if(on) {
			game.player.setGoLEFT(true);
			if(game.tutorial) {
				if(game.errorActive && game.error == Error.NULL) {
					game.errorActive = false;
					game.error = Error.UNKOWN;
				}
			}
		}
		else {
			game.player.setGoLEFT(false);
		}
	}

	public void keyPressed(KeyEvent e) {
		//System.out.println(e.getKeyChar() + ", " + e.getKeyCode());
		switch(e.getKeyCode()) {
		case 87: /* W */
			systemUp(true);
			break;
		case 83: /* S */
			systemDown(true);
			break;
		case 68: /* D */
			systemRight(true);
			break;
		case 65: /* A */
			systemLeft(true);
			break;
		case 32: /* SPACE */
			if(game.openChat != null) {
				if(game.openChat.isOpen()) {
					game.openChat.switchOpen();
					ChatBox chain = game.openChat.getChain();
					if(chain == null) {
						break;
					}
					game.openChat = chain;
					chain.switchOpen();
				}
			}
			if(game.options.isOpen()) {
				int select = game.options.getSelectNum();
				MenuItems items = game.options.getMenuItems().get(select);
				if(items == MenuItems.CANCEL) {
					game.options.setOpen(false);
					game.checkMenuSettings();
				}
				items.switchActive();
				break;
			}
			break;
		case 80: /* P */
			if(game.options.isOpen()) {
				game.options.setOpen(false);
			}
			else {
				game.options.setOpen(true);
			}
			game.checkMenuSettings();
			break;
		case 66: /* B */
			if(!shift) {
				break;
			}
			keyB = true;
			if(keyB && keyU && keyG) {
				game.DEBUG = !game.DEBUG;
				System.out.println("DEBUG MODE TOGGLED!!!!!");
			}
			break;
		case 85: /* U */
			if(!shift) {
				break;
			}
			keyU = true;
			if(keyB && keyU && keyG) {
				game.DEBUG = !game.DEBUG;
				System.out.println("DEBUG MODE TOGGLED!!!!!");
			}
			break;
		case 71: /* G */
			if(!shift) {
				break;
			}
			keyG = true;
			if(keyB && keyU && keyG) {
				game.DEBUG = !game.DEBUG;
				System.out.println("DEBUG MODE TOGGLED!!!!!");
			}
			break;
		case 84: /* T */
			if(!shift) {
				break;
			}
			if(game.DEBUG) {
				String cmd = JOptionPane.showInputDialog("Enter a Command!");
				if(cmd == null) {
					break;
				}
				cmd = cmd.trim();
				String[] args = cmd.split(" ");
				if(args.length == 1) {
					if(args[0].equalsIgnoreCase("__noClip?")) {
						System.out.println("player noclip = " + game.player.getNoClip());
					}
					else if(args[0].equalsIgnoreCase("__noClip-")) {
						game.player.setNoClip(false);
						System.out.println("player noclip now= false");
					}
					else if(args[0].equalsIgnoreCase("__noClip+")) {
						game.player.setNoClip(true);
						System.out.println("player noclip now= true");
					}
					else if(args[0].equalsIgnoreCase("__walkSpeed?")) {
						System.out.println("player walkspeed = " + game.player.getWalkSpeed());
					}
					else if(args[0].equalsIgnoreCase("__showMapCollide?")) {
						System.out.println("map showcollide = " + game.drawCollide);
					}
					else if(args[0].equalsIgnoreCase("__showMapCollide+")) {
						game.drawCollide = true;
						System.out.println("map showcollide now= true");
					}
					else if(args[0].equalsIgnoreCase("__showMapCollide-")) {
						game.drawCollide = false;
						System.out.println("map showcollide now= false");
					}
				}
				else if(args.length == 2) {
					if(args[0].equalsIgnoreCase("__noClip?") && args[1].equalsIgnoreCase("player")) {
						System.out.println("player noclip = " + game.player.getNoClip());
					}
					else if(args[0].equalsIgnoreCase("__noClip-") && args[1].equalsIgnoreCase("player")) {
						game.player.setNoClip(false);
						System.out.println("player noclip now= false");
					}
					else if(args[0].equalsIgnoreCase("__noClip+") && args[1].equalsIgnoreCase("player")) {
						game.player.setNoClip(true);
						System.out.println("player noclip now= true");
					}
					else if(args[0].equalsIgnoreCase("__loadMap")) {
						List<Map> maps = game.world.getMaps();
						for(Map map : maps) {
							if(map.getName().equals(args[1].trim())) {
								game.world.setSelectedMap(map);
								game.world.resetPos();
							}
						}
					}
					else if(args[0].equalsIgnoreCase("__HP") && game.stringtoint(args[1].trim()) != -256) {
						game.player.setHealth(game.stringtoint(args[1].trim()));
						System.out.println("player hp now= " + game.player.getHealth());
					}
					else if(args[0].equalsIgnoreCase("__HP.MAX") && game.stringtoint(args[1].trim()) != -256) {
						game.player.setMaxHealth(game.stringtoint(args[1].trim()));
						System.out.println("player hp.max now= " + game.player.getMaxHealth());
					}
					else if(args[0].equalsIgnoreCase("__HP.MIN") && game.stringtoint(args[1].trim()) != -256) {
						game.player.setMinHealth(game.stringtoint(args[1].trim()));
						System.out.println("player hp.min now= " + game.player.getMinHealth());
					}
					else if(args[0].equalsIgnoreCase("__walkSpeed") && game.stringtoint(args[1].trim()) != -256) {
						game.player.setWalkSpeed(game.stringtoint(args[1].trim()));
						System.out.println("player walkspeed now= " + game.player.getWalkSpeed());
					}
					else if(args[0].equalsIgnoreCase("__walkSpeed?") && args[1].equalsIgnoreCase("player")) {
						System.out.println("player walkspeed = " + game.player.getWalkSpeed());
					}
					else if(args[0].equalsIgnoreCase("__MATERIAL") && game.stringtoint(args[1].trim()) != -256) {
						game.player.getEarth().setMaterialValue(game.stringtoint(args[1].trim()));
						System.out.println("player material now= " + game.player.getHealth());
					}
					else if(args[0].equalsIgnoreCase("__MATERIAL.MAX") && game.stringtoint(args[1].trim()) != -256) {
						game.player.getEarth().setMaxMaterial(game.stringtoint(args[1].trim()));
						System.out.println("player material.max now= " + game.player.getMaxHealth());
					}
					else if(args[0].equalsIgnoreCase("__MATERIAL.MIN") && game.stringtoint(args[1].trim()) != -256) {
						game.player.getEarth().setMinMaterial(game.stringtoint(args[1].trim()));
						System.out.println("player material.min now= " + game.player.getMinHealth());
					}
					else if(args[0].equalsIgnoreCase("__GIVE.NAME")) {
						if(!game.player.getInventory().hasItem(args[1])) {
							for(Item item : Item.values()) {
								if(item.getName().equalsIgnoreCase(args[1])){
									game.player.getInventory().addInventoryItem(item);
									System.out.println("player item + " + item.getName());
									break;
								}
							}	
						}	
					}
					
				}
				else if(args.length == 3) {
					if(args[0].equalsIgnoreCase("__HP") && args[1].equalsIgnoreCase("player") && game.stringtoint(args[2].trim()) != -256) {
						game.player.setHealth(game.stringtoint(args[2].trim()));
						System.out.println("player hp now= " + game.player.getHealth());
					}
					else if(args[0].equalsIgnoreCase("__HP.MAX") && args[1].equalsIgnoreCase("player") && game.stringtoint(args[2].trim()) != -256) {
						game.player.setMaxHealth(game.stringtoint(args[2].trim()));
						System.out.println("player hp.max now= " + game.player.getMaxHealth());
					}
					else if(args[0].equalsIgnoreCase("__HP.MIN") && args[1].equalsIgnoreCase("player") && game.stringtoint(args[2].trim()) != -256) {
						game.player.setMinHealth(game.stringtoint(args[2].trim()));
						System.out.println("player hp.min now= " + game.player.getMinHealth());
					}
					else if(args[0].equalsIgnoreCase("__walkSpeed") && args[1].equalsIgnoreCase("player") && game.stringtoint(args[2].trim()) != -256) {
						game.player.setWalkSpeed(game.stringtoint(args[2].trim()));
						System.out.println("player walkspeed now= " + game.player.getWalkSpeed());
					}
					else if(args[0].equalsIgnoreCase("__MATERIAL") && args[1].equalsIgnoreCase("player") && game.stringtoint(args[2].trim()) != -256) {
						game.player.getEarth().setMaterialValue(game.stringtoint(args[2].trim()));
						System.out.println("player material now= " + game.player.getHealth());
					}
					else if(args[0].equalsIgnoreCase("__MATERIAL.MAX") && args[1].equalsIgnoreCase("player") && game.stringtoint(args[2].trim()) != -256) {
						game.player.getEarth().setMaxMaterial(game.stringtoint(args[2].trim()));
						System.out.println("player material.max now= " + game.player.getMaxHealth());
					}
					else if(args[0].equalsIgnoreCase("__MATERIAL.MIN") && args[1].equalsIgnoreCase("player") && game.stringtoint(args[2].trim()) != -256) {
						game.player.getEarth().setMinMaterial(game.stringtoint(args[2].trim()));
						System.out.println("player material.min now= " + game.player.getMinHealth());
					}
					else if(args[0].equalsIgnoreCase("__GIVE.NAME") && args[1].equalsIgnoreCase("player")) {
						if(!game.player.getInventory().hasItem(args[2])) {
							for(Item item : Item.values()) {
								if(item.getName().equalsIgnoreCase(args[2])){
									game.player.getInventory().addInventoryItem(item);
									System.out.println("player item + " + item.getName());
									break;
								}
							}
						}
					}
				}
			}
			break;
		case 88: /* X */
			if(game.player.getInventory().hasItem(Item.EARTH_RESTORE)) {
				if(game.world.getSelectedMap().checkBlockedCollide(game.player.getX(), game.player.getY())) {
					break;
				}
				game.world.getSelectedMap().setBlockedLoc(new ArrayList<Location>());
				game.world.getSelectedMap().setAddedLoc(new ArrayList<Location>());
				game.player.getEarth().setMaterialValue(game.player.getEarth().getMinMaterial());
			}
			break;
		case 16: /* SHIFT */
			shift = true;
			break;
		default:
			break;
		}
	}

	public void keyReleased(KeyEvent e) {
		switch(e.getKeyCode()) {
		case 87: /* W */
			systemUp(false);
			break;
		case 83: /* S */
			systemDown(false);
			break;
		case 68: /* D */
			systemRight(false);
			break;
		case 65: /* A */
			systemLeft(false);
			break;
		case 32: /* SPACE */
			
			break;
		case 66: /* B */
			if(!shift) {
				break;
			}
			keyB = false;
			break;
		case 85: /* U */
			if(!shift) {
				break;
			}
			keyU = false;
			break;
		case 71: /* G */
			if(!shift) {
				break;
			}
			keyG = false;
			break;
		case 16:
			shift = false;
			break;
		default:
			break;
		}
	}

	public void keyTyped(KeyEvent e) {
		
	}

}
