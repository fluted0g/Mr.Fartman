package net.ausiasmarch.fartman.actors;

//import com.badlogic.gdx.Gdx;
//import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import net.ausiasmarch.fartman.game.Assets;
import net.ausiasmarch.fartman.game.ViewDirection;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Player extends AbstractActor {
	// Tiempo maximo y minimo del salto
	private final float JUMP_TIME_MAX = 0.7f;
	private final float JUMP_TIME_MIN = 0.1f;
	// Animacion normal y corriendo
	public Animation movingRight;
	public Animation movingLeft;
	public Animation staticRight;
	public Animation staticLeft;
	// Vida perdida
	public boolean removeLife;

	// Direcciones de movimiento
	private ViewDirection viewDirection;
	
	// Enumerado con los valores de estado del salto
	public enum JUMP_STATE {
		GROUNDED, 		// Player esta de pie sobre la plataforma
		FALLING, 		// Player esta cayendo
		JUMP_RISING, 	// Player ha iniciado un salto y esta elevandose
		JUMP_FALLING 	// Player cae despues de un salto 
	}
	
	// Estado del salto
	public JUMP_STATE jumpState; 		    
	// Duracion del salto
	public float timeJumping; 				
	// Dimensiones normal y corriendo
	public Vector2 dimPlayerRight = new Vector2(0.91f,1.21f); 
	public Vector2 dimPlayerLeft = new Vector2(0.91f,1.21f);  
	
	// Constructor
	public Player() {
		init();
	}

	// Inicia le Player
	public void init() {
		// Dimensiones del actor
		dimension.set(dimPlayerRight);
		// Animaciones
		movingRight = Assets.instance.player.movingRight;
		movingLeft = Assets.instance.player.movingLeft;		
		staticRight = Assets.instance.player.staticRight;
		staticLeft = Assets.instance.player.staticLeft;
		setAnimation(staticRight);
		// Origen de la imagen centrada en el actor
		origin.set(dimension.x / 2, dimension.y / 2);
		// Limites del rectangulo para deteccion de collisiones
		bounds.set(0, 0, dimension.x, dimension.y);
		// Valores fisicos
		terminalVelocity.set(6.0f, 6.0f);
		friction.set(12.0f, 0.0f);
		acceleration.set(0.0f, -25.0f);
		// Direccion del movimiento
		setViewDirection(ViewDirection.RIGHT);
		// Estado del salto
		jumpState = JUMP_STATE.FALLING;
		// Tiempo del salto
		timeJumping = 0;
		// Pierde una vida
		removeLife = false;	
		// Particulas
	//dustParticles.load(Gdx.files.internal("particles/dust.pfx"),Gdx.files.internal("particles"));
	}

	@Override
	public void update(float deltaTime) {
		super.update(deltaTime);
		if (velocity.x != 0) {
			setViewDirection(velocity.x < 0 ? ViewDirection.LEFT : ViewDirection.RIGHT);
		}

		if (animation == movingRight) {
			dimension.set(dimPlayerRight);
		} else if (animation == movingLeft) {
			dimension.set(dimPlayerLeft);
		}
		
		if (animation == staticRight) {
			dimension.set(dimPlayerRight);
		}
		
		else if (animation == staticLeft) {
			dimension.set(dimPlayerLeft);
		}
	}

	@Override
	protected void updateMotionY(float deltaTime) {
		// Segun sea el estado del salto
		switch (jumpState) {
		// En pie sobre la plataforma
		case GROUNDED:
			jumpState = JUMP_STATE.FALLING; // salto cayendo	

			break;
		// Salto iniciado y aumentando
		case JUMP_RISING:
			// Registra el tiempo de salto
			timeJumping += deltaTime;
			// �Cuanto tiempo queda de salto?
			if (timeJumping <= JUMP_TIME_MAX) {
				// Aun saltando
				velocity.y = terminalVelocity.y;
			}
			break;
		case FALLING:
			break;
		case JUMP_FALLING:
			// Agrega deltaTime al tiempo del salto
			timeJumping += deltaTime;
			// Ir a la altura minima si se ha pulsado poco la tecla de salto
			if (timeJumping > 0 && timeJumping <= JUMP_TIME_MIN) {
				// Aun saltando
				velocity.y = terminalVelocity.y;
			}
		}
		
		if (jumpState != JUMP_STATE.GROUNDED) {
		//	dustParticles.allowCompletion();
			super.updateMotionY(deltaTime);
		}
	}

	@Override
	public void render(SpriteBatch batch) {
		TextureRegion reg = animation.getKeyFrame(stateTime, true);

		
		
		// Dibuja player
		batch.draw(reg.getTexture(), position.x, position.y, origin.x,
				origin.y, dimension.x, dimension.y, scale.x, scale.y, rotation,
				reg.getRegionX(), reg.getRegionY(), reg.getRegionWidth(),
				reg.getRegionHeight(), getViewDirection() == ViewDirection.LEFT, false);

		// Resetea el color

	}

	// Establece el salto
	public void setJumping(boolean jumpKeyPressed) {
		switch (jumpState) {
		case GROUNDED: // Sobre una plataforma
			if (jumpKeyPressed) {
				// Empieza a contar el tiempo de salto
				timeJumping = 0;
				// Inicia el salto
				jumpState = JUMP_STATE.JUMP_RISING;
				// Si ha persido una vida				
				terminalVelocity.y = removeLife ? 2.0f: 4.5f;
			}
			break;
		case JUMP_RISING: // Salto elevandose
			if (!jumpKeyPressed) {
				jumpState = JUMP_STATE.JUMP_FALLING; // Salto cayendo
			}
			break;
	
		case FALLING: case JUMP_FALLING:  // Salto cayendo
		}
	}

	public ViewDirection getViewDirection() {
		return viewDirection;
	}

	public void setViewDirection(ViewDirection viewDirection) {
		this.viewDirection = viewDirection;
	}

}