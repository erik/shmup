package net.boredomist.shmup;

import java.util.ArrayList;
import java.util.Random;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;

public class GameWorld {
	private Context mContext;
	private PlayerShip mPlayerShip;
	private ArrayList<Enemy> mEnemies;
	private ArrayList<ParticleSystem> mSystems;
	private Point mSize;
	private Input mInput;
	private Random mRandom;
	private boolean mGameOver;
	private Paint mPaint;

	public GameWorld(Controller controller) {
		mContext = controller.getContext();
		mSize = new Point(controller.getWidth(), controller.getHeight());
		mInput = controller.getInput();
		mPlayerShip = new PlayerShip(this);
		mEnemies = new ArrayList<Enemy>();
		mSystems = new ArrayList<ParticleSystem>();
		mRandom = new Random();
		mGameOver = false;
		
		mPaint = new Paint();
		mPaint.setColor(Color.WHITE);
		mPaint.setAntiAlias(true);
		mPaint.setTypeface(Typeface.createFromAsset(mContext.getAssets(), "fonts/samplefont.ttf"));
		mPaint.setTextAlign(Paint.Align.CENTER);
		mPaint.setTextSize(64);

	}

	public int getRandom(int n) {
		return mRandom.nextInt(n);
	}

	public void addExplosion(ParticleSystem s) {
		mSystems.add(s);
	}

	public void addExplosion(float x, float y) {
		mSystems.add(new ParticleSystem((int) x, (int) y, 100, 30));
	}

	public void update() {

		if (mEnemies.size() < 5) {
			BasicEnemy e = new BasicEnemy(this);

			mEnemies.add(e);
		}

		for (int i = mSystems.size() - 1; i >= 0; --i) {
			mSystems.get(i).update();
			if (mSystems.get(i).isDead()) {
				mSystems.remove(i);
			}
		}

		for (int i = mEnemies.size() - 1; i >= 0; --i) {
			mEnemies.get(i).update();
			if (mEnemies.get(i).isDead()) {
				mEnemies.remove(i);
			}
		}

		if (mPlayerShip.isDead()) {
			mGameOver = true;
		} else {
			mPlayerShip.update();
		}
	}

	public void draw(Canvas canvas) {
		for (Enemy e : mEnemies) {
			e.draw(canvas);
		}

		for (ParticleSystem s : mSystems) {
			s.draw(canvas);
		}

		if (!mPlayerShip.isDead()) {
			mPlayerShip.draw(canvas);
		} else {
			canvas.drawText("GAME OVER", (int) mSize.X / 2, (int) mSize.Y / 2,
					mPaint);
		}
	}

	public void setSize(int w, int h) {
		mSize.X = w;
		mSize.Y = h;
	}

	public ArrayList<Enemy> getEnemies() {
		return mEnemies;
	}

	public PlayerShip getPlayer() {
		return mPlayerShip;
	}

	public int getWidth() {
		return (int) mSize.X;
	}

	public int getHeight() {
		return (int) mSize.Y;
	}

	public Point getSize() {
		return mSize;
	}

	public Input getInput() {
		return mInput;
	}

	public Context getContext() {
		return mContext;
	}
}
