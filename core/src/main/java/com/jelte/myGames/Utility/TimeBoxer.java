package com.jelte.myGames.Utility;

public class TimeBoxer {
	private static final float CYCLE_TIME = 0.2f;

	public static int getCycleNumber(float timePassed) {
		return (int) (timePassed / CYCLE_TIME);// casting discards everything after the dot.
	}

	private TimeBoxer() {
		throw new IllegalStateException("Utility class");
	}

}
