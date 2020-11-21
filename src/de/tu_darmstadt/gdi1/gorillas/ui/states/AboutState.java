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
import de.tu_darmstadt.gdi1.gorillas.main.Gorillas;
import de.tu_darmstadt.gdi1.gorillas.test.setup.TestGorillas;
import eea.engine.action.basicactions.Movement;
import eea.engine.component.render.ImageRenderComponent;
import eea.engine.entity.Entity;
import eea.engine.entity.StateBasedEntityManager;
import eea.engine.event.basicevents.LoopEvent;

/**
 * 
 * @author Yannick Pferr, Ludwig Koch, Kevin Rueckert
 *
 */

public class AboutState extends BasicTWLGameState {

	private int stateID;
	private StateBasedEntityManager entityManager;

	public StateBasedGame g;

	private Button goBack;

	public AboutState(int sid) {
		stateID = sid;
		entityManager = StateBasedEntityManager.getInstance();
	}

	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {

		getRootPane().setTheme("");
		g = game;

		if (!TestGorillas.debug) {
			// set background image for AboutState
			Entity background = new Entity("AboutState");
			background.setPosition(new Vector2f(400, 300));
			background.addComponent(new ImageRenderComponent(new Image(
					"/assets/backgrounds/KingKong.png")));

			entityManager.addEntity(stateID, background);

			// add "About.png"
			final Entity aboutPic = new Entity("aboutPic");
			aboutPic.addComponent(new ImageRenderComponent(new Image(
					"/assets/other/About.png")));
			aboutPic.setPosition(new Vector2f(400, 900));

			entityManager.addEntity(stateID, aboutPic);

			// create a LoopEvent for the aboutPic ("About.png")
			LoopEvent loopAbout = new LoopEvent();
			Movement aboutMove = new Movement(1.0f) {

				@Override
				public Vector2f getNextPosition(Vector2f position, float speed,
						float rotation, int delta) {

					// let the aboutPic ("About.png") fly in
					if (aboutPic.getPosition().y > 300)
						return new Vector2f(aboutPic.getPosition().x,
								aboutPic.getPosition().y - 0.5f);
					// stop the aboutPic when it reach x = 400 and y = 300
					else
						return new Vector2f(400, 300);
				}

			};

			loopAbout.addAction(aboutMove);
			aboutPic.addComponent(loopAbout);
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

		// create a "Back to Main Menu" Button
		goBack = new Button("Back to Main Menu");
		goBack.addCallback(new Runnable() {
			public void run() {
				// action the button "Back to Main Menu" do when he is pressed
				g.enterState(Gorillas.MAINMENUSTATE);
			}
		});

		rp.add(goBack);

		return rp;
	}

	@Override
	protected void layoutRootPane() {

		// size and position of the button "Back to Main Menu"
		goBack.adjustSize();
		goBack.setPosition(650, 550);

	}

	/**
	 * check if a specific key is pressed
	 */
	public void keyPressed(int x, char c) {
		// if the key n is pressed start a new game
		if (x == Input.KEY_ESCAPE)
			g.enterState(Gorillas.MAINMENUSTATE);
	}

}
