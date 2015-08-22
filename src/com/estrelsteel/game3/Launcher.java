package com.estrelsteel.game3;

import java.applet.Applet;
import java.awt.BorderLayout;

import javax.swing.JFrame;

import com.estrelsteel.game3.handler.InputHandler;
import com.estrelsteel.game3.handler.MouseHandler;
import com.estrelsteel.game3.handler.WindowHandler;
import com.estrelsteel.game3.menu.MenuItems;

@SuppressWarnings("serial")
public class Launcher extends Applet {
	private static Game game = new Game();
	
	@Override
	public void init() {
		setLayout(new BorderLayout());
		add(game, BorderLayout.CENTER);
		setFocusable(true);
		setMinimumSize(Game.dimension);
		setMaximumSize(Game.dimension);
		setPreferredSize(Game.dimension);
		setVisible(true);
		game.input = new InputHandler(game);
		game.mouse = new MouseHandler(game);
		game.window = new WindowHandler(game);
		addKeyListener(game.input);
		addMouseListener(game.mouse);
		addMouseMotionListener(game.mouse);
		addFocusListener(game.window);
		game.applet = true;
		game.showArrow = true;
		for(MenuItems item : game.options.getMenuItems()) {
			if(item == MenuItems.SCREEN_ARROWS) {
				item.setActive(true);
			}
		}
	}
	
	@Override
	public void start() {
		game.start();
		return;
	}
	
	@Override
	public void stop() {
		game.stop();
		return;
	}
	
	public static void main(String[] args) {
		game.window = new WindowHandler(game);
		
		game.setFocusable(true);
		game.setMinimumSize(Game.dimension);
		game.setMaximumSize(Game.dimension);
		game.setPreferredSize(Game.dimension);
		
		JFrame frame = new JFrame(game.title + " " + game.version);
		
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.addWindowListener(game.window);
		frame.addWindowFocusListener(game.window);
		frame.setLayout(new BorderLayout());
		
		frame.add(game, BorderLayout.CENTER);
		
		
		
		frame.setSize(WIDTH, HEIGHT);
		frame.pack();
		
		frame.setResizable(true);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		game.start();
		
	}
}
