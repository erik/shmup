package net.boredomist.shmup;

import java.util.ArrayList;

import android.graphics.Canvas;

public class PlayerGun {
	private ArrayList<Bullet> mBullets;

	private boolean mHasDefault = false, mHasMissile = false,
			mHasAutoMissile = false;
	private int mDefaultTicks = 0, mMissileTicks = 0, mAutoMissileTicks = 0;
	private boolean mDefaultForever = false, mMissileForever = false,
			mAutoMissileForever = false;

	private int mTicks;
	private PlayerShip mShip;
	private GameWorld mWorld;
	private Input mInput;

	public PlayerGun(GameWorld world, PlayerShip p) {
		mTicks = 0;
		mWorld = world;
		mInput = world.getInput();
		mShip = p;

		mBullets = new ArrayList<Bullet>();
	}

	public void addGun(Gun p, int activeForTicks) {
		switch (p) {
		case DEFAULT:
			mHasDefault = true;
			mDefaultForever = false;
			mDefaultTicks = activeForTicks;
			break;
		case MISSILE:
			mHasMissile = true;
			mMissileForever = false;
			mMissileTicks = activeForTicks;
			break;
		case AUTOMISSILE:
			mHasAutoMissile = true;
			mAutoMissileForever = false;
			mAutoMissileTicks = activeForTicks;
			break;
		}
	}

	public void addGun(Gun p) {
		switch (p) {
		case DEFAULT:
			mHasDefault = true;
			mDefaultForever = true;
			break;
		case MISSILE:
			mHasMissile = true;
			mMissileForever = true;
			break;
		case AUTOMISSILE:
			mHasAutoMissile = true;
			mAutoMissileForever = true;
			break;
		}
	}

	public void update() {
		mTicks++;

		if (mHasDefault && !mDefaultForever) {
			if (mDefaultTicks-- <= 0) {
				mHasDefault = false;
			}
		}
		if (mHasMissile && !mMissileForever) {
			if (mMissileTicks-- <= 0) {
				mHasMissile = false;
			}
		}
		if (mHasAutoMissile && !mAutoMissileForever) {
			if (mAutoMissileTicks-- <= 0) {
				mHasAutoMissile = false;
			}
		}

		for (int i = mBullets.size() - 1; i >= 0; --i) {
			Bullet b = mBullets.get(i);

			b.update();
			if (b.isDead()) {
				mBullets.remove(i);
			}
		}

	}

	public void fire(int tx, int ty) {
		int x = (int) mShip.mPosition.X, y = (int) mShip.mPosition.Y;

		if (tx != -1) {
			if (mHasDefault && mTicks % Gun.DEFAULT.getCooldownPeriod() == 0) {
				mBullets.add(new Bullet(x + PlayerShip.WIDTH / 2, y, mWorld));
			}
			if (mHasAutoMissile && mTicks % Gun.AUTOMISSILE.getCooldownPeriod() == 0) {
				mBullets.add(new AutoMissile(x, y, mWorld));
			}
			if (mHasMissile && mTicks % Gun.MISSILE.getCooldownPeriod() == 0) {
				mBullets.add(new Missile(x, y, tx, ty, mWorld));
			}
		}
	}

	public void draw(Canvas canvas) {
		for (Bullet b : mBullets) {
			b.draw(canvas);
		}
	}
}
