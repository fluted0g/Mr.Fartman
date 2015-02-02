package net.ausiasmarch.mygame.actors;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.graphics.g2d.Animation;

public abstract class AbstractActor {

	public Vector2 position;			// posicion
	public Vector2 dimension;			// dimensione
	public Vector2 origin;				// origen
	public Vector2 scale;				// escalado
	public float rotation;				// rotacion en grados
	public Vector2 velocity;			// velocidad
	public Vector2 terminalVelocity;	// maxima velocidad
	public Vector2 friction;			// fuerza opuesta que ralentiza la velocidad hasta que es igual a cero
	public Vector2 acceleration;		// aceleracion
	public Rectangle bounds;			// limites del actor para la deteccion de colisiones
	public float stateTime;				// Tiempo de inicio de animacion
	public Animation animation;			// animacion	
	public boolean removed;			    // actor es eliminado 

	public AbstractActor() {
		position = new Vector2();
		dimension = new Vector2(1, 1);
		origin = new Vector2();
		scale = new Vector2(1, 1);
		velocity = new Vector2();		
		terminalVelocity = new Vector2(1, 1);
		friction = new Vector2();
		acceleration = new Vector2();
		bounds = new Rectangle();		
		rotation = 0;
	}
		
	public void setAnimation(Animation animation){
		this.animation = animation;
		stateTime = 0;
	}
	
	// Actualiza 
	public void update (float deltaTime) {
		stateTime += deltaTime;
		// Actualiza movimiento
		updateMotionX(deltaTime);
		updateMotionY(deltaTime);
		// Mueve a la nueva posicion
		position.x += velocity.x * deltaTime;
		position.y += velocity.y * deltaTime;	
	}

	// Actualiza el movimiento en x
	protected void updateMotionX (float deltaTime) {
		if (velocity.x != 0) {
			// Aplica friccion
			if (velocity.x > 0) {
				velocity.x = Math.max(velocity.x - friction.x * deltaTime, 0);
			} else {
				velocity.x = Math.min(velocity.x + friction.x * deltaTime, 0);
			}
		}
		// Aplica aceleracion cuando la velocidad es cero
		velocity.x += acceleration.x * deltaTime;
		// Hace que la velocidad del objeto no exceda del valor de la velocidad terminal
		velocity.x = MathUtils.clamp(velocity.x, -terminalVelocity.x, terminalVelocity.x);
	}

	// Actualiza el movimiento en y
	protected void updateMotionY (float deltaTime) {
		if (velocity.y != 0) {
			// Aplica friccion
			if (velocity.y > 0) {
				velocity.y = Math.max(velocity.y - friction.y * deltaTime, 0);
			} else {
				velocity.y = Math.min(velocity.y + friction.y * deltaTime, 0);
			}
		}
		// Aplica aceleracion cuando la velocidad es cero
		velocity.y += acceleration.y * deltaTime;
		// Hace que la velocidad del objeto no exceda del valor de la velocidad terminal
		velocity.y = MathUtils.clamp(velocity.y, -terminalVelocity.y, terminalVelocity.y);
	}

	// Renderiza
	public abstract void render (SpriteBatch batch);
	
}
