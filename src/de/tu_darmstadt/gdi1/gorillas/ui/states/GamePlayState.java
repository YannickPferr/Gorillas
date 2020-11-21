package de.tu_darmstadt.gdi1.gorillas.ui.states;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

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
import de.tu_darmstadt.gdi1.gorillas.game.Game;
import de.tu_darmstadt.gdi1.gorillas.main.Gorillas;
import de.tu_darmstadt.gdi1.gorillas.test.setup.TestGorillas;
import de.tu_darmstadt.gdi1.gorillas.ui.objects.Background;
import de.tu_darmstadt.gdi1.gorillas.ui.objects.Gorilla;
import de.tu_darmstadt.gdi1.gorillas.ui.objects.House;
import de.tu_darmstadt.gdi1.gorillas.ui.objects.Skyline;
import de.tu_darmstadt.gdi1.gorillas.ui.objects.Sun;
import eea.engine.component.render.ImageRenderComponent;
import eea.engine.entity.Entity;
import eea.engine.entity.StateBasedEntityManager;

/**
 * 
 * @author Yannick Pferr, Ludwig Koch, Kevin Rueckert
 *
 */

public class GamePlayState extends BasicTWLGameState {

	public int stateID;
	public StateBasedEntityManager entityManager;
	public StateBasedGame g;
	public GameContainer c;
	public RootPane rp;
	Skyline sky;
	public Game game;
	public EditField angleIn;
	public EditField speedIn;
	private Label angleLabel;
	private Label speedLabel;
	private Button shootButton;
	private String bgImg;
	public String feedback = "";

