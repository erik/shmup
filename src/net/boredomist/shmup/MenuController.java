package net.boredomist.shmup;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;

public class MenuController extends Controller {
	private int x, y, xv, yv;
	private Paint mPaint;
	
	public MenuController(GameThread thread) {
		super(thread);
		
		mPaint = new Paint();
		mPaint.setAntiAlias(true);
		mPaint.setColor(Color.WHITE);
		
		mState = GameState.MENU;
		x = y = 100;
		xv = (int)(Math.random() * 35);
		yv = (int)(Math.random() * 35);
	}
	
	public void update() {
		if(x <= 0 || x >= mWidth) {
			xv = -xv;
		}
		if(y < 0 || y > mHeight) {
			yv = -yv;
		}
		x += xv;
		y += yv;
	}

	public void draw(Canvas canvas) {
		canvas.drawColor(Color.BLACK);
		canvas.drawCircle(x,  y, 20, mPaint);
	}

	public Bundle saveState(Bundle b) {
		// TODO Auto-generated method stub
		return null;
	}

	public Bundle restoreState(Bundle b) {
		// TODO Auto-generated method stub
		return null;
	}

}
