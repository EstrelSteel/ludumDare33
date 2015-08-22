package com.estrelsteel.game3;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.IOException;
import java.util.ArrayList;

import com.estrelsteel.game3.block.Exit;
import com.estrelsteel.game3.block.Image;
import com.estrelsteel.game3.block.Map;
import com.estrelsteel.game3.handler.InputHandler;
import com.estrelsteel.game3.handler.MapHandler;
import com.estrelsteel.game3.handler.MouseHandler;
import com.estrelsteel.game3.handler.PlayerHealthHandler;
import com.estrelsteel.game3.handler.PlayerMoveHandler;
import com.estrelsteel.game3.handler.WindowHandler;
import com.estrelsteel.game3.location.Location;
import com.estrelsteel.game3.menu.Menu;
import com.estrelsteel.game3.menu.MenuItems;
import com.estrelsteel.game3.meter.HorizontalMeter;
import com.estrelsteel.game3.music.Music;
import com.estrelsteel.game3.music.Volume;
import com.estrelsteel.game3.world.World;
import com.estrelsteel.game3.character.Player;
import com.estrelsteel.game3.chatbox.ChatBox;
import com.estrelsteel.game3.chatbox.ChatTrigger;
import com.estrelsteel.game3.event.map.MapAlter;
import com.estrelsteel.game3.event.map.MapChange;
import com.estrelsteel.game3.event.player.health.PlayerDamage;
import com.estrelsteel.game3.event.player.health.PlayerHeal;
import com.estrelsteel.game3.event.player.move.PlayerMove;
import com.estrelsteel.game3.error.Error;

public class Game extends Canvas implements Runnable {

	private static final long serialVersionUID = 1L;
	private BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
	private int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
	
	/*
	 * SCALE=2		| 20 FPS	| *
	 * SCALE=1.625	| ~30 FPS	| *
	 * SCALE= 1.5	| 30 FPS 	| *
	 * SCALE= 1		| 57 FPS	|
	 */
	
	public boolean running = false;
	public boolean showArrow = false;
	public boolean pauseClose = true;
	public boolean applet = false;
	
	public int tickCount = 0;
	public int frames;
	private boolean showFPS = false;
	public int fps;
	public int tps;
	
	public InputHandler input;
	public MouseHandler mouse;
	public WindowHandler window;
	
	public PlayerMove playerMove = new PlayerMove();
	public PlayerMoveHandler playerMoveListener;
	
	public PlayerHeal playerHeal = new PlayerHeal();
	public PlayerDamage playerDamage = new PlayerDamage();
	public PlayerHealthHandler playerHealthListener;

	public MapChange mapChange = new MapChange();
	public MapAlter mapAlter = new MapAlter();
	public MapHandler mapHandler;
	
	public Menu options = new Menu("GAME PAUSED");
	
	public boolean DEBUG = false;
	public boolean drawCollide = false;
	
	public String title = "Estrel Engine";
	public String version = "0.1a";
	public int clientBuild = 1;
	
	public static final double SCALE = 1.625;
	public static int WIDTH = 400;
	public static int HEIGHT = 400;
	public static Dimension dimension = new Dimension((int) (WIDTH * Game.SCALE), (int) (HEIGHT * Game.SCALE));
	
	public Image upperMenu = new Image("/com/estrelsteel/game3/res/logo/logo.png");
	
	public int errorY = 2;
	public Error error = Error.NULL;
	public int errorClick = 0;
	public boolean tutorial = true;
	public boolean errorActive = true;
	
	public Image upArrow = new Image("/com/estrelsteel/game3/res/logo/logo.png");
	public Image downArrow = new Image("/com/estrelsteel/game3/res/logo/logo.png");
	public Image rightArrow = new Image("/com/estrelsteel/game3/res/logo/logo.png");
	public Image leftArrow = new Image("/com/estrelsteel/game3/res/logo/logo.png");
	
	public World world = new World(this);
	
