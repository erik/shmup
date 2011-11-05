package net.boredomist.shmup.game;

import java.util.ArrayList;
import java.util.List;

import net.boredomist.shmup.MathHelper;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class ParticleSystem {
	public List<Particle> mParticles;
	protected int mX, mY;
	protected int mNumberEmitted, mNumberToEmit;
	protected int mEmitEachTick;
	protected boolean mDead;
	protected int mStartColor, mStopColor;
	protected Paint mPaint;

	public ParticleSystem(int x, int y, int numEmit, int emitEach) {
		mX = x;
		mY = y;
		mNumberEmitted = 0;
		mNumberToEmit = numEmit;
		mEmitEachTick = emitEach;

		mParticles = new ArrayList<Particle>();
		mDead = false;
		
		mPaint = new Paint();
		
		mStartColor = Color.YELLOW;
		mStopColor = Color.RED;
	}

	// this just exists to make it easier to override in subclasses
	protected Particle createParticle() {
		int cos = (int) (((int)(Math.random() * 10) + 1) * MathHelper.cos((int) (Math.random() * 360)));
		int sin = (int) (((int)(Math.random() * 10) + 1) * MathHelper.sin((int) (Math.random() * 360)));
		
		Particle p = new Particle(mX, mY, cos, sin, (int)(Math.random() * 25), 3, mStartColor, mStopColor);
		return p;
	}
	
	public void draw(Canvas canvas) {
		for (Particle p : mParticles) {
			p.draw(canvas, mPaint);
		}
	}

	public boolean isDead() {
		return mDead;
	}

	public void setColor(int start, int stop) {
		mStartColor = start;
		mStopColor = stop;
	}

	public void setXY(int x, int y) {
		mX = x;
		mY = y;
	}

	public void update() {
		for (int i = 0; i < mEmitEachTick; ++i) {
			if (mNumberToEmit != -1 && mNumberEmitted++ > mNumberToEmit) {
				break;
			} else {
				Particle p = createParticle();
				mParticles.add(p);
			}
		}

		for (int i = mParticles.size() - 1; i >= 0; --i) {
			Particle p = mParticles.get(i);
			p.update();
			if (p.isDead()) {
				mParticles.remove(i);
			}
		}
		
		if(mParticles.size() == 0) {
			mDead = true;
		}
		
	}

}
