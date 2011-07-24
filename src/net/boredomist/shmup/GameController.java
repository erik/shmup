package net.boredomist.shmup;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;

public class GameController extends Controller {

	private static final String KEY_DIFFICULTY = "mDifficulty";
	private static final String KEY_X = "mX";
	private static final String KEY_Y = "mY";
	private static final String KEY_DX = "mDX";
	private static final String KEY_DY = "mDY";
	
	private Paint mPaint;
	private Typeface mFont;
		
	private GameWorld mWorld;
	private ScrollingBackground mBackground;
	
	public GameController(GameThread thread, Difficulty d) {
		super(thread);
				
		mDifficulty = d;
		mPaint = new Paint();
		
		mPaint.setAntiAlias(true);
		mPaint.setColor(Color.WHITE);
		mPaint.setTextAlign(Paint.Align.CENTER);

		mFont = Typeface.createFromAsset(mContext.getAssets(),
				"fonts/samplefont.ttf");
		mPaint.setTypeface(mFont);
		mPaint.setTextSize(64);
		
		mWorld = new GameWorld(this);
		
		mBackground = new ScrollingBackground(this);
		
	}
	
	public void update() {
		mWorld.update();
		mBackground.update();
	}

	public void draw(Canvas canvas) {	
		mBackground.draw(canvas);
		mWorld.draw(canvas);
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
