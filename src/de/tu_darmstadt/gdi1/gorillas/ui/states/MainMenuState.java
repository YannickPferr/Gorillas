package de.tu_darmstadt.gdi1.gorillas.ui.states;

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
import de.tu_darmstadt.gdi1.gorillas.game.Sound;
import de.tu_darmstadt.gdi1.gorillas.main.Gorillas;
import de.tu_darmstadt.gdi1.gorillas.test.setup.TestGorillas;
import eea.engine.component.render.ImageRenderComponent;
import eea.engine.entity.Entity;
import eea.engine.entity.StateBasedEntityManager;

/**
 * 
 * @author Yannick Pferr, Ludwig Koch, Kevin Rueckert
 *
 */

public class MainMenuState extends BasicTWLGameState {

	private int stateID;
	private StateBasedEntityManager entityManager;

	public static boolean pause;

	public StateBasedGame g;

	// buttons for the MainMenuState
	private Button newGameButton;
	private Button highscoreButton;
	private Button optionsButton;
	private Button instructionsButton;
	private Button aboutButton;
	private Button exitGameButton;

	// plays background sound
	public static Sound bgSound = new Sound("assets/sounds/bgSound.wav", true);

	public MainMenuState(int sid) {
		stateID = sid;
		entityManager = StateBasedEntityManager.getInstance();
	}

	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {

		getRootPane().setTheme("");

		g = game;

		if (!TestGorillas.debug) {
			// set background image for MainMenuState
			Entity background = new Entity("MainMenuState");
			background.setPosition(new Vector2f(400, 300));

			background.addComponent(new ImageRenderComponent(new Image(
					"/assets/backgrounds/KingKong.png")));

			entityManager.addEntity(stateID, background);
		}
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {

		entityManager.updateEntities(container, game, delta);
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {

		entityManager.renderEntities(container, game, g);
	}

	@Override
	public int getID() {
		return stateID;
	}

	@Override
	protected RootPane createRootPane() {

		RootPane rp = super.createRootPane();

		// build a button called "New Game"
		newGameButton = new Button("New Game");
		newGameButton.addCallback(new Runnable() {
			public void run() {
				g.enterState(Gorillas.GAMESETUPSTATE);

			}
		});

		// build a button called "Highscore"
		highscoreButton = new Button("Highscore");
		highscoreButton.addCallback(new Runnable() {
			public void run() {
				g.enterState(Gorillas.HIGHSCORESTATE);
			}

		});

		// build a button called "Options"
		optionsButton = new Button("Options");
		optionsButton.addCallback(new Runnable() {
			public void run() {
				g.enterState(Gorillas.OPTIONSTATE);
			}
		});

		// build a button called "Instruction"
		instructionsButton = new Button("Instruction");
		instructionsButton.addCallback(new Runnable() {
			public void run() {
				g.enterState(Gorillas.INSTRUCTIONSSTATE);
			}
		});

		// build a button called "About"
		aboutButton = new Button("About");
		aboutButton.addCallback(new Runnable() {
			public void run() {
				g.enterState(Gorillas.ABOUTSTATE);
			}
		});

		// build a button called "Exit Game"
		exitGameButton = new Button("Exit Game");
		exitGameButton.addCallback(new Runnable() {
			public void run() {
				System.exit(0);
			}
		});

		// add the buttons to the screen
		rp.add(newGameButton);
		rp.add(highscoreButton);
		rp.add(optionsButton);
		rp.add(instructionsButton);
		rp.add(aboutButton);
		rp.add(exitGameButton);
		return rp;

	}

	/**
	 * set the position and size of gui elements
	 */
	@Override
	protected void layoutRootPane() {

		int paneHeight = this.getRootPane().getHeight();
		int paneWidth = this.getRootPane().getWidth();

		// position of the button "New Game"
		newGameButton.setSize(100, 25);
		newGameButton.setPosition(paneWidth / 2 - newGameButton.getWidth() / 2,
				paneHeight / 2 - newGameButton.getHeight() / 2 - 100);

		// position of the button "Highscore"
		highscoreButton.setSize(100, 25);
		highscoreButton.setPosition(paneWidth / 2 - highscoreButton.getWidth()
				/ 2, (paneHeight / 2 - highscoreButton.getHeight() / 2) - 65);

		// position of the button "Options"
		optionsButton.setSize(100, 25);
		optionsButton.setPosition(paneWidth / 2 - optionsButton.getWidth() / 2,
				(paneHeight / 2 - optionsButton.getHeight() / 2) - 30);

		// postiton of the button "Instruction"

		instructionsButton.setSize(100, 25);
		instructionsButton.setPosition(
				paneWidth / 2 - instructionsButton.getWidth() / 2,
				(paneHeight / 2 - instructionsButton.getHeight() / 2) + 5);

		// position of the button "About"
		aboutButton.setSize(100, 25);
		aboutButton.setPosition(paneWidth / 2 - aboutButton.getWidth() / 2,
				(paneHeight / 2 - aboutButton.getHeight() / 2) + 40);

		// position of the button "Exit Game"
		exitGameButton.setSize(100, 25);
		exitGameButton.setPosition(paneWidth / 2 - exitGameButton.getWidth()
				/ 2, (paneHeight / 2 - exitGameButton.getHeight() / 2) + 75);
	}

	/**
	 * check if a specific key is pressed
	 */
	public void keyPressed(int x, char c) {

		// if the key ESCAPE is pressed an the game is paused switch to the
		// GAMEPLAYSTATE
		if (x == Input.KEY_ESCAPE && pause) {
			g.enterState(Gorillas.GAMEPLAYSTATE);
			pause = false;
		}

		// if the key N is pressed start a new game
		if (x == Input.KEY_N)
			g.enterState(Gorillas.GAMESETUPSTATE);
	}
}
