package net.boredomist.shmup.gui;

import net.boredomist.shmup.game.Controller;
import net.boredomist.shmup.game.Difficulty;
import android.content.Context;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.Process;
import android.util.Log;
import android.view.SurfaceHolder;

public class GameThread extends Thread {

	public static final float TIME_STEP = 1.0f / 30.0f;

	private GameState mState;
	private int mWidth, mHeight;

	private Controller mController;

	private SurfaceHolder mSurfaceHolder;
	private Context mContext;
	private boolean mRun = false;

	private Input mInput;

	private boolean mCreated;

	public GameThread(SurfaceHolder surfaceHolder, Context context) {
		mSurfaceHolder = surfaceHolder;
		mContext = context;
		mState = GameState.MENU;
		mInput = new Input();

		mController = null;
		mCreated = false;
	}

	public void endGame() {
		setGameState(GameState.MENU);
		mController = new MenuController(this);
	}

	public Context getContext() {
		return mContext;
	}

	public GameState getGameState() {
		return mState;
	}

	public int getHeight() {
		return mHeight;
	}

	public Input getInput() {
		return mInput;
	}

	public SurfaceHolder getSurfaceHolder() {
		return mSurfaceHolder;
	}

	public int getWidth() {
		return mWidth;
	}

	public synchronized void restoreState(Bundle savedState) {
		synchronized (mSurfaceHolder) {
			setGameState(GameState.PAUSE);
			mRun = true;

			mController.saveState(savedState);
		}
	}

	@Override
	public void run() {
		Process.setThreadPriority(Process.THREAD_PRIORITY_URGENT_DISPLAY);
		Canvas c;
		double nextTick = System.currentTimeMillis();
		while (mRun) {
			try {
				// clamp to 35fps
				Thread.sleep(1000 / 35);
			} catch (InterruptedException e) {
				Log.d("SHM", "Thread interrupted: " + e);
			}

			if (!mCreated) {
				continue;
			}

			final int MAX_FRAMESKIP = 10;
			int loops = 0;
			while (System.currentTimeMillis() > nextTick
					&& loops++ < MAX_FRAMESKIP) {
				mController.update();
				nextTick += 1000 / 30.0;
			}

			c = null;
			try {
				c = mSurfaceHolder.lockCanvas(null);
				synchronized (mSurfaceHolder) {
					mController.draw(c);
				}
			} finally {
				if (c != null) {
					mSurfaceHolder.unlockCanvasAndPost(c);
				}
			}
		}
	}

	public synchronized Bundle saveState(Bundle map) {
		synchronized (mSurfaceHolder) {
			setGameState(GameState.PAUSE);

			mController.saveState(map);
		}
		return map;
	}

	public void setGameState(GameState s) {
		if (mController != null) {
			mController.setGameState(s);
		}
		mState = s;
	}

	public void setRunning(boolean run) {
		mRun = run;
	}

	public void setSurfaceSize(int w, int h) {
		mWidth = w;
		mHeight = h;

		mController = new MenuController(this);
		mController.setGameState(mState);
		mCreated = true;
	}

	public void startGame(Difficulty diff) {
		setGameState(GameState.RUNNING);
		mController = new GameController(this, diff);

		mRun = true;
	}
}
