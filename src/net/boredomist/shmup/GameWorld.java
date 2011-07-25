package net.boredomist.shmup;

import java.util.ArrayList;
import java.util.Random;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;

public class GameWorld {
	protected Context mContext;
	protected PlayerShip mPlayerShip;
	protected ArrayList<Enemy> mEnemies;
	protected ArrayList<ParticleSystem> mSystems;
	protected Point mSize;
	protected Input mInput;
	protected Random mRandom;
	protected boolean mGameOver;
	protected Paint mPaint;
	protected int mKills;
	protected int mStreak;

	public GameWorld(Controller controller) {
		mContext = controller.getContext();
		mSize = new Point(controller.getWidth(), controller.getHeight());
		mInput = controller.getInput();

		mPlayerShip = new PlayerShip(this);
		mEnemies = new ArrayList<Enemy>();
		mSystems = new ArrayList<ParticleSystem>();
		mRandom = new Random();
		mGameOver = false;

		mKills = mStreak = 0;

		mPaint = new Paint();
		mPaint.setColor(Color.WHITE);
		mPaint.setAntiAlias(true);
		mPaint.setTypeface(Typeface.createFromAsset(mContext.getAssets(),
				"fonts/samplefont.ttf"));
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
			ParticleSystem s = mSystems.get(i);
			s.update();
			if (s.isDead()) {
				mSystems.remove(i);
			}
		}

		for (int i = mEnemies.size() - 1; i >= 0; --i) {
			Enemy e = mEnemies.get(i);
			e.update();
			if (e.isDead()) {
				mEnemies.remove(i);
				mKills++;
				mStreak++;
			}
		}

		if (mPlayerShip.isDead()) {
			mGameOver = true;
			mStreak = 0;
		} else {
			mPlayerShip.update();
		}
	}

	public int getStreak() {
		return mStreak;
	}
	
	public int getKills() {
		return mKills;
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
