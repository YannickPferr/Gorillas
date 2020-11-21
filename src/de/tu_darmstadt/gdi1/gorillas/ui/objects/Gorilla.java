package de.tu_darmstadt.gdi1.gorillas.ui.objects;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import de.tu_darmstadt.gdi1.gorillas.game.Sound;
import de.tu_darmstadt.gdi1.gorillas.main.Gorillas;
import de.tu_darmstadt.gdi1.gorillas.ui.states.GamePlayState;
import de.tu_darmstadt.gdi1.gorillas.ui.states.GameSetupState;
import eea.engine.action.basicactions.MoveDownAction;
import eea.engine.entity.DestructibleImageEntity;
import eea.engine.event.Event;

/**
 * 
 * @author Yannick Pferr, Ludwig Koch, Kevin Rueckert
 *
 */
public class Gorilla extends DestructibleImageEntity {

	private Vector2f randomPos;
	private GamePlayState g;
	private Timer timer;
	private String playerName;
	private BufferedImage img;

	public Gorilla(String entityID, BufferedImage image, ArrayList<House> l,
			boolean left, GamePlayState g) {
		super(entityID, image, "game/destruction.png", false);

		randomPos = setOnRandomHouse(l);
		img = image;
		if (left)
			playerName = GameSetupState.player1;
		else
			playerName = GameSetupState.player2;
		this.setPosition(randomPos);
		this.g = g;
		fall();
	}

	/**
	 * Returns a random position for this gorilla
	 * 
	 * @param ArrayList
	 *            <House> l
	 * 
	 * @returns Vector2f gorillaPosition
	 */
	private Vector2f setOnRandomHouse(ArrayList<House> l) {
		ArrayList<House> list = l;
		Vector2f gorillaPos = null;

		int random = Skyline.myRandom(0, l.size());

		float gorillaX = list.get(random).getPosition().x;
		float gorillaY = Gorillas.FRAME_HEIGHT - list.get(random).getSize().y
				- this.getSize().y / 2;
		gorillaPos = new Vector2f(gorillaX, gorillaY);

		return gorillaPos;
	}

	/**
	 * Let's the gorilla fall down if the house underneath was destroyed
	 * 
	 */
	private void fall() {
		Event gorillaCollision = new Event("gorilla collision") {

			// Checks if gorilla stands on top of the house. If not it fires the
			// move down action
			@Override
			protected boolean performAction(GameContainer gc,
					StateBasedGame sb, int delta) {

				Gorilla go = (Gorilla) this.getOwnerEntity();
				if (!(this.getOwnerEntity().collides(g.entityManager.getEntity(
						g.stateID, "skyline")))
						&& go.getPosition().y + img.getHeight() / 2 < Gorillas.FRAME_HEIGHT) {
					return true;
				}

				return false;
			}
		};

		gorillaCollision.addAction(new MoveDownAction(0.5f));
		this.addComponent(gorillaCollision);
	}

	/**
	 * Let´s the gorilla celebrate after it won the match
	 * 
	 */
	public void celebrate() {
		// play sound
		Sound s = new Sound("assets/sounds/GorillaCelebrate.wav", false);
		s.play();

		// set position and visibility
		g.entityManager.getEntity(g.stateID, "gorilla left").setPosition(
				this.getPosition());
		g.entityManager.getEntity(g.stateID, "gorilla right").setPosition(
				this.getPosition());
		this.setVisible(false);
		g.entityManager.getEntity(g.stateID, "gorilla right").setVisible(true);

		TimerTask run = new TimerTask() {

			/**
			 * Creates the celebrating animation
			 * 
			 */
			public void run() {
				String visible;
				String invisible;
				if (g.entityManager.getEntity(g.stateID, "gorilla left")
						.isVisible()) {
					visible = "gorilla left";
					invisible = "gorilla right";
				} else {
					visible = "gorilla right";
					invisible = "gorilla left";
				}
				g.entityManager.getEntity(g.stateID, visible).setVisible(false);
				g.entityManager.getEntity(g.stateID, invisible)
						.setVisible(true);
			}
		};
		timer = new Timer();
		timer.scheduleAtFixedRate(run, 0, 500);
	}

	/**
	 * Returns the player name of this gorilla
	 * 
	 * @returns String playerName
	 */
	public String getPlayerName() {
		return playerName;
	}

	/**
	 * Stops the timer
	 */
	public void stopTimer() {
		timer.cancel();
	}
}
