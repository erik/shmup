package net.boredomist.shmup.game;

import net.boredomist.shmup.gui.GameState;
import net.boredomist.shmup.gui.GameThread;
import net.boredomist.shmup.gui.Input;
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
	
	public abstract void draw(Canvas canvas);

	public Context getContext() {
		return mContext;
	}

	public Difficulty getDifficulty() {
		return mDifficulty;
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
	
	public int getWidth() {
		return mWidth;
	}
	
	public abstract Bundle restoreState(Bundle b);
	
	public abstract Bundle saveState(Bundle b);
	
	public void setDifficulty(Difficulty d) {
		mDifficulty = d;
	}
	
	public void setGameState(GameState s) {
		mState = s;		
	}
	
	public void setSize(int w, int h) {
		mWidth = w;
		mHeight = h;
	}
	
	public abstract void update();
	
}
