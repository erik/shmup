package net.boredomist.shmup.game;

public class Point {
	public float X, Y;
	
	public Point(float x, float y) {
		setXY(x, y);
	}
	
	public void setXY(float x, float y) {
		X = x;
		Y = y;
	}
}
