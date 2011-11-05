package net.boredomist.shmup.gui;

import net.boredomist.shmup.game.BasicEnemy;
import net.boredomist.shmup.game.Controller;
import net.boredomist.shmup.game.Enemy;
import net.boredomist.shmup.game.GameWorld;
import android.graphics.Canvas;

public class MenuWorld extends GameWorld {

	public MenuWorld(Controller controller) {
		super(controller);

		mPlayerShip.setXY(-100, -100);

	}

	@Override
	public void draw(Canvas canvas) {
		for (Enemy e : mEnemies) {
			e.draw(canvas);
		}
	}

	@Override
	public void update() {
		if (getRandom(30) == 5) {
			Enemy e = new BasicEnemy(this.getHeight(), this);
			e.setXY(getRandom(getWidth() - e.getWidth()), -e.getHeight());
			mEnemies.add(e);
		}

		for (int i = mEnemies.size() - 1; i >= 0; --i) {
			Enemy e = mEnemies.get(i);
			e.update();

			if (e.getY() >= getHeight() || e.isDead()) {
				mEnemies.remove(i);
			}
		}
	}

}
