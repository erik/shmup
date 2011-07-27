package net.boredomist.shmup;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

public class Particle {
	public int color;
	private int life;
	private int size;
	private int x, y;
	private int xv, yv;
	private boolean dead;
	private int dr;
	private int dg;
	private int db;

	public Particle(int x, int y, int xv, int yv, int life, int size,
			int start, int end) {
		this.x = x;
		this.y = y;
		this.life = life;
		this.size = size;
		this.xv = xv;
		this.yv = yv;
		
		this.color = Color.rgb(start >> 16 & 0xFF, start >> 8 & 0xFF,
				start & 0xFF);

		dr = (int)(((end >> 16 & 0xFF) - (color >> 16 & 0xFF)) / ((life == 0) ? .001 : life));
		dg = (int)(((end >> 8 & 0xFF) - (color >> 8 & 0xFF)) / ((life == 0) ? .001 : life));
		db = (int)(((end & 0xFF) - (color & 0xFF)) / ((life == 0) ? .001 : life));
		
		this.dead = false;

	}

	public boolean isDead() {
		return dead;
	}

	public void update() {
		if (--life <= 0) {
			dead = true;
			return;
		}
		x += xv;
		y += yv;
 
		int cr = (color >> 16 & 0xFF) + dr;
		int cg = (color >> 8 & 0xFF) + dg;
		int cb = (color & 0xFF) + db;

		color = Color.rgb(cr, cg, cb); 
	}

	public void draw(Canvas canvas, Paint paint) {
		paint.setColor(color);
		canvas.drawRect(x, y, x + size, y + size, paint);
	}
}
