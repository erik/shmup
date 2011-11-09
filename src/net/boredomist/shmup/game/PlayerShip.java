package net.boredomist.shmup.game;

import net.boredomist.shmup.R;
import net.boredomist.shmup.gui.Input;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;

public class PlayerShip extends Entity {
	private enum Direction {
		LEFT, STRAIGHT, RIGHT
	}
	public static final int WIDTH = 64, HEIGHT = 64;

	public static final float MAX_LIFE = 100;;

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

		mGun.addGun(GunType.DEFAULT);

		mLastStreak = 0;

		mDirection = Direction.STRAIGHT;

		mDrawableLeft = mWorld.getContext().getResources()
				.getDrawable(R.drawable.hero_ship_left1);
		mDrawableCenter = mWorld.getContext().getResources()
				.getDrawable(R.drawable.hero_ship_center1);
		mDrawableRight = mWorld.getContext().getResources()
				.getDrawable(R.drawable.hero_ship_right1);

		mRocket = new RocketParticleSystem((int) mPosition.X,
				(int) mPosition.Y, 4);

		mLife = MAX_LIFE;

		mPaint = new Paint();
		mPaint.setColor(Color.WHITE);

		setXY(mWorld.getWidth() / 2, mWorld.getHeight() - (HEIGHT + 20));
	}

	@Override
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
	public int getHeight() {
		return HEIGHT;
	}

	@Override
	public int getWidth() {
		return WIDTH;
	}

	public void givePowerup() {
		int rand = mWorld.getRandom(100);

		if (rand < 25) {
			mLife = 100.0f;
			mWorld.addNotification("HEALTH RESTORED.", 100, true);
		} else if (rand < 50) {
			mWorld.addNotification("MISSILES ONLINE.", 100, true);
			mGun.addGun(GunType.MISSILE, 500);
		} else if (rand < 75) {
			mWorld.addNotification("AUTOMISSILES ONLINE.", 100, true);
			mGun.addGun(GunType.AUTOMISSILE, 500);
		} else if (rand < 100) {
			mWorld.addNotification("MULTISHOT ONLINE.", 100, true);
			mGun.addGun(GunType.MULTISHOT, 500);
		}

	}

	@Override
	public void setXY(float x, float y) {
		super.setXY(x, y);

		mRocket.setXY((int) x, (int) y);
	}

	@Override
	public void update() {
		updateInput();

		mPosition.X += mVelocity.X;
		mPosition.Y += mVelocity.Y;

		checkBounds();

		if (mLife < MAX_LIFE) {
			mLife += .5;
		}

		mRocket.setXY((int) mPosition.X + WIDTH / 2, (int) mPosition.Y + HEIGHT);
		mRocket.update();

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

		if (input.hasTouch()) {
			mGun.fire(input.getTouchX(), input.getTouchY());
		}

	}

}
