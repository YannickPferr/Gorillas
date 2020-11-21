package de.tu_darmstadt.gdi1.gorillas.ui.objects;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import org.newdawn.slick.geom.Vector2f;

import de.tu_darmstadt.gdi1.gorillas.main.Gorillas;

/**
 * 
 * @author Yannick Pferr, Ludwig Koch, Kevin Rueckert
 *
 */

public class Skyline {

	private ArrayList<House> l;
	private ArrayList<House> random;
	private BufferedImage skyline;

	public Skyline(House h1, House h2, House h3, House h4, House h5, House h6,
			House h7, House h8) {
		l = new ArrayList<House>();
		skyline = new BufferedImage(Gorillas.FRAME_WIDTH,
				Gorillas.FRAME_HEIGHT, BufferedImage.TYPE_INT_ARGB);
		l.add(h1);
		l.add(h2);
		l.add(h3);
		l.add(h4);
		l.add(h5);
		l.add(h6);
		l.add(h7);
		l.add(h8);
		random = new ArrayList<House>();
		setRandomMap();

	}

	/**
	 * Returns a random number between the entered minimum and maximum
	 * 
	 * @param low
	 *            should be the minimum
	 * @param high
	 *            should be 1 time bigger than the maximum
	 * @returns random number
	 */
	public static int myRandom(int low, int high) {
		return (int) (Math.random() * (high - low) + low);
	}

	/**
	 * Creates a random map out of the list with all houses and puts them into
	 * one buffered image
	 * 
	 */
	private void setRandomMap() {

		int size = l.size();

		for (int i = 0; i < size; i++) {

			int random1 = myRandom(0, l.size());
			House house = l.get(random1);

			skyline.createGraphics().drawImage(house.getImage(),
					(int) (i * house.getSize().x),
					(int) (Gorillas.FRAME_HEIGHT - house.getSize().y), null);

			/**
			 * x and y coordinates are also saved here because they are needed
			 * later in the game for example when putting the gorillas on the
			 * right house
			 */
			float x = house.getSize().x / 2 + i * house.getSize().x;
			float y = Gorillas.FRAME_HEIGHT - house.getSize().y / 2;
			house.setPosition(new Vector2f(x, y));

			random.add(house);
			l.remove(random1);
		}
	}

	/**
	 * Returns an arraylist with all houses in random order
	 * 
	 * @returns ArrayList<House> random
	 */
	public ArrayList<House> getRandomSkyline() {
		return random;
	}

	/**
	 * Returns the bufferedimage of the random skyline
	 * 
	 * @returns BufferedImage skyline
	 */
	public BufferedImage getSky() {
		return skyline;
	}

	/**
	 * Creates an arraylist with the first three houses of the random skyline
	 * 
	 * @returns ArrayList<House> first3 of the first three houses
	 */
	public ArrayList<House> getFirst3() {
		ArrayList<House> first3 = new ArrayList<House>();
		for (int i = 0; i < 3; i++) {
			first3.add(random.get(i));
		}
		return first3;
	}

	/**
	 * Creates an arraylist with the last three houses of the random skyline
	 * 
	 * @returns ArrayList<House> last3 of the last three houses
	 */
	public ArrayList<House> getLast3() {
		ArrayList<House> last3 = new ArrayList<House>();
		for (int i = 5; i < random.size(); i++) {
			last3.add(random.get(i));
		}
		return last3;
	}
}