	public GamePlayState(int sid) {
		stateID = sid;
		entityManager = StateBasedEntityManager.getInstance();
		game = new Game(this);
	}

	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {

		getRootPane().setTheme("");
		g = game;
		c = container;

		if (!TestGorillas.debug) {
			background();
			if (bgImg.equals("/assets/backgrounds/moonBG.jpg")
					|| bgImg.equals("/assets/backgrounds/desertBG.jpg"))
				skyline(true);
			else
				skyline(false);
			gorillas();
			sun();
			Entity explosion = new Entity("explosion");
			try {
				explosion.addComponent(new ImageRenderComponent(new Image(
						"assets/explosions/explosion.png")));
			} catch (SlickException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			explosion.setVisible(false);
			entityManager.addEntity(stateID, explosion);
		}
	}

	/**
	 * creates a random background picture for the GamePlayState
	 * 
	 * @throws SlickException
	 */
	private void background() throws SlickException {
		String image = "/assets/backgrounds/desertBG.jpg";
		String image2 = "/assets/backgrounds/HighscoreBG.png";
		String image3 = "/assets/backgrounds/snowBG.jpg";
		String image4 = "/assets/backgrounds/moonBG.jpg";

		Background bg = new Background(image, image2, image3, image4);
		String bgImg = bg.getRandomBG();
		Entity background = new Entity("background");
		background.setPosition(new Vector2f(400, 300));
		background.addComponent(new ImageRenderComponent(new Image(bgImg)));

		entityManager.addEntity(stateID, background);

		this.bgImg = bgImg;
	}

	/**
	 * loads the individual houses/abandoned houses images to create a Skyline
	 * Entitiy
	 * 
	 * @param abandoned
	 *            - if this boolean is true the abandoned houses will be used
	 */
	public void skyline(boolean abandoned) {

		// normal house images
		BufferedImage img = loadImage("assets/houses/Haus1.png");
		BufferedImage img2 = loadImage("assets/houses/Haus2.png");
		BufferedImage img3 = loadImage("assets/houses/Haus3.png");
		BufferedImage img4 = loadImage("assets/houses/Haus4.png");
		BufferedImage img5 = loadImage("assets/houses/Haus5.png");
		BufferedImage img6 = loadImage("assets/houses/Haus6.png");
		BufferedImage img7 = loadImage("assets/houses/Haus7.png");
		BufferedImage img8 = loadImage("assets/houses/Haus8.png");

		// abandoned house images
		BufferedImage abandonendImg = loadImage("assets/houses/abandoned.png");
		BufferedImage abandonendImg2 = loadImage("assets/houses/abandoned1.png");
		BufferedImage abandonendImg3 = loadImage("assets/houses/abandoned2.png");
		BufferedImage abandonendImg4 = loadImage("assets/houses/abandoned3.png");
		BufferedImage abandonendImg5 = loadImage("assets/houses/abandoned4.png");
		BufferedImage abandonendImg6 = loadImage("assets/houses/abandoned5.png");
		BufferedImage abandonendImg7 = loadImage("assets/houses/abandoned6.png");
		BufferedImage abandonendImg8 = loadImage("assets/houses/abandoned7.png");

		House h1 = null;
		House h2 = null;
		House h3 = null;
		House h4 = null;
		House h5 = null;
		House h6 = null;
		House h7 = null;
		House h8 = null;

		if (abandoned) {
			h1 = new House("first", abandonendImg);
			h2 = new House("second", abandonendImg2);
			h3 = new House("third", abandonendImg3);
			h4 = new House("fourth", abandonendImg4);
			h5 = new House("fifth", abandonendImg5);
			h6 = new House("sixth", abandonendImg6);
			h7 = new House("seventh", abandonendImg7);
			h8 = new House("eight", abandonendImg8);
		}

		else {
			h1 = new House("first", img);
			h2 = new House("second", img2);
			h3 = new House("third", img3);
			h4 = new House("fourth", img4);
			h5 = new House("fifth", img5);
			h6 = new House("sixth", img6);
			h7 = new House("seventh", img7);
			h8 = new House("eight", img8);
		}

		sky = new Skyline(h1, h2, h3, h4, h5, h6, h7, h8);

		// set skyline
		House skyline = new House("skyline", sky.getSky());
		skyline.setPosition(new Vector2f(400, 300));

		entityManager.addEntity(stateID, skyline);
	}

	/**
	 * sets up two Gorilla Entitys at a random position on top of the skyline
	 * 
	 * @throws SlickException
	 */
	public void gorillas() throws SlickException {
		BufferedImage img9 = loadImage("assets/gorilla/gorilla.png");
		Gorilla gorillaLeft = new Gorilla("gorilla", img9, sky.getFirst3(),
				true, this);
		Gorilla gorillaRight = new Gorilla("gorilla2", img9, sky.getLast3(),
				false, this);

		entityManager.addEntity(stateID, gorillaLeft);
		entityManager.addEntity(stateID, gorillaRight);

		Entity gorillaLeftUp = new Entity("gorilla left");
		gorillaLeftUp.addComponent(new ImageRenderComponent(new Image(
				"/assets/gorilla/gorilla_left_up.png")));

		Entity gorillaRightUp = new Entity("gorilla right");
		gorillaRightUp.addComponent(new ImageRenderComponent(new Image(
				"/assets/gorilla/gorilla_right_up.png")));

		gorillaLeftUp.setVisible(false);
		entityManager.addEntity(stateID, gorillaLeftUp);

		gorillaRightUp.setVisible(false);
		entityManager.addEntity(stateID, gorillaRightUp);
	}

	/**
	 * creates and displays the Sun Entity
	 * 
	 * @throws SlickException
	 */
	public void sun() throws SlickException {
		Sun sun = new Sun("sun smile");
		sun.addComponent(new ImageRenderComponent(new Image(
				"/assets/sun/sun_smiling.png")));
		sun.setPosition(new Vector2f(Gorillas.FRAME_WIDTH / 2, sun.getSize().y));

		entityManager.addEntity(stateID, sun);

		Sun sun2 = new Sun("sun astonished");
		sun2.addComponent(new ImageRenderComponent(new Image(
				"/assets/sun/sun_astonished.png")));
		sun2.setPosition(new Vector2f(sun.getPosition()));
		sun2.setVisible(false);

		entityManager.addEntity(stateID, sun2);

	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {

		entityManager.updateEntities(container, game, delta);
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {

		// set the color of the texts to white
		g.setColor(Color.white);

		entityManager.renderEntities(container, game, g);
		// displays the player names to the screen
		g.drawString(GameSetupState.player1, 0, 0);
		g.drawString(GameSetupState.player2, Gorillas.FRAME_WIDTH
				- GameSetupState.player2.length() * 10, 0);

		// displays the String "Angle: " and "Speed: " to the screen
		if (Game.shootL && !this.game.inAir && this.game.gameRunning) {
			g.drawString("Angle: ", 0, 50);
			g.drawString("Speed: ", 0, 50 + angleLabel.getHeight() + 5);
		}
		if (!Game.shootL && !this.game.inAir && this.game.gameRunning) {
			g.drawString("Angle: ", Gorillas.FRAME_WIDTH - 2 * 50 - 5, 50);
			g.drawString("Speed: ", Gorillas.FRAME_WIDTH - 2 * 50 - 5,
					50 + angleLabel.getHeight() + 5);
		}

		// displays the score to the screen
		g.drawString(this.game.getScore(), Gorillas.FRAME_WIDTH / 2 - 23, 0);

		// displays the wind strength and the direction if wind is set
		if (OptionsState.windSet) {
			if (Game.wind > 0) {
				// arrow to the right
				g.drawImage(
						new Image("/assets/game/arrowRight.png"),
						Gorillas.FRAME_WIDTH
								/ 2
								- new Image("/assets/game/arrowRight.png")
										.getWidth() / 2,
						entityManager.getEntity(stateID, "sun smile")
								.getPosition().y
								+ entityManager.getEntity(stateID, "sun smile")
										.getSize().y);
			}

			if (Game.wind < 0) {
				// arrow to the left
				g.drawImage(
						new Image("/assets/game/arrowLeft.png"),
						Gorillas.FRAME_WIDTH
								/ 2
								- new Image("/assets/game/arrowLeft.png")
										.getWidth() / 2,
						entityManager.getEntity(stateID, "sun smile")
								.getPosition().y
								+ entityManager.getEntity(stateID, "sun smile")
										.getSize().y);
			}
			g.drawString(
					String.valueOf(Math.abs(Game.wind)),
					Gorillas.FRAME_WIDTH / 2 - 23,
					entityManager.getEntity(stateID, "sun smile").getPosition().y
							+ entityManager.getEntity(stateID, "sun smile")
									.getSize().y / 2);
		}

		// displays the name of the winner at the end of a game
		if (!this.game.gameRunning)
			g.drawString(this.game.getWinner() + " you win!",
					this.game.menu.getX() - 10, this.game.menu.getY() - 50);

		// displays the feedback of a throw
		g.drawString(feedback, Gorillas.FRAME_WIDTH / 2
				- new Image("/assets/game/arrowLeft.png").getWidth() / 2
				- feedback.length(),
				entityManager.getEntity(stateID, "sun smile").getPosition().y
						+ entityManager.getEntity(stateID, "sun smile")
								.getSize().y + 50);
	}

	@Override
	public int getID() {
		return stateID;
	}

	@Override
	protected RootPane createRootPane() {

		RootPane rp = super.createRootPane();

		angleLabel = new Label("Angle: ");
		speedLabel = new Label("Speed: ");

		// creates the EditField "angleIn"
		angleIn = new EditField();
		angleIn.addCallback(new Callback() {
			@Override
			public void callback(int key) {
				fieldInput(key, angleIn, this, 360);
			}
		});

		// creates the EditField "speedIn"
		speedIn = new EditField();
		speedIn.addCallback(new Callback() {
			@Override
			public void callback(int key) {
				fieldInput(key, speedIn, this, 200);
			}
		});

		// creates the Button "shootButton"
		shootButton = new Button("Shoot");
		shootButton.addCallback(new Runnable() {

			@Override
			public void run() {
				if (!game.inAir) {
					if (!(angleIn.getText().length() == 0)
							&& !(speedIn.getText().length() == 0)) {

						game.throwBanana();
					}
				}
			}
		});

		angleLabel.setVisible(false);
		speedLabel.setVisible(false);

		rp.add(angleLabel);
		rp.add(speedLabel);
		rp.add(angleIn);
		rp.add(speedIn);
		rp.add(shootButton);

		this.rp = rp;

		return rp;
	}

	@Override
	protected void layoutRootPane() {

		int xOffset = 50;
		int yOffset = 50;
		int gap = 5;

		angleLabel.adjustSize();
		speedLabel.adjustSize();

		// set the size of the EditFields and the Button
		angleIn.setSize(50, 25);
		speedIn.setSize(50, 25);
		shootButton.setSize(50, 25);

		// set the keyboard focus to the EditField "angelIn"
		angleIn.requestKeyboardFocus();

		// set the EditFields and Button to the player how has the current move
		if (Game.shootL) {
			angleLabel.setPosition(0, yOffset);
			angleIn.setPosition(angleLabel.getWidth() + gap, yOffset);

			speedLabel.setPosition(0, yOffset + angleLabel.getHeight() + gap);
			speedIn.setPosition(angleLabel.getWidth() + gap, yOffset
					+ angleLabel.getHeight() + gap);

			shootButton.setPosition(angleLabel.getWidth() + gap, yOffset
					+ angleLabel.getHeight() + gap + speedLabel.getHeight()
					+ gap);
		}

		else {
			angleLabel.setPosition(Gorillas.FRAME_WIDTH - 2 * xOffset, yOffset);
			angleIn.setPosition(Gorillas.FRAME_WIDTH - angleLabel.getWidth()
					- gap, yOffset);

			speedLabel.setPosition(Gorillas.FRAME_WIDTH - 2 * xOffset, yOffset
					+ angleLabel.getHeight() + gap);
			speedIn.setPosition(Gorillas.FRAME_WIDTH - angleLabel.getWidth()
					- gap, yOffset + angleLabel.getHeight() + gap);

			shootButton.setPosition(
					Gorillas.FRAME_WIDTH - angleLabel.getWidth() - gap,
					yOffset + angleLabel.getHeight() + gap
							+ speedLabel.getHeight() + gap);
		}
	}

	/**
	 * load image from a file
	 * 
	 * @param filename
	 *            - path of the file
	 * @return BufferedImage img - returns the loaded image
	 */
	public BufferedImage loadImage(String filename) {
		BufferedImage img = null;
		try {
			File f = new File(filename);
			img = ImageIO.read(f);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return img;
	}

	/**
	 * checks if a specific key was pressed
	 */
	@Override
	public void keyPressed(int key, char c) {
		if (key == Input.KEY_ESCAPE) {
			MainMenuState.pause = true;
			g.enterState(Gorillas.MAINMENUSTATE);
		}
		// if throw is not in air already and the game is running
		// throw the banana when you press enter
		if (key == Input.KEY_ENTER && !game.inAir && game.gameRunning) {
			if (!(angleIn.getText().length() == 0)
					&& !(speedIn.getText().length() == 0)) {
				game.throwBanana();
				game.inAir = true;
			}
		}

		// if the key TAB is pressed jump to the next EditField
		if (key == Input.KEY_TAB && angleIn.hasKeyboardFocus()) {
			speedIn.requestKeyboardFocus();
			speedIn.selectAll();
		}

		else if (key == Input.KEY_TAB && speedIn.hasKeyboardFocus()) {
			angleIn.requestKeyboardFocus();
			angleIn.selectAll();
		}
	}

	/**
	 * handles the fields input so you can only enter digits
	 * 
	 * @param key
	 *            - the key pressed inside the editField
	 * @param editField
	 *            - the used editField
	 * @param callback
	 *            - the callback of used editField
	 * @param maxValue
	 *            - the maximum int value you are allowed to enter
	 */
	private void fieldInput(int key, EditField editField, Callback callback,
			int maxValue) {

		if (key == de.matthiasmann.twl.Event.KEY_NONE) {
			String inputText = editField.getText();

			char inputChar = inputText.charAt(inputText.length() - 1);
			if (!Character.isDigit(inputChar)
					|| Integer.parseInt(inputText) > maxValue) {

				editField.removeCallback(callback);
				editField
						.setText(inputText.substring(0, inputText.length() - 1));
				editField.addCallback(callback);
			}
		}

		if (key == Input.KEY_ENTER) {
			if (!(angleIn.getText().length() == 0)
					&& !(speedIn.getText().length() == 0)) {
				game.throwBanana();
			}
		}

		if (key == Input.KEY_ESCAPE) {
			MainMenuState.pause = true;
			g.enterState(Gorillas.MAINMENUSTATE);
		}
	}

	/**
	 * refreshes the layoutRootPane() and set the focus of the keyboard to the
	 * EditField "angleIn"
	 */
	public void refresh() {
		layoutRootPane();
		angleIn.requestKeyboardFocus();

	}

	/**
	 * displays a short feedback for the players throw
	 * 
	 * @param impact
	 *            - the coordinates of the impact
	 * @param gorillaPos
	 *            - the position of the gorilla
	 */
	public void throwFeedback(Vector2f impact, Vector2f gorillaPos) {

		String feedback = "";
		int distanceX = (int) Math.abs(impact.x - gorillaPos.x);
		int distanceY = (int) Math.abs(impact.y - gorillaPos.y);

		if (distanceX <= 100 && distanceX <= 100)
			feedback = "So close";
		if (distanceX > 100 && distanceX <= 250 || distanceY > 100
				&& distanceY <= 250)
			feedback = "Clean your glasses";
		if (distanceX > 250 || distanceY > 250)
			feedback = "Embarrassing";

		this.feedback = feedback;
	}
}
