package net.boredomist.shmup;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Missile extends Bullet {

	public static final int WIDTH = 3, HEIGHT = 15;

	private Point mTarget;
	private ParticleSystem mRocket;

	public Missile(int x, int y, int targetX, int targetY, GameWorld world) {
		super(x, y, world);

		mTarget = new Point(targetX, targetY);
		mRocket = new RocketParticleSystem(x, y, 2, 10, 5, 10);

		mVelocity.X = (int)Math.abs((targetX - x) / ((targetY - y) / 15) + .01);
		mVelocity.Y = 15;

		mDamage = 35;

		mRocket.setColor(Color.RED,	Color.YELLOW);
		
		mPaint = new Paint();
		mPaint.setColor(Color.RED);
	}

	public void update() {

		if (mPosition.X < mTarget.X - mVelocity.X) {
			mPosition.X += mVelocity.X;
		} else if (mPosition.X > mTarget.X + mVelocity.X) {
			mPosition.X -= mVelocity.X;
		}

		if (mPosition.Y < mTarget.Y - mVelocity.Y) {
			mPosition.Y += mVelocity.Y;
		} else if (mPosition.Y > mTarget.Y + mVelocity.Y) {
			mPosition.Y -= mVelocity.Y;
		} else {
			mWorld.addExplosion(mPosition.X, mPosition.Y);
			mDead = true;
		}

		checkBounds();
		if (mPosition.Y <= 0 || mPosition.Y >= mWorld.getHeight()
				|| mPosition.X <= 0 || mPosition.X >= mWorld.getWidth()) {
			mDead = true;
			return;
		}

		Entity c = checkCollisions();
		if (c != null) {
			mWorld.addExplosion(mPosition.X, mPosition.Y);
			c.takeDamage(mDamage);
			mDead = true;
		}

		mRocket.setXY((int) mPosition.X + WIDTH / 2, (int) mPosition.Y + HEIGHT);
		mRocket.update();
	}

	public void draw(Canvas canvas) {
		canvas.drawRect(mPosition.X, mPosition.Y, mPosition.X + WIDTH,
				mPosition.Y + HEIGHT, mPaint);
		mRocket.draw(canvas);
	}

	@Override
	public int getWidth() {
		return WIDTH;
	}

	@Override
	public int getHeight() {
		return HEIGHT;
	}

}
