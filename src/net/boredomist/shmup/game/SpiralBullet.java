package net.boredomist.shmup.game;

import net.boredomist.shmup.MathHelper;

public class SpiralBullet extends Bullet {

	private int mTicks;
	private double mRadius;
	private Point mInitial;

	public SpiralBullet(int x, int y, GameWorld world) {
		super(x, y, world);

		mTicks = 0;
		mRadius = 0.0;
		mEnemyBullet = true;
		mInitial = new Point(x, y);
	}

	@Override
	public void update() {

		mRadius += .5;
		mTicks++;

		this.mPosition.X = (float) (mRadius * MathHelper.cos(mTicks * 10) + mInitial.X);
		this.mPosition.Y = (float) (mRadius * MathHelper.sin(mTicks * 10) + mInitial.Y)
				+ mTicks;

		super.update();

	}
}
