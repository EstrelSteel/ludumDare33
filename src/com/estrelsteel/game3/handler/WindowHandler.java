package com.estrelsteel.game3.handler;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

import com.estrelsteel.game3.Game;

public class WindowHandler extends WindowAdapter implements WindowFocusListener, FocusListener  {
	Game game;
	
	public WindowHandler(Game game) {
		this.game = game;
	}
	
    public void windowClosing(WindowEvent e) {
    	game.stop();
    	//game.music.stopPlaying();
    	System.exit(0);
    	e.getWindow().dispose();
    	
    }

	@Override
	public void windowGainedFocus(WindowEvent e) {
		
	}

	@Override
	public void windowLostFocus(WindowEvent e) {
		if(game.pauseClose) {
			game.options.setOpen(true);
		}
	}

	@Override
	public void focusGained(FocusEvent e) {
		
	}

	@Override
	public void focusLost(FocusEvent e) {
		if(game.pauseClose) {
			game.options.setOpen(true);
		}
	}
}
