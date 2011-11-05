package net.boredomist.shmup.game;

import android.graphics.Color;

public class ReflectingBullet extends Bullet {
	private int mLife;

	public ReflectingBullet(int x, int y, GameWorld world) {
		this(x, y, 0, -20, false, world);
	}

	public ReflectingBullet(int x, int y, int vx, int vy, boolean enemy,
			GameWorld world) {
		super(x, y, vx, vy, enemy, world);
		mLife = 250;
		mPaint.setColor(Color.RED);
	}

	@Override
	public void update() {
		mPosition.X += mVelocity.X;
		mPosition.Y += mVelocity.Y;
		mLife--;
		if (mPosition.Y <= 0 || mPosition.Y >= mWorld.getHeight() || mLife < 0) {
			mDead = true;
			return;
		}

		if (mPosition.X < 0) {
			mPosition.X = 0;
			mVelocity.X = -mVelocity.X;
		} else if (mPosition.X > mWorld.getWidth() - WIDTH) {
			mPosition.X = mWorld.getWidth() - WIDTH;
			mVelocity.X = -mVelocity.X;
		}

		if (mEnemyBullet) {
			PlayerShip p = mWorld.getPlayer();
			if (collidesWith(p)) {
				p.takeDamage(mDamage);
			}
		} else {
			Entity c = checkCollisions();
			if (c != null) {
				c.takeDamage(mDamage);
				mDead = true;
			}
		}

	}
}
