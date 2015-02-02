package net.ausiasmarch.shadows.actors;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import net.ausiasmarch.shadows.game.Assets;

public class Torch extends AbstractActor {

	private float length;
	private Array<TextureRegion> regTorches;
	private List<Cloud> torches;

	private class Cloud extends AbstractActor {
		private TextureRegion regCloud;

		public Cloud() {
		}

		public void setRegion(TextureRegion region) {
			regCloud = region;
		}

		@Override
		public void render(SpriteBatch batch) {
			TextureRegion reg = regCloud;
			batch.draw(reg.getTexture(), position.x + origin.x, position.y
					+ origin.y, origin.x, origin.y, dimension.x, dimension.y,
					scale.x, scale.y, rotation, reg.getRegionX(),
					reg.getRegionY(), reg.getRegionWidth(),
					reg.getRegionHeight(), false, false);
		}
	}

	public Torch(float length) {
		this.length = length;
		init();
	}

	private void init() {
		dimension.set(2.3f, 1.15f);

		regTorches = new Array<TextureRegion>();
		regTorches.add(Assets.instance.levelDecoration.cloud01);
		regTorches.add(Assets.instance.levelDecoration.cloud02);
		regTorches.add(Assets.instance.levelDecoration.cloud03);

		int distFac = 5;
		int numClouds = (int) (length / distFac);
		torches = new ArrayList<Cloud>(2 * numClouds);
		for (int i = 0; i < numClouds; i++) {
			Cloud cloud = spawnCloud();
			cloud.position.x = i * distFac;
			torches.add(cloud);
		}
	}

	private Cloud spawnCloud() {
		Cloud cloud = new Cloud();
		cloud.dimension.set(dimension);
		// selecciona aleatoriamente la imagen
		cloud.setRegion(regTorches.random());
		// posicion
		Vector2 pos = new Vector2();
		pos.x = length + 10; // posicion despues del fin de nivel
		pos.y += 0.7f; 		 // posicion base
		pos.y += MathUtils.random(0.0f, 0.2f)
				* (MathUtils.randomBoolean() ? 1 : -1); // posicion adicional aleatoria
		cloud.position.set(pos);
		// velocidad
		Vector2 speed = new Vector2();
		speed.x += 0.05f; 							// velocidad base		
		speed.x += MathUtils.random(0.0f, 0.10f); 	// velocidad adicional aleatoria
		cloud.terminalVelocity.set(speed);
		speed.x *= -1; 								// mueve a la izquierda
		cloud.velocity.set(speed);

		return cloud;
	}

	@Override
	public void update (float deltaTime) {
		for (int i = torches.size() - 1; i >= 0; i--) {
			Cloud cloud = torches.get(i);
			cloud.update(deltaTime);
			if (cloud.position.x < -10) {
				// nube movida fuera del mundo del juego
				// destruye la nuve y crea otra nueva al final del nivel
				torches.remove(i);
				torches.add(spawnCloud());
			}
		}
	}	
	
	@Override
	public void render(SpriteBatch batch) {
		for (Cloud cloud : torches)
			cloud.render(batch);
	}

}