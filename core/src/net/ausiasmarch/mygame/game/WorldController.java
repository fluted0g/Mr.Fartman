package net.ausiasmarch.mygame.game;

/**
 * WorldController.java
 * Clase controladora de la logica del mundo del juego
 *  
 * @author Luis
 *
 */

import net.ausiasmarch.mygame.util.CameraAssistant;
import net.ausiasmarch.mygame.util.Constants;
import net.ausiasmarch.mygame.util.GamePreferences;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;



public class WorldController extends InputAdapter {
	/** El juego */
	private Game game;
	/* Nivel de juego */
	public Level level;
	/* Vidas jugador */
	public int lives;
	/** Puntuacion */
	public int score;
	/** Puntuacion visual */
	public float scoreVisual;
	/** Nivel completado */
	private boolean levelCompleted;
	/** Nivel final completado */
	private boolean finalLevel;
	/** Ayudante de camara */
	//public CameraAssistant cameraAssistant;
	/** Rectangulo para colisiones de player*/
	private Rectangle r1 = new Rectangle(); 
	/** Rectangulo para colisiones otros actores*/
	private Rectangle r2 = new Rectangle(); 
	/** Tiempo de retardo despues de Game Over */
	private float timeGameOverDelay;
	/** Usa test de colisiones con platform */
	private boolean testCollisionPlatform;
	/** Numero de nivel */
	private int levelNumber;	
	
	
	private static final String TAG = WorldController.class.getName();
	
	public Sprite[] testSprites;
	public int selectedSprite;
	
	public CameraAssistant cameraAssistant;
	

	/** Constructor */
	public WorldController(Game game) {
		this.game = game;
		init();
	}

	/** Inicia el Wold Controller */
	private void init() {
		// Crea el ayudante de camara
		cameraAssistant = new CameraAssistant();
		// Vidas iniciales para player
		lives = Constants.LIVES_START;
		// Tiempo de retardo despues de geme over
		timeGameOverDelay = 0;
		// inicia el nivel
		initLevel();
		Gdx.input.setInputProcessor(this);

		initTestObjects();
	}

	/** Inicia le nivel de juego */
	private void initLevel() {
		score = 0;
		scoreVisual = score;
		levelCompleted = false;
		finalLevel = false;
		testCollisionPlatform = true;		
		// Crea el nivel de juego
		levelNumber = GamePreferences.instance.level;
		level = new Level(levelNumber);	

	}

	/** Actualiza el juego */
	public void update(float deltaTime) {		
		// Si el juego es game over o se completo un nivel
		if (isGameOver() || levelCompleted || finalLevel) {
			// Disminuye el tiempo de espera		
			timeGameOverDelay -= deltaTime;
			if (timeGameOverDelay < 0)
				backToScreen();
		} else {
			// Inicia el manejador de entrada del mundo del juego
			handleInputGame(deltaTime);
		}	
		
		// Comprueba las colisiones
		testCollisions();
		// Actualiza el ayudante de camara
		cameraAssistant.update(deltaTime);

		// Si el juego no es game over y Player esta en el agua
		if (!isGameOver()) {
			lives--;
			// Si es game over...
			if (isGameOver())
				// Inicia tiempo de espera para mostrar game over
				timeGameOverDelay = Constants.TIME_DELAY_GAME_OVER;
			else
				initLevel();
			
			handleDebugInput(deltaTime);
			updateTestObjects(deltaTime);
			cameraAssistant.update(deltaTime);
		}

		// Comprueba la puntuacion
		if (scoreVisual < score) {
			//scoreVisual = score;
			scoreVisual = Math.min(score, scoreVisual + 200 * deltaTime);
		}
	
	}
	


	// Ve a la pantalla...
	private void backToScreen() {
		// Si el juego finalizo perdiendo el jugador
		if (isGameOver()) {

			return;
		}
		// Si se completo un nivel
		if (levelCompleted) {

			return;
		}
		// Si se completo el ultimo nivel
		if (finalLevel) {
			// game.setScreen(new GameOverWinnerScreen(game));
		}
	}

	// Comprueba si el juego es game over porque player perdio todas sus vidas
	public boolean isGameOver() {
		return lives < 0;
	}
	
	// Comprueba si un nivel ha completado
	public boolean isLevelCompleted() {
		return levelCompleted;
	}


	// -----------------------------------------------------------------
	// TESTS DE COLISIONES
	// -----------------------------------------------------------------
	private void testCollisions() {
		
	}

	/*----------------------------------------------------------------
		COLISIONES
	 ----------------------------------------------------------------*/


	/*--------------------------------------------------------------------------
	 *  Controles de entradas de usuario 
	 --------------------------------------------------------------------------*/

	// Manejador de entradas para Player
	private void handleInputGame(float deltaTime) {
	

	}
	
