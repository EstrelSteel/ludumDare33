package com.estrelsteel.game3.music;

import java.io.IOException;
import java.io.InputStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public enum Music {
	TEST_WAV("/com/estrelsteel/game3/res/music/testSound.wav");
	
	public Volume vol = Volume.LOW;
	
	private Clip clip;
	private boolean toPlay = false;
	
	Music(String soundName) {
		try {
			InputStream is = this.getClass().getResourceAsStream(soundName);
			//File file = new File(soundName);
			AudioInputStream audioIS = AudioSystem.getAudioInputStream(is);
			clip = AudioSystem.getClip();
			clip.open(audioIS);
		}
		catch(UnsupportedAudioFileException e) {
			e.printStackTrace();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		catch(LineUnavailableException e) {
			e.printStackTrace();
		}
	}
	
	public boolean getToPlay() {
		return toPlay;
	}
	
	public boolean isPlaying() {
		return clip.isRunning();
	}
	
	public void play() {
		if(vol != Volume.MUTE) {
			toPlay = false;
			if(clip.isRunning()) {
				clip.stop();
			}
			clip.setFramePosition(0);
			clip.loop(Clip.LOOP_CONTINUOUSLY);
		}
		else {
			toPlay = true;
		}
	}
	
	public void play(int loop) {
		if(vol != Volume.MUTE) {
			toPlay = false;
			if(clip.isRunning()) {
				clip.stop();
			}
			clip.setFramePosition(0);
			if(loop < 0) {
				loop = 0;
			}
			clip.loop(loop);
		}

		else {
			toPlay = true;
		}
	}
	
	public void stopPlaying() {
		toPlay = false;
		if(clip.isRunning()) {
			clip.stop();
		}
	}
}
