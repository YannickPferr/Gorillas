package de.tu_darmstadt.gdi1.gorillas.test.adapter;

import java.util.ArrayList;

import org.newdawn.slick.geom.Vector2f;

import de.tu_darmstadt.gdi1.gorillas.game.Game;
import de.tu_darmstadt.gdi1.gorillas.game.Score;
import de.tu_darmstadt.gdi1.gorillas.test.setup.TestGorillas;
import de.tu_darmstadt.gdi1.gorillas.ui.objects.Skyline;
import de.tu_darmstadt.gdi1.gorillas.ui.states.GamePlayState;

public class GorillasTestAdapterExtended1 extends GorillasTestAdapterMinimal {

	GamePlayState g;
	public ArrayList<String[]> hsc;

	public GorillasTestAdapterExtended1() {
		super();
		g = new GamePlayState(TestGorillas.GAMEPLAYSTATE);
		map = new ArrayList<Vector2f>();
		hsc = new ArrayList<String[]>();
	}

	@Override
	public void rememberGameData() {

		// TODO: Implement

		super.rememberGameData();
	}

	@Override
	public void restoreGameData() {

		// TODO: Implement

		super.restoreGameData();
	}

	/**
	 * This method should create a new RANDOM map. The map should consist of the
	 * coordinates of the left and the right gorilla and the upper left edges of
	 * the buildings of the skyline. The gorilla positions should mark the
	 * center of each gorilla. The buildings of the skyline are rectangular, and
	 * not higher than <code>frameHeight</code> minus 100.
	 * 
	 * Important: The y coordinate of the gorillas and the buildings should be
	 * denoted with the y axis pointing downwards, as slick demands this.
	 * 
	 * The left gorilla has to be placed on the first, second or third building
	 * from the left. Accordingly, the right gorilla has to be placed on the
	 * first, second or third building from the right.
	 * 
	 * Gorillas should be placed in the middle of a building and stand on the
	 * building.
	 * 
	 * The map should be remembered as the current one. Hence,
	 * {@link #getBuildingCoordinates()}, {@link #getLeftGorillaCoordinate()}
	 * and {@link #getRightGorillaCoordinate()} should after an invocation of
	 * this method return the values of the newly created map.
	 * 
	 * The wind should not blow at all in the map.
	 * 
	 * @param frameWidth
	 *            the width of the frame/screen of the gorillas game
	 * @param frameHeight
	 *            the height of the frame/screen of the gorillas game
	 */
	public void createRandomMap(int frameWidth, int frameHeight,
			int gorillaWidth, int gorillaHeight) {

		this.frameWidth = frameWidth;
		this.frameHeight = frameHeight;
		this.gorillaWidth = gorillaWidth;
		this.gorillaHeight = gorillaHeight;
		ArrayList<Vector2f> map = new ArrayList<Vector2f>();
		for (int i = 0; i < frameWidth / 100; i++) {
			Vector2f v = new Vector2f(i * 100, frameHeight
					- Skyline.myRandom(100, frameHeight - 99));
			map.add(v);
		}
		int left = Skyline.myRandom(0, 3);
		gorillaLeft = new Vector2f(map.get(left).getX() + 50, map.get(left)
				.getY() - gorillaHeight / 2);

		int right = Skyline.myRandom(frameWidth / 100 - 3, frameWidth / 100);
		gorillaRight = new Vector2f(map.get(right).getX() + 50, map.get(right)
				.getY() - gorillaHeight / 2);
		this.map = map;
	}

	/**
	 * creates a map, which is NOT RANDOM based on the given parameters
	 * 
	 * @param paneWidth
	 *            the width of the frame/window/pane of the game
	 * @param paneHeight
	 *            the height of the frame/window/pane of the game
	 * @param yOffsetCity
	 *            the top y offset of the city
	 * @param buildingCoordinates
	 *            the building coordinates of the city skyline
	 * @param leftGorillaCoordinate
	 *            the coordinate of the left gorilla
	 * @param rightGorillaCoordinate
	 *            the coordinate of the right gorilla
	 */
	public void createCustomMap(int paneWidth, int paneHeight, int yOffsetCity,
			ArrayList<Vector2f> buildingCoordinates,
			Vector2f leftGorillaCoordinate, Vector2f rightGorillaCoordinate) {

		frameWidth = paneWidth;
		frameHeight = paneHeight;
		map = buildingCoordinates;
		gorillaLeft = leftGorillaCoordinate;
		gorillaRight = rightGorillaCoordinate;
	}

