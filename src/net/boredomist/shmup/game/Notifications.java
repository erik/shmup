package net.boredomist.shmup.game;

import java.util.ArrayList;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

class Notification {
	public String message;
	public int ticks;
	public boolean flash;

	public Notification(String message, int ticks, boolean flash) {
		this.message = message;
		this.ticks = ticks;
		this.flash = flash;
	}
}

public class Notifications {
	private GameWorld mWorld;
	private Paint mPaint;
	private ArrayList<Notification> mNotifications;
	private int mTicks;
	private boolean mFlash;

	public Notifications(GameWorld world) {
		mWorld = world;

		mFlash = false;

		mTicks = 0;

		mNotifications = new ArrayList<Notification>();

		mPaint = new Paint();
		mPaint.setTypeface(mWorld.mTypeface);
		mPaint.setTextSize(32);
		mPaint.setTextAlign(Paint.Align.CENTER);
	}

	public void addNotification(String message, int ticks, boolean flash) {
		mNotifications.add(new Notification(message, ticks, flash));
	}

	public void draw(Canvas canvas) {
		int w = mWorld.getWidth(), h = mWorld.getHeight(), x = -mNotifications
				.size() / 2;
		for (Notification n : mNotifications) {
			mPaint.setColor(Color.WHITE);
			if (n.flash && mFlash) {
				mPaint.setColor(Color.RED);
			}
			canvas.drawText(n.message, w / 2, h / 2 + x * 32, mPaint);

			x++;
		}
	}

	public void update() {
		mTicks++;

		if (mTicks % 15 == 0) {
			mFlash = !mFlash;
		}

		for (int i = mNotifications.size() - 1; i >= 0; --i) {
			Notification not = mNotifications.get(i);
			if (--not.ticks <= 0) {
				mNotifications.remove(i);
			}

		}
	}
}