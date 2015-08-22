package com.estrelsteel.game3.block;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class Image {
	BufferedImage img;
	String src;
	
	public Image(String src) {
		this.src = src;
	}
	
	public BufferedImage getImage() {
		return this.img;
	}
	
	public String getSRC() {
		return this.src;
	}
	
	public void loadImage() {
		InputStream is = getClass().getResourceAsStream(src);
		try {
			this.img = ImageIO.read(is);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return;
	}
	
	public boolean isImageLoaded() {
		if(img != null) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public void unloadImage() {
		this.img = null;
		return;
	}
	
	public void setSRC(String src) {
		this.src = src;
		return;
	}
	
	public void setImage(BufferedImage img) {
		this.img = img;
		return;
	}
}
