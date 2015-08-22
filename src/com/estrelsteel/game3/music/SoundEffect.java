package com.estrelsteel.game3.music;

import java.io.IOException;
import java.io.InputStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public enum SoundEffect {
	TEST_WAV("/com/estrelsteel/game3/res/music/testSound.wav");
	
	public static Volume vol = Volume.LOW;
	
	private Clip clip;
	
	SoundEffect(String soundName) {
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
	
	public void play() {
		if(vol != Volume.MUTE) {
			if(clip.isRunning()) {
				clip.stop();
			}
			clip.setFramePosition(0);
			clip.start();
		}
	}
	
	public void stopPlaying() {
		if(clip.isRunning()) {
			clip.stop();
		}
	}
}
