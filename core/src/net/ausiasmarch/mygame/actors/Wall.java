package net.ausiasmarch.mygame.actors;

import net.ausiasmarch.mygame.game.Assets;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Wall extends AbstractActor {

	private TextureRegion wall;
	private int length;
	
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
