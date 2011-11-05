package net.boredomist.shmup.game;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Bullet extends Entity {
	public static int WIDTH = 5;
	public static int HEIGHT = 5;

	protected int mDamage;
	protected Paint mPaint;
	protected boolean mEnemyBullet;

	public Bullet(int x, int y, GameWorld world) {
		this(x, y, 0, -20, false, world);
	}

	public Bullet(int x, int y, int vx, int vy, boolean enemy, GameWorld world) {
		super(world);
		mPosition.X = x;
		mPosition.Y = y;

		mVelocity.X = vx;
		mVelocity.Y = vy;

		mDamage = 2;
		
		mEnemyBullet = enemy;

		mPaint = new Paint();
		mPaint.setColor(Color.WHITE);
	}

	@Override
	public void draw(Canvas canvas) {
		int x = (int) mPosition.X;
		int y = (int) mPosition.Y;
		canvas.drawRect(x, y, x + WIDTH, y + HEIGHT, mPaint);
	}

	@Override
	public int getHeight() {
		return HEIGHT;
	}

	@Override
	public int getWidth() {
		return WIDTH;
	}

	@Override
	public void update() {
		mPosition.X += mVelocity.X;
		mPosition.Y += mVelocity.Y;

		if (mPosition.Y <= 0 || mPosition.Y >= mWorld.getHeight()
				|| mPosition.X <= 0 || mPosition.X >= mWorld.getWidth()) {
			mDead = true;
			return;
		}

		if (mEnemyBullet) {
			PlayerShip p = mWorld.getPlayer();
			if(collidesWith(p)) {
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