	/**
	 * the current, which was created with
	 * {@link #createRandomMap(int,int,int,int)} of
	 * {@link #createCustomMap(int,int,int,ArrayList,Vector2f,Vector2f)} should
	 * be set as current map in the game, if the game is in GamePlayState
	 */
	public void startCurrrentMap() {
		// TODO: Implement
	}

	/**
	 * should return the building coordinates of the current map
	 * 
	 * @return all the upper left corner's coordinates of the buildings of the
	 *         current map, ordered from left to right
	 */
	public ArrayList<Vector2f> getBuildingCoordinates() {
		// TODO: Implement
		return map;
	}

	/**
	 * should return the coordinate of the left gorilla in the current map
	 * 
	 * @return the center coordinate of the left gorilla
	 */
	public Vector2f getLeftGorillaCoordinate() {
		// TODO: Implement
		return gorillaLeft;
	}

	/**
	 * should return the coordinate of the right gorilla in the current map
	 * 
	 * @return the center coordinate of the right gorilla
	 */
	public Vector2f getRightGorillaCoordinate() {
		// TODO: Implement
		return gorillaRight;
	}

	/**
	 * should return the frameWidth, which was given to create the current map
	 * 
	 * @return the frameWidth which was used to create the current map
	 */
	public float getMapFrameWidth() {
		// TODO: Implement
		return frameWidth;
	}

	/**
	 * should return the frameHeight, which was given to create the current map
	 * 
	 * @return the frameHeight which was used to create the current map
	 */
	public float getMapFrameHeight() {
		// TODO: Implement
		return frameHeight;
	}

	/**
	 * should return the gorillaHeight, which was given to create the current
	 * map
	 * 
	 * @return the gorillaHeight which was used to create the current map
	 */
	public float getGorillaHeight() {
		// TODO: Implement
		return gorillaHeight;
	}

	/**
	 * should return the gorillaWidth, which was given to create the current map
	 * 
	 * @return the gorillaWidth which was used to create the current map
	 */
	public float getGorillaWidth() {
		// TODO: Implement
		return gorillaWidth;
	}

	/**
	 * adds a highscore to the highscore list
	 * 
	 * @param name
	 *            the name of the player
	 * @param numberOfRounds
	 *            the number of rounds played
	 * @param roundsWon
	 *            the number of rounds the player has one
	 * @param bananasThrown
	 *            the number of bananas the player has thrown
	 */
	public void addHighscore(String name, int numberOfRounds, int roundsWon,
			int bananasThrown) {
		// TODO: Implement
		String[] entry = new String[4];
		entry[0] = name;
		entry[1] = String.valueOf(numberOfRounds);
		entry[2] = String.valueOf(roundsWon);
		entry[3] = String.valueOf(bananasThrown);

		int size = hsc.size();
		for (int i = 0; i < size; i++) {
			if (hsc.get(i)[0].equals(name)) {
				entry[1] = String.valueOf(Integer.parseInt(hsc.get(i)[1])
						+ numberOfRounds);
				entry[2] = String.valueOf(Integer.parseInt(hsc.get(i)[2])
						+ roundsWon);
				entry[3] = String.valueOf(Integer.parseInt(hsc.get(i)[3])
						+ bananasThrown);
				hsc.remove(i);
			}

			if (Float.parseFloat(hsc.get(i)[2])
					/ Float.parseFloat(hsc.get(i)[1]) < Float
					.parseFloat(entry[2]) / Float.parseFloat(entry[1])) {
				hsc.add(i, entry);
				break;
			}

			if (i == hsc.size() - 1)
				hsc.add(entry);

		}
		if (hsc.size() == 0)
			hsc.add(entry);
	}

	/**
	 * Resets the highscores. Alle entries are deleted and @see
	 * {@link #getHighscoreCount()} should return 0.
	 */
	public void resetHighscore() {
		// TODO: Implement
		hsc.clear();
	}

	/**
	 * Returns the numnber of highscore entries currently stored.
	 * 
	 * @return number of highscore entries
	 */
	public int getHighscoreCount() {
		// TODO: Implement
		return hsc.size();
	}

	/**
	 * Returns the player name of the highscore entry at the passed position.
	 * The best highscore should be at position 0. See the specification in the
	 * task assignment for the definition of best highscore. Positions that are
	 * invalid should return null.
	 * 
	 * @param position
	 *            position of the highscore entry
	 * @return playername of the highscore entry at the passed position or null
	 *         if position is invalid
	 */
	public String getNameAtHighscorePosition(int position) {
		// TODO: Implement
		if (position > hsc.size() - 1 || position < 0)
			return null;
		return hsc.get(position)[0];
	}

