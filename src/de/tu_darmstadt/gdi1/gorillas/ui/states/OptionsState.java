package de.tu_darmstadt.gdi1.gorillas.ui.states;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import de.matthiasmann.twl.Button;
import de.matthiasmann.twl.EditField;
import de.matthiasmann.twl.EditField.Callback;
import de.matthiasmann.twl.Event;
import de.matthiasmann.twl.Label;
import de.matthiasmann.twl.ToggleButton;
import de.matthiasmann.twl.slick.BasicTWLGameState;
import de.matthiasmann.twl.slick.RootPane;
import de.tu_darmstadt.gdi1.gorillas.game.Game;
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

public class OptionsState extends BasicTWLGameState {

	private int stateID;
	private StateBasedGame g;

	private StateBasedEntityManager entityManager;

	// buttons/EditFields/ToggleButtons for the OptionsState
	private EditField gravityInput;
	private Button apply;
	private ToggleButton soundToggle;
	private ToggleButton windToggle;
	private Button goBack;
	private Label error;

	public static boolean gravitySet = false;
	public static boolean windSet = true;

	public OptionsState(int sid) {
		stateID = sid;
		entityManager = StateBasedEntityManager.getInstance();
	}

	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {

		getRootPane().setTheme("");
		g = game;
		// set background image for OptionsState
		if (!TestGorillas.debug) {

			Entity background = new Entity("menu");
			background.setPosition(new Vector2f(400, 300));
			background.addComponent(new ImageRenderComponent(new Image(
					"/assets/backgrounds/KingKong.png")));

			entityManager.addEntity(stateID, background);
		}
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {

		entityManager.renderEntities(container, game, g);

		// create the labels "Gravity" and "Wind"
		g.setColor(Color.black);
		g.drawString("Gravity: ", Gorillas.FRAME_WIDTH / 2 - 40,
				Gorillas.FRAME_HEIGHT / 2 - 25);
		g.drawString("Sound: ", Gorillas.FRAME_WIDTH / 2 - 25,
				Gorillas.FRAME_HEIGHT / 2 - 130);
		g.drawString("Wind: ", Gorillas.FRAME_WIDTH / 2 - 20,
				Gorillas.FRAME_HEIGHT / 2 - 75);

	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		// changes the Text of the soundToggle Button when it is pressed
		if (Sound.playing)
			soundToggle.setText("ON");

		else
			soundToggle.setText("OFF");

		// changes the Text of the windToggle Button when it is pressed
		if (windSet)
			windToggle.setText("ON");

		else
			windToggle.setText("OFF");

		if (!MainMenuState.pause)
			error.setVisible(false);

	}

	protected RootPane createRootPane() {
		RootPane rp = super.createRootPane();
		// create a ToggleButton "soundToggle"
		soundToggle = new ToggleButton();

		soundToggle.setActive(Sound.playing);
		soundToggle.addCallback(new Runnable() {
			public void run() {
				setSound();
			}
		});

		// create a ToggleButton "windToggle"
		windToggle = new ToggleButton();

		windToggle.setActive(windSet);
		// windToggle.setText("ON");
		windToggle.addCallback(new Runnable() {
			public void run() {
				if (!MainMenuState.pause)
					setWind();
				else
					windToggle.setActive(windSet);
				error.setVisible(true);
			}
		});

		// create a new EditField "gravityInput"
		gravityInput = new EditField();
		gravityInput.addCallback(new Callback() {

			public void callback(int key) {

				handleEditFieldInput(key, gravityInput, this, 30f);
			}
		});

		if (gravitySet)
			gravityInput.setText(String.valueOf(Game.gravity));

		// create a button called "Apply"
		apply = new Button("Apply");
		apply.addCallback(new Runnable() {
			public void run() {
				// action the button "Apply" does when he is pressed
				if (MainMenuState.pause)
					error.setVisible(true);
				else {
					setGravity();
					g.enterState(Gorillas.MAINMENUSTATE);
				}
			}
		});

		// create a button called "Back to Main Menu"
		goBack = new Button("Back to Main Menu");
		goBack.addCallback(new Runnable() {
			public void run() {
				// action the button "Back to Main Menu" does when he is pressed
				error.setVisible(false);
				g.enterState(Gorillas.MAINMENUSTATE);
			}
		});

		error = new Label(
				"ERROR: This option can't be changed while a game is running");

		error.setVisible(false);

		rp.add(soundToggle);
		rp.add(windToggle);
		rp.add(gravityInput);
		rp.add(apply);
		rp.add(goBack);
		rp.add(error);

		return rp;
	}

	protected void layoutRootPane() {

		int paneHeight = getRootPane().getHeight();
		int paneWidth = getRootPane().getWidth();

		// size and position of the ToggleButton "soundToggle"
		soundToggle.setSize(40, 25);
		soundToggle.setPosition(paneWidth / 2 - soundToggle.getWidth() / 2,
				paneHeight / 2 - 110);

		// size and position of the ToggleButton "windToggle"
		windToggle.setSize(40, 25);
		windToggle.setPosition(paneWidth / 2 - windToggle.getWidth() / 2,
				paneHeight / 2 - 55);

		// size and position of the Button "apply"
		apply.setSize(100, 25);
		apply.setPosition(paneWidth / 2 - apply.getWidth() / 2, paneHeight / 2
				+ 3 * apply.getHeight() / 2);

		// size and position of the EditField "gravityInput"
		gravityInput.setSize(100, 25);
		gravityInput.setPosition(paneWidth / 2 - apply.getWidth() / 2,
				paneHeight / 2);

		// size and position of the Button "Back to Main Menu"
		goBack.adjustSize();
		goBack.setPosition(paneWidth / 2 - goBack.getWidth() / 2, paneHeight
				/ 2 - goBack.getHeight() / 2 + 90);

		// size and position of the Label error (error message)
		error.adjustSize();
		error.setPosition(paneWidth / 2 - error.getWidth() / 2, 0);
	}

	/**
	 * is called when a change within the EditField occurs and handle keypresses
	 * when the EditField is focused
	 * 
	 * @param key
	 *            - the ID of the pressed button
	 * @param editField
	 *            - the used editField
	 * @param callback
	 *            - the callback of the used editField
	 * @param maxValue
	 *            - max value of the input field (float)
	 */
	void handleEditFieldInput(int key, EditField editField, Callback callback,
			float maxValue) {

		// check for valid input
		if (key == Event.KEY_NONE) {
			String inputText = editField.getText();

			char inputChar = inputText.charAt(inputText.length() - 1);

			if (
			// check if there is more than one '.' in the EditField
			(inputChar == '.' && inputText.indexOf('.') != inputText.length() - 1)
					// check if the '.' is not the first character
					|| (inputChar == '.' && inputText.indexOf('.') == 0)
					// check if the input character is valid
					|| (!Character.isDigit(inputChar) && inputChar != '.')
					// check if input is over max value
					|| (Float.parseFloat(inputText) > maxValue)) {

				editField.removeCallback(callback);
				editField
						.setText(inputText.substring(0, inputText.length() - 1));
				editField.addCallback(callback);

			}

		}

		// enter the MainMenuState and set the gravity if the key ENTER is
		// pressed
		if (key == Input.KEY_ENTER) {

			if (MainMenuState.pause) {
				error.setVisible(true);
			} else {
				setGravity();
				error.setVisible(false);
				g.enterState(Gorillas.MAINMENUSTATE);
			}
		}

		// enter the MainMenuState if the key ESCAPE is pressed (gravity is
		// not changed)
		if (key == Input.KEY_ESCAPE) {
			error.setVisible(false);
			g.enterState(Gorillas.MAINMENUSTATE);
		}

	}

	@Override
	public int getID() {

		return stateID;
	}

	/**
	 * if the background sound is playing stops it else starts the loop
	 */
	private void setSound() {

		if (Sound.playing)
			MainMenuState.bgSound.stop();
		else
			MainMenuState.bgSound.playLoop();
	}

	/**
	 * changes the state of the boolean "windSet" so wind will be set off
	 */
	private void setWind() {

		windSet = !windSet;
	}

	/**
	 * check if a specific key is pressed
	 */
	public void keyPressed(int key, char c) {
		if (key == Input.KEY_ESCAPE) {
			error.setVisible(false);
			g.enterState(Gorillas.MAINMENUSTATE);
		}
	}

	/**
	 * sets the gravity value and gravitySet true, if valid
	 */
	private void setGravity() {
		if (gravityInput.getText().length() > 0) {
			gravitySet = true;
			Game.gravity = Float.parseFloat(gravityInput.getText());
		} else
			gravitySet = false;
	}

}
