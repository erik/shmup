package net.boredomist.shmup.gui;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

public class GameView extends SurfaceView implements SurfaceHolder.Callback,
		SensorEventListener, View.OnTouchListener {
	private GameThread thread;
	private Sensor mAccelerometer;
	private SensorManager mSensorManager;

	public GameView(Context ctx, AttributeSet attrs) {
		super(ctx, attrs);

		mSensorManager = (SensorManager) ctx
				.getSystemService(Context.SENSOR_SERVICE);
		mAccelerometer = mSensorManager
				.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

		thread = new GameThread(getHolder(), getContext());
		thread.setGameState(GameState.MENU);

		this.setOnTouchListener(this);

		getHolder().addCallback(this);
		this.setFocusable(true);
	}

	public GameThread getThread() {
		return thread;
	}

	public void onAccuracyChanged(Sensor event, int arg1) {
		return;
	}

	public void onSensorChanged(SensorEvent event) {
		if (event.sensor.getType() != Sensor.TYPE_ACCELEROMETER) {
			return;
		} else {
			thread.getInput().setAccel(event);
		}

	}

	public boolean onTouch(View view, MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_UP) {
			thread.getInput().setHasTouch(false);
			return false;
		} else {
			int x = (int) event.getX();
			int y = (int) event.getY();
			thread.getInput().setTouch(x, y);
		}
		return true;
	}

	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int heigth) {
		thread.setSurfaceSize(width, heigth);
	}

	public void surfaceCreated(SurfaceHolder holder) {
		mSensorManager.registerListener(this, mAccelerometer,
				SensorManager.SENSOR_DELAY_GAME);

		thread.setRunning(true);
		try {
			thread.start();
		} catch (IllegalThreadStateException e) {
			thread = new GameThread(getHolder(), getContext());
			thread.setRunning(true);
			thread.start();
		}
	}

	public void surfaceDestroyed(SurfaceHolder holder) {
		mSensorManager.unregisterListener(this);
		thread.setRunning(false);
		boolean retry = true;
		while (retry) {
			try {
				thread.join();
				retry = false;
			} catch (InterruptedException e) {
			}
		}
	}
}