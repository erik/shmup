package net.boredomist.shmup.game;

import java.util.Random;

import android.graphics.Color;

public class RocketParticleSystem extends ParticleSystem {

	private Point mVelocity;
	private int mLife;
	private Random mRandom;

	public RocketParticleSystem(int x, int y, int emitEach) {
		this(x, y, 2, 10, 15, emitEach);
	}

	public RocketParticleSystem(int x, int y, int xv, int yv, int life,
			int emitEach) {
		super(x, y, -1, emitEach);
		mVelocity = new Point(xv, yv);
		mLife = life;

		mRandom = new Random();

		mStartColor = Color.RED;
		mStopColor = Color.YELLOW;

	}

	@Override
	public Particle createParticle() {
		int x = mX + mRandom.nextInt(10) - 5;

		int xv = mRandom.nextInt((int) mVelocity.X) - (int) mVelocity.X / 2;
		int yv = mRandom.nextInt((int) mVelocity.Y);

		Particle p = new Particle(x, mY, xv, yv, mRandom.nextInt(mLife), 5,
				mStartColor, mStopColor);
		return p;
	}

}
