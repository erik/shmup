package net.boredomist.shmup;

import android.graphics.Canvas;
import android.graphics.Color;

public class AutoMissile extends Bullet {

	public static final int WIDTH = 5, HEIGHT = 10;

	private Entity mTarget;
	private RocketParticleSystem mRocket;

	public AutoMissile(int x, int y, GameWorld world) {
		super(x, y, world);

		if (world.getEnemies().size() == 0) {
			mTarget = null;
		} else {
			mTarget = world.getEnemies().get(
					world.getRandom(world.getEnemies().size()));
		}

		mDamage = 35;

		mVelocity.X = 0;
		mVelocity.Y = -20;

		mRocket = new RocketParticleSystem(x, y, 1);
		mRocket.setColor(Color.BLUE, Color.WHITE);

		mPaint.setColor(Color.BLUE);
	}

	@Override
	public void update() {
		if (mTarget.isDead()) {
			mDead = true;
		} else if (mTarget != null) {
			if (mTarget.getX() < mPosition.X - 5) {
				mVelocity.X = -10;
			} else if (mTarget.getX() > mPosition.X + 5) {
				mVelocity.X = 10;
			} else {
				mVelocity.X = 0;
			}

			if (mTarget.getY() < mPosition.Y - 10) {
				mVelocity.Y = -20;
			} else if (mTarget.getY() > mPosition.Y + 10) {
				mVelocity.Y = 20;
			} else {
				mVelocity.Y = 0;
			}
		}

		super.update();

		mRocket.setXY((int) mPosition.X + WIDTH / 2, (int) mPosition.Y + HEIGHT);
		mRocket.update();
	}

	public void draw(Canvas canvas) {
		canvas.drawRect(mPosition.X, mPosition.Y, mPosition.X + WIDTH,
				mPosition.Y + HEIGHT, mPaint);
		mRocket.draw(canvas);
	}
}
