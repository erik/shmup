package net.boredomist.shmup;

import android.graphics.Canvas;

public class MenuWorld extends GameWorld {

	public MenuWorld(Controller controller) {
		super(controller);
		
		mPlayerShip.setXY(-100, -100);
	}
	
	@Override
	public void update() {
		if(getRandom(30) == 5) {
			Enemy e = new BasicEnemy(this.getHeight(), this);
			e.setXY(getRandom(getWidth() - e.getWidth()), e.getY());
			mEnemies.add(e);
		}
		
		for(int i = mEnemies.size() - 1; i >= 0; --i) {
			Enemy e = mEnemies.get(i);
			e.update();
			
			if(e.getY() + e.getHeight() > getHeight()) {
				mEnemies.remove(i);
			}
			
		}		
	}
	
	@Override
	public void draw(Canvas canvas) {
		for(Enemy e : mEnemies) {
			e.draw(canvas);
		}
	}
	
}
