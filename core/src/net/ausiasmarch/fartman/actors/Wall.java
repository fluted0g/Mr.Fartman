package net.ausiasmarch.fartman.actors;

import net.ausiasmarch.fartman.game.Assets;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Wall extends AbstractActor {

	private TextureRegion wall;
	private int length;
	private int height;
	
	public Wall() {
		init();
	}
	
	private void init() {
		dimension.set(1,1f);
		wall = Assets.instance.walls.wall;
		setLength(1);
	}
	
	private void setLength(int length) {

		this.length = length;
		bounds.set(0, 0, dimension.x * length, dimension.y);

	}
	
	public void increaseLength(int amount) {
		
		setLength(length + amount);
		
	}
	
	public void setHeight(int height) {
		this.height = height;
		bounds.set(0,0,dimension.x, dimension.y *height);
	}
	
	public void increaseHeight(int amount) {
		setHeight(height + amount);
	}
	
	@Override
	public void update(float deltaTime) {
		
		super.update(deltaTime);
		
	}
	
	@Override
	public void render(SpriteBatch batch) {
		TextureRegion reg = null;

		float relX = 0;
		float relY = 0;
		
		relX = 0;
        reg = wall;
		for (int i = 0; i < length; i++) {
			batch.draw(reg.getTexture(), 
				position.x + relX, position.y + relY, 
				origin.x, origin.y, dimension.x, dimension.y,
				scale.x, scale.y, rotation, 
				reg.getRegionX(), reg.getRegionY(), 
				reg.getRegionWidth(), reg.getRegionHeight(), false,	false);
			relX += dimension.x;
		}
	}
}
