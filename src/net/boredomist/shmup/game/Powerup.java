package net.boredomist.shmup.game;

import net.boredomist.shmup.MathHelper;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Powerup extends Entity {
	public static final int WIDTH = 5, HEIGHT = 5;

	private PlayerShip mShip;
	private Paint mPaint;

	private ParticleSystem mSystem;

	public Powerup(GameWorld world, int x, int y) {
		super(world);

		mPosition = new Point(x, y);
		mVelocity.X = 0;
		mVelocity.Y = 10;

		mShip = mWorld.getPlayer();

		mSystem = new ParticleSystem(x, y, -1, 5) {
			private int mWut = 0;

			@Override
			protected Particle createParticle() {
				mWut += 10;
				int cos = (int) (2 * MathHelper.cos(mWut));
				int sin = (int) (2 * MathHelper.sin(mWut));

				Particle p = new Particle(mX, mY, cos, sin, 15, 3, mStartColor,
						mStopColor);
				return p;
			}
		};
		mSystem.setColor(Color.WHITE, Color.CYAN);

		mPaint = new Paint();

	}

	@Override
	public void draw(Canvas canvas) {
		mPaint.setColor(Color.rgb(255, 255, 255));
		canvas.drawRect((int) mPosition.X, (int) mPosition.Y, (int) mPosition.X
				+ WIDTH, (int) mPosition.Y + HEIGHT, mPaint);
		mSystem.draw(canvas);
	}

	@Override
	public int getHeight() {
		return HEIGHT;
	}

	@Override
	public int getWidth() {
		return WIDTH;
	}

	@Override
	public void update() {

		mPosition.X += mVelocity.X;
		mPosition.Y += mVelocity.Y;

		if (mPosition.Y >= mWorld.getHeight()) {
			mDead = true;
		}

		checkBounds();

		mSystem.update();
		mSystem.setXY((int) mPosition.X + WIDTH / 2, (int) mPosition.Y + HEIGHT
				/ 2);

		if (this.collidesWith(mShip)) {
			mDead = true;
			mShip.givePowerup();
		}

	}

}
