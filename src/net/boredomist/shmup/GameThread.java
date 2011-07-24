package net.boredomist.shmup;

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

	public GameThread(SurfaceHolder surfaceHolder, Context context) {
		mSurfaceHolder = surfaceHolder;
		mContext = context;
		mState = GameState.MENU;
		mInput = new Input();
		
		mController = new MenuController(this);
	}

	public Input getInput() {
		return mInput;
	}

	public void setSurfaceSize(int w, int h) {
		mController.setSize(w, h);

		mWidth = w;
		mHeight = h;
	}

	public void setGameState(GameState s) {
		mController.setGameState(s);
		mState = s;
	}

	public GameState getGameState() {
		return mState;
	}

	public void setRunning(boolean run) {
		mRun = run;
	}

	public SurfaceHolder getSurfaceHolder() {
		return mSurfaceHolder;
	}

	@Override
	public void run() {
		Process.setThreadPriority(Process.THREAD_PRIORITY_URGENT_DISPLAY);
		Canvas c;
		double nextTick = System.currentTimeMillis();
		while (mRun) {

			try {
				// clamp to 30fps
				Thread.sleep(1000 / 30);
			} catch (InterruptedException e) {
				Log.d("SHM", "Thread interrupted: " + e);
			}

			final int MAX_FRAMESKIP = 10;
			int loops = 0;
			while ((double) System.currentTimeMillis() > nextTick
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

	public synchronized void restoreState(Bundle savedState) {
		synchronized (mSurfaceHolder) {
			setGameState(GameState.PAUSE);
			mRun = true;

			mController.saveState(savedState);
		}
	}

	public void startGame(Difficulty diff) {
		setGameState(GameState.RUNNING);
		mController = new GameController(this, diff);

		mRun = true;
	}

	public void endGame() {
		setGameState(GameState.MENU);
		mController = new MenuController(this);
	}

	public Context getContext() {
		return mContext;
	}

	public int getWidth() {
		return mWidth;
	}

	public int getHeight() {
		return mHeight;
	}
}
