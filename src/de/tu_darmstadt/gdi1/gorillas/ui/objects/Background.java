package de.tu_darmstadt.gdi1.gorillas.ui.objects;

import java.util.ArrayList;

import de.tu_darmstadt.gdi1.gorillas.game.Game;
import de.tu_darmstadt.gdi1.gorillas.ui.states.OptionsState;

/**
 * 
 * @author Yannick Pferr, Ludwig Koch, Kevin Rueckert
 *
 */

public class Background {

	private ArrayList<String> l;
	private String random;
	public static boolean moon;

	public Background(String b1, String b2, String b3, String b4) {
		l = new ArrayList<String>();
		l.add(b1);
		l.add(b2);
		l.add(b3);
		l.add(b4);
		setRandomBG();
		moon();
	}

	/**
	 * Chooses a random background and sets the variable moon true if the random
	 * background is the moon map
	 * 
	 */
	private void setRandomBG() {

		int random1 = Skyline.myRandom(0, l.size());
		random = l.get(random1);
		if (random1 == 3)
			moon = true;
		else
			moon = false;
	}

	/**
	 * Changes the gravity and wind speed automatically if the random background
	 * is the moon background also checks if gravity and wind are manually set
	 * else sets the standard value
	 * 
	 */
	private void moon() {
		if (moon) {
			Game.gravity = 1.6f;
			Game.wind = 0;
		} else {
			if (!OptionsState.gravitySet)
				Game.gravity = 9.81f;
			if (OptionsState.windSet)
				Game.wind = Skyline.myRandom(-15, 16);
			if (!OptionsState.windSet)
				Game.wind = 0;
		}
	}

	/**
	 * Returns a string of the random background
	 * 
	 * @returns String random
	 */
	public String getRandomBG() {
		return random;
	}
}
