package com.estrelsteel.game3;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
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
import com.estrelsteel.game3.music.MP3;
import com.estrelsteel.game3.music.Music;
import com.estrelsteel.game3.music.SoundEffect;
import com.estrelsteel.game3.world.World;
import com.estrelsteel.game3.character.NPC;
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
	public boolean tempHideArrow = false;
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
	
	public String title = "Truth";
	public String version = "0.1a";
	public int clientBuild = 1;
	
	public static final double SCALE = 1.625;
	public static int WIDTH = 400;
	public static int HEIGHT = 400;
	public static Dimension dimension = new Dimension((int) (WIDTH * Game.SCALE), (int) (HEIGHT * Game.SCALE));
	
	public Image upperMenu = new Image("/com/estrelsteel/game3/res/hud/hud.png");
	
	public int errorY = 2;
	public Error error = Error.NULL;
	public int errorClick = 0;
	public boolean tutorial = true;
	public boolean errorActive = true;
	
	public Image shadow = new Image("/com/estrelsteel/game3/res/misc/shadow.png");
	
	public Image playerStop1 = new Image("/com/estrelsteel/game3/res/player/player_stop_1.png");
	public Image playerStop2 = new Image("/com/estrelsteel/game3/res/player/player_stop_2.png");
	public int playerStop = 0;
	
	public Image npcImageGreen = new Image("/com/estrelsteel/game3/res/player/person2_20x50_mod.png");
	public Image npcImageBrown = new Image("/com/estrelsteel/game3/res/player/person2_20x50_mod_brown.png");
	public Image npcImageBlue = new Image("/com/estrelsteel/game3/res/player/person2_20x50_mod_blue.png");
	
	public Image upArrow = new Image("/com/estrelsteel/game3/res/hud/up_arrow.png");
	public Image downArrow = new Image("/com/estrelsteel/game3/res/hud/down_arrow.png");
	public Image rightArrow = new Image("/com/estrelsteel/game3/res/hud/right_arrow.png");
	public Image leftArrow = new Image("/com/estrelsteel/game3/res/hud/left_arrow.png");
	
	public World world = new World(this);
	
	public Image map1Collide = new Image("/com/estrelsteel/game3/res/map/m1-0-c.png");
	public Image map1Image = new Image("/com/estrelsteel/game3/res/map/m1-0-r.png");;
	public Map map1 = new Map(this, map1Collide, map1Image, "map1", true, true, 250, 500, 1500, 1500, world);
	public Image map2Collide = new Image("/com/estrelsteel/game3/res/map/m1-1-c.png");
	public Image map2Image = new Image("/com/estrelsteel/game3/res/map/m1-1-r.png");;
	public Map map2 = new Map(this, map2Collide, map2Image, "map2", true, true, 500, 550, 1500, 1500, world);
	
	public Music music;
	public MP3 mp3 = new MP3("/com/estrelsteel/game3/res/music/LD33.mp3");
	public long musicCheck = -256;
	
	public Player player = new Player(this, world, 5, 5, 22.0, 52.0, 5);
	public HorizontalMeter healthMeter = new HorizontalMeter(true, player.getMaxHealth(), player.getMinHealth(), player.getHealth(), Color.GREEN, 49, 19, 102, 22, DEBUG);
	public HorizontalMeter materialMeter = new HorizontalMeter(true, player.getEarth().getMaxMaterial(), player.getEarth().getMaterialValue(), player.getEarth().getMinMaterial(), Color.GREEN, 49, 49, 102, 22, DEBUG);
	
	private Thread thread; 
	
	public ArrayList<ChatTrigger> chatMsg = new ArrayList<ChatTrigger>();
	public ChatBox openChat;
	
	public boolean bug1 = false;
	public boolean bug2 = false;
	public boolean bug3 = false;
	public boolean bug4 = false;
	public int ghostX = 0;
	public int ghostY = 0;
	public long bug4Start = -256;
	
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
		
		//MAP 1-0
		NPC npc = new NPC(npcImageGreen, world, 500, 1000, 30, 75, 0, "PERSON");
		map1.addNPC(npc);
		npc = new NPC(npcImageBlue, world, 375, 500, 30, 75, 0, "PERSON");
		map1.addNPC(npc);
		npc = new NPC(npcImageBlue, world, 250, 750, 30, 75, 0, "PERSON");
		map1.addNPC(npc);
		npc = new NPC(npcImageBrown, world, 280, 750, 30, 75, 0, "PERSON");
		map1.addNPC(npc);
		npc = new NPC(npcImageGreen, world, 800, 750, 30, 75, 0, "PERSON");
		map1.addNPC(npc);
		npc = new NPC(npcImageBlue, world, 1000, 750, 30, 75, 0, "PERSON");
		map1.addNPC(npc);
		
		ChatBox chat = new ChatBox("PLEASE...", 20, 80, 40, 60);
		chatMsg.add(new ChatTrigger(chat, map1, 250, 750, 30, 75));
		chat = new ChatBox("STOP...", 20, 80, 40, 60);
		chatMsg.add(new ChatTrigger(chat, map1, 280, 750, 30, 75));
		chat = new ChatBox("BEGONE...", 20, 80, 40, 60);
		chatMsg.add(new ChatTrigger(chat, map1, 800, 750, 30, 75));
		chat = new ChatBox("MONSTER...", 20, 80, 40, 60);
		chatMsg.add(new ChatTrigger(chat, map1, 1000, 750, 30, 75));
		
		//MAP 1-1
		//ROW 1
		npc = new NPC(npcImageBrown, world, 625, 500, 30, 75, 0, "PERSON");
		map2.addNPC(npc);
		npc = new NPC(npcImageBlue, world, 670, 500, 30, 75, 0, "PERSON");
		map2.addNPC(npc);
		npc = new NPC(npcImageGreen, world, 715, 500, 30, 75, 0, "PERSON");
		map2.addNPC(npc);
		npc = new NPC(npcImageBrown, world, 760, 500, 30, 75, 0, "PERSON");
		map2.addNPC(npc);
		npc = new NPC(npcImageBrown, world, 805, 500, 30, 75, 0, "PERSON");
		map2.addNPC(npc);
		npc = new NPC(npcImageBlue, world, 850, 500, 30, 75, 0, "PERSON");
		map2.addNPC(npc);
		npc = new NPC(npcImageGreen, world, 895, 500, 30, 75, 0, "PERSON");
		map2.addNPC(npc);
		npc = new NPC(npcImageBrown, world, 940, 500, 30, 75, 0, "PERSON");
		map2.addNPC(npc);
		//ROW 2
		npc = new NPC(npcImageBrown, world, 625, 600, 30, 75, 0, "PERSON");
		map2.addNPC(npc);
		npc = new NPC(npcImageBrown, world, 670, 600, 30, 75, 0, "PERSON");
		map2.addNPC(npc);
		npc = new NPC(npcImageBrown, world, 715, 600, 30, 75, 0, "PERSON");
		map2.addNPC(npc);
		npc = new NPC(npcImageBlue, world, 760, 600, 30, 75, 0, "PERSON");
		map2.addNPC(npc);
		npc = new NPC(npcImageGreen, world, 805, 600, 30, 75, 0, "PERSON");
		map2.addNPC(npc);
		npc = new NPC(npcImageBlue, world, 850, 600, 30, 75, 0, "PERSON");
		map2.addNPC(npc);
		npc = new NPC(npcImageGreen, world, 895, 600, 30, 75, 0, "PERSON");
		map2.addNPC(npc);
		npc = new NPC(npcImageBrown, world, 940, 600, 30, 75, 0, "PERSON");
		map2.addNPC(npc);
		//ROW 3
		npc = new NPC(npcImageBlue, world, 625, 700, 30, 75, 0, "PERSON");
		map2.addNPC(npc);
		npc = new NPC(npcImageGreen, world, 670, 700, 30, 75, 0, "PERSON");
		map2.addNPC(npc);
		npc = new NPC(npcImageGreen, world, 715, 700, 30, 75, 0, "PERSON");
		map2.addNPC(npc);
		npc = new NPC(npcImageBrown, world, 760, 700, 30, 75, 0, "PERSON");
		map2.addNPC(npc);
		npc = new NPC(npcImageBlue, world, 805, 700, 30, 75, 0, "PERSON");
		map2.addNPC(npc);
		npc = new NPC(npcImageBrown, world, 850, 700, 30, 75, 0, "PERSON");
		map2.addNPC(npc);
		npc = new NPC(npcImageGreen, world, 895, 700, 30, 75, 0, "PERSON");
		map2.addNPC(npc);
		npc = new NPC(npcImageBlue, world, 940, 700, 30, 75, 0, "PERSON");
		map2.addNPC(npc);
		
		
		chat = new ChatBox("X...", 20, 80, 40, 60);
		chatMsg.add(new ChatTrigger(chat, map2, 625, 500, 30, 75));
		chat = new ChatBox("CLOSE...", 20, 80, 40, 60);
		chatMsg.add(new ChatTrigger(chat, map2, 670, 500, 30, 75));
		chat = new ChatBox("END...", 20, 80, 40, 60);
		chatMsg.add(new ChatTrigger(chat, map2, 715, 500, 30, 75));
		chat = new ChatBox("PLEASE...", 20, 80, 40, 60);
		chatMsg.add(new ChatTrigger(chat, map2, 760, 500, 30, 75));
		chat = new ChatBox("LEAVE US ALONE...", 20, 80, 40, 60);
		chatMsg.add(new ChatTrigger(chat, map2, 805, 500, 30, 75));
		chat = new ChatBox("NOW GO...", 20, 80, 40, 60);
		chatMsg.add(new ChatTrigger(chat, map2, 850, 500, 30, 75));
		
		ChatBox chatExtra1 = new ChatBox("GHOST", "I WILL CEASE TO BE BOUND BY YOUR INPUTS...", 20, 80, 40, 60);
		ChatBox chatExtra2 = new ChatBox(chatExtra1, "GHOST", "WHY MUST YOU MAKE ME DO THIS...", 20, 80, 40, 60);
		ChatBox chatExtra3 = new ChatBox(chatExtra2, "GHOST", "MONSTER...", 20, 80, 40, 60);
		
		chat = new ChatBox(chatExtra3, "GHOST", "YOU...", 20, 80, 40, 60);
		chatMsg.add(new ChatTrigger(chat, map2, 940, 500, 30, 75));
		
		chat = new ChatBox("No, I should stay.", 20, 80, 40, 60);
		ChatTrigger exitMap2 = new ChatTrigger(chat, map2, 400, 350, 20, 750);
		exitMap2.setTurnOff(false);
		//chatMsg.add(exitMap2);
		exitMap2 = new ChatTrigger(chat, map2, 1150, 350, 20, 750);
		exitMap2.setTurnOff(false);
		chatMsg.add(exitMap2);
		exitMap2 = new ChatTrigger(chat, map2, 400, 350, 750, 20);
		exitMap2.setTurnOff(false);
		chatMsg.add(exitMap2);
		exitMap2 = new ChatTrigger(chat, map2, 400, 1100, 750, 20);
		exitMap2.setTurnOff(false);
		chatMsg.add(exitMap2);
		
		Image ground = new Image("/com/estrelsteel/game3/res/map/background.png");
		Image wall = new Image("/com/estrelsteel/game3/res/map/background.png");
		Image background = new Image("/com/estrelsteel/game3/res/map/background.png");
		map1.addExits(new Exit(map2, new Location(world, 1180, 100, 300, 1100)));
		map1.setBackgroundImage(background);
		map1.setGroundImage(ground);
		map1.setWallImage(wall);
		world.addMap(map1);
		
		map2.setBackgroundImage(background);
		map2.setGroundImage(ground);
		map2.setWallImage(wall);
		map2.addExits(new Exit(map1, new Location(world, 400, 350, 20, 750), 1000, 500));
		map2.addExits(new Exit(map2, new Location(world, 1150, 350, 20, 750)));
		
		map2.addExits(new Exit(map2, new Location(world, 400, 350, 750, 20)));
		map2.addExits(new Exit(map2, new Location(world, 400, 1100, 750, 20)));
		world.addMap(map2);
		
		world.setSelected(0);
		world.addCharacter(player);
		playerStop1.loadImage();
		playerStop2.loadImage();
		shadow.loadImage();
		npcImageGreen.loadImage();
		npcImageBrown.loadImage();
		npcImageBlue.loadImage();
		
		world.resetPos();
		world.getSelectedMap().getPlayerMap().loadImage();
		upperMenu.loadImage();
		
		error = Error.UNKOWN;
		errorActive = true;
		
		mp3.play();
		musicCheck = System.currentTimeMillis();
		//music = Music.THEME_WAV;
		//music.vol = Volume.LOW;
		//music.play();
		
		upArrow.loadImage();
		downArrow.loadImage();
		rightArrow.loadImage();
		leftArrow.loadImage();
		
		/*
		chat = new ChatBox("Hello, World! 123", 20, 80, 40, 60);
		chat.setOpen(false);
		ChatTrigger trigChat = new ChatTrigger(chat, map1, 240, 240, 20, 20);
		chatMsg.add(trigChat);
		*/
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
	
	@SuppressWarnings("unused")
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
		ctx.drawImage(upperMenu.getImage(), 0, 0, 168, 71, null);
		Location loc = healthMeter.getFillArea();
		ctx.setColor(healthMeter.getColour());
		ctx.fillRect(loc.getX(), loc.getY(), (int) loc.getW(), (int) loc.getH());
		ctx.setColor(Color.BLACK);
		ctx.drawString("" + (int) player.getHealth() + "/" + (int) player.getMaxHealth(), 50, 35);
		/*
		loc = materialMeter.getFillArea();
		ctx.setColor(materialMeter.getColour());
		ctx.fillRect(loc.getX(), loc.getY(), (int) loc.getW(), (int) loc.getH());
		ctx.setColor(Color.BLACK);
		ctx.drawString("" + (int) player.getEarth().getMaterialValue() + "/" + (int) player.getEarth().getMaxMaterial(), 50, 65);
		*/
		//ctx = renderERROR(ctx);
		return ctx;
	}
	
	public void checkMenuSettings() {
		for(MenuItems item : options.getMenuItems()) {
			if(item.isActive()) {
				if(item == MenuItems.MUSIC_VOL && !mp3.isPlaying()) {
					mp3.play();
				}
				if(item == MenuItems.SCREEN_ARROWS && !showArrow) {
					showArrow = true;
				}
				if(item == MenuItems.PAUSE_CLOSE && !pauseClose) {
					pauseClose = true;
				}
			}
			else {
				if(item == MenuItems.MUSIC_VOL && mp3 != null && mp3.isPlaying()) {
					mp3.close();
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
		if(musicCheck == -256 || System.currentTimeMillis() - musicCheck > 4300) {
			musicCheck = System.currentTimeMillis();
			if(mp3.player.isComplete() && options.getMenuItems().get(options.getMenuItems().indexOf(MenuItems.MUSIC_VOL)).isActive()) {
				mp3.play();
			}
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
			player.setXFormat(playerX);
			player.setYFormat(playerY);
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
			
			ctx.drawImage(shadow.getImage(), playerX - (int) (player.getW() / 2), playerY - 9, (int) player.getW(), (int) 14, null);
			
			for(NPC npc : world.getSelectedMap().getNPCs()) {
				if(npc.getHealth() > npc.getMinHealth()) {
					if(npc.getX() + npc.getW() > player.getX() && npc.getX() < player.getX() && npc.getY() + npc.getH() > player.getY() && npc.getY() < player.getY()) {
						npc.setHealth(0.0);
						player.setHealth(player.getHealth() - 1);
						if(options.getMenuItems().get(options.getMenuItems().indexOf(MenuItems.SFX_VOL)).isActive()) {
							SoundEffect.KILL_WAV.play();
						}
					}
					if(npc.hasShadow()) {
						ctx.drawImage(shadow.getImage(), npc.getX() + mapX, (int) (npc.getY() + npc.getH()) - 9 + mapY, (int) npc.getW(), (int) 14, null);
					}
					ctx.drawImage(npc.getImage().getImage(), npc.getX() + mapX, npc.getY() + mapY, (int) npc.getW(), (int) npc.getH(), null);
				}
			}
			
			if(playerStop <= 15) {
				playerStop = playerStop + 1;
				ctx.drawImage(playerStop1.getImage(), playerX - (int) (player.getW() / 2), playerY - (int) (player.getH()), (int) player.getW(), (int) player.getH(), null);
			}
			else {
				playerStop = playerStop + 1;
				if(playerStop > 31) {
					playerStop = 0;
				}
				ctx.drawImage(playerStop2.getImage(), playerX - (int) (player.getW() / 2), playerY - (int) (player.getH()), (int) player.getW(), (int) player.getH(), null);
			}
			ctx = renderHUD(ctx);
			ctx.setColor(Color.RED);
			for(ChatTrigger chat : chatMsg) {
				if(chat.getMap() == world.getSelectedMap()) {
					Location loc = new Location(chat.getWorld(), chat.getX(), chat.getY(), chat.getW(), chat.getH());
					if(DEBUG) {
						ctx.fillRect(loc.getX() + mapX, loc.getY() + mapY, (int) loc.getW(), (int) loc.getH());
					}
					if(loc.getX() + loc.getW() > player.getX() && loc.getX() < player.getX() && loc.getY() + loc.getH() > player.getY() && loc.getY() < player.getY() && chat.isOn()) {
						chat.getChat().setOpen(true);
						openChat = chat.getChat();
						input.systemUp(false);
						input.systemDown(false);
						input.systemLeft(false);
						input.systemRight(false);
						if(chat.isTurnOff()) {
							chat.setOn(false);
						}
					}
					if(chat.getChat().getText() == "CLOSE..." && chat.getChat().isOpen()) {
						bug1 = true;
					}
					else if(chat.getChat().getText() == "LEAVE US ALONE..." && chat.getChat().isOpen()) {
						bug2 = true;
					}
					else if(chat.getChat().getText() == "NOW GO..." && chat.getChat().isOpen()) {
						bug3 = true;
					}
				}
			}
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
					if(options.getMenuItems().get(options.getMenuItems().indexOf(MenuItems.SFX_VOL)).isActive()) {
						SoundEffect.EXIT_WAV.play();
					}
				}
			}
			
			if(openChat != null && openChat.isOpen()) {
				tempHideArrow = true;
				ctx = openChat.getGraphics(ctx);
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
		if(bug3) {
			ctx.setColor(Color.CYAN);
			if(fps % 2 == 0) {
				ctx.fillRect(getWidth() / 2 + 50, 0, 102, 430);
				ctx.setColor(Color.ORANGE);
			}
			else {
				ctx.drawRect(getWidth() / 2 + 50, 0, 102, 430);
				ctx.setColor(Color.MAGENTA);
			}
		}
		if(bug1) {
			ctx.fillRect(0, getHeight() - 50, 30, 50);
		}
		if(bug2) {
			ctx.setColor(Color.GREEN);
			ctx.fillRect(getWidth() - 50, getHeight() / 2, 81, 33);
		}
		if(showArrow && !tempHideArrow) {
			ctx.drawImage(upArrow.getImage(), getWidth() - 86, getHeight() - 86, 39, 39, null);
			ctx.drawImage(downArrow.getImage(), getWidth() - 86, getHeight() - 43, 39, 39, null);
			ctx.drawImage(leftArrow.getImage(), getWidth() - 129, getHeight() - 43, 39, 39, null);
			ctx.drawImage(rightArrow.getImage(), getWidth() - 43, getHeight() - 43, 39, 39, null);
		}
		if(bug4) {
			if(bug4Start == -256){
				bug4Start = System.currentTimeMillis();
			}
			ctx.setColor(Color.BLACK);
			ctx.fillRect(-10, -10, getWidth() + 10, getHeight() + 10);
			ctx.setColor(Color.WHITE);
			ctx.setFont(new Font("Garamond", Font.BOLD, 32));
			ctx.drawString("The End", getWidth() + (getWidth() / 2) + ghostX + 200, (getHeight() / 3) + ghostY);
			ctx.setFont(new Font("Garamond", Font.PLAIN, 24));
			ctx.drawString("By: EstrelSteel", getWidth() + (getWidth() / 2) + ghostX + 200, (getHeight() / 3) + ghostY + 42);
			ctx.drawString("Thank you for playing!", getWidth() + (getWidth() / 2) + ghostX + 200, (getHeight() / 3) + ghostY + 76);
			ctx.drawString("Ludum Dare 33", getWidth() + (getWidth() / 2) + ghostX + 200, getHeight() - (getHeight() / 3) + ghostY + 86);
			ctx.drawString("Theme: You are the Monster", getWidth() + (getWidth() / 2) + ghostX + 200, getHeight() - (getHeight() / 3) + ghostY + 120);
			ctx.drawImage(shadow.getImage(), player.getXFormat() - (int) (player.getW() / 2) + ghostX, player.getYFormat() - 9 + ghostY, (int) player.getW(), (int) 14, null);
			
			if(System.currentTimeMillis() - bug4Start > 500 && System.currentTimeMillis() - bug4Start < 1000) {
				ctx.drawImage(playerStop1.getImage(), player.getXFormat() - (int) (player.getW() / 2) + ghostX, player.getYFormat() - (int) (player.getH()) + ghostY, (int) player.getW(), (int) player.getH(), null);
			}
			else {
				ctx.drawImage(playerStop2.getImage(), player.getXFormat() - (int) (player.getW() / 2) + ghostX, player.getYFormat() - (int) (player.getH()) + ghostY, (int) player.getW(), (int) player.getH(), null);
			}
			if(ghostX > -1 * getWidth() - 400 && System.currentTimeMillis() - bug4Start > 1500) {
				ghostX = ghostX - 5;
			}
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
