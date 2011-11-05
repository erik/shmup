package net.boredomist.shmup.game;

import net.boredomist.shmup.MathHelper;
import net.boredomist.shmup.R;
import net.boredomist.shmup.game.BulletEmitter.BulletPattern;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;

public class BasicEnemy extends Enemy {
	public int WIDTH = 55, HEIGHT = 35;

	private Drawable mDrawable;

	private int mTicks;
	private int mYDest;
	private int mDir;

	private BulletEmitter mBulletEmitter;

	public BasicEnemy(GameWorld world) {
		this(world.getRandom(500), world);
	}

	public BasicEnemy(int yd, GameWorld world) {
		super(world);
		mPosition.X = mWorld.getWidth() / 2;
		mPosition.Y = 0;
		mYDest = yd;
		mLife = 45;
		mTicks = 0;

		int rand = mWorld.getRandom(100);

		if (rand < 20) {
			mBulletEmitter = new BulletEmitter(world, this,
					BulletPattern.CIRCLE);
		} else if (rand < 50) {
			mBulletEmitter = new BulletEmitter(world, this,
					BulletPattern.SPOKES);
		} else if (rand < 80) {
			mBulletEmitter = new BulletEmitter(world, this,
					BulletPattern.RADIAL);
		} else {
			mBulletEmitter = new BulletEmitter(world, this, BulletPattern.BEAM);
		}

		int tmp = world.getRandom(2) + 4;

		mDir = tmp % 2 == 0 ? -1 * tmp : 1 * tmp;

		mDrawable = world.getContext().getResources()
				.getDrawable(R.drawable.basic_enemy);
	}

	@Override
	public void draw(Canvas canvas) {
		mDrawable.setBounds((int) mPosition.X, (int) mPosition.Y,
				(int) mPosition.X + WIDTH, (int) mPosition.Y + HEIGHT);
		mDrawable.draw(canvas);

		Paint paint = new Paint();

		paint.setColor(Color.rgb(255 - (int) ((mLife / 45) * 255),
				(int) (((mLife / 45) * 255)), 0));

		canvas.drawRect(mPosition.X, mPosition.Y - 10, mPosition.X
				+ (mLife / 45) * WIDTH, mPosition.Y - 5, paint);

		mBulletEmitter.draw(canvas);
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

		mTicks++;

		if (mTicks % 200 == 0) {
			mYDest = mWorld.getRandom(mWorld.getHeight() - 200);
		}

		if (mPosition.Y < mYDest - 5) {
			mPosition.Y += 5;
		} else if (mPosition.Y > mYDest + 5) {
			mPosition.Y -= 5;
		}
		checkBounds();

		mBulletEmitter.update();

		/*
		 * if (--mBulletCooldown <= 0) { mBulletCooldown = 50; mBullets.add(new
		 * Bullet((int) mPosition.X + WIDTH / 2, (int) mPosition.Y + HEIGHT, 0,
		 * 20, true, mWorld)); } if (--mMissileCooldown <= 0) { mMissileCooldown
		 * = 300; PlayerShip p = mWorld.getPlayer();
		 * 
		 * int tx = (int) p.getX(), ty = (int) p.getY(); tx = tx < 0 ?
		 * mWorld.getWidth() / 2 : tx; ty = ty < 0 ? mWorld.getHeight() : ty +
		 * p.getHeight() / 2;
		 * 
		 * mBullets.add(new Missile((int) mPosition.X + WIDTH / 2, (int)
		 * mPosition.Y + HEIGHT, tx - 10 + mWorld.getRandom(20), ty, true,
		 * mWorld)); }
		 */

		mPosition.X += (int) (MathHelper.cos(mTicks) * mDir);

		/*
		 * for (int i = mBullets.size() - 1; i >= 0; --i) { Bullet b =
		 * mBullets.get(i);
		 * 
		 * b.update(); if (b.isDead()) { mBullets.remove(i); } }
		 */

	}
}
