package net.ausiasmarch.fartman.actors;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import net.ausiasmarch.fartman.game.Assets;

public class Torches extends AbstractActor {

	private float length;
	private Array<TextureRegion> regTorches;
	private List<Torch> torches;

	private class Torch extends AbstractActor {
		private TextureRegion regTorch;

		public Torch() {
		}

		public void setRegion(TextureRegion region) {
			regTorch = region;
		}

		@Override
		public void render(SpriteBatch batch) {
			TextureRegion reg = regTorch;
			batch.draw(reg.getTexture(), position.x + origin.x, position.y
					+ origin.y, origin.x, origin.y, dimension.x, dimension.y,
					scale.x, scale.y, rotation, reg.getRegionX(),
					reg.getRegionY(), reg.getRegionWidth(),
					reg.getRegionHeight(), false, false);
		}
	}

	public Torches(float length) {
		this.length = length;
		init();
	}

	private void init() {
		dimension.set(1f, 1f);

		regTorches = new Array<TextureRegion>();
		regTorches.add(Assets.instance.levelDecoration.torch);
		//regTorches.add(Assets.instance.levelDecoration.cloud02);
		//regTorches.add(Assets.instance.levelDecoration.cloud03);

		int distFac = 5;
		int numTorches = (int) (length / distFac);
		torches = new ArrayList<Torch>(2 * numTorches);
		for (int i = 0; i < numTorches; i++) {
			Torch torch = spawnTorch();
			torch.position.x = i * distFac;
			torches.add(torch);
		}
	}

	private Torch spawnTorch() {
		Torch torch = new Torch();
		torch.dimension.set(dimension);
		// selecciona aleatoriamente la imagen
		torch.setRegion(regTorches.random());
		// posicion
		Vector2 pos = new Vector2();
		pos.x = length + 10; // posicion despues del fin de nivel
		pos.y += 0.7f; 		 // posicion base
		pos.y += MathUtils.random(0.0f, 0.2f)
				* (MathUtils.randomBoolean() ? 1 : -1); // posicion adicional aleatoria
		torch.position.set(pos);
		// velocidad
		Vector2 speed = new Vector2();
		speed.x += 0.05f; 							// velocidad base		
		speed.x += MathUtils.random(0.0f, 0.10f); 	// velocidad adicional aleatoria
		torch.terminalVelocity.set(speed);
		speed.x *= -1; 								// mueve a la izquierda
		torch.velocity.set(speed);

		return torch;
	}

	@Override
	public void update (float deltaTime) {
		for (int i = torches.size() - 1; i >= 0; i--) {
			Torch torch = torches.get(i);
			torch.update(deltaTime);
			if (torch.position.x < -10) {
				// nube movida fuera del mundo del juego
				// destruye la nuve y crea otra nueva al final del nivel
				torches.remove(i);
				torches.add(spawnTorch());
			}
		}
	}	
	
	@Override
	public void render(SpriteBatch batch) {
		for (Torch torch : torches)
			torch.render(batch);
	}

}