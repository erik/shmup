package net.boredomist.shmup;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;

public class MenuController extends Controller {
	private Paint mPaint;
	private Typeface mFont;
	private GameWorld mWorld;
	private ScrollingBackground mBackground;
	
	public MenuController(GameThread thread) {
		super(thread);
		
		mPaint = new Paint();
		mPaint.setAntiAlias(true);
		mPaint.setColor(Color.WHITE);

		mPaint.setTextAlign(Paint.Align.CENTER);

		mFont = Typeface.createFromAsset(mContext.getAssets(),
				"fonts/samplefont.ttf");
		mPaint.setTypeface(mFont);
		mPaint.setTextSize(64);
		
		mWorld = new MenuWorld(this);
		
		mBackground = new ScrollingBackground(this);
		
		mState = GameState.MENU;
	}
	
	public void update() {
		mWorld.update();
		mBackground.update();
	}

	public void draw(Canvas canvas) {
		mBackground.draw(canvas);
		mWorld.draw(canvas);
		canvas.drawText("Shmup", getWidth() / 2, getHeight() / 2, mPaint);
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
