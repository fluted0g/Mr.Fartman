package net.ausiasmarch.mygame.util;

import net.ausiasmarch.mygame.actors.AbstractActor;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;






/**
 * CameraAssistants.java
 * Controla la camara para un determinado Actor  
 * 
 * @author Luis
 *
 */
public class CameraAssistant {
	/** Velocidad de seguimiento */
	private final float FOLLOW_SPEED = 4.0f;
	/** Posicion de la camara */
	private Vector2 position;
	/** Actor destinatario */
	//private AbstractActor target;
	
	private Sprite target;

	/** Constructor */
	public CameraAssistant () {
		position = new Vector2();
	}

	/** Actualiza la camara */
	public void update (float deltaTime) {
		/*if (!hasTarget()) return;		
		position.lerp(target.position, FOLLOW_SPEED * deltaTime);				
		// Previene que la camara se mueva muy hacia abajo
		position.y = Math.max(-1f, position.y);*/
		
		if (!hasTarget()) return;
		position.x = target.getX() + target.getOriginX();
		position.y = target.getY() + target.getOriginY();
	}

	/** Establece la posicion de la camara */
	public void setPosition (float x, float y) {
		this.position.set(x, y);
	}

	/** Obtiene la posicion de la camara */
	public Vector2 getPosition () {
		return position;
	}

	/** Establece el actor destinatario de la camara */
	/*public void setTarget (AbstractActor target) {
		this.target = target;
	}*/
	
	public void setTarget(Sprite sprite) {
		this.target = sprite;
	}
	

	/** Obtiene el actor destinatario de la camara */
	/*public AbstractActor getTarget () {
		return target;
	}*/
	

	public Sprite getTarget () {
		return target;
	}
	
	/** Comprueba si la camara tiene destinatario */
	public boolean hasTarget () {
		return target != null;
	}
	
	/** Comprueba si la camara tiene un actor destinatario */
	public boolean hasTarget (AbstractActor target) {
		return hasTarget() && this.target.equals(target);
	}
	
	/** Aplica una determinada camara */
	public void applyTo (OrthographicCamera camera) {
		camera.position.x = position.x;
		camera.position.y = position.y;
		camera.update();
	}
	
	/** Aplica una determinada camara solo en eje X*/
	public void applyToAxisX (OrthographicCamera camera) {
		camera.position.x = position.x;
		camera.update();
	}
	
	/** Aplica una determinada camara solo en eje Y*/
	public void applyToAxisY (OrthographicCamera camera) {
		camera.position.y = position.y;
		camera.update();
	}
	
}
