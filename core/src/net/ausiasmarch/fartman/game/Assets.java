package net.ausiasmarch.fartman.game;

/**
 * Assets.java
 * Clase para cargar todos los recursos del juego
 *  
 * @author Luis
 *
 */

import net.ausiasmarch.fartman.util.Constants;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.utils.Disposable;

public class Assets implements Disposable, AssetErrorListener {
	public static final String TAG = Assets.class.getName();

	public static final Assets instance = new Assets();
	// Gestos de carga de los recursos del juego
	private AssetManager assetManager;
	// Clases para recursos
	public AssetFonts fonts;
	public AssetWall walls;
	public AssetFloor floors;
	public AssetLevelDecoration levelDecoration;
	public AssetTorch torches;
	
	
	
	


	// singleton: previene la instanciacion desde otras clases
	private Assets() {
	}

	// CLASES ANIDADAS .............................................................
	
	/** Clase para las fonts */
	public class AssetFonts {
		public final BitmapFont defaultSmall;
		public final BitmapFont defaultNormal;
		public final BitmapFont defaultBig;

		public AssetFonts() {
			// crea tres bitmap font para fonts de 19px
			defaultSmall = new BitmapFont(
					Gdx.files.internal("images/verdana39.fnt"), true);
			defaultNormal = new BitmapFont(
					Gdx.files.internal("images/verdana39.fnt"), true);
			defaultBig = new BitmapFont(
					Gdx.files.internal("images/verdana39.fnt"), true);
			// establece las dimensiones de las fonts
			defaultSmall.setScale(0.50f, 0.49f);
			defaultNormal.setScale(0.9f, 0.8f);
			defaultBig.setScale(1.5f, 1.4f);
			// activa el filtro linear para el suavizado de las fonts
			defaultSmall.getRegion().getTexture()
					.setFilter(TextureFilter.Linear, TextureFilter.Linear);
			defaultNormal.getRegion().getTexture()
					.setFilter(TextureFilter.Linear, TextureFilter.Linear);
			defaultBig.getRegion().getTexture()
					.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		}
	}
	
	public class AssetWall {
		
		public final AtlasRegion wall;
		
		public AssetWall(TextureAtlas atlas) {
			wall = atlas.findRegion("wall");
		}
	}
	
	public class AssetFloor {
		
		public final AtlasRegion floor;
		
		public AssetFloor(TextureAtlas atlas) {
			floor = atlas.findRegion("floor");
		}
		
	}
	
	public class AssetTorch {
		public final AtlasRegion torch;
		
		public AssetTorch(TextureAtlas atlas) {
			torch = atlas.findRegion("torch");
		}
	}
	
	public class AssetLevelDecoration {
		public final AtlasRegion torch;
		
		public AssetLevelDecoration(TextureAtlas atlas) {
			torch = atlas.findRegion("torch");
		}
	}
	
	

	/** Clase para cargar los recursos */
	public void init(AssetManager assetManager) {
		//TextureAtlas atlas;
		
		this.assetManager = assetManager;	
		// establece el manejador de errores para la carga de texturas
		assetManager.setErrorListener(this);
		
		// carga el texturel atlas
	assetManager.load(Constants.TEXTURE_ATLAS_ACTORS, TextureAtlas.class);

		// inicia la carga de assets y espera hasta finalizar
		assetManager.finishLoading();
		
		// Obtiene el texture atlas
		TextureAtlas atlas = assetManager.get(Constants.TEXTURE_ATLAS_ACTORS);

		// Activa el filto linear en las texturas para el suavizado de pixeles
		for (Texture t : atlas.getTextures()) {
			t.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		}

		// Crea los recursos del juego
		fonts = new AssetFonts();
		walls = new AssetWall(atlas);
		floors = new AssetFloor(atlas);
		torches = new AssetTorch(atlas);
		levelDecoration = new AssetLevelDecoration(atlas);
		
		
		
		
		
		
	}

	@Override
	public void dispose() {
		assetManager.dispose();
		fonts.defaultSmall.dispose();
		fonts.defaultNormal.dispose();
		fonts.defaultBig.dispose();
	}

	@Override
	public void error(@SuppressWarnings("rawtypes") AssetDescriptor asset, Throwable throwable) {
		Gdx.app.error(TAG, "Couldn't load asset '" + asset + "'", (Exception) throwable);
	}

}
