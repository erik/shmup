package net.boredomist.shmup.game;

import java.util.ArrayList;

import net.boredomist.shmup.MathHelper;
import android.graphics.Canvas;

public class BulletEmitter {
	public enum BulletPattern {
		RADIAL, BEAM, SPOKES, CIRCLE
	}

	private GameWorld mWorld;
	private Entity mEntity;
	private BulletPattern mPattern;
	private ArrayList<Bullet> mBullets;
	private int mTicks;

	public BulletEmitter(GameWorld world, Entity entity, BulletPattern pattern) {
		mWorld = world;
		mEntity = entity;
		mPattern = pattern;
		mBullets = new ArrayList<Bullet>();
		mTicks = 0;
	}

	public void draw(Canvas canvas) {
		for (Bullet b : mBullets) {
			b.draw(canvas);
		}
	}

	public void setType(BulletPattern pattern) {
		mPattern = pattern;
	}

	public void update() {
		mTicks++;

		switch (mPattern) {
		case BEAM:
			updateBeam();
			break;
		case RADIAL:
			updateRadial();
			break;
		case SPOKES:
			updateSpokes();
			break;
		case CIRCLE:
			updateCircle();
		}

		for (int i = mBullets.size() - 1; i >= 0; --i) {
			Bullet b = mBullets.get(i);

			b.update();
			if (b.isDead()) {
				mBullets.remove(i);
			}
		}
	}

	public void updateBeam() {
		if (mTicks % 4 != 0 || ((mTicks / 40) % 2 == 0)) {
			return;
		}

		int xf = (int) mWorld.getPlayer().mPosition.X;
		int yf = (int) mWorld.getPlayer().mPosition.Y;

		float tmp = (yf - mEntity.mPosition.Y) / 5;
		tmp = tmp == 0 ? 1 : tmp;

		int xv = Math.min((int) ((xf - mEntity.mPosition.X) / tmp), 20);

		mBullets.add(new Bullet((int) mEntity.mPosition.X + mEntity.getWidth()
				/ 2, (int) mEntity.mPosition.Y, xv, 10, true, mWorld));

	}

	public void updateCircle() {
		if (mTicks % 300 != 0) {
			return;
		}
		for (int i = 0; i < 89; ++i) {
			int xv = (int) (MathHelper.cos(i * 4) * 10);
			int yv = (int) (MathHelper.sin(i * 4) * 10);

			mBullets.add(new Bullet((int) mEntity.mPosition.X
					+ mEntity.getWidth() / 2, (int) mEntity.mPosition.Y, xv,
					yv, true, mWorld));
		}
	}

	public void updateRadial() {
		if (mTicks % 4 != 0 || ((mTicks / 120) % 2 == 0)) {
			return;
		}

		int xv = (int) (MathHelper.cos(mTicks * 5) * 10);
		int yv = (int) (MathHelper.sin(mTicks * 5) * 10);

		mBullets.add(new Bullet((int) mEntity.mPosition.X + mEntity.getWidth()
				/ 2, (int) mEntity.mPosition.Y, xv, yv, true, mWorld));

	}

	public void updateSpokes() {
		if (mTicks % 3 != 0 || ((mTicks / 60) % 2 == 0)) {
			return;
		}

		for (int i = 1; i <= 3; ++i) {
			int xv = (int) (MathHelper.cos(mTicks * 4 + 120 * i) * 10);
			int yv = (int) (MathHelper.sin(mTicks * 4 + 120 * i) * 10);

			mBullets.add(new Bullet((int) mEntity.mPosition.X
					+ mEntity.getWidth() / 2, (int) mEntity.mPosition.Y, xv,
					yv, true, mWorld));
		}
	}
}
