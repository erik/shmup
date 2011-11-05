package net.boredomist.shmup.game;

import java.util.ArrayList;

import net.boredomist.shmup.MathHelper;
import net.boredomist.shmup.gui.Input;
import android.graphics.Canvas;

public class PlayerGun {
	private ArrayList<Bullet> mBullets;

	private boolean mHasDefault = false, mHasMissile = false,
			mHasAutoMissile = false, mHasMultishot = false;
	private int mDefaultTicks = 0, mMissileTicks = 0, mAutoMissileTicks = 0,
			mMultishotTicks = 0;
	private boolean mDefaultForever = false, mMissileForever = false,
			mAutoMissileForever = false, mMultishotForever = false;

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

	public void addGun(GunType p) {
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
		case MULTISHOT:
			mHasMultishot = true;
			mMultishotForever = false;
			break;
		}
	}

	public void addGun(GunType p, int activeForTicks) {
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
		case MULTISHOT:
			mHasMultishot = true;
			mMultishotForever = false;
			mMultishotTicks = activeForTicks;
			break;
		}
	}

	public void draw(Canvas canvas) {
		for (Bullet b : mBullets) {
			b.draw(canvas);
		}
	}

	public void fire(int tx, int ty) {
		int x = (int) mShip.mPosition.X + mShip.getWidth() / 2, y = (int) mShip.mPosition.Y;

		if (tx != -1) {
			if (mHasAutoMissile
					&& mTicks % GunType.AUTOMISSILE.getCooldownPeriod() == 0) {
				mBullets.add(new AutoMissile(x, y, mWorld));
			}
			if (mHasMissile
					&& mTicks % GunType.MISSILE.getCooldownPeriod() == 0) {
				mBullets.add(new Missile(x, y, tx, ty, mWorld));
			}
			if (mHasMultishot
					&& mTicks % GunType.MULTISHOT.getCooldownPeriod() == 0) {
				for (int i = 40; i < 140; i += 20) {
					int cos = (int) (MathHelper.cos(i) * 10);
					int sin = (int) (MathHelper.sin(i) * -20);
					mBullets.add(new Bullet(x, y, cos, sin, false, mWorld));
				}
			}
		}
	}

	public void update() {
		mTicks++;

		if (mHasDefault) {
			if (mTicks % GunType.DEFAULT.getCooldownPeriod() == 0) {
				mBullets.add(new Bullet((int) mShip.mPosition.X
						+ PlayerShip.WIDTH / 2, (int) mShip.mPosition.Y, mWorld));
			}
			if (!mDefaultForever && mDefaultTicks-- <= 0) {
				mHasDefault = false;
			}
		}
		if (mHasMissile && !mMissileForever) {
			if (mMissileTicks-- <= 0) {
				mHasMissile = false;
				mWorld.addNotification("MISSILES OFFLINE.", 100, true);
			}
		}
		if (mHasAutoMissile && !mAutoMissileForever) {
			if (mAutoMissileTicks-- <= 0) {
				mHasAutoMissile = false;
				mWorld.addNotification("AUTOMISSILES OFFLINE.", 100, true);
			}
		}

		if (mHasMultishot && !mMissileForever) {
			if (mMultishotTicks-- <= 0) {
				mHasMultishot = false;
				mWorld.addNotification("MULTISHOT OFFLINE.", 100, true);
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
}
