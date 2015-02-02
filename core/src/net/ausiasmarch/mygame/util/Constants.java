package net.ausiasmarch.mygame.util;

/**
 * Constants.java
 * Clase que define todas las constantes generales del juego
 * 
 * @author Luis
 *
 */
public class Constants {
	/** Ancho visible del mundo juego en metros */
	public static final float VIEWPORT_WIDTH = 6;
	/** Alto visible del mundo juego en metros */
	public static final float VIEWPORT_HEIGHT = 6;	
	/** Ancho de GUI */
	public static final float VIEWPORT_GUI_WIDTH = 1280;
	/** Alto del GUI */
	public static final float VIEWPORT_GUI_HEIGHT = 768;	
	/** Ficheros atlas de texturas **/
	public static final String TEXTURE_ATLAS_ACTORS = "images/farts.pack";  
	public static final String TEXTURE_ATLAS_UI = "images/mygame-ui.pack";
	public static final String TEXTURE_ATLAS_LIBGDX_UI = "images/uiskin.atlas";
	/** Fichero de descripcion para skin */
	public static final String SKIN_LIBGDX_UI = "images/uiskin.json";
	/** Fichero de descripcion para skin */	
	public static final String SKIN_SHADOWS_UI = "images/mygame-ui.json";		
	/** Fichero de map nivel */
	public static final String LEVEL = "levels/level-.png";
	/** Numero de vidas iniciales de player */
	public static final int LIVES_START = 3;	
	/** Fichero de preferencias del juego */
    public static final String PREFERENCES = "shadows.prefs";  
	/** Tiempo de retardo despues de game over lost */
	public static final float TIME_DELAY_GAME_OVER = 1;	
	/** Tiempo de retardo despues game over */ 
	public static final float TIME_DELAY_LEVEL_FINISHED = 2;

}
