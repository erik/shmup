package net.boredomist.shmup;

public enum Gun {
	MISSILE, AUTOMISSILE, MULTISHOT, DEFAULT;

	public int getCooldownPeriod() {
		switch (this) {
		case DEFAULT:
			return 4;
		case MISSILE:
			return 20;
		case MULTISHOT:
			return 10;
		case AUTOMISSILE:
			return 10;
		}
		return 0;

	}
}
