package de.tu_darmstadt.gdi1.gorillas.game;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;

import de.tu_darmstadt.gdi1.gorillas.ui.states.GameSetupState;

/**
 * 
 * @author Yannick Pferr, Ludwig Koch, Kevin Rueckert
 *
 */
public class Score {
	public static int left = 0;
	public static int right = 0;
	public static int thrown1 = 0;
	public static int thrown2 = 0;
	ArrayList<String> hsc = new ArrayList<String>();
	public int bananasThrownP1;
	public int bananasHitP1;
	public int roundsWonP1;
	public int roundsPlayedP1;
	public int bananasThrownP2;
	public int bananasHitP2;
	public int roundsWonP2;
	public int roundsPlayedP2;

	/**
	 * reads the "savedScores.hsc" file and looks up if one of the players is
	 * already in the highscore-list
	 */
	public void readHighscore() {
		String line = null;
		String[] s = null;
		try {
			// reads the file "savedScores.hsc"
			FileInputStream fis = new FileInputStream(new File(
					"assets/game/savedScores.hsc"));
			BufferedReader bf = new BufferedReader(new InputStreamReader(fis));
			while ((line = bf.readLine()) != null) {
				// splits the lines between every ", " and pastes them into an
				// array
				s = line.split(", ");

				// if one of the players already is a member of the
				// highscore-list reads out his stats
				if (s[0].equals(GameSetupState.player1)) {
					roundsPlayedP1 = Integer.parseInt(s[1]);
					roundsWonP1 = Integer.parseInt(s[2]);
					bananasThrownP1 = Integer.parseInt(s[5]);
					bananasHitP1 = Integer.parseInt(s[6]);
				}
				if (s[0].equals(GameSetupState.player2)) {
					roundsPlayedP2 = Integer.parseInt(s[1]);
					roundsWonP2 = Integer.parseInt(s[2]);
					bananasThrownP2 = Integer.parseInt(s[5]);
					bananasHitP2 = Integer.parseInt(s[6]);

				}
				// add all of the rest entrys of the file who are not player1 or
				// two
				// to the ArrayList
				if (line.length() > 0 && !s[0].equals(GameSetupState.player1)
						&& !s[0].equals(GameSetupState.player2)) {
					hsc.add(line);
				}
			}

			bf.close();
			fis.close();
		} catch (IOException exception) {
			System.out.println("ERROR! Could not read file");
			exception.printStackTrace();
		}
	}

	/**
	 * writes the new highscore-list into the savedScores.hsc
	 * 
	 * @param entry
	 *            - an entry of the highscore-list
	 */
	private void writeHighscore(ArrayList<String> entry) {

		try {
			// writes the file "savedScores.hsc"
			BufferedWriter b = new BufferedWriter(new FileWriter(
					"assets/game/savedScores.hsc"));

			for (int i = 0; i < entry.size(); i++) {
				b.write(entry.get(i));
				b.newLine();
			}
			b.close();
		} catch (IOException e) {
			System.out.println("Fehler: " + e.toString());
		}
	}

	/**
	 * generates a new highscore entry
	 * 
	 * @param name
	 *            - the name of the player
	 * @param numberOfRounds
	 *            - the total number played by this player
	 * @param roundsWon
	 *            - the number of winning rounds of this player
	 * @param bananasThrown
	 *            - the number how often the banana was thrown
	 * @param bananasHit
	 *            - the number how often the banana hits an gorilla
	 */
	public void addHighscore(String name, int numberOfRounds, int roundsWon,
			int bananasThrown, int bananasHit) {

		StringBuilder sb = new StringBuilder(1000);
		sb.append(name + ", ");
		sb.append(numberOfRounds + ", ");
		sb.append(roundsWon + ", ");
		sb.append((float) roundsWon / numberOfRounds + ", ");
		if (bananasThrown > 0)
			sb.append((float) bananasHit / (float)bananasThrown + ", ");
		else
			sb.append(0.0 + ", ");
		sb.append(bananasThrown + ", ");
		sb.append(bananasHit);
		hsc.add(sb.toString());
		if (hsc.size() > 1)
			sortList();
		writeHighscore(hsc);
		System.out.println(bananasThrown);
	}

	/**
	 * sort the highscore-list
	 */
	private void sortList() {
		hsc.sort(new Comparator<String>() {

			@Override
			public int compare(String o1, String o2) {
				String[] s = o1.split(", ");
				String[] s2 = o2.split(", ");

				if (Float.parseFloat(s[3]) < Float.parseFloat(s2[3]))
					return 1;
				if (Float.parseFloat(s[3]) > Float.parseFloat(s2[3]))
					return -1;
				if (Float.parseFloat(s[4]) < Float.parseFloat(s2[4]))
					return 1;
				if (Float.parseFloat(s[4]) > Float.parseFloat(s2[4]))
					return -1;
				else
					return 0;
			}
		});
	}

	/**
	 * reset all the variables to 0
	 */
	public static void resetCounters() {

		left = 0;
		right = 0;
		thrown1 = 0;
		thrown2 = 0;
	}
}
