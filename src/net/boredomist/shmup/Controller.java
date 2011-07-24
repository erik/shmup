package net.boredomist.shmup;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Bundle;

public abstract class Controller {
	protected int mWidth, mHeight;
	protected Difficulty mDifficulty;
	protected Context mContext;
	protected Input mInput;
	
	public GameState mState;

	public Controller(GameThread thread) {
		mContext = thread.getContext();
		mDifficulty = Difficulty.NONE;
		mWidth = thread.getWidth();
		mHeight = thread.getHeight();
		mInput = thread.getInput();
	}
	
	public abstract void update();

	public abstract void draw(Canvas canvas);

	public abstract Bundle saveState(Bundle b);

	public abstract Bundle restoreState(Bundle b);
	
	public void setSize(int w, int h) {
		mWidth = w;
		mHeight = h;
	}
	
	public Context getContext() {
		return mContext;
	}
	
	public Input getInput() {
		return mInput;
	}
	
	public int getWidth() {
		return mWidth;
	}
	
	public int getHeight() {
		return mHeight;
	}
	
	public void setGameState(GameState s) {
		mState = s;		
	}
	
	public GameState getGameState() {
		return mState;		
	}
	
	public void setDifficulty(Difficulty d) {
		mDifficulty = d;
	}
	
	public Difficulty getDifficulty() {
		return mDifficulty;
	}
	
}
