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
import eea.engine.component.render.ImageRenderComponent;
import eea.engine.entity.Entity;
import eea.engine.entity.StateBasedEntityManager;

/**
 * 
 * @author Yannick Pferr, Ludwig Koch, Kevin Rueckert
 *
 */

public class InstructionsState extends BasicTWLGameState {

	private int stateID;
	StateBasedEntityManager entityManager;
	private StateBasedGame g;
	private Button nextButton;
	private Button previousButton;
	private Button menuButton;
	private int page = 1;
	private Entity instruction1;
	private Entity instruction2;
	private Entity instruction3;
	private Entity instruction4;
	private Entity instruction5;

	public InstructionsState(int sid) {
		stateID = sid;
		entityManager = StateBasedEntityManager.getInstance();
	}

	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {

		getRootPane().setTheme("");
		g = game;

		instruction1 = new Entity("Instruction1");
		instruction1.setPosition(new Vector2f(400, 300));
		instruction1.addComponent(new ImageRenderComponent(new Image(
				"/assets/instruction/Instruction1.png")));
		entityManager.addEntity(stateID, instruction1);

		instruction2 = new Entity("Instruction2");
		instruction2.setPosition(new Vector2f(400, 300));
		instruction2.addComponent(new ImageRenderComponent(new Image(
				"/assets/instruction/Instruction2.png")));
		entityManager.addEntity(stateID, instruction2);
		instruction2.setVisible(false);

		instruction3 = new Entity("Instruction3");
		instruction3.setPosition(new Vector2f(400, 300));
		instruction3.addComponent(new ImageRenderComponent(new Image(
				"/assets/instruction/Instruction3.png")));
		entityManager.addEntity(stateID, instruction3);
		instruction3.setVisible(false);

		instruction4 = new Entity("Instruction4");
		instruction4.setPosition(new Vector2f(400, 300));
		instruction4.addComponent(new ImageRenderComponent(new Image(
				"/assets/instruction/Instruction4.png")));
		entityManager.addEntity(stateID, instruction4);
		instruction4.setVisible(false);
		
		instruction5 = new Entity("Instruction5");
		instruction5.setPosition(new Vector2f(400, 300));
		instruction5.addComponent(new ImageRenderComponent(new Image(
				"/assets/instruction/Instruction5.png")));
		entityManager.addEntity(stateID, instruction5);
		instruction5.setVisible(false);

	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {

		entityManager.renderEntities(container, game, g);

	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		entityManager.updateEntities(container, game, delta);

	}

	@Override
	public int getID() {
		return stateID;
	}

	public void keyPressed(int key, char c) {
		if (key == Input.KEY_ESCAPE)
			g.enterState(Gorillas.MAINMENUSTATE);
	}

	protected RootPane createRootPane() {

		RootPane rp = super.createRootPane();

		// create main menu button
		menuButton = new Button("Back to Main Menu");
		menuButton.addCallback(new Runnable() {

			public void run() {
				g.enterState(Gorillas.MAINMENUSTATE);
			}
		});

		// create next button
		nextButton = new Button("NEXT");
		nextButton.addCallback(new Runnable() {
			public void run() {
				page = page + 1;
				if (page == 2) {
					instruction1.setVisible(false);
					instruction2.setVisible(true);
					nextButton.setVisible(true);
					previousButton.setVisible(true);

				}
				if (page == 3) {

					instruction2.setVisible(false);
					instruction3.setVisible(true);
					nextButton.setVisible(true);
					previousButton.setVisible(true);
				}
				if (page == 4) {
					instruction3.setVisible(false);
					instruction4.setVisible(true);
				}
				if (page == 5) {
					instruction4.setVisible(false);
					instruction5.setVisible(true);
					nextButton.setVisible(false);
					previousButton.setVisible(true);
				}
			}
		});

		// create previous button
		previousButton = new Button("PREVIOUS");
		previousButton.setVisible(false);
		previousButton.addCallback(new Runnable() {
			public void run() {
				page = page - 1;
				if (page == 1) {
					instruction1.setVisible(true);
					instruction2.setVisible(false);
					previousButton.setVisible(false);
					nextButton.setVisible(true);
				}
				if (page == 2) {
					instruction2.setVisible(true);
					instruction3.setVisible(false);

					previousButton.setVisible(true);
					nextButton.setVisible(true);
				}
				if (page == 3) {
					instruction3.setVisible(true);
					instruction4.setVisible(false);
				}
				if (page == 4){
					instruction4.setVisible(true);
					instruction5.setVisible(false);
					previousButton.setVisible(true);
					nextButton.setVisible(true);
				}

			}
		});

		rp.add(menuButton);
		rp.add(nextButton);
		rp.add(previousButton);
		return rp;

	}

	protected void layoutRootPane() {

		menuButton.adjustSize();;
		menuButton.setPosition(650, 550);

		nextButton.setSize(100, 25);
		nextButton.setPosition(450, 550);

		previousButton.setSize(100, 25);
		previousButton.setPosition(250, 550);

	}

}
