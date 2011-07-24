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
	private ArrayList<Bullet> mBullets;
	private int mBulletCooldown, mRocketCooldown;
	private Paint mPaint;

	public PlayerShip(GameWorld world) {
		super(world);

		mDirection = Direction.STRAIGHT;

		mDrawableLeft = mWorld.getContext().getResources()
				.getDrawable(R.drawable.hero_ship_left);
		mDrawableCenter = mWorld.getContext().getResources()
				.getDrawable(R.drawable.hero_ship_center);
		mDrawableRight = mWorld.getContext().getResources()
				.getDrawable(R.drawable.hero_ship_right);

		mRocket = new RocketParticleSystem((int) mPosition.X,
				(int) mPosition.Y, 10);
		mBulletCooldown = mRocketCooldown = 0;
		mBullets = new ArrayList<Bullet>();
		
		mLife = MAX_LIFE;

		mPaint = new Paint();
		mPaint.setColor(Color.WHITE);
		
		setXY(mWorld.getWidth() / 2, mWorld.getHeight() - (HEIGHT + 20));
	}

	public void fire() {
		if (mRocketCooldown <= 0) {
			mBullets.add(new Missile((int) mPosition.X + WIDTH / 2,
					(int) mPosition.Y, mWorld.getInput().getTouchX(), mWorld
							.getInput().getTouchY(), mWorld));
			mRocketCooldown = 20;
		} else {
			mBullets.add(new Bullet((int) mPosition.X + WIDTH / 2,
					(int) mPosition.Y, mWorld));
			mBulletCooldown = 4;
		}
	}

	@Override
	public void setXY(float x, float y) {
		super.setXY(x, y);

		mRocket.setXY((int) x, (int) y);
	}

	public void updateInput() {
		Input input = mWorld.getInput();
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

		int xt = input.getTouchX();
		int yt = input.getTouchY();

		if (xt != -1 && yt != -1
				&& (mBulletCooldown <= 0 || mRocketCooldown <= 0)) {
			this.fire();
			input.setTouch(-1, -1);
		}
	}

	public void update() {
		updateInput();

		mPosition.X += mVelocity.X;
		mPosition.Y += mVelocity.Y;

		checkBounds();

		mBulletCooldown--;
		mRocketCooldown--;

		for (int i = mBullets.size() - 1; i >= 0; --i) {
			Bullet b = mBullets.get(i);

			b.update();
			if (b.isDead()) {
				mBullets.remove(i);
			}
		}

		if(mLife < MAX_LIFE) {
			mLife += .5;
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

		for (Bullet b : mBullets) {
			b.draw(canvas);
		}

		frsrs.setBounds(x, y, x + WIDTH, y + HEIGHT);
		frsrs.draw(canvas);

		mRocket.draw(canvas);
		
		canvas.drawText("Life: " + mLife, 0, mWorld.getHeight() - 20, mPaint);
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
