package net.ausiasmarch.mygame.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

/**
 * GamePreferences
 * Carga y guarda las preferencias del juego
 * 
 * @author Luis
 *
 */
public class GamePreferences {
	public static final GamePreferences instance = new GamePreferences();
	public boolean sound;
	public boolean music;
	public float volSound;
	public float volMusic;
	public int charSkin;
	public int level = 1;
	public boolean accelerometer;	
	private static Preferences prefs;

	/** Singleton: previene la instanciacion desde otras clases */
	private GamePreferences () {
		prefs = Gdx.app.getPreferences(Constants.PREFERENCES);
	}

	/** Carga las preferencias del juego */
	public  void load () {		
		accelerometer = prefs.getBoolean("accelerometer", false);
		sound = prefs.getBoolean("sound", true);
		music = prefs.getBoolean("music", true);
		level = prefs.getInteger("level",1);
	}

	/** Guarda las preferencias del juego */
	public  void save () {
		prefs.putBoolean("accelerometer", accelerometer);
		prefs.putBoolean("sound", sound);
		prefs.putBoolean("music", music);
		prefs.putInteger("level", level);
		prefs.flush();
	}

}
