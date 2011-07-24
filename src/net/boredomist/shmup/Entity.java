package net.boredomist.shmup;

import java.util.ArrayList;

import android.graphics.Canvas;
import android.graphics.Rect;

public abstract class Entity {
	protected GameWorld mWorld;
	protected Point mPosition;
	protected Point mVelocity;
	protected float mLife;
	protected boolean mDead;

	public Entity(GameWorld world) {
		mWorld = world;
		mPosition = new Point(0, 0);
		mVelocity = new Point(0, 0);
		mLife = 100f;
		mDead = false;
	}

	public abstract void update();

	public abstract void draw(Canvas canvas);

	public abstract int getWidth();
	public abstract int getHeight();
	
	public boolean isDead() {
		return mDead;
	}

	public void takeDamage(float dam) {
		if(mDead) {
			return;
		}
		
		mLife -= dam;

		if (mLife <= 0) {
			mDead = true;
			mWorld.addExplosion(mPosition.X, mPosition.Y);
		}

	}

	protected Entity checkCollisions() {
		ArrayList<Enemy> enemies = mWorld.getEnemies();
		Rect hitbox = getHitBox();
		for (Entity e : enemies) {
			if (hitbox.intersect(e.getHitBox())) {
				return e;
			}
		}
		return null;
	}

	public boolean collidesWith(Entity other) {
		return getHitBox().intersect(other.getHitBox());
	}

	public void checkBounds() {
		mPosition.X = Math.max(Math.min(mPosition.X, mWorld.getWidth() - getWidth()), 0);
		mPosition.Y = Math
				.max(Math.min(mPosition.Y, mWorld.getHeight() - getHeight()), 0);
	}

	public Rect getHitBox() {
		return new Rect((int) mPosition.X, (int) mPosition.Y, (int) mPosition.X
				+ getWidth(), (int) mPosition.Y + getHeight());
	}

	public float getX() {
		return mPosition.X;
	}
	
	public float getY() {
		return mPosition.Y;
	}
	
	public void setXY(float x, float y) {
		mPosition.X = x;
		mPosition.Y = y;
	}

}
