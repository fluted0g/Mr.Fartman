package net.ausiasmarch.fartman.game;

import net.ausiasmarch.fartman.actors.AbstractActor;
import net.ausiasmarch.fartman.actors.Floor;
import net.ausiasmarch.fartman.actors.Player;
import net.ausiasmarch.fartman.actors.Torches;
import net.ausiasmarch.fartman.actors.Wall;
import net.ausiasmarch.fartman.util.Constants;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

public class Level {

	public static final String TAG = Level.class.getName();

	// Enumerado con los bloques de color del nivel para situar los objetos
	public enum BLOCK_TYPE {
		EMPTY(0, 0, 0), // black
		FLOOR(255,0,0), // red
		WALL(0,255,0), // green
		TORCH(0,0,255), // blue
		PLAYER(255,255,0); //yellow
		

		private int color;

		private BLOCK_TYPE(int r, int g, int b) {
			color = r << 24 | g << 16 | b << 8 | 0xff;
		}

		public boolean sameColor(int color) {
			return this.color == color;
		}

		public int getColor() {
			return color;
		}
	} // fin enum BLOCK_TYPE

	// Jugador
	public Player player;
	
	// Enemigos
	

	// Objetos
	

	// Plataformas
	public Array<Wall> walls;
	public Array<Floor> floors;
	public Array<Torches> torches;

	// Decorados
	//public Torches torches;

	// Lista general de actores usada para update y render
	public Array<AbstractActor> actors;

	
	//Lista para actores sobre suelo
	public Array<AbstractActor> actorsOnFloor;
	public Array<AbstractActor> actorsOnWall;

	
	// Constructor
	public Level(int levelNumber) {
		init(levelNumber);
	}

	// Inicia el nivel
	private void init(int levelNumber) {

		// Creamos todas las listas de actores
		actors = new Array<AbstractActor>();
		walls = new Array<Wall>();
		floors = new Array<Floor>();
		torches = new Array<Torches>();
		actorsOnFloor = new Array<AbstractActor>();
		actorsOnWall = new Array<AbstractActor>();
		
		
		
		
		// Obtenemos el nombre fichero de imagen ddel nivel
		String[] sLevel = Constants.LEVEL.split("\\.");
		String filename = sLevel[0] + levelNumber + "." + sLevel[1];		
		// Carga el fichero de imagen que representa el nivel de datos
		Pixmap pixmap = new Pixmap(Gdx.files.internal(filename));
		
		// Escanea los pixeles de la imagen desde la esquina superor izquierda hasta la inferior derecha
		int lastPixel = -1;
		for (int pixelY = 0; pixelY < pixmap.getHeight(); pixelY++) {
			for (int pixelX = 0; pixelX < pixmap.getWidth(); pixelX++) {
				AbstractActor obj = null;
				// Altura compensada
				float offsetHeight = 0;
				// Altura desde la base hacia arriba del pixmap
				float baseHeight = pixmap.getHeight() - pixelY;
				// Obtiene el color de 32-bit RGBA del pixel actual
				int currentPixelColor = pixmap.getPixel(pixelX, pixelY);

				// Comprueba si currentPixelColor en x,y coincide con un BLOCK_TYPE.
				// Si es un espacio vacio
				if (BLOCK_TYPE.EMPTY.sameColor(currentPixelColor)) {
					// no hacemos nada
				
				}else if (BLOCK_TYPE.WALL.sameColor(currentPixelColor)) {
					
					if (lastPixel != currentPixelColor) {
						
						obj = new Wall();
						float heightIncreaseFactor = 0.25f;
						offsetHeight = 0f;
						obj.position.set(pixelX - 3.0f, baseHeight * obj.dimension.y
								* heightIncreaseFactor + offsetHeight);
						walls.add((Wall) obj);
						
						
					} else {
						
						//walls.get(walls.size - 1).increaseLength(1);
						walls.get(walls.size - 1).increaseHeight(1);
					
					}
				}else if (BLOCK_TYPE.FLOOR.sameColor(currentPixelColor)) {
				
						if (lastPixel != currentPixelColor) {
						
						obj = new Floor();
						float heightIncreaseFactor = 0.25f;
						offsetHeight = -3.0f;
						obj.position.set(pixelX -3.0f, baseHeight * obj.dimension.y
								* heightIncreaseFactor + offsetHeight);
						floors.add((Floor) obj);
						
						
					} else {
						
						floors.get(floors.size - 1).increaseLength(1);
					
					}
					
				}else if (BLOCK_TYPE.TORCH.sameColor(currentPixelColor)) {
					
					if (lastPixel != currentPixelColor) {
					
					obj = new Torches();
					float heightIncreaseFactor = 0.25f;
					offsetHeight = -3.0f;
					obj.position.set(pixelX -3.0f, baseHeight * obj.dimension.y
							* heightIncreaseFactor + offsetHeight);
					torches.add((Torches) obj);
					
					} else {
					
					//floors.get(floors.size - 1).increaseLength(1);
					}
				}	else if (BLOCK_TYPE.PLAYER.sameColor(currentPixelColor)) {
						obj = new Player();
						offsetHeight = -17.4f;
						obj.position.set(pixelX-3f, baseHeight * obj.dimension.y + offsetHeight);
						player = (Player) obj;
						
				}
			
			
				
				
				
				
				
				
				// Si no es ninguno de los anteriores
				else {
					Gdx.app.error(TAG, "Objeto desconocido");
				}

				lastPixel = currentPixelColor;
			} // fin for x
		} // fin for y

		
		// Crea los decorados
		//torches = new Torches(pixmap.getWidth());

		
		// Situa los decorados
	//	torches.position.set(0.1f, 0.1f);

		

		// Agrega los actores a lista actors en el orden en que se dibujaran
		actors.addAll(walls);
		actors.addAll(floors);
		actors.addAll(torches);
		actors.add(player);
		
		
		

		// Libera la memoria ocupada por pixmap
		pixmap.dispose();
	}

	// Actualiza el nivel
	public void update(float deltaTime) {
		for (AbstractActor actor : actors) {
			actor.update(deltaTime);
		}

	}

	// Renderiza el nivel
	public void render(SpriteBatch batch) {
		for (AbstractActor actor : actors) {
			actor.render(batch);
		}
	}

}
