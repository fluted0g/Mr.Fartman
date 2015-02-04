package net.ausiasmarch.fartman.desktop;

import net.ausiasmarch.fartman.MrFartmanMain;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

/**
 * Main.java
 * Clase principal de arranque del juego en Desktop
 *  
 * @author Luis
 *
 */

public class Main {
	public static void main (String[] arg) {
		// Crea la Configuracion del juego
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();					
		float perc = 0.80f;
		config.title = "MrFartman";				
		config.width =  (int) (1280 * perc);  				
		config.height = (int) (768 * perc); 
		config.resizable = true;	
		// Crea la aplicacion
		new LwjglApplication(new MrFartmanMain(), config);
	}
}
