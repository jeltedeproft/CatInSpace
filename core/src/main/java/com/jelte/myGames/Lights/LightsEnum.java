package com.jelte.myGames.Lights;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import lombok.ToString;

@ToString
public enum LightsEnum {
	SMALL_ROUND_LIGHT("light1"),
	BIG_ROUND_LIGHT("light2"),
	MEDIUM_ROUND_LIGHT("light3"),
	CONE_LIGHT("light4"),
	BRIGHT_ROUND_LIGHT("light5"),
	ELLIPSE_LIGHT("light6"),
	DIM_ROUND_LIGHT("light7"),
	SMOKE_LIGHT("light8"),
	STATIC_LIGHT("light9"),
	REALLY_BIG_ROUND("light9");

	private static final List<LightsEnum> TYPES = Collections.unmodifiableList(Arrays.asList(LightsEnum.values()));
	private static final int SIZE = TYPES.size();
	private static final Random random = new Random();

	private String lightName;

	public String getLightName() {
		return lightName;
	}

	LightsEnum(final String lightName) {
		this.lightName = lightName;
	}

	public static LightsEnum randomLightType() {
		return TYPES.get(random.nextInt(SIZE));
	}
}
