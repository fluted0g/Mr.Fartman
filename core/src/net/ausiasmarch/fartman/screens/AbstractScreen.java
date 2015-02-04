package net.ausiasmarch.fartman.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;

import net.ausiasmarch.fartman.game.Assets;

/**
 * AbtractScreen.java
 * Todas las pantallas del juego deberan heredear de esta 
 * 
 * @author Luis
 *
 */
public abstract class AbstractScreen implements Screen {
	
	protected Game game;

	public AbstractScreen (Game game) {
		this.game = game;
	}
	
	public abstract void render(float deltatime);
	public abstract void resize(int width, int height);
	public abstract void show();
	public abstract void hide();
	public abstract void pause();

	
	@Override
	public void resume() {
		Assets.instance.init(new AssetManager());
	}

	@Override
	public void dispose() {
		Assets.instance.dispose();
	}

}
