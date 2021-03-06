package net.boredomist.shmup.gui;

import android.hardware.SensorEvent;

public class Input {
	private float mAccelX;
	private float mAccelY;
	private float mAccelZ;

	private int mTouchX;
	private int mTouchY;
	private boolean mHasTouch;

	public Input() {
		mAccelX = 0.0f;
		mAccelY = 0.0f;
		mAccelZ = 0.0f;

		mTouchX = -1;
		mTouchY = -1;

		mHasTouch = false;
	}

	public float getAccelX() {
		return mAccelX;
	}

	public float getAccelY() {
		return mAccelY;
	}

	public float getAccelZ() {
		return mAccelZ;
	}

	public int getTouchX() {
		return mTouchX;
	}

	public int getTouchY() {
		return mTouchY;
	}

	public boolean hasTouch() {
		return mHasTouch;
	}

	public void setAccel(SensorEvent event) {
		mAccelX = -event.values[0];
		mAccelY = event.values[1];
		mAccelZ = event.values[2];
	}

	public void setHasTouch(boolean b) {
		mHasTouch = b;
	}

	public void setTouch(int x, int y) {
		mTouchX = x;
		mTouchY = y;
		mHasTouch = true;
	}

}
