package net.ausiasmarch.mygame.game;

/**
 * WorldRenderer.java
 * Clase para la renderizacion del mundo del juego y 
 * la interfaz grafica de usuario
 *  
 * @author Luis
 *
 */

import net.ausiasmarch.mygame.util.Constants;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;

public class WorldRenderer implements Disposable {
	/** Camara del mundo del juego */
	private OrthographicCamera camera;
	/** Camara de la interfaz de usuario */
	private OrthographicCamera cameraGUI;
	/** Buffer de sprites para el renderizado */
	private SpriteBatch batch;
	/** Controlador del mundo del juego */
	private WorldController worldController;
	
	public static Texture backgroundTexture;
    public static Sprite backgroundSprite;

	// Constructor
	public WorldRenderer(WorldController worldController) {
		this.worldController = worldController;
		init();
	}

	/** Inicia el juego */
	private void init() {
		batch = new SpriteBatch();
		// Crea la camara para el mundo del juego
		camera = new OrthographicCamera(Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT);	
		camera.position.set(0, 0, 0);
		camera.update();
		// Crea la camara para el GUI
		cameraGUI = new OrthographicCamera(Constants.VIEWPORT_GUI_WIDTH, Constants.VIEWPORT_GUI_HEIGHT);
		cameraGUI.position.set(0, 0, 0);
		cameraGUI.setToOrtho(true);
		cameraGUI.update();
        backgroundTexture = new Texture("images/background.png");
        backgroundSprite =new Sprite(backgroundTexture);
        backgroundSprite.setBounds(0, 0, 1280,768);
        backgroundSprite.rotate(180);
	}
	
/* CODIGO ORIGINAL DE STACKOVERFLOW
 * http://stackoverflow.com/questions/17623373/libgdx-image-background
  	private void loadTextures() {
        backgroundTexture = new Texture("images/background.png");
        backgroundSprite =new Sprite(backgroundTexture);

    }
*/
	public void renderBackground(SpriteBatch sb) {
        batch.begin();
		backgroundSprite.draw(sb);
		batch.end();
    }
	
	public void renderTestObjects () {
		worldController.cameraAssistant.applyTo(camera);
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		for(Sprite sprite : worldController.testSprites) {
		sprite.draw(batch);
		}
		batch.end();
		}

	/** Renderiza el juego */
	public void render() {
		renderBackground(batch);
		renderWorld(batch);
		renderGui(batch);
	}

	/** Renderiza el mundo del juego */
	private void renderWorld(SpriteBatch batch) {
		
		worldController.cameraAssistant.applyToAxisX(camera);
		worldController.cameraAssistant.applyToAxisY(camera);

		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		worldController.level.render(batch);
		batch.end();
	}

	/** Renderiza el GUI (interfaz de usuario) */
	private void renderGui(SpriteBatch batch) {
		batch.setProjectionMatrix(cameraGUI.combined);
		batch.begin();
		 //renderGuiScore(batch); 		  // Renderiza la puntuacion
		 //renderGuiLives(batch);	 		  // Renderiza las vidas
		 
		 
		 
		 
		 
		
		 //renderGuiGameOverMessage(batch); // Renderiza el texto game over
		batch.end();
	}

	/** Renderiza la puntuacion (score) */
	private void renderGuiScore(SpriteBatch batch) {
		float x = 30, y = 18;		
		Assets.instance.fonts.defaultNormal.setColor(Color.BLACK);
		Assets.instance.fonts.defaultNormal.draw(batch,
				Integer.toString((int) worldController.scoreVisual), x + 2, y + 2);
		Assets.instance.fonts.defaultNormal.setColor(Color.WHITE);
		Assets.instance.fonts.defaultNormal.draw(batch,
				Integer.toString((int) worldController.scoreVisual), x , y);
	}

	/** Renderiza las vidas de Player */
	private void renderGuiLives(SpriteBatch batch) {
		
		
		
		
		
		
		
	}


	
	/** Renderiza game Over **/
	private void renderGuiGameOverMessage (SpriteBatch batch) {
		float x = cameraGUI.viewportWidth / 2;
		float y = cameraGUI.viewportHeight / 2;
		if (worldController.isGameOver()) {
			BitmapFont fontGameOver = Assets.instance.fonts.defaultBig;
			fontGameOver.setColor(1, 0.75f, 0.25f, 1);
			fontGameOver.drawMultiLine(batch, "GAME OVER", x, y, 1,BitmapFont.HAlignment.CENTER);
			fontGameOver.setColor(1, 1, 1, 1);
		} else if (worldController.isLevelCompleted()){
			BitmapFont fontGameOver = Assets.instance.fonts.defaultBig;
			fontGameOver.setColor(1, 0, 0, 1);
			fontGameOver.drawMultiLine(batch, "LEVEL COMPLETED", x, y, 1,BitmapFont.HAlignment.CENTER);
			fontGameOver.setColor(1, 1, 1, 1);
		}
	}
	
	
	/** Al cambiar las dimensiones de la pantalla */
	public void resize(int width, int height) {
		camera.viewportWidth = (Constants.VIEWPORT_HEIGHT / (float)height) * (float)width;	
		camera.update();		
		cameraGUI.viewportHeight = Constants.VIEWPORT_GUI_HEIGHT;
		cameraGUI.viewportWidth = (Constants.VIEWPORT_GUI_HEIGHT / (float)height) * (float)width;
		cameraGUI.position.set(cameraGUI.viewportWidth / 2, cameraGUI.viewportHeight / 2, 0);		cameraGUI.position.set(cameraGUI.viewportWidth / 2, cameraGUI.viewportHeight / 2, 0);		cameraGUI.position.set(cameraGUI.viewportWidth / 2, cameraGUI.viewportHeight / 2, 0);
		cameraGUI.update();
	}

	@Override
	public void dispose() {
		batch.dispose();
	}

	
}
