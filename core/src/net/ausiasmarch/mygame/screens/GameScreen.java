package net.ausiasmarch.mygame.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL30;
import net.ausiasmarch.mygame.game.*;

public class GameScreen extends AbstractScreen {
	/** Contralador */
	private WorldController worldController;
	/** Renderizador */
	private WorldRenderer worldRenderer;
	/** Para hacer una pausa en Android */
	private boolean paused;

	/** Constructor */
	public GameScreen(Game game) {
		super(game);
	}

	@Override
	public void show() {
		// Crea un multiprocesador de entradas
		InputMultiplexer multiplexer = new InputMultiplexer();
		// Crea el controlador y el renderizador
		worldController = new WorldController(game);
		worldRenderer = new WorldRenderer(worldController);
		// Agrega worldController a multiplexer
		multiplexer.addProcessor(worldController);
		// Agrega multiplexer como procesador de entradas
		Gdx.input.setInputProcessor(multiplexer); 
	}
	
	/** Renderiza */
	@Override
	public void render(float deltatime) {
		float delta = Math.min(Gdx.graphics.getDeltaTime(), 1.0f / 60.0f);
		// Si no se ha hecho pausa ...
		if (!paused) {
		// Actualiza el mundo del juego
			worldController.update(delta); 
		}

		// Limpia la pantalla 
		Gdx.gl.glClearColor(0f, 0f, 0f, 1);
		Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);

		// Renderiza el mundo del juego
		worldRenderer.render();
	}

	@Override
	public void resize(int width, int height) {
		// Actualiza dimensiones del ViewPort
		worldRenderer.resize(width, height);
	}

	@Override
	public void hide() {
		worldRenderer.dispose();
		// Establece si el botón BACK en Android debe ser capturado. 
		Gdx.input.setCatchBackKey(false);	
	}

	@Override
	public void pause() {
		paused = true;
	}

	@Override
	public void resume() {
		super.resume();
		paused = false;
	}
	
}

