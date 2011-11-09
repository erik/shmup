package net.boredomist.shmup.game;

import net.boredomist.shmup.R;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

public class ScrollingBackground {

	private Point mPosition;
	private Point mSize;

	private Bitmap mBackground;

	public ScrollingBackground(Controller cont) {

		mPosition = new Point(0, 0);
		mSize = new Point(cont.getWidth(), cont.getHeight());

		Bitmap tmp = BitmapFactory.decodeResource(cont.getContext()
				.getResources(), R.drawable.background1);

		mBackground = Bitmap.createScaledBitmap(tmp, cont.getWidth(),
				cont.getHeight(), false);

		tmp.recycle();
	}

	public void draw(Canvas canvas) {
		if (mPosition.Y != 0) {
			canvas.drawBitmap(mBackground, mPosition.X, mPosition.Y
					- mBackground.getHeight(), null);
		}

		canvas.drawBitmap(mBackground, mPosition.X, mPosition.Y, null);
	}

	public void update() {

		mPosition.Y = mPosition.Y + 2;

		if (mPosition.Y >= mSize.Y) {
			mPosition.Y = 0;
		}

	}
}