	public Image map1Collide = new Image("/com/estrelsteel/game3/res/logo/Estrelsteel.png");
	public Image map1Image = new Image("/com/estrelsteel/game3/res/logo/Estrelsteel.png");;
	public Map map1 = new Map(this, map1Collide, map1Image, "map1", true, true, 250, 250, 1500, 1500, world);
	
	public Music music;
	
	public Player player = new Player(this, world, 5, 5, 5.0, 5.0, 5);
	public HorizontalMeter healthMeter = new HorizontalMeter(true, player.getMaxHealth(), player.getMinHealth(), player.getHealth(), Color.GREEN, 49, 19, 102, 22, DEBUG);
	public HorizontalMeter materialMeter = new HorizontalMeter(true, player.getEarth().getMaxMaterial(), player.getEarth().getMaterialValue(), player.getEarth().getMinMaterial(), Color.GREEN, 49, 49, 102, 22, DEBUG);
	
	private Thread thread; 
	
	public ArrayList<ChatTrigger> chatMsg = new ArrayList<ChatTrigger>();
	public ChatBox openChat;
	
	public Game() {
		input = new InputHandler(this);
		mouse = new MouseHandler(this);
		addKeyListener(input);
		addMouseListener(mouse);
		addMouseMotionListener(mouse);
		
		playerMoveListener = new PlayerMoveHandler();
		playerHealthListener = new PlayerHealthHandler();
		mapHandler = new MapHandler();
		
		playerMove.addPlayerMoveListener(playerMoveListener);
		playerHeal.addPlayerHealListener(playerHealthListener);
		playerDamage.addPlayerDamageListener(playerHealthListener);
		mapChange.addMapChangeListener(mapHandler);
		mapAlter.addMapAlterListener(mapHandler);
		
		
		Image ground = new Image("/com/estrelsteel/game3/res/logo/logo.png");
		Image wall = new Image("/com/estrelsteel/game3/res/logo/logo.png");
		Image background = new Image("/com/estrelsteel/game3/res/logo/logo.png");
		map1.addExits(new Exit(map1, new Location(world, 880, 1180, 300, 20)));
		map1.setBackgroundImage(background);
		map1.setGroundImage(ground);
		map1.setWallImage(wall);
		world.addMap(map1);
		
		world.resetPos();
		world.getSelectedMap().getPlayerMap().loadImage();
		upperMenu.loadImage();
		
		error = Error.UNKOWN;
		errorActive = true;
		//music = Music.TEST_WAV;
		//music.vol = Volume.MUTE;
		//music.play();
		
		upArrow.loadImage();
		downArrow.loadImage();
		rightArrow.loadImage();
		leftArrow.loadImage();
		
		ChatBox chat = new ChatBox("Hello, World! 123", 20, 80, 40, 60);
		chat.setOpen(false);
		ChatTrigger trigChat = new ChatTrigger(chat, map1, 240, 240, 20, 20);
		chatMsg.add(trigChat);
	}
	
	public synchronized void start() {
		running = true;
		input = new InputHandler(this);
		mouse = new MouseHandler(this);
		
		thread = new Thread(this, title + "_main");
		thread.start();
	}
	
