package net.ausiasmarch.fartman;

import net.ausiasmarch.fartman.game.Assets;
import net.ausiasmarch.fartman.screens.GameScreen;
import net.ausiasmarch.fartman.util.GamePreferences;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;

/**
 * NightShadowsMain.java
 * Clase Principal del juego
 * 1) Carga los recursos del juego: archivos de imagene, musica y sonido.
 * 2) Carga las preferencias.
 * 3) Muestra la pantalla de inicio del juego 
 *  
 * @author 
 *
 */

public class MrFartmanMain extends Game {

	@Override
	public void create() {
		// Tipo de mensaje a mostrar durante la fase de dasarrollo
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		// Garga recursos
		Assets.instance.init(new AssetManager());
		// Carga  preferencias 
		GamePreferences.instance.load();		
		// Inicia pantalla menu principal
		setScreen(new GameScreen(this));
		
	}
}