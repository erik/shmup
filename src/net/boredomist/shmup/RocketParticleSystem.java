package net.boredomist.shmup;

import android.graphics.Color;

public class RocketParticleSystem extends ParticleSystem {

	private Point mVelocity;
	private int mLife;
	
	public RocketParticleSystem(int x, int y, int emitEach) {
		super(x, y, -1, emitEach);
		
		mVelocity = new Point(2, 10);
		mLife = 15;
		
		mStartColor = Color.RED;
		mStopColor = Color.YELLOW;
	}
	
	public RocketParticleSystem(int x, int y, int xv, int yv, int life, int emitEach) {
		super(x, y, -1, emitEach);
		mVelocity = new Point(xv, yv);
		mLife = life;
	}
	
	@Override
	public Particle createParticle() {
		int x = mX + (int)(Math.random() * 10) - 5;
		
		int xv = (int)(Math.random() * mVelocity.X) - (int)mVelocity.X / 2;
		int yv = (int)(Math.random() * mVelocity.Y);
		
		Particle p = new Particle(x, mY, xv, yv, ((int)(Math.random() * mLife)), 2, mStartColor, mStopColor);
		return p;		
	}

}
