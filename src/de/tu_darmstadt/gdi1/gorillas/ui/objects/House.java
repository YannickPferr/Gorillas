package de.tu_darmstadt.gdi1.gorillas.ui.objects;

import java.awt.image.BufferedImage;

import eea.engine.entity.DestructibleImageEntity;

/**
 * 
 * @author Yannick Pferr, Ludwig Koch, Kevin Rueckert
 *
 */

public class House extends DestructibleImageEntity {

	private BufferedImage img;

	public House(String entityID, BufferedImage image) {
		super(entityID, image, "game/destruction.png", false);
		img = image;
	}

	/**
	 * Returns the BufferedImage used to create the house
	 * 
	 * @returns BufferedImage img
	 */
	public BufferedImage getImage() {
		return img;
	}

}