	public synchronized void stop() {
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
		long lastTime = System.nanoTime();
		double nsPerTick = 1000000000D/60D;
		
		int ticks = 0;
		frames = 0;
		
		long lastTimer = System.currentTimeMillis();
		double delta = 0;
		
		
		while(running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / nsPerTick;
			lastTime = now;
			boolean shouldRender = true;

			WIDTH = getWidth();
			HEIGHT = getHeight();
			
			while(delta >= 5) {
				ticks++;
				tick();
				delta -= 1;
				shouldRender = true;
			}
			
			try {
				Thread.sleep(2);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			if(shouldRender) {
				frames++;
				try {
					render();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			if(System.currentTimeMillis() - lastTimer >= 1000) {
				lastTimer += 1000;
				if(showFPS) {
					System.out.println(frames + " fps, " + ticks + " tps");
				}
				fps = frames;
				tps = ticks;
				ticks = 0;
				frames = 0;
			}
		}
		
	}
	
	public void tick() {
		tickCount++;
		
		for(int i = 0; i < pixels.length; i++) {
			pixels[i] = i + tickCount;
		}
		
		player.setUP(0);
		player.setDOWN(0);
		player.setRIGHT(0);
		player.setLEFT(0);
		//System.out.println("x:" + player.getX() + " y:" + player.getY());
		if(world.getSelectedMap().checkCollide(player.getX(), player.getY() - player.getWalkSpeed())) {
			player.setUP(player.getUP() + 1);
			//player.setGoUP(false);
		}
		if(world.getSelectedMap().checkCollide(player.getX(), player.getY() + player.getWalkSpeed())) {
			player.setDOWN(player.getDOWN() + 1);
			//player.setGoDOWN(false);
		}
		if(world.getSelectedMap().checkCollide(player.getX() + player.getWalkSpeed(), player.getY())) {
			player.setRIGHT(player.getRIGHT() + 1);
			//player.setGoRIGHT(false);
		}
		if(world.getSelectedMap().checkCollide(player.getX() - player.getWalkSpeed(), player.getY())) {
			player.setLEFT(player.getLEFT() + 1);
			//player.setGoLEFT(false);
		}
		
		if(player.canGoUP()) {
			player.moveUP();
		}
		if(player.canGoDOWN()) {
			player.moveDOWN();
		}
		if(player.canGoRIGHT()) {
			player.moveRIGHT();
		}
		if(player.canGoLEFT()) {
			player.moveLEFT();
		}
		
		healthMeter.setMax(player.getMaxHealth());
		healthMeter.setMin(player.getMinHealth());
		healthMeter.setValue(player.getHealth());
		healthMeter.setOutBounds(DEBUG);
		
		materialMeter.setMax(player.getEarth().getMaxMaterial());
		materialMeter.setMin(player.getEarth().getMinMaterial());
		materialMeter.setValue(player.getEarth().getMaterialValue());
		materialMeter.setOutBounds(DEBUG);
		
		if(errorActive) {
			if(errorY < 36) {
				errorY = errorY + 5;
			}
		}
		else {
			if(errorY > 0) {
				errorY = errorY - 5;
			}
		}
	}
	
	private Graphics renderERROR(Graphics ctx) {
		if(errorClick > 3) {
			errorActive = true;
		}
		
		if(errorActive) {
			Image errorImg = error.getImage();
			// img >>> 320x40
			if(!errorImg.isImageLoaded()) {
				errorImg.loadImage();
			}
			ctx.drawImage(errorImg.getImage(), (int) (getWidth() / 2) - 160, errorY - 40, 320, 40, null);
		}
		return ctx;
	}
	
	private Graphics renderHUD(Graphics ctx) {
		ctx.drawImage(upperMenu.getImage(), 0, 0, 200, 200, null);
		Location loc = healthMeter.getFillArea();
		ctx.setColor(healthMeter.getColour());
		ctx.fillRect(loc.getX(), loc.getY(), (int) loc.getW(), (int) loc.getH());
		ctx.setColor(Color.BLACK);
		ctx.drawString("" + (int) player.getHealth() + "/" + (int) player.getMaxHealth(), 50, 35);
		
		loc = materialMeter.getFillArea();
		ctx.setColor(materialMeter.getColour());
		ctx.fillRect(loc.getX(), loc.getY(), (int) loc.getW(), (int) loc.getH());
		ctx.setColor(Color.BLACK);
		ctx.drawString("" + (int) player.getEarth().getMaterialValue() + "/" + (int) player.getEarth().getMaxMaterial(), 50, 65);
		ctx = renderERROR(ctx);
		return ctx;
	}
	
	public void checkMenuSettings() {
		for(MenuItems item : options.getMenuItems()) {
			if(item.isActive()) {
				if(item == MenuItems.MUSIC_VOL && !music.isPlaying()) {
					music.vol = Volume.LOW;
					music.play();
				}
				if(item == MenuItems.SCREEN_ARROWS && !showArrow) {
					showArrow = true;
				}
				if(item == MenuItems.PAUSE_CLOSE && !pauseClose) {
					pauseClose = true;
				}
			}
			else {
				if(item == MenuItems.MUSIC_VOL && music.isPlaying()) {
					music.vol = Volume.MUTE;
					music.stopPlaying();
				}
				if(item == MenuItems.SCREEN_ARROWS && showArrow) {
					showArrow = false;
				}
				if(item == MenuItems.PAUSE_CLOSE && pauseClose) {
					pauseClose = false;
				}
			}
		}
	}
	
	public void render() throws IOException {
		BufferStrategy bs = getBufferStrategy();
		if(bs == null) {
			createBufferStrategy(3);
			return;
		}
		
		Graphics ctx = bs.getDrawGraphics();
		ctx.setColor(Color.BLACK);
		ctx.fillRect(-10, -10, getWidth() + 10, getHeight() + 10);
		
		
		if(!options.isOpen()) {
			Map map = world.getSelectedMap();
			if(map.getBackgroundImage() != null) {
				if(!map.getBackgroundImage().isImageLoaded()) {
					map.getBackgroundImage().loadImage();
				}
				ctx.drawImage(map.getBackgroundImage().getImage(), 0, 0, getWidth(), getHeight(), null);
			}
			
			int playerX = getWidth() / 2;
			int playerY = getHeight() / 2;
			
			if(map.getScrollX()) {
				map.setXFormat(player.getX() * -1 + (getWidth() / 2));
			}
			else {
				playerX = player.getX() + (int) ((getWidth() / 2) - (map.getW() / 2));
				map.setXFormat((int) ((getWidth() / 2) - (map.getW() / 2)));
			}
			if(map.getScrollY()) {
				map.setYFormat(player.getY() * -1 + (getHeight() / 2));
			}
			else {
				playerY = player.getY() + (int) ((getHeight() / 2) - (map.getH() / 2));
				map.setYFormat((int) ((getHeight() / 2) - (map.getH() / 2)));
			}
			
			int mapX = map.getXFormat();
			int mapY = map.getYFormat();
			//if(!(loc.getX() + mapX + loc.getW() < -1 || loc.getX() > (int) (WIDTH * SCALE) + 1 || loc.getY() + mapY + loc.getH() < -1 || loc.getY() > (int) (HEIGHT * SCALE) + 1))
			if(!(mapX + map.getW() < -1 || mapX > (int) (WIDTH * SCALE) + 1 || mapY + map.getH() < -1 || mapY > (int) (HEIGHT * SCALE) + 1)) {
				if(drawCollide) {
					if(!map.getCollideMap().isImageLoaded()) {
						map.getCollideMap().loadImage();
					}
					ctx.drawImage(map.getCollideMap().getImage(), mapX, mapY, (int) map.getW(), (int) map.getH(), null);
				}
				else {
					if(!map.getPlayerMap().isImageLoaded()) {
						map.getPlayerMap().loadImage();
					}
					ctx.drawImage(map.getPlayerMap().getImage(), mapX, mapY, (int) map.getW(), (int) map.getH(), null);
				}
			}
			else {
				if(drawCollide) {
					if(map.getCollideMap().isImageLoaded()) {
						map.getCollideMap().unloadImage();
					}
				}
				else {
					if(map.getPlayerMap().isImageLoaded()) {
						map.getPlayerMap().unloadImage();
					}
				}
			}
			
			world.setSelectedMap(map);
			
			Image wall = world.getSelectedMap().getWallImage();
			ctx.setColor(Color.YELLOW);
			for(Location loc : world.getSelectedMap().getAddedLoc()) {
				//if(!(loc.getX() + mapX + loc.getW() < -1 || loc.getX() > (int) (WIDTH * SCALE) + 1 || loc.getY() + mapY + loc.getH() < -1 || loc.getY() > (int) (HEIGHT * SCALE) + 1)) {
					if(wall == null/* || ground.getImage().getWidth() != loc.getW() || ground.getImage().getHeight() != loc.getH()*/) {
						ctx.fillRect(loc.getX() + mapX, loc.getY() + mapY, (int) loc.getW(), (int) loc.getH());
					}
					else {
						ctx.drawImage(wall.getImage(), loc.getX() + mapX, loc.getY() + mapY, (int) loc.getW(), (int) loc.getH(), null);
					}
				//}
			}
			
			Image ground = world.getSelectedMap().getGroundImage();
			ctx.setColor(Color.WHITE);
			for(Location loc : world.getSelectedMap().getBlockedLoc()) {
				//if(!(loc.getX() + mapX + loc.getW() < -1 || loc.getX() > (int) (WIDTH * SCALE) + 1 || loc.getY() + mapY + loc.getH() < -1 || loc.getY() > (int) (HEIGHT * SCALE) + 1)) {
					if(ground == null/* || ground.getImage().getWidth() != loc.getW() || ground.getImage().getHeight() != loc.getH()*/) {
						ctx.fillRect(loc.getX() + mapX, loc.getY() + mapY, (int) loc.getW(), (int) loc.getH());
					}
					else {
						ctx.drawImage(ground.getImage(), loc.getX() + mapX, loc.getY() + mapY, (int) loc.getW(), (int) loc.getH(), null);
					}
				//}
			}
			
			ctx.setColor(Color.RED);
			ctx.drawRect(playerX - (int) (player.getW() / 2), playerY - (int) (player.getH() / 2), (int) player.getW(), (int) player.getH());
			
			ctx = renderHUD(ctx);
			ctx.setColor(Color.BLACK);
			for(Exit exit : world.getSelectedMap().getExits()) {
				Location loc = exit.getLocation();
				if(DEBUG) {
					ctx.fillRect(loc.getX() + mapX, loc.getY() + mapY, (int) loc.getW(), (int) loc.getH());
				}
				if(loc.getX() + loc.getW() > player.getX() && loc.getX() < player.getX() && loc.getY() + loc.getH() > player.getY() && loc.getY() < player.getY()) {
					world.setSelectedMap(exit.getExitMap());
					player.setX(exit.getEnterX());
					player.setY(exit.getEnterY());
					
				}
			}
			ctx.setColor(Color.RED);
			for(ChatTrigger chat : chatMsg) {
				if(chat.getMap() == world.getSelectedMap()) {
					Location loc = new Location(chat.getWorld(), chat.getX(), chat.getY(), chat.getW(), chat.getH());
					if(DEBUG) {
						ctx.fillRect(loc.getX() + mapX, loc.getY() + mapY, (int) loc.getW(), (int) loc.getH());
					}
					if(loc.getX() + loc.getW() > player.getX() && loc.getX() < player.getX() && loc.getY() + loc.getH() > player.getY() && loc.getY() < player.getY() && chat.isOn()) {
						chat.getChat().setOpen(true);
						input.systemUp(false);
						input.systemDown(false);
						input.systemLeft(false);
						input.systemRight(false);
						if(chat.isTurnOff()) {
							chat.setOn(false);
						}
					}
					if(chat.getChat().isOpen()) {
						ctx = chat.getChat().getGraphics(ctx);
						openChat = chat.getChat();
					}
				}
			}
		}
		else {
			ctx.setColor(Color.GRAY);
			ctx.fillRect(0, 0, getWidth(), getHeight());
			ctx = options.getBasicMenu(ctx, 10, 20, 0, 20);
		}
		if(showFPS) {
			ctx.drawString(fps + " fps, " + tps + " tps", 10, 10);
		}
		if(showArrow) {
			ctx.drawImage(upArrow.getImage(), getWidth() - 86, getHeight() - 86, 39, 39, null);
			ctx.drawImage(downArrow.getImage(), getWidth() - 86, getHeight() - 43, 39, 39, null);
			ctx.drawImage(leftArrow.getImage(), getWidth() - 129, getHeight() - 43, 39, 39, null);
			ctx.drawImage(rightArrow.getImage(), getWidth() - 43, getHeight() - 43, 39, 39, null);
		}
		ctx.dispose();
		bs.show();
		
	}
	
	public int stringtoint(String s) {
		int i;
		try {
			i = Integer.parseInt(s);
		}
		catch (NumberFormatException e) {
			i = -256;
		} 
		return i;
	}

}
