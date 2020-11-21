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
import de.matthiasmann.twl.Label;
import de.matthiasmann.twl.slick.BasicTWLGameState;
import de.matthiasmann.twl.slick.RootPane;
import de.tu_darmstadt.gdi1.gorillas.game.Score;
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
public class GameSetupState extends BasicTWLGameState {

	private int stateID;
	private StateBasedEntityManager entityManager;

	private Button startGame;
	private Button goBack;

	private EditField inputNamePlayer1;
	private Label labelPlayer1;
	private EditField inputNamePlayer2;
	private Label labelPlayer2;

	private Label error;

	public static String player1;
	public static String player2;

	public StateBasedGame g;
	public GameContainer c;

	public GameSetupState(int sid) {
		stateID = sid;
		entityManager = StateBasedEntityManager.getInstance();
	}

	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {

		getRootPane().setTheme("");
		g = game;
		c = container;

		if (!TestGorillas.debug) {

			Entity background = new Entity("GameSetupState");
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

		// if the EditField gets focus and contains "Enter Your Name" than set
		// it to ""
		if (inputNamePlayer1.hasKeyboardFocus()
				&& inputNamePlayer1.getText().equals("Enter Your Name")) {
			inputNamePlayer1.setText("");
		}

		// if the EditField loses focus and is empty display "Enter Your Name"
		// in the EditField
		if (!inputNamePlayer1.hasKeyboardFocus()
				&& inputNamePlayer1.getTextLength() == 0) {
			inputNamePlayer1.setText("Enter Your Name");
		}

		// if the EditField gets focus and contains "Enter Your Name" than set
		// it to ""
		if (inputNamePlayer2.hasKeyboardFocus()
				&& inputNamePlayer2.getText().equals("Enter Your Name")) {
			inputNamePlayer2.setText("");
		}

		// if the EditField loses focus and is empty display "Enter Your Name"
		// in the EditField
		if (!inputNamePlayer2.hasKeyboardFocus()
				&& inputNamePlayer2.getTextLength() == 0) {
			inputNamePlayer2.setText("Enter Your Name");
		}
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {

		entityManager.renderEntities(container, game, g);
		g.setColor(Color.white);
	}

	@Override
	public int getID() {
		return stateID;
	}

	@Override
	protected RootPane createRootPane() {

		RootPane rp = super.createRootPane();

		// create Label with the name "Player 1:"
		labelPlayer1 = new Label("Player 1: ");
		// create an EditField "inputNamePlayer1"
		inputNamePlayer1 = new EditField();

		inputNamePlayer1.addCallback(new Callback() {
			public void callback(int key) {
				handleEditFieldInput(key, inputNamePlayer1, this);
			}
		});

		// create a Label with the name "Player 2:"
		labelPlayer2 = new Label("Player 2: ");
		// create an EditField "inputNamePlayer1"
		inputNamePlayer2 = new EditField();

		inputNamePlayer2.addCallback(new Callback() {
			public void callback(int key) {
				handleEditFieldInput(key, inputNamePlayer2, this);

			}
		});

		// create a button called "Start Game"
		startGame = new Button("Start Game");
		startGame.addCallback(new Runnable() {
			public void run() {
				startGame(); 
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

		// set the text "Enter Your Name" into the EditFields
		// "inputNamePlayer1"/"inputNamePlayer2"
		if (player1 == null) {
			inputNamePlayer1.setText("Enter Your Name");
		} else
			inputNamePlayer1.setText(player1);

		if (player1 == null) {
			inputNamePlayer2.setText("Enter Your Name");
		} else
			inputNamePlayer2.setText(player2);

		// create an empty error Label
		error = new Label("");

		// add the Buttons/Labels/EditFields to the screen
		rp.add(labelPlayer1);
		rp.add(inputNamePlayer1);
		rp.add(labelPlayer2);
		rp.add(inputNamePlayer2);
		rp.add(error);
		rp.add(startGame);
		rp.add(goBack);

		return rp;
	}

	@Override
	protected void layoutRootPane() {

		int paneHeight = this.getRootPane().getHeight();
		int paneWidth = this.getRootPane().getWidth();

		// size of the Labels "Player 1: " and "Player 2: "
		labelPlayer1.adjustSize();
		labelPlayer2.adjustSize();

		// size of the EditFields for "Player 1: " and "Player 2: "
		inputNamePlayer1.setSize(150, 25);
		inputNamePlayer2.setSize(150, 25);

		// position of the Labels/EditFields "Player 1: " and "Player 2: "
		labelPlayer1.setPosition(paneWidth / 2 - labelPlayer1.getWidth() / 2,
				paneHeight / 2 - labelPlayer1.getHeight() / 2 - 125);
		inputNamePlayer1.setPosition(
				paneWidth / 2 - inputNamePlayer1.getWidth() / 2, paneHeight / 2
						- inputNamePlayer1.getHeight() / 2 - 100);

		labelPlayer2.setPosition(paneWidth / 2 - labelPlayer1.getWidth() / 2,
				paneHeight / 2 - labelPlayer2.getHeight() / 2 - 75);
		inputNamePlayer2.setPosition(
				paneWidth / 2 - inputNamePlayer1.getWidth() / 2, paneHeight / 2
						- inputNamePlayer2.getHeight() / 2 - 50);

		// size and position of the Label error (error message)
		error.adjustSize();
		error.setPosition(paneWidth / 2 - error.getWidth() / 2, 0);

		// size and position of the button "Start Game"
		startGame.adjustSize();
		startGame.setPosition(paneWidth / 2 - startGame.getWidth() / 2,
				paneHeight / 2 - startGame.getHeight() / 2);

		// size and position of the button "Back to Main Menu"
		goBack.adjustSize();
		goBack.setPosition(paneWidth / 2 - goBack.getWidth() / 2, paneHeight
				/ 2 - goBack.getHeight() / 2 + 50);

	}

	/**
	 * handles the fields input so you return to main menu when you press escape
	 * inside a field and proceed to GamePlayState if enter is pressed and names
	 * are valid
	 * 
	 * @param key
	 *            - the key pressed inside the editField
	 * @param editField
	 *            - the used editField
	 * @param callback
	 *            - the callback of used editField
	 * 
	 */
	void handleEditFieldInput(int key, EditField editField, Callback callback) {

		if (key == Input.KEY_ENTER && editField == inputNamePlayer1) {
			inputNamePlayer2.requestKeyboardFocus();
			inputNamePlayer2.selectAll();
		}

		if (key == Input.KEY_ENTER && editField == inputNamePlayer2) {
			
			startGame(); 
		}

		if (key == Input.KEY_ESCAPE) {
			error.setVisible(false);
			g.enterState(Gorillas.MAINMENUSTATE);
		}

	}

	/**
	 * checks if the player names are valid
	 */
	public boolean validPlayerNames() {

		// if player names are valid store them in Strings "player1"/"player2"
		if (!inputNamePlayer1.getText().equals(inputNamePlayer2.getText())
				&& !inputNamePlayer1.getText().equals("Enter Your Name")
				&& !inputNamePlayer2.getText().equals("Enter Your Name")
				&& inputNamePlayer1.getText().length() > 0
				&& inputNamePlayer2.getText().length() > 0) {
			setPlayerNames();
			return true;
		}
		return false;
	}

	/**
	 * checks which error occurred
	 */
	public void checkError() {
		// if not valid create an error code
		if (inputNamePlayer1.getText().equals(inputNamePlayer2.getText()))
			showError("equals");
		if (inputNamePlayer1.getText().equals("Enter Your Name")
				|| inputNamePlayer1.getText().length() == 0)
			showError("player1");
		if (inputNamePlayer2.getText().equals("Enter Your Name")
				|| inputNamePlayer2.getText().length() == 0)
			showError("player2");
		if ((inputNamePlayer1.getText().equals("Enter Your Name") || inputNamePlayer1
				.getText().length() == 0)
				&& (inputNamePlayer2.getText().equals("Enter Your Name") || inputNamePlayer2
						.getText().length() == 0))
			showError("nonames");
	}

	/**
	 * displays the appropriate error message
	 * 
	 * @param string
	 *            - errorcode
	 */
	public void showError(String string) {

		if (string == "equals") {
			error.setText("ERROR: Please enter 2 distinct names");

		} else if (string == "player1") {
			error.setText("ERROR: Please enter a name for Player 1");

		} else if (string == "player2") {
			error.setText("ERROR: Please enter a name for Player 2");

		} else if (string == "nonames") {
			error.setText("ERROR: Please enter 2 names for the Players");
		}

	}

	/**
	 * save the player names form the EditFilds "inputNamePlayer1" and
	 * "inputNamePlayer2" to a String
	 */
	public void setPlayerNames() {
		player1 = inputNamePlayer1.getText();
		player2 = inputNamePlayer2.getText();
	}

	/**
	 * check if a specific key is pressed
	 */
	public void keyPressed(int x, char c) {

		// if the key TAB is pressed jump to the next EditField
		if (x == Input.KEY_TAB && inputNamePlayer1.hasKeyboardFocus()) {
			inputNamePlayer2.requestKeyboardFocus();
			inputNamePlayer2.selectAll();
		}

		else if (x == Input.KEY_TAB && inputNamePlayer2.hasKeyboardFocus()) {
			inputNamePlayer1.requestKeyboardFocus();
			inputNamePlayer1.selectAll();
		}

	}
	
	/**
	 * starts the game after all checks are successfully completed
	 */
	public void startGame() {
		
		if (MainMenuState.pause)
			Score.resetCounters();
		
		if (validPlayerNames()) {
	
		StateBasedEntityManager.getInstance()
				.clearEntitiesFromState(Gorillas.GAMEPLAYSTATE);
		try {

			c.getInput().clearKeyPressedRecord();
			c.getInput().clearControlPressedRecord();
			c.getInput().clearMousePressedRecord();
			g.init(c);
			g.enterState(Gorillas.GAMEPLAYSTATE);
		} catch (SlickException e) {
			e.printStackTrace();
		}
	} else
		checkError();

	}

}