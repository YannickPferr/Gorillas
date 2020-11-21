package de.tu_darmstadt.gdi1.gorillas.game;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import de.matthiasmann.twl.Button;
import de.tu_darmstadt.gdi1.gorillas.main.Gorillas;
import de.tu_darmstadt.gdi1.gorillas.ui.objects.Banana;
import de.tu_darmstadt.gdi1.gorillas.ui.objects.Gorilla;
import de.tu_darmstadt.gdi1.gorillas.ui.objects.House;
import de.tu_darmstadt.gdi1.gorillas.ui.objects.Sun;
import de.tu_darmstadt.gdi1.gorillas.ui.states.GamePlayState;
import de.tu_darmstadt.gdi1.gorillas.ui.states.GameSetupState;
import eea.engine.action.Action;
import eea.engine.action.basicactions.ChangeStateInitAction;
import eea.engine.component.Component;
import eea.engine.component.render.ImageRenderComponent;
import eea.engine.entity.DestructibleImageEntity;
import eea.engine.entity.Entity;
import eea.engine.entity.StateBasedEntityManager;
import eea.engine.event.Event;
import eea.engine.event.basicevents.CollisionEvent;
import eea.engine.event.basicevents.LeavingScreenEvent;

/**
 * 
 * @author Yannick Pferr, Ludwig Koch, Kevin Rueckert
 *
 */

public class Game {

	public static float gravity;
	public static float wind;
	
	public boolean gameRunning;
	public static boolean shootL;
	public boolean inAir;
	public Button menu;
	public float t;
	public boolean sunAstonished;
	
	private GamePlayState g;
	private String winnerName;
	private Gorilla winner;
	private String angleP1;
	private String speedP1;
	private String angleP2;
	private String speedP2;
	private Score hsc;
	
	public Game(GamePlayState g) {
		this.g = g;
		gameRunning = true;
		shootL = true;
		inAir = false;
		hsc = new Score();
	}

	/**
	 * Throws the banana and counts the amount of throws for each gorilla
	 * 
	 */
	public void throwBanana() {

		g.feedback = "";
		inAir = true;
		saveParam();
		throwGorillaVisibile(true);
		String gorillaID;
		if (shootL) {
			gorillaID = "gorilla";
			Score.thrown1++;
		} else {
			gorillaID = "gorilla2";
			Score.thrown2++;
		}
		
		Banana banana = new Banana("banana",
				(Gorilla) g.entityManager.getEntity(g.stateID, gorillaID), g,
				this);
		g.entityManager.addEntity(g.stateID, banana);
		bananaCollision();
		leavingScreen();
	}

	/**
	 * Saves the entered parameters for each player
	 */
	private void saveParam() {
		if (shootL) {
			angleP1 = g.angleIn.getText();
			speedP1 = g.speedIn.getText();
		} else {
			angleP2 = g.angleIn.getText();
			speedP2 = g.speedIn.getText();
		}
	}

	/**
	 * Remembers the previous parameters of angle and speed for each player. If
	 * it's the first shoot for both gorillas the text is empty
	 * 
	 */
	private void setParam() {
		if (shootL) {
			if (angleP1 != null && speedP1 != null) {
				g.angleIn.setText(angleP1);
				g.speedIn.setText(speedP1);
			}
		} else {
			if (angleP2 != null && speedP2 != null) {
				g.angleIn.setText(angleP2);
				g.speedIn.setText(speedP2);

			} else {
				g.angleIn.setText(" ");
				g.speedIn.setText(" ");
			}
		}
	}

	/**
	 * Checks if the banana left the screen on the right, on the left or on the
	 * bottom
	 * 
	 */
	private void leavingScreen() {
		LeavingScreenEvent l = new LeavingScreenEvent();
		l.addAction(new Action() {

			@Override
			public void update(GameContainer gc, StateBasedGame sb, int delta,
					Component event) {

				if (!(g.entityManager.getEntity(g.stateID, "banana")
						.getPosition().y < 0)) {
					// Banana is removed
					g.entityManager.removeEntity(g.stateID,
							g.entityManager.getEntity(g.stateID, "banana"));
					// Round for this player is over
					shootEnd();
				}

			}
		});
		g.entityManager.getEntity(g.stateID, "banana").addComponent(l);
	}

