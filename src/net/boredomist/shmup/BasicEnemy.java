package net.boredomist.shmup;

import java.util.ArrayList;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

public class BasicEnemy extends Enemy {
	public int WIDTH = 55, HEIGHT = 35;

	private Drawable mDrawable;

	private int mWut;

	private int mYDest;

	private ArrayList<Bullet> mBullets;
	private int mBulletCooldown;

	private int mDir;

	private int mMissileCooldown;

	public BasicEnemy(int yd, GameWorld world) {
		super(world);
		mPosition.X = mWorld.getWidth() / 2;
		mPosition.Y = 0;
		mYDest = yd;
		mLife = 45;
		mWut = 0;

		mBullets = new ArrayList<Bullet>();

		int tmp = world.getRandom(2) + 4;

		mDir = tmp % 2 == 0 ? -1 * tmp : 1 * tmp;

		mBulletCooldown = world.getRandom(15) + 20;
		mMissileCooldown = world.getRandom(500);

		mDrawable = world.getContext().getResources()
				.getDrawable(R.drawable.basic_enemy);
	}

	public BasicEnemy(GameWorld world) {
		this(world.getRandom(500), world);
	}

	@Override
	public int getWidth() {
		return WIDTH;
	}

	@Override
	public int getHeight() {
		return HEIGHT;
	}

	public void update() {
		++mWut;
		if (mPosition.Y < mYDest) {
			mPosition.Y += 5;
		}
		checkBounds();

		if (--mBulletCooldown <= 0) {
			mBulletCooldown = 50;
			mBullets.add(new Bullet((int) mPosition.X + WIDTH / 2,
					(int) mPosition.Y + HEIGHT, 0, 20, true, mWorld));
		}
		if (--mMissileCooldown <= 0) {
			mMissileCooldown = 300;
			PlayerShip p = mWorld.getPlayer();

			int tx = (int) p.getX(), ty = (int) p.getY();
			tx = tx < 0 ? mWorld.getWidth() / 2 : tx;
			ty = ty < 0 ? mWorld.getHeight() : ty + p.getHeight() / 2;

			mBullets.add(new Missile((int) mPosition.X + WIDTH / 2,
					(int) mPosition.Y + HEIGHT, tx - 10 + mWorld.getRandom(20),
					ty, true, mWorld));
		}

		mPosition.X += (int) (MathHelper.cos(mWut) * mDir);

		for (int i = mBullets.size() - 1; i >= 0; --i) {
			Bullet b = mBullets.get(i);

			b.update();
			if (b.isDead()) {
				mBullets.remove(i);
			}
		}

	}

	public void draw(Canvas canvas) {
		mDrawable.setBounds((int) mPosition.X, (int) mPosition.Y,
				(int) mPosition.X + WIDTH, (int) mPosition.Y + HEIGHT);
		mDrawable.draw(canvas);

		for (Bullet b : mBullets) {
			b.draw(canvas);
		}

	}

}