	//DEBUGGING STUFF
	private void updateTestObjects(float deltaTime) {
		//Get current rotation from selected sprite
		float rotation = testSprites[selectedSprite].getRotation();
		//Rotate sprite by 90 degrees per second
		rotation += 90 * deltaTime;
		//Wrap around at 360 degrees
		rotation %= 360;
		//Set new rotation calue to selected sprite
		testSprites[selectedSprite].setRotation(rotation);
		}
	
	private void initTestObjects() {
		//Create new array for 5 sprites
		testSprites = new Sprite[5];
		//Create empty POT-sized mixmap with 8 bit RGBA pixel data
		int width = 32;
		int height = 32;
		Pixmap pixmap = createProceduralPixmap(width, height);
		//Create a new texture from pixmap data
		Texture texture = new Texture(pixmap);
		//Create new sprites using the just created texture
		for (int i = 0; i < testSprites.length; i++) {
		Sprite spr = new Sprite(texture);
		//Define sprite size to be 1m by 1m in the game world;
		spr.setSize(1, 1);
		//Set origin to sprite center
		spr.setOrigin(spr.getWidth() /2.0f, spr.getHeight() / 2.0f);
		//Calculate random position for sprite
		float randomX = MathUtils.random(-2.0f, 2.0f);
		float randomY = MathUtils.random(-2.0f, 2.0f);
		spr.setPosition(randomX, randomY);
		//Put the sprite into the array
		testSprites[i] = spr;
		}
		//Set the first sprite as the selected one
		selectedSprite = 0;
		}
	
	private Pixmap createProceduralPixmap(int width, int height) {
		Pixmap pixmap = new Pixmap(width, height, Format.RGBA8888);
		//Fill square with red color at 50% opacity
		pixmap.setColor(1, 0, 0, 0.5f);
		pixmap.fill();
		//draw a yellow-colored X shape on square
		pixmap.setColor(1, 1, 0, 1);
		pixmap.drawLine(0, 0, width, height);
		pixmap.drawLine(width, 0, 0, height);
		//Draw a cyan-colored border around square
		pixmap.setColor(0, 1, 1, 1);
		pixmap.drawRectangle(0, 0, width, height);
		return pixmap;
		}
	
	private void moveSelectedSprite(float x, float y) {
		testSprites[selectedSprite].translate(x, y);
		}
	
	@Override
	public boolean keyUp (int keycode) {
	// Reset game world
	if (keycode == Keys.R) {
	init();
	Gdx.app.debug(TAG, "Game world resetted");
	} else if (keycode == Keys.SPACE) {
		selectedSprite = (selectedSprite + 1) % testSprites.length;
		// Update camera's target to follow the currently
		// selected sprite
		if (cameraAssistant.hasTarget()) {
		cameraAssistant.setTarget(testSprites[selectedSprite]);
		}
		Gdx.app.debug(TAG, "Sprite #" + selectedSprite + "selected");
	}
	// Toggle camera follow
	else if (keycode == Keys.ENTER) {
	cameraAssistant.setTarget(cameraAssistant.hasTarget() ? null :
	testSprites[selectedSprite]);
	Gdx.app.debug(TAG, "Camera follow enabled: " +
	cameraAssistant.hasTarget());
	}
	return false;
	}
	
	private void handleDebugInput (float deltaTime) {
		if (Gdx.app.getType() != ApplicationType.Desktop) return;
		// Selected Sprite Controls
		float sprMoveSpeed = 5 * deltaTime;
		if (Gdx.input.isKeyPressed(Keys.A)) moveSelectedSprite(
		-sprMoveSpeed, 0);
		if (Gdx.input.isKeyPressed(Keys.D))
		moveSelectedSprite(sprMoveSpeed, 0);
		if (Gdx.input.isKeyPressed(Keys.W)) moveSelectedSprite(0,
		sprMoveSpeed);
		if (Gdx.input.isKeyPressed(Keys.S)) moveSelectedSprite(0,
		-sprMoveSpeed);
		// Camera Controls (move)
		float camMoveSpeed = 5 * deltaTime;
		float camMoveSpeedAccelerationFactor = 5;
		if (Gdx.input.isKeyPressed(Keys.SHIFT_LEFT)) camMoveSpeed *=
		camMoveSpeedAccelerationFactor;
		if (Gdx.input.isKeyPressed(Keys.LEFT)) moveCamera(-camMoveSpeed,
		0);
		if (Gdx.input.isKeyPressed(Keys.RIGHT)) moveCamera(camMoveSpeed,
		0);
		if (Gdx.input.isKeyPressed(Keys.UP)) moveCamera(0, camMoveSpeed);
		if (Gdx.input.isKeyPressed(Keys.DOWN)) moveCamera(0,
		-camMoveSpeed);
		if (Gdx.input.isKeyPressed(Keys.BACKSPACE))
		cameraAssistant.setPosition(0, 0);
	}
		
		private void moveCamera (float x, float y) {
		x += cameraAssistant.getPosition().x;
		y += cameraAssistant.getPosition().y;
		cameraAssistant.setPosition(x, y);
		}
	
}