	/**
	 * Checks if the banana collides with the sun, skyline or gorilla
	 * 
	 */
	private void bananaCollision() {
		Event collision = new CollisionEvent();
		collision.addAction(new Action() {
			@Override
			public void update(GameContainer gc, StateBasedGame sb, int delta,
					Component event) {

				CollisionEvent collider = (CollisionEvent) event;
				Entity entity = collider.getCollidedEntity();

				DestructibleImageEntity destructible = null;
				/**
				 * If the banana collides with the sun, the sun is astonished
				 * 
				 */
				if (entity instanceof Sun) {
					sunAstonished = true;
					g.entityManager.getEntity(g.stateID, "sun smile")
							.setVisible(false);
					g.entityManager.getEntity(g.stateID, "sun astonished")
							.setVisible(true);
				}
				/**
				 * If a DestructibleImageEntity was hit it gets destroyed at
				 * this point and the move for this player is over if the entity
				 * was the skyline
				 * 
				 */
				if (entity instanceof DestructibleImageEntity) {
					// play sound
					Sound collision = new Sound("assets/sounds/explosion.wav",
							false);
					collision.play();

					// destroy entity
					destructible = (DestructibleImageEntity) entity;
					destructible.impactAt(event.getOwnerEntity().getPosition());

					// remove banana
					g.entityManager.removeEntity(g.stateID,
							g.entityManager.getEntity(g.stateID, "banana"));

					if (entity instanceof House) {
						if (shootL)
							g.throwFeedback(
									event.getOwnerEntity().getPosition(),
									g.entityManager.getEntity(g.stateID,
											"gorilla2").getPosition());
						else
							g.throwFeedback(
									event.getOwnerEntity().getPosition(),
									g.entityManager.getEntity(g.stateID,
											"gorilla").getPosition());
						shootEnd();
					}
				}

				/**
				 * If the gorilla was hit, it is destroyed and the game is over
				 * 
				 */
				if (entity instanceof Gorilla) {
					// play sound
					Sound collision = new Sound("assets/sounds/explosion.wav",
							false);
					collision.play();

					throwGorillaVisibile(false);
					g.rp.setVisible(false);

					g.entityManager.getEntity(g.stateID, "explosion")
							.setPosition(entity.getPosition());
					g.entityManager.getEntity(g.stateID, "explosion")
							.setVisible(true);

					if (entity.getID().equals("gorilla")) {
						
						gameEnd((Gorilla) g.entityManager.getEntity(g.stateID,
								"gorilla2"));
					}

					else {
						gameEnd((Gorilla) g.entityManager.getEntity(g.stateID,
								"gorilla"));
					}
					g.refresh();
				}
			}
		});
		g.entityManager.getEntity(g.stateID, "banana").addComponent(collision);
	}

	/**
	 * Causes the throw animation by setting the right gorilla pictures visible
	 * or invisible
	 * 
	 * @param start
	 *            - true if throw starts false if shot ends
	 */
	private void throwGorillaVisibile(boolean start) {
		String gorillaID;
		String gorillaThrowID;
		if (shootL) {
			gorillaID = "gorilla";
			gorillaThrowID = "gorilla left";
		} else {
			gorillaID = "gorilla2";
			gorillaThrowID = "gorilla right";
		}
		// rootpane visibility
		g.rp.setVisible(!start);
		g.entityManager.getEntity(g.stateID, gorillaThrowID).setPosition(
				g.entityManager.getEntity(g.stateID, gorillaID).getPosition());
		g.entityManager.getEntity(g.stateID, gorillaID).setVisible(!start);
		g.entityManager.getEntity(g.stateID, gorillaThrowID).setVisible(start);
	}

	/**
	 * Resets the variables and the sun after each shot
	 * 
	 */
	private void shootEnd() {
		inAir = false;
		sunAstonished = false;
		g.entityManager.getEntity(g.stateID, "sun smile").setVisible(true);
		g.entityManager.getEntity(g.stateID, "sun astonished")
				.setVisible(false);
		throwGorillaVisibile(false);
		if (shootL)
			shootL = false;
		else
			shootL = true;
		setParam();
		g.refresh();
	}

