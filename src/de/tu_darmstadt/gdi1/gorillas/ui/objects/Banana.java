package de.tu_darmstadt.gdi1.gorillas.ui.objects;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

import de.tu_darmstadt.gdi1.gorillas.game.Game;
import de.tu_darmstadt.gdi1.gorillas.game.Sound;
import de.tu_darmstadt.gdi1.gorillas.main.Gorillas;
import de.tu_darmstadt.gdi1.gorillas.test.setup.TestGorillas;
import de.tu_darmstadt.gdi1.gorillas.ui.states.GamePlayState;
import eea.engine.action.basicactions.Movement;
import eea.engine.component.render.ImageRenderComponent;
import eea.engine.entity.Entity;
import eea.engine.event.basicevents.LoopEvent;

/**
 * 
 * @author Yannick Pferr, Ludwig Koch, Kevin Rueckert
 *
 */
public class Banana extends Entity {

	private Vector2f bananaPos;
	private GamePlayState g;
	private Game game;
	private float speed;
	private Sound banana;
	private float bananaRotation = 0;

	/**
	 * create the Entity of the Banana
	 * 
	 * @param entityID
	 * @param entity
	 * @param g
	 * @param game
	 */
	public Banana(String entityID, Gorilla entity, GamePlayState g, Game game) {
		super(entityID);
		
		this.g = g;
		this.game = game;
		if (!TestGorillas.debug) {
			try {
				// add image of the banana "banana.png"
				this.addComponent(new ImageRenderComponent(new Image(
						"/assets/game/banana.png")));
			} catch (SlickException e) {
				e.printStackTrace();
			}
			setBanana(entity);
		}
		// add sound "Throw.wav"
		banana = new Sound("assets/sounds/Throw.wav", false);
		throwBanana();
	}

	/**
	 * set the start position of the banana
	 * 
	 * @param gorilla
	 *            - the start position is based on this Entity
	 * @return a Vector2f "bananaPos"
	 */
	private Vector2f setBanana(Entity gorilla) {
		if (Game.shootL) {
			this.setPosition(new Vector2f(gorilla.getPosition().x
					- gorilla.getSize().x / 2, gorilla.getPosition().y
					- gorilla.getSize().y / 2 - 10));
		} else {
			this.setPosition(new Vector2f(gorilla.getPosition().x
					+ gorilla.getSize().x / 2, gorilla.getPosition().y
					- gorilla.getSize().y / 2 - 10));
		}
		return bananaPos;
	}

	/**
	 * let the banana fly
	 */
	private void throwBanana() {
		// play sound
		banana.play();
		game.t = 0;
		speed = Float.parseFloat(g.speedIn.getText());
		LoopEvent loop = new LoopEvent();
		Movement ban = new Movement(speed) {

			@Override
			public Vector2f getNextPosition(Vector2f position, float speed,
					float rotation, int delta) {
				// TODO Auto-generated method stub

				g.entityManager.getEntity(g.stateID, "banana").setRotation(bananaRotation);
				bananaRotation += speed / 20;
				// sets half value of speed if map is moon
				if (Background.moon)
					speed /= 2;
				
				rotation = Float.parseFloat(g.angleIn.getText());
				float time = delta * 0.001f;
				float angle = rotation * (float) Math.PI / 180;

				float vX = (float) Math.cos(angle) * speed;
				float vY = (float) Math.sin(angle) * speed;

				float sX = vX * delta / 200f;
				float sY = vY * delta / 200f;

				// wind
				float sWind;
				sWind = 0.5f * 0.1f * Game.wind * (float) Math.pow(game.t, 2);

				float x = 0;
				if (Game.shootL)
					x = position.x + sX + sWind;
				else
					x = position.x - sX + sWind;

				float y = position.y - sY
						+ (0.5f * Game.gravity * (float) Math.pow(game.t, 2));
					
				game.t += time;

				//bounce
				if (y +
						g.entityManager.getEntity(g.stateID, "banana")
						.getSize().y / 2 >= Gorillas.FRAME_HEIGHT) {
					if (this.speed > 30) {
						//play sound
						Sound boing = new Sound("assets/sounds/Boing.wav", false);
						boing.play();
						//bounce back
						game.t = 0;
						this.speed *= 0.8f;	
					}
				}
				
				Vector2f vec = new Vector2f(x, y);

				return vec;
			}
		};
		loop.addAction(ban);
		this.addComponent(loop);
	}
}
