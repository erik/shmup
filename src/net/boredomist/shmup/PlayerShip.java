package net.boredomist.shmup;

import java.util.ArrayList;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;

public class PlayerShip extends Entity {
	public static final int WIDTH = 64, HEIGHT = 64;
	public static final float MAX_LIFE = 100;

	private enum Direction {
		LEFT, STRAIGHT, RIGHT
	};

	private Direction mDirection;
	private Drawable mDrawableLeft, mDrawableCenter, mDrawableRight;
	private RocketParticleSystem mRocket;
	private Paint mPaint;
	private PlayerGun mGun;
	private int mLastStreak;

	public PlayerShip(GameWorld world) {
		super(world);

		mAllowOutY = false;

		mGun = new PlayerGun(world, this);

		mGun.addGun(Gun.DEFAULT);
		
		mLastStreak = 0;

		mDirection = Direction.STRAIGHT;

		mDrawableLeft = mWorld.getContext().getResources()
				.getDrawable(R.drawable.hero_ship_left);
		mDrawableCenter = mWorld.getContext().getResources()
				.getDrawable(R.drawable.hero_ship_center);
		mDrawableRight = mWorld.getContext().getResources()
				.getDrawable(R.drawable.hero_ship_right);

		mRocket = new RocketParticleSystem((int) mPosition.X,
				(int) mPosition.Y, 10);

		mLife = MAX_LIFE;

		mPaint = new Paint();
		mPaint.setColor(Color.WHITE);

		setXY(mWorld.getWidth() / 2, mWorld.getHeight() - (HEIGHT + 20));
	}

	@Override
	public void setXY(float x, float y) {
		super.setXY(x, y);

		mRocket.setXY((int) x, (int) y);
	}

	public void updateInput() {
		Input input = mWorld.getInput();

		mGun.update();

		float x = input.getAccelX();
		float y = input.getAccelY();

		mVelocity.X = x * 3;
		mVelocity.Y = y * 3;

		if (mVelocity.X < -5) {
			mDirection = Direction.LEFT;
		} else if (mVelocity.X > 5) {
			mDirection = Direction.RIGHT;
		} else {
			mDirection = Direction.STRAIGHT;
		}

		if (input.getTouchX() != -1) {
			mGun.fire(input.getTouchX(), input.getTouchY());
			input.setTouch(-1, -1);
		}

	}

	public void update() {
		updateInput();

		mPosition.X += mVelocity.X;
		mPosition.Y += mVelocity.Y;

		checkBounds();

		if (mLife < MAX_LIFE) {
			mLife += .5;
		}

		int streak = mWorld.getStreak();
		if (streak == 0 || mLastStreak == streak) {

		} else if (streak % 31 == 0) {
			mWorld.addNotification("AUTOMISSILES ACQUIRED.", 100, true);
			mGun.addGun(Gun.AUTOMISSILE, 500);
			mLastStreak = streak;
		} else if (streak % 7 == 0) {
			mWorld.addNotification("MISSILES ACQUIRED.", 100, true);
			mGun.addGun(Gun.MISSILE, 500);
			mLastStreak = streak;
		}

		mRocket.setXY((int) mPosition.X + WIDTH / 2, (int) mPosition.Y + HEIGHT);
		mRocket.update();

	}

	public void draw(Canvas canvas) {
		Drawable frsrs = null;
		switch (mDirection) {
		case STRAIGHT:
			frsrs = mDrawableCenter;
			break;
		case LEFT:
			frsrs = mDrawableLeft;
			break;
		case RIGHT:
			frsrs = mDrawableRight;
			break;
		}

		int x = (int) mPosition.X;
		int y = (int) mPosition.Y;

		frsrs.setBounds(x, y, x + WIDTH, y + HEIGHT);
		frsrs.draw(canvas);

		mRocket.draw(canvas);
		mGun.draw(canvas);

		mPaint.setTextAlign(Paint.Align.LEFT);
		canvas.drawText("Life: " + mLife, 0, mWorld.getHeight() - 20, mPaint);
		
		mPaint.setTextAlign(Paint.Align.RIGHT);
		canvas.drawText("Streak: " + mWorld.getStreak(), mWorld.getWidth(),
				mWorld.getHeight() - 20, mPaint);
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
