package net.boredomist.shmup.gui;

import net.boredomist.shmup.game.Controller;
import net.boredomist.shmup.game.GameWorld;
import net.boredomist.shmup.game.ScrollingBackground;
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

	@Override
	public void draw(Canvas canvas) {
		mBackground.draw(canvas);
		mWorld.draw(canvas);
		canvas.drawText("Shmup", getWidth() / 2, getHeight() / 2, mPaint);
	}

	@Override
	public Bundle restoreState(Bundle b) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Bundle saveState(Bundle b) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update() {
		mWorld.update();
		mBackground.update();
	}

}