	/**
	 * Ends the game if one of the two players has scored 3 points else adds
	 * point to winner of round
	 * 
	 * @param gorilla
	 *            - the gorilla who won
	 */
	private void gameEnd(Gorilla gorilla) {
		if (gorilla.getID().equals("gorilla"))
			Score.left++;
		else
			Score.right++;

		if (Score.left == 3 || Score.right == 3) {
			gameRunning = false;
			winner = gorilla;
			winnerName = winner.getPlayerName();
			g.rp.removeAllChildren();
			g.rp.setVisible(true);
			gorilla.celebrate();
			winField();

			/**
			 * If score is less than 3 the round is over and a new map is
			 * created
			 * 
			 */
		}
		Event e = new Event("new map") {

			@Override
			protected boolean performAction(GameContainer gc,
					StateBasedGame sb, int delta) {

				if (Score.left != 3 && Score.right != 3) {
					StateBasedEntityManager.getInstance()
							.clearEntitiesFromState(g.stateID);
					try {
						g.c.getInput().clearKeyPressedRecord();
						g.c.getInput().clearControlPressedRecord();
						g.c.getInput().clearMousePressedRecord();

						g.g.init(g.c);
					} catch (SlickException e) {
						e.printStackTrace();
					}
					return true;
				} else
					return false;
			}
		};
		e.addAction(new ChangeStateInitAction(Gorillas.GAMEPLAYSTATE));
		gorilla.addComponent(e);

	}

	/**
	 * Opens up a win field with the name of the winner in it after he won the
	 * game. It also includes a button to return to the main menu
	 * 
	 */
	private void winField() {
		Entity win = new Entity("win");
		win.setPosition(new Vector2f(Gorillas.FRAME_WIDTH / 2,
				Gorillas.FRAME_HEIGHT / 2));
		try {
			win.addComponent(new ImageRenderComponent(new Image(
					"/assets/game/WinField.png")));
		} catch (SlickException e) {
			e.printStackTrace();
		}
		g.entityManager.addEntity(g.stateID, win);

		menu = new Button("Hauptmenü");
		menu.setSize(100, 25);
		menu.setPosition((int) win.getPosition().x - menu.getWidth() / 2,
				(int) win.getPosition().y);
		menu.addCallback(new Runnable() {

			/**
			 * Updates highscore entries for each player and switches to main
			 * menu
			 * 
			 */
			@Override
			public void run() {
				hsc.readHighscore();
				hsc.addHighscore(GameSetupState.player1, hsc.roundsPlayedP1
						+ Score.left + Score.right, hsc.roundsWonP1
						+ Score.left, hsc.bananasThrownP1 + Score.thrown1,
						hsc.bananasHitP1 + Score.left);
				hsc.addHighscore(GameSetupState.player2, hsc.roundsPlayedP2
						+ Score.left + Score.right, hsc.roundsWonP2
						+ Score.right, hsc.bananasThrownP2 + Score.thrown2,
						hsc.bananasHitP2 + Score.right);
				winner.stopTimer();
				Score.resetCounters();
				g.g.enterState(Gorillas.MAINMENUSTATE);

				// clear all
				StateBasedEntityManager.getInstance().clearEntitiesFromState(
						g.stateID);
				try {
					g.c.getInput().clearKeyPressedRecord();
					g.c.getInput().clearControlPressedRecord();
					g.c.getInput().clearMousePressedRecord();
					g.g.init(g.c);
				} catch (SlickException e) {
					e.printStackTrace();
				}
			}
		});
		g.rp.add(menu);
	}

	/**
	 * Returns the current game score
	 * 
	 * @return String score
	 */
	public String getScore() {
		return Score.left + " - " + Score.right;
	}

	/**
	 * Returns the name of the winning gorilla
	 * 
	 * @return String winnerName
	 */
	public String getWinner() {
		return winnerName;
	}
}
