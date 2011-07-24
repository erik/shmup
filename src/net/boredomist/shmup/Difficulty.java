package net.boredomist.shmup;


public enum Difficulty {
	EASY, MEDIUM, HARD, LUDICROUS, NONE;

	public static Difficulty fromInt(int val) {
		switch (val) {
		case 0:
			return EASY;
		case 1:
			return MEDIUM;
		case 2:
			return HARD;
		case 3:
			return LUDICROUS;
		}
		return null;
	}

}
