package com.estrelsteel.game3.music;

import java.io.BufferedInputStream;
import java.io.InputStream;

import javazoom.jl.player.Player;

/*
 * Taken and Modified from: http://introcs.cs.princeton.edu/java/faq/mp3/mp3.html
 * 
 */
public class MP3 {
	    private String filename;
	    public Player player; 
	    private boolean playing = false;
	    // constructor that takes the name of an MP3 file
	    public MP3(String filename) {
	        this.filename = filename;
	    }

	    public void close() {
	    	if (player != null){ 
	    		playing = false;
	    		player.close();
	    	}
	    }

	    // play the MP3 file to the sound card
	    public void play() {
	        try {
	        	InputStream is = this.getClass().getResourceAsStream(filename);
	            //FileInputStream fis = new FileInputStream(filename);
	            BufferedInputStream bis = new BufferedInputStream(is);
	            player = new Player(bis);
	            playing = true;
	        }
	        catch (Exception e) {
	            System.out.println("Problem playing file " + filename);
	            System.out.println(e);
	        }

	        // run in new thread to play in background
	        new Thread() {
	            public void run() {
	                try {
	                	player.play(); 
	                	playing = true;
	                }
	                catch (Exception e) {
	                	System.out.println(e);
	                }
	            }
	        }.start();

	    }
	    
	    public boolean isPlaying() {
	    	return playing;
	    }
}