	/**
	 * Returns the number of rounds played in total of the highscore entry a the
	 * passed position The best highscore should be at position 0. See the
	 * specification in the task assignment for the definition of best
	 * highscore. Positions that are invalid should return -1.
	 * 
	 * @param position
	 *            position of the highscore entry
	 * @return number of rounds played in total of the highscore entry at the
	 *         passed position or -1 if position is invalid
	 */
	public int getRoundsPlayedAtHighscorePosition(int position) {
		// TODO: Implement
		if (position > hsc.size() - 1)
			return -1;
		return Integer.parseInt(hsc.get(position)[1]);
	}

	/**
	 * Returns the number of rounds won of the highscore entry a the passed
	 * position The best highscore should be at position 0. See the
	 * specification in the task assignment for the definition of best
	 * highscore. Positions that are invalid should return -1.
	 * 
	 * @param position
	 *            position of the highscore entry
	 * @return number of rounds won of the highscore entry at the passed
	 *         position or -1 if position is invalid
	 */
	public int getRoundsWonAtHighscorePosition(int position) {
		// TODO: Implement
		if (position > hsc.size() - 1 || position < 0)
			return -1;
		return Integer.parseInt(hsc.get(position)[2]);
	}

	/**
	 * Returns the percentage of rounds won of the highscore entry a the passed
	 * position The best highscore should be at position 0. See the
	 * specification in the task assignment for the definition of best
	 * highscore. Positions that are invalid should return -1.
	 * 
	 * @param position
	 *            position of the highscore entry
	 * @return percentage of rounds won of the highscore entry at the passed
	 *         position or -1 if position is invalid
	 */
	public int getPercentageWonAtHighscorePosition(int position) {
		// TODO: Implement
		if (position > hsc.size() - 1 || position < 0)
			return -1;
		float percentage = Float.parseFloat(hsc.get(position)[2])
				/ Float.parseFloat(hsc.get(position)[1]) * 100;
		return Math.round(percentage);
	}

	/**
	 * Returns the mean accuracy of the highscore entry a the passed position
	 * The best highscore should be at position 0. See the specification in the
	 * task assignment for the definition of best highscore. Positions that are
	 * invalid should return -1.
	 * 
	 * @param position
	 *            position of the highscore entry
	 * @return mean accuracy of the highscore entry at the passed position or -1
	 *         if position is invalid
	 */
	public double getMeanAccuracyAtHighscorePosition(int position) {
		// TODO: Implement
		if (position > hsc.size() - 1 || position < 0)
			return -1;
		return Float.parseFloat(hsc.get(position)[3])
				/ Float.parseFloat(hsc.get(position)[2]);
	}

	/**
	 * if the game is in the GamePlayState, this method should return the
	 * current score of player one
	 * 
	 * @return the current score of player one or -1 if the game is not in the
	 *         GamePlayState
	 */
	public int getPlayer1Score() {
		// TODO: Implement
		return Score.left;
	}

	/**
	 * if the game is in the GamePlayState, this method should return the
	 * current score of player two
	 * 
	 * @return the current score of player two or -1 if the game is not in the
	 *         GamePlayState
	 */
	public int getPlayer2Score() {
		// TODO: Implement
		return Score.right;
	}

	/**
	 * if the game is in the GamePlayState, this method should return whether it
	 * is the turn of player one
	 * 
	 * If it is the turn of a player is decided on the fact if the player is
	 * currently able to parameterize a shot.
	 * 
	 * @return true if it is the turn of player one, false if it is the turn of
	 *         player two or the game is not in GamePlayState or it is not the
	 *         turn of anyone
	 */
	public boolean isPlayer1Turn() {
		// TODO: Implement
		return Game.shootL;
	}

	/**
	 * if the game is in the GamePlayState, this method should return whether it
	 * is the turn of player two
	 * 
	 * If it is the turn of a player is decided on the fact if the player is
	 * currently able to parameterize a shot.
	 * 
	 * @return true if it is the turn of player two, false if it is the turn of
	 *         player one or the game is not in GamePlayState or it is not the
	 *         turn of anyone
	 */
	public boolean isPlayer2Turn() {
		// TODO: Implement
		return !Game.shootL;
	}
}
