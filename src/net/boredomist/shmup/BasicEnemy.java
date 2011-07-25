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

	public BasicEnemy(int yd, GameWorld world) {
		super(world);
		mPosition.X = mWorld.getWidth() / 2;
		mPosition.Y = 0;
		mYDest = yd;
		mLife = 45;
		mWut = 0;

		mBullets = new ArrayList<Bullet>();
		mBulletCooldown = 0;

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
		mWut = mWut + 1;
		if (mPosition.Y < mYDest) {
			mPosition.Y += 5;
		}
		checkBounds();

		mBulletCooldown--;
		if (mBulletCooldown <= 0) {
			mBulletCooldown = 50;
			mBullets.add(new Bullet((int) mPosition.X + WIDTH / 2,
					(int) mPosition.Y + HEIGHT, 0, 20, true, mWorld));
		}
		mPosition.X += (int) (5 * Math.cos(Math.toRadians(mWut)));

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
