package de.tu_darmstadt.gdi1.gorillas.ui.states;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import de.matthiasmann.twl.Button;
import de.matthiasmann.twl.slick.BasicTWLGameState;
import de.matthiasmann.twl.slick.RootPane;
import de.tu_darmstadt.gdi1.gorillas.main.Gorillas;
import eea.engine.component.render.ImageRenderComponent;
import eea.engine.entity.Entity;
import eea.engine.entity.StateBasedEntityManager;

/**
 * 
 * @author Yannick Pferr, Ludwig Koch, Kevin Rueckert
 *
 */

public class HighscoreState extends BasicTWLGameState {

	private int stateID;
	private StateBasedGame g;
	private StateBasedEntityManager entityManager;
	private Button menuButton;

	public HighscoreState(int sid) {
		stateID = sid;
		entityManager = StateBasedEntityManager.getInstance();
	}

	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {

		// background entity
		Entity background = new Entity("background");
		background.setPosition(new Vector2f(535, 400));
		background.addComponent(new ImageRenderComponent(new Image(
				"/assets/backgrounds/HighscoreBG.png")));
		StateBasedEntityManager.getInstance().addEntity(stateID, background);

		getRootPane().setTheme("");
		g = game;
	}

	/**
	 * Draws highscore table, reads necessary highscore data from the file where
	 * all scores are saved and draws the score into the table
	 */
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {

		g.scale(0.75f, 0.75f);
		g.setColor(Color.white);
		entityManager.renderEntities(container, game, g);
		g.setLineWidth(5);
		// the table is drawn here
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 11; j++) {
				g.drawRect(125 + i * 133, 100 + j * 54, 133, 54);
				if (i == 0 && j > 0)
					g.drawString(j + ".", 125 + 10, 100 + 27 + j * 54);
			}
		}

		g.drawString("Position", 125 + 10, 100 + 27);
		g.drawString("Name", 125 + 143, 100 + 27);
		g.drawString("All rounds", 125 + 133 * 2 + 10, 100 + 27);
		g.drawString("Rounds won", 125 + 133 * 3 + 10, 100 + 27);
		g.drawString("Win rate", 125 + 133 * 4 + 10, 100 + 27);
		g.drawString("Hit rate", 125 + 133 * 5 + 10, 100 + 27);

		String line = null;
		try {
			FileInputStream fis = new FileInputStream(new File(
					"assets/game/savedScores.hsc"));
			BufferedReader bf = new BufferedReader(new InputStreamReader(fis));
			int i = 81;
			int j = 1;
			// read data from highscore file and write it in the table
			while ((line = bf.readLine()) != null && j <= 10) {
				if (line.length() > 0) {
					String[] s = line.split(", ");

					g.drawString(s[0], 125 + 143, 100 + i);
					g.drawString(s[1], 125 + 133 * 2 + 10, 100 + i);
					g.drawString(s[2], 125 + 133 * 3 + 10, 100 + i);
					g.drawString((int) (Float.parseFloat(s[3]) * 100) + "% ",
							125 + 133 * 4 + 10, 100 + i);
					g.drawString((int) (Float.parseFloat(s[4]) * 100) + "% ",
							125 + 133 * 5 + 10, 100 + i);
					i += 54;
					j++;
				}
			}

			bf.close();
			fis.close();
		} catch (IOException exception) {
			System.out.println("ERROR! Could not read file");
			exception.printStackTrace();
		}
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		// TODO Auto-generated method stub

	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return stateID;
	}

	/**
	 * Returns to main menu if escape key is pressed
	 * 
	 * @param int key, char c
	 */
	public void keyPressed(int key, char c) {
		if (key == Input.KEY_ESCAPE)
			g.enterState(Gorillas.MAINMENUSTATE);
	}

	protected RootPane createRootPane() {

		RootPane rp = super.createRootPane();
		// create mainmenu button
		menuButton = new Button("Back to Main Menu");
		menuButton.addCallback(new Runnable() {

			public void run() {
				g.enterState(Gorillas.MAINMENUSTATE);
			}
		});

		rp.add(menuButton);
		return rp;

	}

	protected void layoutRootPane() {
		// set size and position of mainmenu button
		menuButton.adjustSize();
		menuButton.setPosition(350, 550);

	}
}
